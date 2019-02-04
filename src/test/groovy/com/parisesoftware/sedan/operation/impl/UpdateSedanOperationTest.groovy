package com.parisesoftware.sedan.operation.impl

import com.parisesoftware.sedan.operation.ISedanOperation
import spock.lang.Specification

class UpdateSedanOperationTest extends Specification {

    def "UpdateSedanOperation: should implement ISedanOperation"() {

        when: 'a new UpdateSedanOperation instance is created'
        UpdateSedanOperation resultant = Mock(UpdateSedanOperation)

        then: 'the resultant implements ISedanOperation'
        resultant instanceof ISedanOperation
    }

    def "UpdateSedanOperation: should implement AbstractSedanOperation"() {

        when: 'a new UpdateSedanOperation instance is created'
        UpdateSedanOperation resultant = Mock(UpdateSedanOperation)

        then: 'the resultant implements AbstractSedanOperation'
        resultant instanceof AbstractSedanOperation
    }
    
}
