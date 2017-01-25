package com.example

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ModelController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Model.list(params), model:[modelCount: Model.count()]
    }

    def show(Model model) {
        respond model
    }

    def create() {
        respond new Model(params)
    }

    @Transactional
    def save(Model model) {
        if (model == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (model.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond model.errors, view:'create'
            return
        }

        model.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'model.label', default: 'Model'), model.id])
                redirect model
            }
            '*' { respond model, [status: CREATED] }
        }
    }

    def edit(Model model) {
        respond model
    }

    @Transactional
    def update(Model model) {
        if (model == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (model.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond model.errors, view:'edit'
            return
        }

        model.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'model.label', default: 'Model'), model.id])
                redirect model
            }
            '*'{ respond model, [status: OK] }
        }
    }

    @Transactional
    def delete(Model model) {

        if (model == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        model.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'model.label', default: 'Model'), model.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'model.label', default: 'Model'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
