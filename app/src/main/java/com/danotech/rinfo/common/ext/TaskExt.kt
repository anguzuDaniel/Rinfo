package com.danotech.rinfo.common.ext

import com.danotech.rinfo.model.Review


fun Review?.hasDueDate(): Boolean {
    return this?.hasDueTime() == true
}

fun Review?.hasDueTime(): Boolean {
    return this?.hasDueTime() == true
}
