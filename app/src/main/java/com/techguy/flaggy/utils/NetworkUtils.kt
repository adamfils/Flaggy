package com.techguy.flaggy.utils

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.google.gson.Gson
import com.techguy.flaggy.BuildConfig
import com.techguy.flaggy.model.CountryData
import com.techguy.flaggy.model.CountryModel
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONArray
import kotlin.coroutines.resume

object NetworkUtils {

    suspend fun fetchCountries(): CountryData = suspendCancellableCoroutine { continuation ->
        AndroidNetworking.get(BuildConfig.BASE_URL)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    val model =
                        Gson().fromJson(response.toString(), Array<CountryModel>::class.java)
                            .toCollection(ArrayList())
                    val data = CountryData()
                    data.countries = model
                    data.networkCode = 200
                    continuation.resume(data)
                }

                override fun onError(anError: ANError?) {
                    val data = CountryData()
                    data.networkCode = anError?.errorCode ?: 0
                    continuation.resume(data)
                    anError?.logError()
                }
            })
    }

    fun Any.log(prefix: String = "Flaggy") {
        if (!BuildConfig.DEBUG) return
        Log.e("FLAGGY", "$prefix: $this")
    }

    fun ANError.logError() {
        localizedMessage?.log("AnError Localized Message")
        message?.log("AnError Message")
        errorCode.log("AnError Code")
        errorBody?.log("AnError Body")
        errorDetail?.log("AnError Detail")
    }
}