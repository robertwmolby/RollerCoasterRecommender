package app.molby.rcrecommender;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.model.HttpApiV2ProxyRequest;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class StreamLambdaHandler implements RequestStreamHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SpringBootLambdaContainerHandler<HttpApiV2ProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            loadSecretsIntoSystemProperties();

            handler = SpringBootLambdaContainerHandler
                    .getHttpApiV2ProxyHandler(RollerCoasterRecommenderApplication.class);

        } catch (ContainerInitializationException e) {
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    private static void loadSecretsIntoSystemProperties() {
        String secretArn = System.getenv("DB_SECRET_ARN");
        if (secretArn == null || secretArn.isBlank()) {
            System.err.println("DB_SECRET_ARN is not set; datasource properties will be missing.");
            return;
        }

        String regionEnv = System.getenv("AWS_REGION");
        Region region = (regionEnv == null || regionEnv.isBlank()) ? Region.US_EAST_2 : Region.of(regionEnv);

        try (SecretsManagerClient client = SecretsManagerClient.builder().region(region).build()) {
            String json = client.getSecretValue(GetSecretValueRequest.builder()
                    .secretId(secretArn)
                    .build()).secretString();

            Map<String, String> props = MAPPER.readValue(json, new TypeReference<>() {});
            props.forEach((k, v) -> {
                if (k != null && v != null) System.setProperty(k, v);
            });

            // Don’t print secrets; just confirm presence of key config
            System.out.println("Secrets loaded. spring.datasource.url present = " +
                    (System.getProperty("spring.datasource.url") != null));

        } catch (Exception e) {
            throw new RuntimeException("Failed to load secret from Secrets Manager: " + secretArn, e);
        }
    }

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        handler.proxyStream(input, output, context);
    }
}
