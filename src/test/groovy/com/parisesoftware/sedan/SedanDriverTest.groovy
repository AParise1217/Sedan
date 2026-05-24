package com.parisesoftware.sedan

import com.parisesoftware.sedan.data.SedanDataSource
import com.parisesoftware.sedan.data.impl.MapSedanDataAdapter
import com.parisesoftware.sedan.operation.OperationType
import com.parisesoftware.sedan.operation.SedanOperation
import spock.lang.Specification

class SedanDriverTest extends Specification {

    static SedanDataSource sedanData(Map dataMap) {
        return new MapSedanDataAdapter(dataMap)
    }

    // ── difference() ─────────────────────────────────────────────────────────

    def "difference(): should return an empty list for two identical, empty maps"() {
        given:
        SedanDriver driver = new SedanDriver()

        when:
        List result = driver.difference([:], [:])

        then:
        result.isEmpty()
    }

    def "difference(): should return an empty list for two identical, populated maps of a single key"() {
        given:
        SedanDriver driver = new SedanDriver()

        when:
        List result = driver.difference([a: 3], [a: 3])

        then:
        result.isEmpty()
    }

    def "difference(): should add an ADD operation when a key is only in the target"() {
        given:
        SedanDriver driver = new SedanDriver()

        when:
        List<SedanOperation> result = driver.difference([a: 3], [a: 3, b: 4])

        then:
        !result.isEmpty()
        result.find { it.type() == OperationType.ADD } != null
    }

    def "difference(): should not add a DELETE operation when just one add is needed"() {
        given:
        SedanDriver driver = new SedanDriver()

        when:
        List<SedanOperation> result = driver.difference([a: 3], [a: 3, b: 4])

        then:
        result.find { it.type() == OperationType.DELETE } == null
    }

    def "difference(): should not add an UPDATE operation when just one add is needed"() {
        given:
        SedanDriver driver = new SedanDriver()

        when:
        List<SedanOperation> result = driver.difference([a: 3], [a: 3, b: 4])

        then:
        result.find { it.type() == OperationType.UPDATE } == null
    }

    def "difference(): should add a DELETE operation when a key is only in the source"() {
        given:
        SedanDriver driver = new SedanDriver()

        when:
        List<SedanOperation> result = driver.difference([a: 3, b: 4], [a: 3])

        then:
        !result.isEmpty()
        result.find { it.type() == OperationType.DELETE } != null
    }

    def "difference(): should not add an UPDATE operation when just one delete is needed"() {
        given:
        SedanDriver driver = new SedanDriver()

        when:
        List<SedanOperation> result = driver.difference([a: 3, b: 4], [a: 3])

        then:
        result.find { it.type() == OperationType.UPDATE } == null
    }

    def "difference(): should not add an ADD operation when just one delete is needed"() {
        given:
        SedanDriver driver = new SedanDriver()

        when:
        List<SedanOperation> result = driver.difference([a: 3, b: 4], [a: 3])

        then:
        result.find { it.type() == OperationType.ADD } == null
    }

    def "difference(): should add an UPDATE operation when a shared key has a different value"() {
        given:
        SedanDriver driver = new SedanDriver()

        when:
        List<SedanOperation> result = driver.difference([a: 3], [a: 4])

        then:
        !result.isEmpty()
        result.find { it.type() == OperationType.UPDATE } != null
    }

    def "difference(): should not add an ADD operation when just one update is needed"() {
        given:
        SedanDriver driver = new SedanDriver()

        when:
        List<SedanOperation> result = driver.difference([a: 3], [a: 4])

        then:
        result.find { it.type() == OperationType.ADD } == null
    }

    def "difference(): should not add a DELETE operation when just one update is needed"() {
        given:
        SedanDriver driver = new SedanDriver()

        when:
        List<SedanOperation> result = driver.difference([a: 3], [a: 4])

        then:
        result.find { it.type() == OperationType.DELETE } == null
    }

    // ── hasDifferentValueAtKey() ──────────────────────────────────────────────

    def "hasDifferentValueAtKey(): should return true when values differ — single key"() {
        given:
        SedanDataSource data1 = sedanData([a: 3])
        SedanDataSource data2 = sedanData([a: 5])

        expect:
        new SedanDriver().hasDifferentValueAtKey(data1, data2, 'a')
    }

    def "hasDifferentValueAtKey(): should return true when values differ — multi key"() {
        given:
        SedanDataSource data1 = sedanData([a: 3, b: 52, c: 99])
        SedanDataSource data2 = sedanData([a: 3, b: 52, c: 100])

        expect:
        new SedanDriver().hasDifferentValueAtKey(data1, data2, 'c')
    }

    def "hasDifferentValueAtKey(): should return false when values are equal — multi key"() {
        given:
        SedanDataSource data1 = sedanData([a: 32, b: 52, c: 99])
        SedanDataSource data2 = sedanData([a: 33, b: 52, c: 100])

        expect:
        !new SedanDriver().hasDifferentValueAtKey(data1, data2, 'b')
    }

    def "hasDifferentValueAtKey(): should return false when values are equal — single key"() {
        given:
        SedanDataSource data1 = sedanData([a: 32])
        SedanDataSource data2 = sedanData([a: 32])

        expect:
        !new SedanDriver().hasDifferentValueAtKey(data1, data2, 'a')
    }

    def "hasDifferentValueAtKey(): should return false when key is absent from both maps"() {
        given:
        SedanDataSource data1 = sedanData([a: 32])
        SedanDataSource data2 = sedanData([a: 32])

        expect:
        !new SedanDriver().hasDifferentValueAtKey(data1, data2, 'b')
    }

    def "hasDifferentValueAtKey(): should return true when key is only in the first map"() {
        given:
        SedanDataSource data1 = sedanData([b: 32])
        SedanDataSource data2 = sedanData([a: 32])

        expect:
        new SedanDriver().hasDifferentValueAtKey(data1, data2, 'a')
    }

    def "hasDifferentValueAtKey(): should return true when key is only in the second map"() {
        given:
        SedanDataSource data1 = sedanData([b: 32])
        SedanDataSource data2 = sedanData([a: 32])

        expect:
        new SedanDriver().hasDifferentValueAtKey(data1, data2, 'b')
    }
}
