package com.freegym.ej.freegym

class Place {
    var id: Int = 0
    var nome: String = ""
    var logradouro: String = ""
    var bairro: String = ""
    var lat: Double = 0.0
    var long: Double = 0.0

    constructor(nome: String, logradouro: String, bairro: String, lat: Double, long: Double) {
        this.nome = nome
        this.logradouro = logradouro
        this.bairro = bairro
        this.lat = lat
        this.long = long
    }
}