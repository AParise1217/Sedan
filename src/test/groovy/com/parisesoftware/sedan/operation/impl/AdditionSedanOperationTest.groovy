package com.parisesoftware.sedan.operation.impl

import com.parisesoftware.sedan.operation.ISedanOperation
import spock.lang.Specification

class AdditionSedanOperationTest extends Specification {

    def "AdditionSedanOperation: should implement ISedanOperation"() {

        when: 'a new AdditionSedanOperation instance is created'
        AdditionSedanOperation resultant = Mock(AdditionSedanOperation)

        then: 'the resultant implements ISedanOperation'
        resultant instanceof ISedanOperation
    }

    def "AdditionSedanOperation: should implement AbstractSedanOperation"() {

        when: 'a new AdditionSedanOperation instance is created'
        AdditionSedanOperation resultant = Mock(AdditionSedanOperation)

        then: 'the resultant implements AbstractSedanOperation'
        resultant instanceof AbstractSedanOperation
    }

}
