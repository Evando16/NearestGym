package com.freegym.ej.freegym.model

class Gym {
    var id: Int = 0
    var name: String = ""
    var streetName: String = ""
    var neighborhood: String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    constructor(
            name: String,
            streetName: String,
            neighborhood: String,
            latitude: Double,
            longitude: Double
    ) {
        this.name = name
        this.streetName = streetName
        this.neighborhood = neighborhood
        this.latitude = latitude
        this.longitude = longitude
    }
}