package com.example.nal_oreo.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SupabaseClient {
    private const val BASE_URL = "https://rcegxrykakrihvpauqum.supabase.co/rest/v1/"
    private const val API_KEY = "sb_publishable_5fwcz23EC6D-PfyV0-VBAg_bRNrNwpm"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer $API_KEY")
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "return=minimal")
                .build()
            chain.proceed(request)
        }
        .build()

    val apiService: SupabaseApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SupabaseApiService::class.java)
    }
}