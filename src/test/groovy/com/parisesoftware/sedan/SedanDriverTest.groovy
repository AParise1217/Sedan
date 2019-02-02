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
        resultant.contains([operation: 'add', name: 'b', value: 4])
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
        resultant.contains([operation: 'delete', name: 'b'])
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
        resultant.contains([operation: 'update', name: 'a', value: 4])
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

        given: 'a test maps'
        Map testMap = [color: 'red']

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the containsKey method is invoked on the map, with a key that exists in the map'
        boolean resultant = testSedanDriver.containsKey(testMap, 'color')

        then: 'the resultant is true'
        resultant
    }

    def "containsKey(): should return false when the key does not exist in the map. Single-index map."() {

        given: 'a test maps'
        Map testMap = [color: 'blue']

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the containsKey method is invoked on the map, with a key that exists in the map'
        boolean resultant = testSedanDriver.containsKey(testMap, 'shape')

        then: 'the resultant is false'
        !resultant
    }

    def "DELETE_OPERATION: should equal `delete`."() {

        when: 'the DELETE_OPERATION value is pulled from the driver'
        String resultant = SedanDriver.DELETE_OPERATION

        then: 'the resultant is equal to `delete`'
        resultant == 'delete'
    }

    def "UPDATE_OPERATION: should equal `update`."() {

        when: 'the UPDATE_OPERATION value is pulled from the driver'
        String resultant = SedanDriver.UPDATE_OPERATION

        then: 'the resultant is equal to `update`'
        resultant == 'update'
    }

    def "ADD_OPERATION: should equal `add`."() {

        when: 'the ADD_OPERATION value is pulled from the driver'
        String resultant = SedanDriver.ADD_OPERATION

        then: 'the resultant is equal to `add`'
        resultant == 'add'
    }

}
