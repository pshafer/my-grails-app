package com.example

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MakeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Make.list(params), model:[makeCount: Make.count()]
    }

    def show(Make make) {
        respond make
    }

    def create() {
        respond new Make(params)
    }

    @Transactional
    def save(Make make) {
        if (make == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (make.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond make.errors, view:'create'
            return
        }

        make.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'make.label', default: 'Make'), make.id])
                redirect make
            }
            '*' { respond make, [status: CREATED] }
        }
    }

    def edit(Make make) {
        respond make
    }

    @Transactional
    def update(Make make) {
        if (make == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (make.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond make.errors, view:'edit'
            return
        }

        make.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'make.label', default: 'Make'), make.id])
                redirect make
            }
            '*'{ respond make, [status: OK] }
        }
    }

    @Transactional
    def delete(Make make) {

        if (make == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        make.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'make.label', default: 'Make'), make.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'make.label', default: 'Make'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
