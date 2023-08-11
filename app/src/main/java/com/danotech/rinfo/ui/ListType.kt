package com.danotech.rinfo.ui

class ListType<T>(val items: MutableList<T> = mutableListOf()) {
    // Other methods and properties can go here
}

fun <T> printList(list: List<T>) {
    for (item in list) {
        println(item)
    }
}
