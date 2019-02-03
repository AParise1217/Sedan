package com.parisesoftware.sedan.data

import com.parisesoftware.sedan.data.impl.SedanDataMapAdapter
import com.parisesoftware.sedan.domain.IAmAFakeSedanDataType
import org.apache.commons.lang3.NotImplementedException
import spock.lang.Specification

class SedanDataFactoryTest extends Specification {

    def "construct(): should throw NotImplementedException, when an unknown type is passed in"() {

        given: 'an unknown type mock'
        IAmAFakeSedanDataType unknownType = Mock(IAmAFakeSedanDataType)

        when: 'SedanDataFactory.construct() is invoked with the unknown type'
        SedanDataFactory.construct(unknownType)

        then: 'a NotImplementedException is thrown'
        thrown(NotImplementedException)
    }

    def "construct(): should delegate to the SedanDataMapAdapter, when a Map is passed in"() {

        given: 'a Map mock'
        Map testMap = Mock(Map)

        and: 'a global SedanDataMapAdapterMock'
        GroovyMock(SedanDataMapAdapter, global: true)

        when: 'SedanDataFactory.construct() is invoked with the Map'
        SedanDataFactory.construct(testMap)

        then: 'the SedanDataMapAdapter constructor is invoked'
        1 * new SedanDataMapAdapter(testMap)
    }

}
