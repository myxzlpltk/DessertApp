package com.bangkit.dessert.core.di

import android.content.Context
import com.bangkit.dessert.core.BuildConfig
import com.bangkit.dessert.core.data.source.remote.network.DessertApiService
import com.bangkit.dessert.core.data.source.remote.network.SSLCertificateConfigurator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Arrays
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        // Trust manager
        val trustManagerFactory = SSLCertificateConfigurator.getTrustManager(context)
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException(
                "Unexpected default trust managers:" + Arrays.toString(trustManagers)
            )
        }
        val trustManager = trustManagers[0] as X509TrustManager

        return OkHttpClient.Builder()
            .sslSocketFactory(
                SSLCertificateConfigurator.getSSLConfiguration(context).socketFactory,
                trustManager
            )
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): DessertApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(DessertApiService::class.java)
    }
}