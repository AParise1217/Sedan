package com.parisesoftware.sedan

import spock.lang.Specification

class SedanDriverTest extends Specification {

    def "difference(): should return an empty list for two identical, empty maps"() {

        given: 'two identical, empty maps'
        Map testMap1 = [:]
        Map testMap2 = [:]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is an empty list'
        resultant.isEmpty()
    }

    def "difference(): should return an empty list for two identical, populated maps of a single key"() {

        given: 'two identical maps with a single key'
        Map testMap1 = [a: 3]
        Map testMap2 = [a: 3]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is an empty list'
        resultant.isEmpty()
    }

    def "difference(): should add an `add` operation, when just one add is needed"() {

        given: 'two test maps'
        Map testMap1 = [a: 3]
        Map testMap2 = [a: 3, b: 4]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is a non-empty list'
        !resultant.isEmpty()

        then: 'the resultant contains an addition operation'
        resultant.contains([operation: OperationType.ADD, name: 'b', value: 4])
    }

    def "difference(): should add a `delete` operation, when just one delete is needed"() {

        given: 'two test maps'
        Map testMap1 = [a: 3, b: 4]
        Map testMap2 = [a: 3]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is a non-empty list'
        !resultant.isEmpty()

        then: 'the resultant contains an addition operation'
        resultant.contains([operation: OperationType.DELETE, name: 'b'])
    }

    def "difference(): should add an `update` operation, when just one update is needed"() {

        given: 'two test maps'
        Map testMap1 = [a: 3]
        Map testMap2 = [a: 4]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is a non-empty list'
        !resultant.isEmpty()

        then: 'the resultant contains an addition operation'
        resultant.contains([operation: OperationType.UPDATE, name: 'a', value: 4])
    }

    def "hasDifferentValueAtKey(): should return true when there is a different value. Single index maps."() {

        given: 'two test maps'
        Map testMap1 = [a: 3]
        Map testMap2 = [a: 4]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testMap1, testMap2, 'a')

        then: 'the resultant is true'
        resultant
    }

    def "hasDifferentValueAtKey(): should return true when there is a different value. Multi-index maps."() {

        given: 'two test maps'
        Map testMap1 = [a: 3, b: 52, c: 99]
        Map testMap2 = [a: 3, b: 52, c: 100]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testMap1, testMap2, 'c')

        then: 'the resultant is true'
        resultant
    }

    def "hasDifferentValueAtKey(): should return false when there is not different value. Multi-index maps."() {

        given: 'two test maps'
        Map testMap1 = [a: 32, b: 52, c: 99]
        Map testMap2 = [a: 33, b: 52, c: 100]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testMap1, testMap2, 'b')

        then: 'the resultant is false'
        !resultant
    }

    def "hasDifferentValueAtKey(): should return false when there is not different value. Single index maps."() {

        given: 'two test maps'
        Map testMap1 = [a: 32]
        Map testMap2 = [a: 32]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testMap1, testMap2, 'a')

        then: 'the resultant is false'
        !resultant
    }

    def "hasDifferentValueAtKey(): should return false when the key does not exist in either map. Single index maps."() {

        given: 'two test maps'
        Map testMap1 = [a: 32]
        Map testMap2 = [a: 32]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testMap1, testMap2, 'b')

        then: 'the resultant is false'
        !resultant
    }

    def "hasDifferentValueAtKey(): should return true when the key does exist in the first map. Single index maps."() {

        given: 'two test maps'
        Map testMap1 = [b: 32]
        Map testMap2 = [a: 32]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists in the second map'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testMap1, testMap2, 'a')

        then: 'the resultant is true'
        resultant
    }

    def "hasDifferentValueAtKey(): should return true when the key does exist in the second map. Single index maps."() {

        given: 'two test maps'
        Map testMap1 = [b: 32]
        Map testMap2 = [a: 32]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists in the first map'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testMap1, testMap2, 'b')

        then: 'the resultant is true'
        resultant
    }

    def "containsKey(): should return true when the key does exist in the map. Single-index map."() {

        given: 'a test map'
        Map testMap = [color: 'red']

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the containsKey method is invoked on the map, with a key that exists in the map'
        boolean resultant = testSedanDriver.containsKey(testMap, 'color')

        then: 'the resultant is true'
        resultant
    }

    def "containsKey(): should return false when the key does not exist in the map. Single-index map."() {

        given: 'a test map'
        Map testMap = [color: 'blue']

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the containsKey method is invoked on the map, with a key that exists in the map'
        boolean resultant = testSedanDriver.containsKey(testMap, 'shape')

        then: 'the resultant is false'
        !resultant
    }

    def "getKeys(): should delegate to the param Map\'s keySet() method"() {

        given: 'a mocked map'
        Map mockMap = Mock(Map)

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the getKeys method is invoked on the Sedan Driver, with the mocked map'
        testSedanDriver.getKeys(mockMap)

        then: 'the mocked map\'s keySet() method was invoked'
        1 * mockMap.keySet()
    }

    //TODO: move this to an integration test module
    def "getKeys(): should return an empty set when the map is empty"() {

        given: 'an empty map'
        Map testMap = [:]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the getKeys method is invoked on the Sedan Driver, with the empty map'
        Set resultant = testSedanDriver.getKeys(testMap)

        then: 'the resultant set is not null'
        resultant != null

        and: 'the resultant set is empty'
        resultant.isEmpty()
    }

    //TODO: move this to an integration test module
    def "getKeys(): should return an empty set when the map is null"() {

        given: 'a null map'
        Map testMap = null

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the getKeys method is invoked on the Sedan Driver, with the null map'
        Set resultant = testSedanDriver.getKeys(testMap)

        then: 'the resultant set is not null'
        resultant != null

        and: 'the resultant set is empty'
        resultant.isEmpty()
    }

    def "getKeys(): should not throw a NullPointerException when the map is null"() {

        given: 'a null map'
        Map testMap = null

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the getKeys method is invoked on the Sedan Driver, with the null map'
        testSedanDriver.getKeys(testMap)

        then: 'the method did not throw a NullPointerException'
        notThrown(NullPointerException)
    }

    def "getValueAt(): should return null when the source map is null"() {

        given: 'a null map'
        Map testMap = null

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the getValueAt method is invoked on the Sedan Driver, with the null map'
        Object resultant = testSedanDriver.getValueAt(testMap, 'fresh out')

        then: 'the resultant object is null'
        resultant == null
    }

    def "getValueAt(): should not throw NullPointerException when the source map is null"() {

        given: 'a null map'
        Map testMap = null

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the getValueAt method is invoked on the Sedan Driver, with the null map'
        testSedanDriver.getValueAt(testMap, 'fresh out')

        then: 'the method did not throw a NullPointerException'
        notThrown(NullPointerException)
    }

}
