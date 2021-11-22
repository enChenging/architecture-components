package com.release.architecture.base.utils

import com.orhanobut.logger.Logger

/** 对[Logger]的封装，避免多个相同的Logger类名导致写代码时选择麻烦 */

fun logD(message: String, tag: String = "") {
    if (tag.isNotEmpty()) {
        Logger.t(tag).d(message)
    } else {
        Logger.d(message)
    }
}

fun logE(throwable: Throwable, message: String, tag: String = "") {
    if (tag.isNotEmpty()) {
        Logger.t(tag).e(throwable, message)
    } else {
        Logger.e(throwable, message)
    }
}

fun logE(message: String, tag: String = "") {
    if (tag.isNotEmpty()) {
        Logger.t(tag).e(message)
    } else {
        Logger.e(message)
    }
}

fun logI(message: String, tag: String = "") {
    if (tag.isNotEmpty()) {
        Logger.t(tag).i(message)
    } else {
        Logger.i(message)
    }
}

fun logV(message: String, tag: String = "") {
    if (tag.isNotEmpty()) {
        Logger.t(tag).v(message)
    } else {
        Logger.v(message)
    }
}

fun logW(message: String, tag: String = "") {
    if (tag.isNotEmpty()) {
        Logger.t(tag).w(message)
    } else {
        Logger.w(message)
    }
}

fun logWTF(message: String, tag: String = "") {
    if (tag.isNotEmpty()) {
        Logger.t(tag).wtf(message)
    } else {
        Logger.wtf(message)
    }
}

fun logJSON(json: String, tag: String = "") {
    if (tag.isNotEmpty()) {
        Logger.t(tag).json(json)
    } else {
        Logger.json(json)
    }
}

fun logXML(xml: String, tag: String = "") {
    if (tag.isNotEmpty()) {
        Logger.t(tag).xml(xml)
    } else {
        Logger.xml(xml)
    }
}