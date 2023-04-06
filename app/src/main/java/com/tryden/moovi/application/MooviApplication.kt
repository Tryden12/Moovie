package com.tryden.moovi.application

import android.app.Application
import android.content.Context

class MooviApplication: Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}