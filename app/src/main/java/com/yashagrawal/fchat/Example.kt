package com.yashagrawal.fchat

class Example {
}

fun main() {
    var n = 5
    for(i in 1..5){
        for (j in 5-1 downTo i){
            print("  ")
        }
        for (k in 1..i){
            print("* ")
        }
        for (l in 2..i){
            print("* ")
        }
        println()
    }
}