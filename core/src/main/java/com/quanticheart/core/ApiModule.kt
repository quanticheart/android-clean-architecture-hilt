package com.quanticheart.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiModule {

    fun provideBaseUrl() = BuildConfig.BASE_URL

    fun provideConnectionTimeout() = 60L

    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }

        OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .addInterceptor(requestInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    inline fun <reified T> provideRetrofit(baseUrl: String? = null): T =
        Retrofit.Builder()
            .baseUrl(baseUrl ?: provideBaseUrl())
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .build()
            .create(T::class.java)
}