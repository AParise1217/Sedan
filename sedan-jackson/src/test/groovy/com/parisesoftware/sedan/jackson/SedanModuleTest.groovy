package com.parisesoftware.sedan.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.parisesoftware.sedan.operation.AdditionSedanOperation
import com.parisesoftware.sedan.operation.DeletionSedanOperation
import com.parisesoftware.sedan.operation.MoveSedanOperation
import com.parisesoftware.sedan.operation.OperationType
import com.parisesoftware.sedan.operation.SedanOperation
import com.parisesoftware.sedan.operation.UpdateSedanOperation
import spock.lang.Shared
import spock.lang.Specification

class SedanModuleTest extends Specification {

    @Shared
    ObjectMapper mapper = new ObjectMapper().registerModule(new SedanModule())

    def "roundtrip: AdditionSedanOperation"() {
        given:
        def op = new AdditionSedanOperation('city', 'NYC')

        when:
        String json = mapper.writeValueAsString(op)
        SedanOperation result = mapper.readValue(json, SedanOperation)

        then:
        result instanceof AdditionSedanOperation
        result.type() == OperationType.ADD
        result.name() == 'city'
        result.value() == 'NYC'
    }

    def "roundtrip: UpdateSedanOperation"() {
        given:
        def op = new UpdateSedanOperation('age', 42)

        when:
        String json = mapper.writeValueAsString(op)
        SedanOperation result = mapper.readValue(json, SedanOperation)

        then:
        result instanceof UpdateSedanOperation
        result.type() == OperationType.UPDATE
        result.name() == 'age'
        result.value() == 42
    }

    def "roundtrip: DeletionSedanOperation"() {
        given:
        def op = new DeletionSedanOperation('role')

        when:
        String json = mapper.writeValueAsString(op)
        SedanOperation result = mapper.readValue(json, SedanOperation)

        then:
        result instanceof DeletionSedanOperation
        result.type() == OperationType.DELETE
        result.name() == 'role'
        result.value() == null
    }

    def "roundtrip: MoveSedanOperation"() {
        given:
        def op = new MoveSedanOperation('hero', 'champion', 'Thor')

        when:
        String json = mapper.writeValueAsString(op)
        SedanOperation result = mapper.readValue(json, SedanOperation)

        then:
        result instanceof MoveSedanOperation
        result.type() == OperationType.MOVE
        (result as MoveSedanOperation).sourceName() == 'hero'
        (result as MoveSedanOperation).targetName() == 'champion'
        result.value() == 'Thor'
    }

    def "serialize: type field uses uppercase enum name"() {
        when:
        String json = mapper.writeValueAsString(new AdditionSedanOperation('x', 1))

        then:
        json.contains('"type":"ADD"')
    }

    def "deserialize: unknown type throws IllegalArgumentException"() {
        when:
        mapper.readValue('{"type":"UNKNOWN","name":"x"}', SedanOperation)

        then:
        thrown(Exception)
    }
}
