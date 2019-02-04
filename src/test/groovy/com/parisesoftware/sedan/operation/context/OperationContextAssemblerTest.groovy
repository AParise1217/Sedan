package com.parisesoftware.sedan.operation.context

import com.parisesoftware.sedan.operation.IOperationContext
import com.parisesoftware.sedan.operation.OperationType
import spock.lang.Specification

class OperationContextAssemblerTest extends Specification {

    def "OperationContextAssembler.createDeleteContext(): should properly construct a Deletion OperationContext"() {

        given: 'some sample data'
        Object testName = 'Iron Man'

        when: 'the OperationContextAssembler.createDeleteContext() method is invoked with our sample data'
        IOperationContext resultant = OperationContextAssembler.createDeleteContext(testName)

        then: 'the resultant is not null'
        resultant != null

        then: 'the resultant\'s name is set to our sample name'
        resultant.name == testName

        then: 'the resultant\'s type is set to Delete'
        resultant.type == OperationType.DELETE
    }

    def "OperationContextAssembler.createUpdateContext(): should properly construct an Update OperationContext"() {

        given: 'some sample data'
        Object testName = 'Dr Strange'
        Object testValue = 'Steven Strange'

        when: 'the OperationContextAssembler.createUpdateContext() method is invoked with our sample data'
        IOperationContext resultant = OperationContextAssembler.createUpdateContext(testName, testValue)

        then: 'the resultant is not null'
        resultant != null

        then: 'the resultant\'s name is set to our sample name'
        resultant.name == testName

        then: 'the resultant\'s value is set to our sample value'
        resultant.value == testValue

        then: 'the resultant\'s type is set to UPDATE'
        resultant.type == OperationType.UPDATE
    }

    def "OperationContextAssembler.createAddContext(): should properly construct an Add OperationContext"() {

        given: 'some sample data'
        Object testName = 'Hulk'
        Object testValue = 'Bruce Banner'

        when: 'the OperationContextAssembler.createAddContext() method is invoked with our sample data'
        IOperationContext resultant = OperationContextAssembler.createAddContext(testName, testValue)

        then: 'the resultant is not null'
        resultant != null

        then: 'the resultant\'s name is set to our sample name'
        resultant.name == testName

        then: 'the resultant\'s value is set to our sample value'
        resultant.value == testValue

        then: 'the resultant\'s type is set to ADD'
        resultant.type == OperationType.ADD
    }

}
