package com.parisesoftware.sedan.operation.impl

import com.parisesoftware.sedan.operation.ISedanOperation
import spock.lang.Specification

class DeletionSedanOperationTest extends Specification {

    def "DeletionSedanOperation: should implement ISedanOperation"() {

        when: 'a new DeletionSedanOperation instance is created'
        DeletionSedanOperation resultant = Mock(DeletionSedanOperation)

        then: 'the resultant implements ISedanOperation'
        resultant instanceof ISedanOperation
    }

    def "DeletionSedanOperation: should implement AbstractSedanOperation"() {

        when: 'a new DeletionSedanOperation instance is created'
        DeletionSedanOperation resultant = Mock(DeletionSedanOperation)

        then: 'the resultant implements AbstractSedanOperation'
        resultant instanceof AbstractSedanOperation
    }
    
}
