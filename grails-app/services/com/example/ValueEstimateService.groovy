package com.example

import grails.transaction.Transactional

@Transactional
class ValueEstimateService {

    def getEstimate(Vehicle vehicle) {
        log.info "Estimating vehicle value..."

        def value = Math.round (vehicle.name.size() + vehicle.model.name.size() + vehicle.year) * 2

        return value
    }
}
