package myapp

import com.example.Make
import com.example.Model
import com.example.Vehicle

class BootStrap {

    def init = { servletContext ->

    	def nissan = new Make(name: "Nissan").save()
    	def ford = new Make(name: "Ford").save()

    	def titan = new Model(name: "Titan", make: nissan).save()
    	def leaf = new Model(name: "Leaf", make: nissan).save()
    	def escape = new Model(name: "Escape", make: ford).save()

    	new Vehicle(name: "Pickup",  make: nissan, model: titan, year: 2012).save()
        new Vehicle(name: "Economy", make: nissan, model: leaf, year: 2014).save()
        new Vehicle(name: "SUV", make: ford, model: escape, year: 2017).save()
    }
    def destroy = {

    }
}
