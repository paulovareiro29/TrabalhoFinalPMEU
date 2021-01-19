package com.estimote.proximity.estimote

import android.content.Context
import android.util.Log
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials
import com.estimote.proximity_sdk.api.ProximityObserver
import com.estimote.proximity_sdk.api.ProximityObserverBuilder
import com.estimote.proximity_sdk.api.ProximityZoneBuilder
import ipvc.estg.trabalhofinal.MainActivity
import java.lang.NumberFormatException

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

class ProximityContentManager(private val context: Context, val mainActivity: MainActivity) {

    private var proximityObserverHandler: ProximityObserver.Handler? = null

    fun start() {

        val proximityObserver = ProximityObserverBuilder(context, EstimoteCloudCredentials("paulovareiro29-gmail-com-s-ork", "873c154c8107ba0cca4c2bc01608139e"))
                .withTelemetryReportingDisabled()
                .withEstimoteSecureMonitoringDisabled()
                .onError { throwable ->
                    Log.e("app", "proximity observer error: $throwable")
                }
                .withBalancedPowerMode()
                .build()

        val zone = ProximityZoneBuilder()
                .forTag("paulovareiro29-gmail-com-s-ork")
                .inNearRange()
                .onContextChange { contexts ->

                    for (context in contexts) {
                        val idBeacon: String = context.attachments["paulovareiro29-gmail-com-s-ork/title"] ?: "unknown"

                        try {
                            val id = idBeacon.toInt()
                            mainActivity.showInfoPopup(id)
                        }catch (e: NumberFormatException){
                            Log.e("Beacon","Nao deu para transformar ${idBeacon}")
                        }


                    }

                }
                .build()

        proximityObserverHandler = proximityObserver.startObserving(zone)
    }

    fun stop() {
        proximityObserverHandler?.stop()
    }
}
