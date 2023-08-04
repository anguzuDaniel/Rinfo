
package com.danotech.rinfo.model.service

interface LogService {
  fun logNonFatalCrash(throwable: Throwable)
}
