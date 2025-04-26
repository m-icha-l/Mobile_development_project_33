package com.example.travel_buddy.classes_res.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class Summary(
    val query: String,
    val numResults: Int,
)

data class Poi(
    val name: String?,
    val phone: String?,
    val url: String?,
)

data class Address(
    val municipality: String?,
    val municipalitySubdivision: String?,
    val countrySubdivision: String?,
    val countrySubdivisionName: String?,
    val country: String?,
    val freeformAddress: String?
)

data class Position(
    val lat: Double,
    val lon: Double
)

data class Geometry(
    val id: String
)

data class DataSources(
    val geometry: Geometry
)

data class SearchResult(
    val type: String,
    val id: String,
    val score: Double,
    val entityType: String,
    val poi: Poi,
    val address: Address,
    val position: Position,
    val dataSources: DataSources
)

data class TomTomSearchResponse(
    val summary: Summary,
    val results: List<SearchResult>
)

const val PLACES_BASE_URL = "https://api.tomtom.com"
interface PlacesApi {
    @GET("search/2/{searchType}/{query}.json?key=XeiqdyGb8PxtA4ssGRrmpp5EFxDxDFuT")
    suspend fun getRoute(
        @Path("searchType") searchType: String,
        @Path("query") query: String,
        @Query("limit") limit: Int = 10,
        @Query("typeahead") typeahead: Boolean = true,
        @Query("entityType") entityType: String = "",
        @Query("countrySet") countrySet: String = "",
        @Query("categorySet") categorySet: String = "",
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
        @Query("radius") radius: Int = 0,
    ): TomTomSearchResponse
    companion object {
        var placesService : PlacesApi? = null

        fun getInstance(): PlacesApi {
            if(placesService === null){
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                val client = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
                placesService = Retrofit.Builder()
                    .baseUrl(PLACES_BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(PlacesApi::class.java)
            }
            return placesService!!
        }
    }
}