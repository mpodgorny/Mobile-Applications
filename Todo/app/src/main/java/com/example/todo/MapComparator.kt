package com.example.todo

internal class MapComparator(private val key: String) : Comparator<Map<String, String>> {

    override fun compare(
        first: Map<String, String>,
        second: Map<String, String>
    ): Int {
        val firstValue = first[key]
        val secondValue = second[key]
        return firstValue!!.compareTo(secondValue!!)
    }
}