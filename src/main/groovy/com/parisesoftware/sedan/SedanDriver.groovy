package com.parisesoftware.sedan

class SedanDriver {

    List difference(Map source, Map target) {
        List result = []

        source.keySet().each { key ->
            if(target.containsKey(key)) {
                if(source[key] != target[key]) {
                    result.add([operation: 'update', name: key, value: target[key]])
                }
            } else {
                result.add([operation: 'delete', name: key])
            }
        }

        target.keySet().each { key ->
            if(!source.containsKey(key)) {
                result.add([operation: 'add', name: key, value: target[key]])
            }
        }
        return result
    }

}
