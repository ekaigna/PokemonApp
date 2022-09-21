package com.example.pokemonapp

fun formatNumber (number : Int): String {
   return "Nº" + number.toString().padStart(3, '0')
}

fun formatName (name : String): String {
    return name.substring(0, 1).uppercase() + name.substring(1).lowercase();
}