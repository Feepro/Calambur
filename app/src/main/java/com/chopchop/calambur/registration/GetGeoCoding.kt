package com.chopchop.calambur.registration

import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import java.io.StringReader

class GetGeoCoding {
    val ACCESS_GEOCODING_TOKEN = "pk.a879b90ddeff0832850ffe3ade551f91"
    private val urlGetGeocoding = "https://api.locationiq.com/v1/autocomplete.php?key=$ACCESS_GEOCODING_TOKEN"
    private fun getGeoCodingRequest(city:String): Request {
        return Fuel.get("$urlGetGeocoding&q=$city&limit=1&countrycodes=ru&accept-language=ru")

    }
    suspend fun getGeoCoding(city:String): GeoCodeModel? {
        var geoCodeModel : GeoCodeModel?
        try {
            val (request, responce, result) = getGeoCodingRequest(city).awaitStringResponseResult()
            println(request)
            println(responce)
            println(result)
            val geo = GeoCodeModel(
                name = Klaxon().parseJsonArray(StringReader(result.component1())).obj("address").string("name")[0],
                state = Klaxon().parseJsonArray(StringReader(result.component1())).obj("address").string("state")[0],
            )
            return geo

        } catch (e: FuelError) {
            println(e.message)
            return null
        }
    }
    class GeoCodeModel (
        var name:String?,
        var state:String?,
        //var display_name:String?,
    )
}