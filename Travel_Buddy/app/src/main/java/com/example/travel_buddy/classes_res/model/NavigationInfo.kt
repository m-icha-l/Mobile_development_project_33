package com.example.travel_buddy.classes_res.model

import retrofit2.http.GET
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Objects

data class RouteResponse(
    val routes: List<Route>
)

data class Route(
    val summary: RouteSummary
)

data class RouteSummary(
    val lengthInMeters: Int,
    val travelTimeInSeconds: Int,
    val trafficDelayInSeconds: Int,
    val trafficLengthInMeters: Int,
    val departureTime: String,
    val arrivalTime: String
)

const val BASE_URL = "https://api.tomtom.com"
interface DirectionsApi {

    @GET("/routing/1/calculateRoute/{locations}/json?&instructionsType=text&instructionAnnouncementPoints=all&instructionPhonetics=LHP&instructionRoadShieldReferences=all&key=XeiqdyGb8PxtA4ssGRrmpp5EFxDxDFuT")
    suspend fun getRoute(
        @Path("locations") locations: String,
        @Query("language") language: String,
        @Query("traffic") canShowTrafficDelays: Boolean,
        @Query("travelMode") type: String
    ): RouteResponse
    companion object {
        var directionsService : DirectionsApi? = null

        fun getInstance(): DirectionsApi {
            if(directionsService === null){
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                val client = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
                directionsService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(DirectionsApi::class.java)
            }
            return directionsService!!
        }
    }
}