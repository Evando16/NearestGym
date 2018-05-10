package com.freegym.ej.freegym

class Place {
    var id: Int = 0
    var nome: String = ""
    var logradouro: String = ""
    var bairro: String = ""
    var lat: Int = 0
    var long: Int = 0

    constructor(nome: String, logradouro: String, bairro: String, lat: Int, long: Int) {
        this.nome = nome
        this.logradouro = logradouro
        this.bairro = bairro
        this.lat = lat
        this.long = long
    }
}