package com.parisesoftware.sedan.data.impl;

import spock.lang.Specification

class MapSedanDataAdapterTest extends Specification {

    def "should adapt map correctly"() {
        given: 'a test map'
        def testMap = [stringKey: 'stringValue', intKey: 42, numberKey: 3.14, stringNumberKey: '123']

        when: 'creating an adapter'
        def adapter = new MapSedanDataAdapter(testMap)

        then: 'string values work correctly'
        adapter.getStringValue('stringKey') == 'stringValue'
        adapter.getStringValue('intKey') == '42'
        adapter.getStringValue('nonexistent') == null

        and: 'integer values work correctly'
        adapter.getIntegerValue('intKey') == 42
        adapter.getIntegerValue('numberKey') == 3  // Double to int conversion
        adapter.getIntegerValue('stringNumberKey') == 123  // String to int conversion
        adapter.getIntegerValue('nonexistent') == null

        and: 'other operations work correctly'
        adapter.hasValue('stringKey')
        !adapter.hasValue('nonexistent')
        adapter.getAllKeys() == testMap.keySet() as Set
        adapter.size() == 4
        !adapter.isEmpty()
    }

    def "should handle empty map"() {
        expect: 'empty map works'
        def emptyAdapter = new MapSedanDataAdapter([:])
        emptyAdapter.getStringValue('key') == null
        emptyAdapter.getIntegerValue('key') == null
        emptyAdapter.hasValue('key') == false
        emptyAdapter.getAllKeys().isEmpty()
        emptyAdapter.size() == 0
        emptyAdapter.isEmpty()
    }

    def "should throw exception for null map"() {
        when: 'creating an adapter with null'
        new MapSedanDataAdapter(null)

        then: 'an exception is thrown'
        thrown(NullPointerException)
    }

    def "should support map class"() {
        expect: 'supports Map and its subclasses'
        new MapSedanDataAdapter().supports(Map.class)
        new MapSedanDataAdapter().supports(java.util.HashMap.class)
        !new MapSedanDataAdapter().supports(String.class)
    }

    def "should adapt maps correctly"() {
        given: 'an adapter instance'
        def adapter = new MapSedanDataAdapter()
        def testMap = [key: 'value']

        when: 'adapting a map'
        def result = adapter.adapt(testMap)

        then: 'the result is a working SedanDataSource'
        result.getStringValue('key') == 'value'
        result.hasValue('key')
        result.size() == 1
    }
}