package com.parisesoftware.sedan.operation.context

import com.parisesoftware.sedan.operation.OperationType
import spock.lang.Specification

class OperationContextFactoryTest extends Specification {

    def "OperationContextFactory.createDeleteContext(): should properly construct a Deletion OperationContext"() {

        given: 'some sample data'
        Object testName = 'Iron Man'

        when: 'the OperationContextFactory.createDeleteContext() method is invoked with our sample data'
        OperationContext resultant = OperationContextFactory.createDeleteContext(testName)

        then: 'the resultant is not null'
        resultant != null

        then: 'the resultant\'s name is set to our sample name'
        resultant.name == testName

        then: 'the resultant\'s type is set to Delete'
        resultant.type == OperationType.DELETE
    }

    def "OperationContextFactory.createUpdateContext(): should properly construct an Update OperationContext"() {

        given: 'some sample data'
        Object testName = 'Dr Strange'
        Object testValue = 'Steven Strange'

        when: 'the OperationContextFactory.createUpdateContext() method is invoked with our sample data'
        OperationContext resultant = OperationContextFactory.createUpdateContext(testName, testValue)

        then: 'the resultant is not null'
        resultant != null

        then: 'the resultant\'s name is set to our sample name'
        resultant.name == testName

        then: 'the resultant\'s value is set to our sample value'
        resultant.value == testValue

        then: 'the resultant\'s type is set to UPDATE'
        resultant.type == OperationType.UPDATE
    }

    def "OperationContextFactory.createAddContext(): should properly construct an Add OperationContext"() {

        given: 'some sample data'
        Object testName = 'Hulk'
        Object testValue = 'Bruce Banner'

        when: 'the OperationContextFactory.createAddContext() method is invoked with our sample data'
        OperationContext resultant = OperationContextFactory.createAddContext(testName, testValue)

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
