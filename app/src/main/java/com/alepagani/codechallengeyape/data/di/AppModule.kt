package com.alepagani.codechallengeyape.data.di

import com.alepagani.codechallengeyape.core.AppConstant.BASE_URL
import com.alepagani.codechallengeyape.data.RepositoryImpl
import com.alepagani.codechallengeyape.data.interceptor.AuthInterceptor
import com.alepagani.codechallengeyape.data.remote.RemoteRecipesDataSource
import com.alepagani.codechallengeyape.data.remote.TastyApiService
import com.alepagani.codechallengeyape.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun provideTastyApiServices(retrofit: Retrofit): TastyApiService {
        return retrofit.create(TastyApiService::class.java)
    }

    @Provides
    fun provideRepository(remoteDataSource: RemoteRecipesDataSource): Repository {
        return RepositoryImpl(remoteDataSource)
    }

    @Provides
    fun provideRemoteDataSource(apiService: TastyApiService): RemoteRecipesDataSource {
        return RemoteRecipesDataSource(apiService)
    }

}