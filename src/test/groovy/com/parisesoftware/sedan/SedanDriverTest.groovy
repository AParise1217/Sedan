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

}
