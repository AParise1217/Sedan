package com.parisesoftware.sedan.operation

import spock.lang.Specification

class MoveSedanOperationTest extends Specification {

    def "should implement SedanOperation"() {
        expect:
        new MoveSedanOperation('from', 'to', 'value') instanceof SedanOperation
    }

    def "type() should return MOVE"() {
        expect:
        new MoveSedanOperation('from', 'to', 'value').type() == OperationType.MOVE
    }

    def "name() should return sourceName"() {
        expect:
        new MoveSedanOperation('source', 'target', 'v').name() == 'source'
    }

    def "sourceName() is accessible"() {
        expect:
        new MoveSedanOperation('hero', 'champion', 'Thor').sourceName() == 'hero'
    }

    def "targetName() is accessible"() {
        expect:
        new MoveSedanOperation('hero', 'champion', 'Thor').targetName() == 'champion'
    }

    def "value() returns the moved value"() {
        expect:
        new MoveSedanOperation('from', 'to', 42).value() == 42
    }

    def "value() can be null"() {
        expect:
        new MoveSedanOperation('from', 'to', null).value() == null
    }

    def "throws NullPointerException for null sourceName"() {
        when:
        new MoveSedanOperation(null, 'to', 'v')

        then:
        thrown(NullPointerException)
    }

    def "throws NullPointerException for null targetName"() {
        when:
        new MoveSedanOperation('from', null, 'v')

        then:
        thrown(NullPointerException)
    }
}
