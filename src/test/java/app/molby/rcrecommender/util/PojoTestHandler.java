package app.molby.rcrecommender.util;

import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

/**
 * Utility for testing simple POJOs using reflection. This does general
 * POJO unit tests for ones created by Lombok and following its general
 * design. It handles Lombok's usage of hashCode, equals, toString, empty
 * argument constructors, and all-argument constructors.
 *
 * Notes / limitations:
 * <ul>
 *     <li>Map-typed fields are treated as collection-like but are currently
 *     NOT supported by this utility. They are skipped and a warning is logged.</li>
 *     <li>It does NOT handle tests of identity that are based on only a subset
 *     of properties. If you have classes whose equals/hashCode only consider
 *     some fields, those need explicit tests.</li>
 * </ul>
 *
 * Usage inside a POJO test:
 *
 * <pre>
 * &#64;Test
 * void validatePojo() throws Exception {
 *     PojoTestHandler.assertPojoContract(SomePojo.class);
 * }
 * </pre>
 *
 * Verifies:
 *  - values set via setters are returned via getters
 *  - constructor parameters are exposed via getters (for the "largest" constructor)
 *  - object equals itself
 *  - two identically-populated objects are equal and have same hash code
 *  - toString contains all property names and values
 *  - for Collection fields: multiple elements are created and returned as expected
 *  - for Collection fields annotated with Lombok @ToString.Exclude or
 *    @EqualsAndHashCode.Exclude, they are ignored for toString checks and not
 *    relied upon for equality expectations
 *
 * @author Bob Molby
 */
public final class PojoTestHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PojoTestHandler.class);

    /**
     * Maximum depth for recursively populating nested complex types.
     * Prevents infinite recursion on cyclic graphs (A -> B -> A, etc.).
     */
    private static final int MAX_COMPLEX_TYPE_DEPTH = 2;

    private PojoTestHandler() {
    }

    /**
     * Verify POJO standard handling as described in class details.
     *
     * @param clazz Name of POJO class being validated
     * @param <T>   Type of POJO class being validated
     * @throws Exception unexpected exception or assertion failure
     */
    public static <T> void assertPojoContract(Class<T> clazz) throws Exception {
        // --- Create two instances using no-arg constructor ---
        Constructor<T> noArgumentConstructor = clazz.getDeclaredConstructor();
        noArgumentConstructor.setAccessible(true);
        T instance1 = noArgumentConstructor.newInstance();
        T instance2 = noArgumentConstructor.newInstance();

        // Track fields + values actually exercised (used for toString verification)
        Map<String, Object> fieldValues = new LinkedHashMap<>();

        Field[] declaredFields = clazz.getDeclaredFields();

        // --- For each non-static field, exercise setter/getter ---
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            String fieldName = field.getName();
            Class<?> fieldType = field.getType();

            // Explicitly skip Map-like fields for now
            if (Map.class.isAssignableFrom(fieldType)) {
                LOGGER.warn("Skipping Map-typed field {} of type {} (not supported)", fieldName, fieldType.getName());
                continue;
            }

            boolean isCollection = Collection.class.isAssignableFrom(fieldType);
            boolean toStringExcluded =
                    isCollection && hasLombokToStringExclude(field);
            boolean equalsExcluded =
                    isCollection && hasLombokEqualsAndHashCodeExclude(field);

            Object sampleValue;
            if (isCollection) {
                // For collections, we want multiple elements and correct element type
                sampleValue = createSampleCollectionValue(field, fieldName);
            } else {
                sampleValue = createSampleValue(fieldType, fieldName);
            }

            if (sampleValue == null) {
                // Skip unsupported types
                LOGGER.warn("Skipping unsupported type {} for field {}", fieldType, fieldName);
                continue;
            }

            String capitalized = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            String setterName = "set" + capitalized;
            String getterName =
                    (fieldType == boolean.class || fieldType == Boolean.class)
                            ? "is" + capitalized
                            : "get" + capitalized;

            try {
                Method setter = clazz.getMethod(setterName, fieldType);
                Method getter = clazz.getMethod(getterName);

                // instance1: set value and assert getter returns it
                setter.invoke(instance1, sampleValue);
                Object getterResult = getter.invoke(instance1);

                Assertions.assertNotNull(getterResult,
                        "Getter for " + fieldName + " should not return null after setter call");
                Assertions.assertEquals(sampleValue, getterResult,
                        "Getter for " + fieldName + " should return value set by setter");

                // If this is a collection, verify that multiple elements are present
                if (isCollection) {
                    assertCollectionRoundTrip(fieldName, (Collection<?>) sampleValue, getterResult);
                }

                // instance2: set same value for equals/hashCode tests later on
                setter.invoke(instance2, sampleValue);

                // Only include in toString assertions if not marked with Lombok @ToString.Exclude
                if (!toStringExcluded) {
                    fieldValues.put(fieldName, sampleValue);
                }

                // equalsExcluded is informational here; we still populate to match
                // Lombok behavior where those fields are excluded from equals/hashCode.

            } catch (NoSuchMethodException ignored) {
                // No standard getter/setter -> skip this field
                LOGGER.warn("Skipping field {} of type {}: no standard getter/setter", fieldName, fieldType);
            }
        }

        // --- Constructor test (largest-arity constructor) ---
        // Assumes the "all-args" constructor parameter order matches declared fields.
        Optional<Constructor<?>> optConstructorsWithArguments = Arrays.stream(clazz.getDeclaredConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount));

        if (optConstructorsWithArguments.isPresent()
                && optConstructorsWithArguments.get().getParameterCount() > 0) {

            Constructor<?> constructorWithArguments = optConstructorsWithArguments.get();
            constructorWithArguments.setAccessible(true);
            Class<?>[] parameterTypes = constructorWithArguments.getParameterTypes();
            Object[] constructorArguments = new Object[parameterTypes.length];

            for (int parameterNumber = 0; parameterNumber < parameterTypes.length; parameterNumber++) {
                Class<?> paramType = parameterTypes[parameterNumber];

                Object argumentValue;
                if (Map.class.isAssignableFrom(paramType)) {
                    LOGGER.warn("Skipping Map parameter at index {} in constructor {}", parameterNumber,
                            constructorWithArguments.toString());
                    argumentValue = null;
                } else if (Collection.class.isAssignableFrom(paramType) && parameterNumber < declaredFields.length) {
                    // Use field's generic info to build collection argument
                    argumentValue = createSampleCollectionValue(declaredFields[parameterNumber], declaredFields[parameterNumber].getName());
                } else {
                    String guessedFieldName = parameterNumber < declaredFields.length
                            ? declaredFields[parameterNumber].getName()
                            : "param" + parameterNumber;
                    argumentValue = createSampleValue(paramType, guessedFieldName);
                }
                constructorArguments[parameterNumber] = argumentValue;
            }

            @SuppressWarnings("unchecked")
            T constructed = (T) constructorWithArguments.newInstance(constructorArguments);

            // Validate that getters reflect constructor-injected values (best-effort guess by order)
            for (int i = 0; i < Math.min(parameterTypes.length, declaredFields.length); i++) {
                Field field = declaredFields[i];
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();

                if (Map.class.isAssignableFrom(fieldType)) {
                    LOGGER.warn("Skipping constructor validation for Map-typed field {} of type {}",
                            fieldName, fieldType.getName());
                    continue;
                }

                String capitalized = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                String getterName =
                        (fieldType == boolean.class || fieldType == Boolean.class)
                                ? "is" + capitalized
                                : "get" + capitalized;

                try {
                    Method getter = clazz.getMethod(getterName);
                    Object getterResult = getter.invoke(constructed);
                    Assertions.assertEquals(constructorArguments[i], getterResult,
                            "Getter for " + fieldName + " should expose constructor argument");
                } catch (NoSuchMethodException ignored) {
                    // If there's no getter, skip
                    LOGGER.warn("Skipping constructor validation for field {} of type {}: no getter",
                            fieldName, fieldType);
                }
            }
        }

        // --- equals / hashCode tests ---
        // equality with itself
        Assertions.assertEquals(instance1, instance1, "Object should be equal to itself");
        Assertions.assertEquals(instance1.hashCode(), instance1.hashCode(),
                "hashCode should be stable for the same object");

        // equality with another instance with same values
        Assertions.assertEquals(instance1, instance2,
                "Two instances with the same field values should be equal");
        Assertions.assertEquals(instance1.hashCode(), instance2.hashCode(),
                "hashCode should match for equal objects");

        // --- toString test: contains all field names and their values ---
        String toStringValue = instance1.toString();
        for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            String valueStr = String.valueOf(value);

            Assertions.assertTrue(
                    toStringValue.contains(fieldName),
                    "toString() should contain field name: " + fieldName
            );
            Assertions.assertTrue(
                    toStringValue.contains(valueStr),
                    "toString() should contain value for " + fieldName + ": " + valueStr
            );
        }
    }

    /**
     * Checks that a collection survives the setter/getter round-trip with all entries intact.
     */
    private static void assertCollectionRoundTrip(String fieldName,
                                                  Collection<?> expectedCollection,
                                                  Object getterResult) {
        Assertions.assertTrue(getterResult instanceof Collection,
                "Getter for " + fieldName + " should return a Collection");
        @SuppressWarnings("unchecked")
        Collection<Object> returned = (Collection<Object>) getterResult;

        Assertions.assertEquals(expectedCollection.size(), returned.size(),
                "Collection size mismatch for field " + fieldName);
        Assertions.assertTrue(returned.containsAll(expectedCollection),
                "Collection for field " + fieldName + " should contain all expected elements");
    }

    /**
     * Public entry for creating a sample value for non-collection, non-map types.
     */
    private static Object createSampleValue(Class<?> type, String fieldName) {
        return createSampleValue(type, fieldName, 0);
    }

    /**
     * Create a sample value for use in constructing objects (non-collection, non-map types).
     * This version tracks recursion depth for complex/nested types.
     *
     * @param type      Type of property
     * @param fieldName Name of property
     * @param depth     Current recursion depth for complex type population
     * @return Sample value to use in setter, getter, and construction.
     */
    private static Object createSampleValue(Class<?> type, String fieldName, int depth) {
        // Common/simple scalar types
        if (type == String.class) {
            return fieldName + "-value";
        }
        if (type == Long.class || type == long.class) {
            return 1L;
        }
        if (type == Integer.class || type == int.class) {
            return 1;
        }
        if (type == Double.class || type == double.class) {
            return 1.23d;
        }
        if (type == Float.class || type == float.class) {
            return 1.23f;
        }
        if (type == BigDecimal.class) {
            return new BigDecimal("4.75");
        }
        if (type == Boolean.class || type == boolean.class) {
            return Boolean.TRUE;
        }
        if (type == Instant.class) {
            return Instant.parse("2025-01-01T00:00:00Z");
        }
        if (Enum.class.isAssignableFrom(type)) {
            return type.getEnumConstants().length > 0 ? type.getEnumConstants()[0] : null;
        }
        if (Map.class.isAssignableFrom(type)) {
            LOGGER.warn("Requested sample value for Map type {}; returning null", type.getName());
            return null;
        }

        // Non-common reference types: attempt to create and populate fields.
        if (isPlainReferenceTypeNeedingPopulation(type) && depth < MAX_COMPLEX_TYPE_DEPTH) {
            Object populated = createAndPopulateComplexType(type, fieldName, depth);
            if (populated != null) {
                return populated;
            }
        }

        // Fallback: for other reference types, try to use no-arg constructor if present
        try {
            Constructor<?> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            // Final fallback: skip this field by returning null
            LOGGER.warn(
                    "Could not determine sample value to construct for class {}; field {} will be set to null",
                    type.getName(), fieldName
            );
            return null;
        }
    }

    /**
     * Creates and populates a complex (non-simple) reference type instance.
     *
     * For each non-static, non-collection, non-map field, a sample value is generated
     * and assigned via a standard setter if available.
     */
    private static Object createAndPopulateComplexType(Class<?> type, String baseName, int depth) {
        try {
            Constructor<?> ctor = type.getDeclaredConstructor();
            ctor.setAccessible(true);
            Object instance = ctor.newInstance();

            for (Field f : type.getDeclaredFields()) {
                if (Modifier.isStatic(f.getModifiers())) {
                    continue;
                }

                Class<?> ft = f.getType();
                String fname = f.getName();

                // Skip nested collections/maps to avoid deep graphs and recursion issues
                if (Collection.class.isAssignableFrom(ft) || Map.class.isAssignableFrom(ft)) {
                    LOGGER.warn("Skipping nested collection/map field {} on type {}", fname, type.getName());
                    continue;
                }

                // Avoid direct self-reference recursion (e.g., class A { A parent; })
                if (ft == type) {
                    LOGGER.warn("Skipping self-referential field {} on type {}", fname, type.getName());
                    continue;
                }

                Object fieldValue = createSampleValue(ft, baseName + "_" + fname, depth + 1);
                if (fieldValue == null) {
                    continue;
                }

                String cap = Character.toUpperCase(fname.charAt(0)) + fname.substring(1);
                String setterName = "set" + cap;

                try {
                    Method setter = type.getMethod(setterName, ft);
                    setter.invoke(instance, fieldValue);
                } catch (NoSuchMethodException e) {
                    LOGGER.warn("No setter {} on type {}; field {} will remain default",
                            setterName, type.getName(), fname);
                }
            }

            return instance;
        } catch (Exception e) {
            LOGGER.warn("Could not create/populate complex type {} for base {}; falling back to naive construction",
                    type.getName(), baseName);
            try {
                Constructor<?> ctor = type.getDeclaredConstructor();
                ctor.setAccessible(true);
                return ctor.newInstance();
            } catch (Exception ex) {
                LOGGER.warn("Could not even naively construct complex type {}; returning null", type.getName());
                return null;
            }
        }
    }

    /**
     * Creates a sample collection instance for a given field, including multiple elements
     * of the appropriate element type (when generics are available).
     *
     * For element types that are not simple scalars, this will create instances and attempt
     * to populate their scalar fields using the same sample-value logic.
     */
    private static Object createSampleCollectionValue(Field field, String fieldName) {
        Class<?> collectionType = field.getType();
        Type genericType = field.getGenericType();

        Collection<Object> collection = instantiateCollection(collectionType);
        if (collection == null) {
            return null;
        }

        // Determine element type from generics if possible
        Class<?> elementClass = String.class; // default fallback
        if (genericType instanceof ParameterizedType parameterizedType) {
            Type[] args = parameterizedType.getActualTypeArguments();
            if (args.length == 1 && args[0] instanceof Class<?> ec) {
                elementClass = ec;
            }
        }

        // Avoid degenerate recursion (collection element is same as collection type)
        if (elementClass == collectionType) {
            elementClass = String.class;
        }

        Object item1 = createSampleElement(elementClass, fieldName + "Item1");
        Object item2 = createSampleElement(elementClass, fieldName + "Item2");

        if (item1 != null) {
            collection.add(item1);
        }
        if (item2 != null) {
            collection.add(item2);
        }

        return collection;
    }

    /**
     * Create a sample element for inclusion in a collection. Delegates entirely
     * to {@link #createSampleValue(Class, String)} so that complex types get
     * the same population behavior as top-level fields.
     */
    private static Object createSampleElement(Class<?> elementClass, String baseName) {
        return createSampleValue(elementClass, baseName);
    }

    /**
     * Heuristic: types that are not primitives/wrappers/String/etc. are considered
     * "plain reference" types that might benefit from field population.
     */
    private static boolean isPlainReferenceTypeNeedingPopulation(Class<?> type) {
        if (type.isPrimitive()) return false;
        if (type == String.class ||
                type == Long.class || type == Integer.class ||
                type == Double.class || type == Float.class ||
                type == Boolean.class || type == BigDecimal.class ||
                type == Instant.class) {
            return false;
        }
        if (Enum.class.isAssignableFrom(type)) {
            return false;
        }
        return true;
    }

    /**
     * Instantiate a concrete collection given the declared type.
     *
     * Map types are NOT handled here and should be filtered out before calling this method.
     */
    private static Collection<Object> instantiateCollection(Class<?> collectionType) {
        // Try common interfaces first
        if (Set.class.isAssignableFrom(collectionType)) {
            return new LinkedHashSet<>();
        }
        else if (List.class.isAssignableFrom(collectionType)) {
            return new ArrayList<>();
        }
        else {
            LOGGER.warn("Collection property in class could not be instantiated since it was an unsupported type {}.",collectionType.getName());
        }

        // If it's a concrete class and not abstract, try to instantiate directly
        if (!collectionType.isInterface() && !Modifier.isAbstract(collectionType.getModifiers())) {
            try {
                Constructor<?> ctor = collectionType.getDeclaredConstructor();
                ctor.setAccessible(true);
                Object instance = ctor.newInstance();
                if (instance instanceof Collection) {
                    @SuppressWarnings("unchecked")
                    Collection<Object> coll = (Collection<Object>) instance;
                    return coll;
                }
            } catch (Exception e) {
                LOGGER.warn("Could not instantiate concrete collection type {}; defaulting to ArrayList",
                        collectionType.getName());
            }
        }


        // Fallback
        LOGGER.warn("Collection property could not be instanted for test due to invalid type or usage as " +
                "an interface or abstract class. Will ignore.");
        return null;
    }

    /**
     * Detects Lombok's @ToString.Exclude on a field based on annotation type name.
     */
    private static boolean hasLombokToStringExclude(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            String name = annotation.annotationType().getName();
            if ("lombok.ToString$Exclude".equals(name) || "lombok.ToString.Exclude".equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Detects Lombok's @EqualsAndHashCode.Exclude on a field based on annotation type name.
     */
    private static boolean hasLombokEqualsAndHashCodeExclude(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            String name = annotation.annotationType().getName();
            if ("lombok.EqualsAndHashCode$Exclude".equals(name)
                    || "lombok.EqualsAndHashCode.Exclude".equals(name)) {
                return true;
            }
        }
        return false;
    }
}
