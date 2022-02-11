package com.example.roblemejorado.model

class Usuario(){
    var usuario: String? =null
    var nombre: String? =null
    var centro_estudios :String? = null
    var apellidos : String?= null

    constructor(usuario:String?,nombre:String?,centro_estudios:String?,apellidos:String?):this(){
        this.usuario=usuario!!
        this.nombre=nombre
        this.centro_estudios=centro_estudios
        this.apellidos=apellidos
    }
}