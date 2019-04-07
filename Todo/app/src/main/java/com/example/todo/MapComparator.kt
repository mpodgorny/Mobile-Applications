package com.example.todo

import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal class MapComparator(private val key: String) : Comparator<Map<String, String>> {

    override fun compare(
        first: Map<String, String>,
        second: Map<String, String>
    ): Int {

        var firstValue = first[key]
        var secondValue = second[key]
        when(key){
            "date" ->{
                val firstValue = LocalDate.parse(firstValue, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                val secondValue = LocalDate.parse(secondValue, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                return firstValue!!.compareTo(secondValue!!)
                }
                else ->{
                return firstValue!!.compareTo(secondValue!!)
                }
        }
    }
}