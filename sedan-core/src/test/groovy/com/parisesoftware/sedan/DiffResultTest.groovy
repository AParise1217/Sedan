package com.parisesoftware.sedan

import com.parisesoftware.sedan.operation.AdditionSedanOperation
import com.parisesoftware.sedan.operation.DeletionSedanOperation
import com.parisesoftware.sedan.operation.MoveSedanOperation
import com.parisesoftware.sedan.operation.UpdateSedanOperation
import spock.lang.Specification

class DiffResultTest extends Specification {

    def "isEmpty() returns true when operations list is empty"() {
        expect:
        new DiffResult([]).isEmpty()
    }

    def "areEqual() returns true when operations list is empty"() {
        expect:
        new DiffResult([]).areEqual()
    }

    def "isEmpty() returns false when operations are present"() {
        expect:
        !new DiffResult([new AdditionSedanOperation('x', 1)]).isEmpty()
    }

    def "additions() filters only AdditionSedanOperation instances"() {
        given:
        def ops = [
            new AdditionSedanOperation('a', 1),
            new DeletionSedanOperation('b'),
            new UpdateSedanOperation('c', 3)
        ]

        when:
        def result = new DiffResult(ops).additions()

        then:
        result.size() == 1
        result[0].name() == 'a'
    }

    def "deletions() filters only DeletionSedanOperation instances"() {
        given:
        def ops = [
            new AdditionSedanOperation('a', 1),
            new DeletionSedanOperation('b'),
            new UpdateSedanOperation('c', 3)
        ]

        when:
        def result = new DiffResult(ops).deletions()

        then:
        result.size() == 1
        result[0].name() == 'b'
    }

    def "updates() filters only UpdateSedanOperation instances"() {
        given:
        def ops = [
            new AdditionSedanOperation('a', 1),
            new DeletionSedanOperation('b'),
            new UpdateSedanOperation('c', 3)
        ]

        when:
        def result = new DiffResult(ops).updates()

        then:
        result.size() == 1
        result[0].name() == 'c'
    }

    def "operations list is immutable"() {
        given:
        def result = new DiffResult([new AdditionSedanOperation('x', 1)])

        when:
        result.operations().clear()

        then:
        thrown(UnsupportedOperationException)
    }

    def "empty DiffResult has empty view lists"() {
        given:
        def result = new DiffResult([])

        expect:
        result.additions().isEmpty()
        result.deletions().isEmpty()
        result.updates().isEmpty()
        result.moves().isEmpty()
    }

    def "moves() filters only MoveSedanOperation instances"() {
        given:
        def ops = [
            new AdditionSedanOperation('a', 1),
            new MoveSedanOperation('b', 'c', 2)
        ]

        when:
        def result = new DiffResult(ops).moves()

        then:
        result.size() == 1
        result[0].sourceName() == 'b'
        result[0].targetName() == 'c'
    }
}
