package com.danotech.rinfo.ui.components

import androidx.compose.ui.graphics.vector.ImageVector
import com.danotech.rinfo.ui.components.rinfo.Contactus
import com.danotech.rinfo.ui.components.rinfo.Dark
import com.danotech.rinfo.ui.components.rinfo.Darksolid
import com.danotech.rinfo.ui.components.rinfo.Delete
import com.danotech.rinfo.ui.components.rinfo.Feed
import com.danotech.rinfo.ui.components.rinfo.Feedback
import com.danotech.rinfo.ui.components.rinfo.Home
import com.danotech.rinfo.ui.components.rinfo.Homesolid
import com.danotech.rinfo.ui.components.rinfo.Light
import com.danotech.rinfo.ui.components.rinfo.Lightsolid
import com.danotech.rinfo.ui.components.rinfo.Logoout
import com.danotech.rinfo.ui.components.rinfo.Notification
import com.danotech.rinfo.ui.components.rinfo.Notificationon
import com.danotech.rinfo.ui.components.rinfo.Notificationsolid
import com.danotech.rinfo.ui.components.rinfo.Password
import com.danotech.rinfo.ui.components.rinfo.Search
import com.danotech.rinfo.ui.components.rinfo.Settings
import com.danotech.rinfo.ui.components.rinfo.Verified
import com.danotech.rinfo.ui.components.rinfo.Whatsnew
import kotlin.collections.List as ____KtList

public object Rinfo

private var __Icons: ____KtList<ImageVector>? = null

public val Rinfo.Icons: ____KtList<ImageVector>
  get() {
    if (__Icons != null) {
      return __Icons!!
    }
    __Icons= listOf(Search, Home, Darksolid, Verified, Logoout, Whatsnew, Homesolid, Lightsolid,
        Settings, Dark, Feed, Notificationsolid, Notification, Feedback, Notificationon, Delete,
        Contactus, Password, Light)
    return __Icons!!
  }
