package com.parisesoftware.sedan

import com.parisesoftware.sedan.DiffResult
import com.parisesoftware.sedan.data.SedanDataSource
import com.parisesoftware.sedan.data.impl.MapSedanDataAdapter
import com.parisesoftware.sedan.operation.MoveSedanOperation
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

    // ── diff() ───────────────────────────────────────────────────────────────────

    def "diff(): returns DiffResult wrapping the same operations as difference()"() {
        given:
        SedanDriver driver = new SedanDriver()

        when:
        DiffResult result = driver.diff([a: 1], [a: 2])

        then:
        result.operations().size() == 1
        result.updates().size() == 1
    }

    def "diff(): areEqual() is true for identical maps"() {
        expect:
        new SedanDriver().diff([a: 1], [a: 1]).areEqual()
    }

    def "diff(): areEqual() is false when maps differ"() {
        expect:
        !new SedanDriver().diff([a: 1], [a: 2]).areEqual()
    }

    // ── apply() ──────────────────────────────────────────────────────────────────

    def "apply(): ADD operation inserts new key"() {
        given:
        SedanDriver driver = new SedanDriver()
        def ops = driver.difference([a: 1], [a: 1, b: 2])

        when:
        def result = driver.apply([a: 1], ops)

        then:
        result == [a: 1, b: 2]
    }

    def "apply(): DELETE operation removes key"() {
        given:
        SedanDriver driver = new SedanDriver()
        def ops = driver.difference([a: 1, b: 2], [a: 1])

        when:
        def result = driver.apply([a: 1, b: 2], ops)

        then:
        result == [a: 1]
        !result.containsKey('b')
    }

    def "apply(): UPDATE operation replaces value"() {
        given:
        SedanDriver driver = new SedanDriver()
        def ops = driver.difference([a: 1], [a: 99])

        when:
        def result = driver.apply([a: 1], ops)

        then:
        result == [a: 99]
    }

    def "apply(): result map is unmodifiable"() {
        given:
        def result = new SedanDriver().apply([a: 1], [])

        when:
        result.put('x', 'y')

        then:
        thrown(UnsupportedOperationException)
    }

    def "apply(): round-trip — apply(source, difference(source, target)) equals target"() {
        given:
        def source = [name: 'Alice', role: 'user', age: 30]
        def target = [name: 'Alice', role: 'admin', city: 'NYC']
        SedanDriver driver = new SedanDriver()

        when:
        def ops = driver.difference(source, target)
        def result = driver.apply(source, ops)

        then:
        result == target
    }

    // ── MOVE detection ───────────────────────────────────────────────────────────

    def "difference(): emits MOVE when a key is renamed but value is unchanged"() {
        when:
        List result = new SedanDriver().difference([hero: 'Thor'], [champion: 'Thor'])

        then:
        result.size() == 1
        result[0] instanceof MoveSedanOperation
        result[0].sourceName() == 'hero'
        result[0].targetName() == 'champion'
    }

    def "difference(): does NOT emit MOVE when values differ between renamed keys"() {
        when:
        List result = new SedanDriver().difference([hero: 'Thor'], [champion: 'Loki'])

        then:
        result.find { it instanceof MoveSedanOperation } == null
        result.find { it.type() == OperationType.DELETE } != null
        result.find { it.type() == OperationType.ADD } != null
    }

    def "difference(): emits MOVE alongside UPDATE for mixed changes"() {
        when:
        List result = new SedanDriver().difference([a: 'Thor', b: 1], [x: 'Thor', b: 2])

        then:
        result.find { it instanceof MoveSedanOperation }?.sourceName() == 'a'
        result.find { it instanceof MoveSedanOperation }?.targetName() == 'x'
        result.find { it.type() == OperationType.UPDATE }?.name() == 'b'
    }

    def "apply(): MOVE operation renames key preserving value"() {
        given:
        def ops = new SedanDriver().difference([hero: 'Thor'], [champion: 'Thor'])

        when:
        def result = new SedanDriver().apply([hero: 'Thor'], ops)

        then:
        result == [champion: 'Thor']
        !result.containsKey('hero')
    }

    def "apply(): round-trip with MOVE — apply(source, difference(source, target)) equals target"() {
        given:
        def source = [a: 1, b: 2, c: 3]
        def target = [a: 1, x: 2, c: 99]

        when:
        def result = new SedanDriver().apply(source, new SedanDriver().difference(source, target))

        then:
        result == target
    }
}
