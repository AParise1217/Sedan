package com.parisesoftware.sedan.data.impl

import com.parisesoftware.sedan.data.ISedanData
import spock.lang.Specification

class SedanDataMapAdapterTest extends Specification {

    def "SedanDataMapAdapter: should implement ISedanData"() {

        when: 'a new SedanDataMapAdapter instance is created'
        SedanDataMapAdapter resultant = Mock(SedanDataMapAdapter)

        then: 'the resultant implements ISedanData'
        resultant instanceof ISedanData
    }

    def "containsKey(): should return true when the key does exist in the map. Single-index map."() {

        given: 'a test map'
        Map testMap = [color: 'red']

        and: 'a Sedan Driver'
        SedanDataMapAdapter testSedanDataMapAdapter = new SedanDataMapAdapter(testMap)

        when: 'the containsKey method is invoked on the map, with a key that exists in the map'
        boolean resultant = testSedanDataMapAdapter.containsKey('color')

        then: 'the resultant is true'
        resultant
    }

    def "containsKey(): should return false when the key does not exist in the map. Single-index map."() {

        given: 'a test map'
        Map testMap = [color: 'blue']

        and: 'Sedan Data'
        SedanDataMapAdapter testSedanDataMapAdapter = new SedanDataMapAdapter(testMap)

        when: 'the containsKey method is invoked on the map, with a key that exists in the map'
        boolean resultant = testSedanDataMapAdapter.containsKey('shape')

        then: 'the resultant is false'
        !resultant
    }

    def "getKeys(): should delegate to the param Map\'s keySet() method"() {

        given: 'a mocked map'
        Map mockMap = Mock(Map)

        and: 'Sedan Data'
        SedanDataMapAdapter testSedanDataMapAdapter = new SedanDataMapAdapter(mockMap)

        when: 'the getKeys method is invoked on the Sedan Data, with the mocked map'
        testSedanDataMapAdapter.getKeys()

        then: 'the mocked map\'s keySet() method was invoked'
        1 * mockMap.keySet()
    }

    //TODO: move this to an integration test module
    def "getKeys(): should return an empty set when the map is empty"() {

        given: 'an empty map'
        Map testMap = [:]

        and: 'Sedan Data'
        SedanDataMapAdapter testSedanDataMapAdapter = new SedanDataMapAdapter(testMap)

        when: 'the getKeys method is invoked on the Sedan Data, with the empty map'
        Set resultant = testSedanDataMapAdapter.getKeys()

        then: 'the resultant set is not null'
        resultant != null

        and: 'the resultant set is empty'
        resultant.isEmpty()
    }

    //TODO: move this to an integration test module
    def "getKeys(): should return an empty set when the map is null"() {

        given: 'a null map'
        Map testMap = null

        and: 'a Sedan Data Map'
        SedanDataMapAdapter testSedanDataMapAdapter = new SedanDataMapAdapter(testMap)

        when: 'the getKeys method is invoked on the Sedan Driver, with the null map'
        Set resultant = testSedanDataMapAdapter.getKeys()

        then: 'the resultant set is not null'
        resultant != null

        and: 'the resultant set is empty'
        resultant.isEmpty()
    }

    def "getKeys(): should not throw a NullPointerException when the map is null"() {

        given: 'a null map'
        Map testMap = null

        and: 'a Sedan Data Map'
        SedanDataMapAdapter testSedanDataMapAdapter = new SedanDataMapAdapter(testMap)

        when: 'the getKeys method is invoked on the Sedan Driver, with the null map'
        testSedanDataMapAdapter.getKeys()

        then: 'the method did not throw a NullPointerException'
        notThrown(NullPointerException)
    }

    def "getValueAt(): should return null when the source map is null"() {

        given: 'a null map'
        Map testMap = null

        and: 'a Sedan Data Map'
        SedanDataMapAdapter testSedanDataMapAdapter = new SedanDataMapAdapter(testMap)

        when: 'the getValueAt method is invoked on the Sedan Data, with the null map'
        Object resultant = testSedanDataMapAdapter.getValueAt('fresh out')

        then: 'the resultant object is null'
        resultant == null
    }

    def "getValueAt(): should not throw NullPointerException when the source map is null"() {

        given: 'a null map'
        Map testMap = null

        and: 'a Sedan Data Map'
        SedanDataMapAdapter testSedanDataMapAdapter = new SedanDataMapAdapter(testMap)

        when: 'the getValueAt method is invoked on the Sedan Data, with the null map'
        testSedanDataMapAdapter.getValueAt('fresh out')

        then: 'the method did not throw a NullPointerException'
        notThrown(NullPointerException)
    }
    
}
