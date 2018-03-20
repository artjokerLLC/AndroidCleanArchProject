package com.project.source.data.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    fun provideApi(@Named(RETROFIT_APP) retrofit: Retrofit): AppServerSpec =
            retrofit.create(AppServerSpec::class.java)

    @Provides
    fun providePaymentApi(@Named(RETROFIT_PAYMENT) retrofit: Retrofit): PaymentServerSpec =
            retrofit.create(PaymentServerSpec::class.java)

    @Provides
    @Named(RETROFIT_APP)
    fun provideRetrofit(client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(SimpleXmlConverterFactory.create(Persister(AnnotationStrategy())))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

    @Provides
    @Named(RETROFIT_PAYMENT)
    fun providePaymentRetrofit(client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(SimpleXmlConverterFactory.create(Persister(AnnotationStrategy())))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
            OkHttpClient.Builder()
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build()


    @Provides
    fun provideLoggingInterceptor() =
            HttpLoggingInterceptor()
                    .apply { level = HttpLoggingInterceptor.Level.BODY }

    companion object {
        const val TIMEOUT = 25L
        const val RETROFIT_APP = "AppRetrofit"
        const val RETROFIT_PAYMENT = "PaymentRetrofit"
    }
}
