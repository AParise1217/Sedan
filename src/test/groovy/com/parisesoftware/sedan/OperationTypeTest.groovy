package com.parisesoftware.sedan

import spock.lang.Specification

class OperationTypeTest extends Specification {

    def "DELETE.toString(): should equal `delete`."() {

        when: 'the DELETE value is pulled'
        String resultant = OperationType.DELETE

        then: 'the resultant is equal to `delete`'
        resultant == 'delete'
    }

    def "UPDATE.toString(): should equal `update`."() {

        when: 'the UPDATE value is pulled from the driver'
        String resultant = OperationType.UPDATE

        then: 'the resultant is equal to `update`'
        resultant == 'update'
    }

    def "ADD.toString(): should equal `add`."() {

        when: 'the ADD value is pulled from the driver'
        String resultant = OperationType.ADD

        then: 'the resultant is equal to `add`'
        resultant == 'add'
    }

}
