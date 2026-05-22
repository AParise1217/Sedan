package com.parisesoftware.sedan.data;

import spock.lang.Specification

class SedanDataFactoryTest extends Specification {

    def "should adapt Map to SedanDataSource"() {
        given: 'a test map'
        def testMap = [key1: 'value1', key2: 42, key3: '123']

        when: 'adapting the map'
        def result = SedanDataFactory.construct(testMap)

        then: 'returns an adapter that behaves like the original map'
        result.getStringValue('key1') == 'value1'
        result.getIntegerValue('key2') == 42
        result.getIntegerValue('key3') == 123  // String to Integer conversion
        result.hasValue('key1')
        !result.hasValue('nonexistent')
        result.getAllKeys() == testMap.keySet() as Set
        result.size() == 3
        !result.isEmpty()
    }

    def "should handle edge cases"() {
        expect: 'empty map works'
        def emptyResult = SedanDataFactory.construct([:])
        emptyResult.getAllKeys().isEmpty()
        emptyResult.size() == 0
        emptyResult.isEmpty()
    }

    def "should handle null values correctly"() {
        given: 'a map with null values'
        def mapWithNull = [key: null]

        when: 'constructing an adapter'
        def adapterWithNull = SedanDataFactory.construct(mapWithNull)

        then: 'null values are handled correctly'
        adapterWithNull.getStringValue('key') == null
        adapterWithNull.getIntegerValue('key') == null
        adapterWithNull.hasValue('key')
    }

    def "should throw exception for null input"() {
        when: 'constructing with null'
        SedanDataFactory.construct(null)

        then: 'an exception is thrown'
        thrown(IllegalArgumentException)
    }

    def "should check adapter availability"() {
        expect: 'correct adapter availability'
        SedanDataFactory.canAdapt(Map.class)
        !SedanDataFactory.canAdapt(String.class)
    }

    def "should allow custom adapter registration"() {
        given: 'a custom adapter for strings'
        def stringAdapter = new SedanDataAdapter<String>() {
            @Override
            boolean supports(Class<?> sourceType) {
                return String.class.isAssignableFrom(sourceType)
            }

            @Override
            SedanDataSource adapt(String source) {
                return new SedanDataSource() {
                    @Override
                    String getStringValue(String key) {
                        return key.equals("value") ? source : null
                    }

                    @Override
                    Integer getIntegerValue(String key) {
                        def value = getStringValue(key)
                        try {
                            return value != null ? Integer.parseInt(value) : null
                        } catch (NumberFormatException e) {
                            return null
                        }
                    }

                    @Override
                    boolean hasValue(String key) {
                        return getStringValue(key) != null
                    }

                    @Override
                    Set<String> getAllKeys() {
                        return ["value"]
                    }

                    @Override
                    int size() {
                        return 1
                    }

                    @Override
                    boolean isEmpty() {
                        return source == null || source.isEmpty()
                    }
                }
            }
        }

        when: 'registering the adapter'
        SedanDataFactory.getRegistry().registerAdapter(stringAdapter)

        then: 'the factory can now adapt strings'
        SedanDataFactory.canAdapt(String.class)

        when: 'adapting a string'
        def result = SedanDataFactory.construct("test")

        then: 'the adapter works correctly'
        result.getStringValue("value") == "test"
        result.getIntegerValue("value") == null
        result.hasValue("value")
        result.getAllKeys() == ["value"] as Set
        result.size() == 1
        !result.isEmpty()
    }
}