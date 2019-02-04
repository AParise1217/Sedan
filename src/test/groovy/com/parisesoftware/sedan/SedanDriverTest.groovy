package com.parisesoftware.sedan

import com.parisesoftware.sedan.data.ISedanData
import com.parisesoftware.sedan.data.impl.SedanDataMapAdapter
import com.parisesoftware.sedan.operation.IOperationContext
import com.parisesoftware.sedan.operation.ISedanOperation
import com.parisesoftware.sedan.operation.OperationType
import com.parisesoftware.sedan.operation.SedanOperationFactory
import com.parisesoftware.sedan.operation.context.OperationContextAssembler
import spock.lang.Specification

class SedanDriverTest extends Specification {

    /**
     * Helper to construct instances of ISedanData
     * @return {@code ISedanData}
     */
    static ISedanData sedanData(Map dataMap) {
        return new SedanDataMapAdapter(dataMap)
    }

    def "difference(): should return an empty list for two identical, empty maps"() {

        given: 'two identical, empty maps'
        Map testMap1 = [:]
        Map testMap2 = [:]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is an empty list'
        resultant.isEmpty()
    }

    def "difference(): should return an empty list for two identical, populated maps of a single key"() {

        given: 'two identical maps with a single key'
        Map testMap1 = [a: 3]
        Map testMap2 = [a: 3]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is an empty list'
        resultant.isEmpty()
    }

    def "difference(): should add an `add` operation, when just one add is needed"() {

        given: 'two test maps'
        Map testMap1 = [a: 3]
        Map testMap2 = [a: 3, b: 4]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is a non-empty list'
        !resultant.isEmpty()

        then: 'the resultant contains an addition operation'
        resultant.find { ((ISedanOperation) it).type == OperationType.ADD } != null
    }

    def "difference(): should not add a `delete` operation, when just one add is needed"() {

        given: 'two test maps'
        Map testMap1 = [a: 3]
        Map testMap2 = [a: 3, b: 4]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is a non-empty list'
        !resultant.isEmpty()

        then: 'the resultant does not contain a delete operation'
        resultant.find { ((ISedanOperation) it).type == OperationType.DELETE } == null
    }

    def "difference(): should not add an `update` operation, when just one add is needed"() {

        given: 'two test maps'
        Map testMap1 = [a: 3]
        Map testMap2 = [a: 3, b: 4]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is a non-empty list'
        !resultant.isEmpty()

        then: 'the resultant does not contain an update operation'
        resultant.find { ((ISedanOperation) it).type == OperationType.UPDATE } == null
    }

    def "difference(): should add a `delete` operation, when just one delete is needed"() {

        given: 'two test maps'
        Map testMap1 = [a: 3, b: 4]
        Map testMap2 = [a: 3]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List<ISedanOperation> resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is a non-empty list'
        !resultant.isEmpty()

        then: 'the resultant contains a delete operation'
        resultant.find { ((ISedanOperation) it).type == OperationType.DELETE }
    }

    def "difference(): should not add an `update` operation, when just one delete is needed"() {

        given: 'two test maps'
        Map testMap1 = [a: 3, b: 4]
        Map testMap2 = [a: 3]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List<ISedanOperation> resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is a non-empty list'
        !resultant.isEmpty()

        then: 'the resultant does not contain an update operation'
        resultant.find { ((ISedanOperation) it).type == OperationType.UPDATE } == null
    }

    def "difference(): should not add an `add` operation, when just one delete is needed"() {

        given: 'two test maps'
        Map testMap1 = [a: 3, b: 4]
        Map testMap2 = [a: 3]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List<ISedanOperation> resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is a non-empty list'
        !resultant.isEmpty()

        then: 'the resultant does not contain an add operation'
        resultant.find { ((ISedanOperation) it).type == OperationType.ADD } == null
    }

    def "difference(): should add an `update` operation, when just one update is needed"() {

        given: 'two test maps'
        Map testMap1 = [a: 3]
        Map testMap2 = [a: 4]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is a non-empty list'
        !resultant.isEmpty()

        then: 'the resultant contains an update operation'
        resultant.find { ((ISedanOperation) it).type == OperationType.UPDATE } != null
    }

    def "difference(): should not add an `add` operation, when just one update is needed"() {

        given: 'two test maps'
        Map testMap1 = [a: 3]
        Map testMap2 = [a: 4]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is a non-empty list'
        !resultant.isEmpty()

        then: 'the resultant does not contain an addition operation'
        resultant.find { ((ISedanOperation) it).type == OperationType.ADD } == null
    }

    def "difference(): should not add a `delete` operation, when just one update is needed"() {

        given: 'two test maps'
        Map testMap1 = [a: 3]
        Map testMap2 = [a: 4]

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the difference is calculated between the maps'
        List resultant = testSedanDriver.difference(testMap1, testMap2)

        then: 'the resultant is a non-empty list'
        !resultant.isEmpty()

        then: 'the resultant does not contain a delete operation'
        resultant.find { ((ISedanOperation) it).type == OperationType.DELETE } == null
    }

    def "hasDifferentValueAtKey(): should return true when there is a different value. Single index maps."() {

        given: 'two test Sedan Data sets'
        ISedanData testData1 = sedanData([a: 3])
        ISedanData testData2 = sedanData([a: 5])

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testData1, testData2, 'a')

        then: 'the resultant is true'
        resultant
    }

    def "hasDifferentValueAtKey(): should return true when there is a different value. Multi-index maps."() {

        given: 'two test Sedan Data sets'
        ISedanData testData1 = sedanData([a: 3, b: 52, c: 99])
        ISedanData testData2 = sedanData([a: 3, b: 52, c: 100])

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testData1, testData2, 'c')

        then: 'the resultant is true'
        resultant
    }

    def "hasDifferentValueAtKey(): should return false when there is not different value. Multi-index maps."() {

        given: 'two test Sedan Data sets'
        ISedanData testData1 = sedanData([a: 32, b: 52, c: 99])
        ISedanData testData2 = sedanData([a: 33, b: 52, c: 100])

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testData1, testData2, 'b')

        then: 'the resultant is false'
        !resultant
    }

    def "hasDifferentValueAtKey(): should return false when there is not different value. Single index maps."() {

        given: 'two test Sedan Data sets'
        ISedanData testData1 = sedanData([a: 32])
        ISedanData testData2 = sedanData([a: 32])

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testData1, testData2, 'a')

        then: 'the resultant is false'
        !resultant
    }

    def "hasDifferentValueAtKey(): should return false when the key does not exist in either map. Single index maps."() {

        given: 'two test Sedan Data sets'
        ISedanData testData1 = sedanData([a: 32])
        ISedanData testData2 = sedanData([a: 32])

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testData1, testData2, 'b')

        then: 'the resultant is false'
        !resultant
    }

    def "hasDifferentValueAtKey(): should return true when the key does exist in the first map. Single index maps."() {

        given: 'two test Sedan Data sets'
        ISedanData testData1 = sedanData([b: 32])
        ISedanData testData2 = sedanData([a: 32])

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists in the second map'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testData1, testData2, 'a')

        then: 'the resultant is true'
        resultant
    }

    def "hasDifferentValueAtKey(): should return true when the key does exist in the second map. Single index maps."() {

        given: 'two test Sedan Data sets'
        ISedanData testData1 = sedanData([b: 32])
        ISedanData testData2 = sedanData([a: 32])

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the hasDifferentValueAtKey method is invoked between the maps, with a key that exists in the first map'
        boolean resultant = testSedanDriver.hasDifferentValueAtKey(testData1, testData2, 'b')

        then: 'the resultant is true'
        resultant
    }

    def "createUpdateOperation(): should delegate to OperationContextAssembler for OperationContext instance."() {

        given: 'some test data'
        Object testName = 'Sports'
        Object testValue = 'Football'

        and: 'a globally mocked OperationContextAssembler'
        GroovyMock(OperationContextAssembler, global: true)

        and: 'a globally mocked SedanOperationFactory'
        GroovyMock(SedanOperationFactory, global: true)

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the createUpdateOperation() method is invoked '
        testSedanDriver.createUpdateOperation(testName, testValue)

        then: 'the OperationContextAssembler was invoked with the proper params'
        1 * OperationContextAssembler.createUpdateContext(testName, testValue)
    }

    def "createUpdateOperation(): should invoke the SedanOperationFactory with the Context from OperationContextAssembler."() {

        given: 'a mocked IOperationContext'
        IOperationContext mockContext = Mock(IOperationContext)

        and: 'a globally mocked OperationContextAssembler'
        GroovyMock(OperationContextAssembler, global: true)

        and: 'the assembler is stubbed to always return our mocked Context'
        OperationContextAssembler.createUpdateContext(_, _) >> mockContext

        and: 'a globally mocked SedanOperationFactory'
        GroovyMock(SedanOperationFactory, global: true)

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the createUpdateOperation() method is invoked '
        testSedanDriver.createUpdateOperation('these values', 'dont matter')

        then: 'the SedanOperationFactory was invoked with the proper params'
        1 * SedanOperationFactory.construct(mockContext)
    }

    def "createUpdateOperation(): should return the value returned by the SedanOperationFactory."() {

        given: 'a mocked ISedanOperation'
        ISedanOperation mockOperation = Mock(ISedanOperation)

        and: 'a globally mocked OperationContextAssembler'
        GroovyMock(OperationContextAssembler, global: true)

        and: 'the assembler is stubbed to always return a mocked Context'
        OperationContextAssembler.createUpdateContext(_, _) >> Mock(IOperationContext)

        and: 'a globally mocked SedanOperationFactory'
        GroovyMock(SedanOperationFactory, global: true)

        and: 'the factory is stubbed to always return our mocked Operation'
        SedanOperationFactory.construct(_) >> mockOperation

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the createUpdateOperation() method is invoked'
        ISedanOperation resultant = testSedanDriver.createUpdateOperation('these values', 'dont matter')

        then: 'the resultant SedanOperation is equal to the value stubbed to return from the factory'
        resultant == mockOperation
    }

    def "createAddOperation(): should delegate to OperationContextAssembler for OperationContext instance."() {

        given: 'some test data'
        Object testName = 'Sports'
        Object testValue = 'Football'

        and: 'a globally mocked OperationContextAssembler'
        GroovyMock(OperationContextAssembler, global: true)

        and: 'a globally mocked SedanOperationFactory'
        GroovyMock(SedanOperationFactory, global: true)

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the createAddOperation() method is invoked '
        testSedanDriver.createAddOperation(testName, testValue)

        then: 'the OperationContextAssembler was invoked with the proper params'
        1 * OperationContextAssembler.createAddContext(testName, testValue)
    }

    def "createAddOperation(): should invoke the SedanOperationFactory with the Context from OperationContextAssembler."() {

        given: 'a mocked IOperationContext'
        IOperationContext mockContext = Mock(IOperationContext)

        and: 'a globally mocked OperationContextAssembler'
        GroovyMock(OperationContextAssembler, global: true)

        and: 'the assembler is stubbed to always return our mocked Context'
        OperationContextAssembler.createAddContext(_, _) >> mockContext

        and: 'a globally mocked SedanOperationFactory'
        GroovyMock(SedanOperationFactory, global: true)

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the createUpdateOperation() method is invoked '
        testSedanDriver.createAddOperation('these values', 'dont matter')

        then: 'the SedanOperationFactory was invoked with the proper params'
        1 * SedanOperationFactory.construct(mockContext)
    }

    def "createAddOperation(): should return the value returned by the SedanOperationFactory."() {

        given: 'a mocked ISedanOperation'
        ISedanOperation mockOperation = Mock(ISedanOperation)

        and: 'a globally mocked OperationContextAssembler'
        GroovyMock(OperationContextAssembler, global: true)

        and: 'the assembler is stubbed to always return a mocked Context'
        OperationContextAssembler.createAddContext(_, _) >> Mock(IOperationContext)

        and: 'a globally mocked SedanOperationFactory'
        GroovyMock(SedanOperationFactory, global: true)

        and: 'the factory is stubbed to always return our mocked Operation'
        SedanOperationFactory.construct(_) >> mockOperation

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the createUpdateOperation() method is invoked'
        ISedanOperation resultant = testSedanDriver.createAddOperation('these values', 'dont matter')

        then: 'the resultant SedanOperation is equal to the value stubbed to return from the factory'
        resultant == mockOperation
    }

    def "createDeleteOperation(): should delegate to OperationContextAssembler for OperationContext instance."() {

        given: 'some test data'
        Object testName = 'Sports'

        and: 'a globally mocked OperationContextAssembler'
        GroovyMock(OperationContextAssembler, global: true)

        and: 'a globally mocked SedanOperationFactory'
        GroovyMock(SedanOperationFactory, global: true)

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the createDeleteOperation() method is invoked '
        testSedanDriver.createDeleteOperation(testName)

        then: 'the OperationContextAssembler was invoked with the proper params'
        1 * OperationContextAssembler.createDeleteContext(testName)
    }

    def "createDeleteOperation(): should invoke the SedanOperationFactory with the Context from OperationContextAssembler."() {

        given: 'a mocked IOperationContext'
        IOperationContext mockContext = Mock(IOperationContext)

        and: 'a globally mocked OperationContextAssembler'
        GroovyMock(OperationContextAssembler, global: true)

        and: 'the assembler is stubbed to always return our mocked Context'
        OperationContextAssembler.createDeleteContext(_) >> mockContext

        and: 'a globally mocked SedanOperationFactory'
        GroovyMock(SedanOperationFactory, global: true)

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the createDeleteOperation() method is invoked '
        testSedanDriver.createDeleteOperation('these values')

        then: 'the SedanOperationFactory was invoked with the proper params'
        1 * SedanOperationFactory.construct(mockContext)
    }

    def "createDeleteOperation(): should return the value returned by the SedanOperationFactory."() {

        given: 'a mocked ISedanOperation'
        ISedanOperation mockOperation = Mock(ISedanOperation)

        and: 'a globally mocked OperationContextAssembler'
        GroovyMock(OperationContextAssembler, global: true)

        and: 'the assembler is stubbed to always return a mocked Context'
        OperationContextAssembler.createDeleteContext(_) >> Mock(IOperationContext)

        and: 'a globally mocked SedanOperationFactory'
        GroovyMock(SedanOperationFactory, global: true)

        and: 'the factory is stubbed to always return our mocked Operation'
        SedanOperationFactory.construct(_) >> mockOperation

        and: 'a Sedan Driver'
        SedanDriver testSedanDriver = new SedanDriver()

        when: 'the createDeleteOperation() method is invoked'
        ISedanOperation resultant = testSedanDriver.createDeleteOperation('these values')

        then: 'the resultant SedanOperation is equal to the value stubbed to return from the factory'
        resultant == mockOperation
    }

}
