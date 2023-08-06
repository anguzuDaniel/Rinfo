package com.danotech.rinfo.common.ext

import com.danotech.rinfo.ui.components.Review


fun Review?.hasDueDate(): Boolean {
    return this?.hasDueTime() == true
}

fun Review?.hasDueTime(): Boolean {
    return this?.hasDueTime() == true
}
