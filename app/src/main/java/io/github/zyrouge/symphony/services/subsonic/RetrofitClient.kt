package io.github.zyrouge.symphony.services.subsonic

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import io.github.zyrouge.symphony.App
import io.github.zyrouge.symphony.services.subsonic.Subsonic
import io.github.zyrouge.symphony.services.subsonic.utils.CacheUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient(subsonic: Subsonic) {
    var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(subsonic.url)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
                )
            )
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(getOkHttpClient())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val cacheUtil = CacheUtil(60, 60 * 60 * 24 * 30)

        // BrowsingClient 60
        // MediaAnnotationClient 0
        // MediaLibraryScanningClient 0
        // MediaRetrievalClient 0
        // PlaylistClient 0
        // PodcastClient 60
        // SearchClient 60
        // SystemClient 60
        // AlbumSongListClient 60

        return OkHttpClient.Builder()
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(getHttpLoggingInterceptor())
            .addInterceptor(cacheUtil.offlineInterceptor)
            // .addNetworkInterceptor(cacheUtil.onlineInterceptor)
            .addInterceptor(ChuckerInterceptor.Builder(App.getInstance()).build())
            .cache(getCache())
            .build()
    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }

    private fun getCache(): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(App.getContext().cacheDir, cacheSize.toLong())
    }
}