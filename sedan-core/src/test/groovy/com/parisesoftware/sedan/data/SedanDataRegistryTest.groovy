package com.parisesoftware.sedan.data;

import com.parisesoftware.sedan.data.impl.MapSedanDataAdapter;
import spock.lang.Specification

class SedanDataRegistryTest extends Specification {

    def "should register and use adapters"() {
        given: 'a registry with a map adapter'
        def registry = new SedanDataRegistry()
        registry.registerAdapter(new MapSedanDataAdapter())

        and: 'a test map'
        def testMap = [key1: 'value1', key2: 42]

        when: 'adapting the map'
        def result = registry.adapt(testMap)

        then: 'the result is a working SedanDataSource'
        result.getStringValue('key1') == 'value1'
        result.getIntegerValue('key2') == 42
        result.hasValue('key1')
        !result.hasValue('nonexistent')
        result.getAllKeys() == ['key1', 'key2'] as Set
        result.size() == 2
        !result.isEmpty()
    }

    def "should throw exception when no adapter is found"() {
        given: 'an empty registry'
        def registry = new SedanDataRegistry()

        when: 'adapting an unsupported type'
        registry.adapt("unsupported")

        then: 'an exception is thrown'
        thrown(IllegalArgumentException)
    }

    def "should check for adapter availability"() {
        given: 'a registry with a map adapter'
        def registry = new SedanDataRegistry()
        registry.registerAdapter(new MapSedanDataAdapter())

        expect: 'correct adapter availability'
        registry.hasAdapterFor(Map.class)
        !registry.hasAdapterFor(String.class)

        and: 'adapter count is correct'
        registry.getAdapterCount() == 1
    }

    def "should handle null source gracefully"() {
        given: 'a registry with a map adapter'
        def registry = new SedanDataRegistry()
        registry.registerAdapter(new MapSedanDataAdapter())

        when: 'adapting null'
        registry.adapt(null)

        then: 'an exception is thrown'
        thrown(IllegalArgumentException)
    }
}