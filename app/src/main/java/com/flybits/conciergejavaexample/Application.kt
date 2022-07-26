package com.flybits.conciergejavaexample

import android.app.Application
import com.flybits.context.ContextManager.start
import com.flybits.commons.library.api.FlybitsManager
import com.flybits.commons.library.api.idps.AnonymousIDP
import com.flybits.commons.library.api.results.callbacks.BasicResultCallback
import com.flybits.commons.library.exceptions.FlybitsException
import com.flybits.commons.library.logging.VerbosityLevel
import com.flybits.context.ReservedContextPlugin

class Application : Application() {
    lateinit var flybitsManager: FlybitsManager
    override fun onCreate() {
        super.onCreate()
        FlybitsManager.setLoggingVerbosity(VerbosityLevel.ALL)
        flybitsManager = FlybitsManager.Builder(applicationContext)
                .setProjectId("2F0D58EE-B270-4871-8AEF-905226E0088E")
                .setGatewayURL("https://api.development.flybits.com")
                .build()

        flybitsManager.connect(AnonymousIDP(), object : BasicResultCallback {
            override fun onException(exception: FlybitsException) {
                println(" on connect onException")
            }

            override fun onSuccess() {
                println(" on connect onSuccess")

            }

        })
    }

    companion object {
        const val CHANNEL_ID = "com.flybits.concierge.channel.id"
    }
}