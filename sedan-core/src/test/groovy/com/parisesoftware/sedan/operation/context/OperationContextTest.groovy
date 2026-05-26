package com.parisesoftware.sedan.operation.context

import com.parisesoftware.sedan.operation.AdditionSedanOperation
import com.parisesoftware.sedan.operation.DeletionSedanOperation
import com.parisesoftware.sedan.operation.OperationType
import com.parisesoftware.sedan.operation.UpdateSedanOperation
import spock.lang.Specification

class OperationContextTest extends Specification {

    def "toOperation(): ADD context produces an AdditionSedanOperation"() {
        given:
        def context = new OperationContext(OperationType.ADD, 'hero', 'Thor')

        when:
        def result = context.toOperation()

        then:
        result instanceof AdditionSedanOperation
        result.name() == 'hero'
        result.value() == 'Thor'
    }

    def "toOperation(): UPDATE context produces an UpdateSedanOperation"() {
        given:
        def context = new OperationContext(OperationType.UPDATE, 'hero', 'Loki')

        when:
        def result = context.toOperation()

        then:
        result instanceof UpdateSedanOperation
        result.name() == 'hero'
        result.value() == 'Loki'
    }

    def "toOperation(): DELETE context produces a DeletionSedanOperation"() {
        given:
        def context = new OperationContext(OperationType.DELETE, 'hero', null)

        when:
        def result = context.toOperation()

        then:
        result instanceof DeletionSedanOperation
        result.name() == 'hero'
        result.value() == null
    }

    def "toOperation(): null type throws NullPointerException"() {
        given:
        def context = new OperationContext(null, 'hero', null)

        when:
        context.toOperation()

        then:
        thrown(NullPointerException)
    }
}
