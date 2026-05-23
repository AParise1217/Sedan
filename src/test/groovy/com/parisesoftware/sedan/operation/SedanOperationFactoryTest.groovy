package com.parisesoftware.sedan.operation

import com.parisesoftware.sedan.operation.context.OperationContext
import com.parisesoftware.sedan.operation.impl.AdditionSedanOperation
import com.parisesoftware.sedan.operation.impl.DeletionSedanOperation
import com.parisesoftware.sedan.operation.impl.UpdateSedanOperation
import org.apache.commons.lang3.NotImplementedException
import spock.lang.Specification

class SedanOperationFactoryTest extends Specification {

    def "SedanOperationFactory.construct(): should invoke the AdditionSedanOperation constructor when there is an ADD type"() {

        given: 'some sample data'
        OperationContext testContext = new OperationContext(OperationType.ADD, 'Black Panther', 'T\'Challa')

        and: 'a global AdditionSedanOperation mock'
        GroovyMock(AdditionSedanOperation, global: true)

        when: 'the SedanOperationFactory.construct() method is invoked with our sample data'
        SedanOperationFactory.construct(testContext)

        then: 'the AdditionSedanOperation constructor was invoked with the proper params'
        1 * new AdditionSedanOperation(testContext.name, testContext.value)
    }

    def "SedanOperationFactory.construct(): should invoke the DeletionSedanOperation constructor when there is a DELETE type"() {

        given: 'some sample data'
        OperationContext testContext = new OperationContext(OperationType.DELETE, 'Thor', null)

        and: 'a global DeletionSedanOperation mock'
        GroovyMock(DeletionSedanOperation, global: true)

        when: 'the SedanOperationFactory.construct() method is invoked with our sample data'
        SedanOperationFactory.construct(testContext)

        then: 'the DeletionSedanOperation constructor was invoked with the proper params'
        1 * new DeletionSedanOperation(testContext.name)
    }

    def "SedanOperationFactory.construct(): should invoke the UpdateSedanOperation constructor when there is an UPDATE type"() {

        given: 'some sample data'
        OperationContext testContext = new OperationContext(OperationType.UPDATE, 'Black Widow', 'Natasha')

        and: 'a global UpdateSedanOperation mock'
        GroovyMock(UpdateSedanOperation, global: true)

        when: 'the SedanOperationFactory.construct() method is invoked with our sample data'
        SedanOperationFactory.construct(testContext)

        then: 'the UpdateSedanOperation constructor was invoked with the proper params'
        1 * new UpdateSedanOperation(testContext.name, testContext.value)
    }

    def "SedanOperationFactory.construct(): should throw NotImplementedException when there is an unknown type passed in"() {

        given: 'some sample data'
        OperationContext testContext = new OperationContext(null, 'Black Widow', 'Natasha')

        when: 'the SedanOperationFactory.construct() method is invoked with our sample data'
        SedanOperationFactory.construct(testContext)

        then: 'a NotImplementedException is thrown'
        thrown(NotImplementedException)
    }

}
