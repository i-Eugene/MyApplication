package com.ieugene.kotlinlab.kotlin

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import java.lang.Exception
import kotlin.system.*

fun main() {

    var a = "1"
    val b = a.run {
        println("11 $length")
        a += "2"
        println("22 $length")
    }
    println("33 ${a.length}")
    println(a)
    println(b)

//    with(a) {
//        println("11 $length")
//        a += "2"
//        println("22 $length")
//    }
//    println("33 ${a.length}")
//    println(a)

//    a.let {
//        println("11 ${it.length}")
//        a += "2"
//        println("22 ${it.length}")
//    }
//    println("33 ${a.length}")
//    println(a)


//    a.apply {
//        println("11 $length")
//        a += "2"
//        println("22 $length")
//    }
//    println("33 ${a.length}")
//    println(a)
}

data class Channel(var a: String)