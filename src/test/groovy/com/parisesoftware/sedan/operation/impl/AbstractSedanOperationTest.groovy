package com.parisesoftware.sedan.operation.impl

import com.parisesoftware.sedan.operation.ISedanOperation
import com.parisesoftware.sedan.operation.OperationType
import spock.lang.Specification

class AbstractSedanOperationTest extends Specification {

    def "AbstractSedanOperation: should implement ISedanOperation"() {

        when: 'a new AbstractSedanOperation instance is created'
        AbstractSedanOperation resultant = Mock(AbstractSedanOperation)

        then: 'the resultant implements ISedanOperation'
        resultant instanceof ISedanOperation
    }

    def "getType(): should return the type passed into the constructor"() {

        given: 'a sample type'
        OperationType testType = OperationType.ADD

        and: 'an Abstract Sedan Operation Spy'
        AbstractSedanOperation testOperation =
                Spy(AbstractSedanOperation, constructorArgs: ['ADD', testType]) as AbstractSedanOperation

        when: 'the type is queried for on the operation'
        OperationType resultant = testOperation.getType()

        then: 'the resultant is our sample type'
        resultant == testType
    }

    def "getName(): should return the name passed into the constructor"() {

        given: 'a sample type'
        Object testName = 'ADD'

        and: 'an Abstract Sedan Operation Spy'
        AbstractSedanOperation testOperation =
                Spy(AbstractSedanOperation, constructorArgs: [testName, OperationType.ADD]) as AbstractSedanOperation

        when: 'the name is queried for on the operation'
        Object resultant = testOperation.getName()

        then: 'the resultant is our sample name'
        resultant == testName
    }

}
