package com.example.focusongraphql.di

import com.apollographql.apollo3.ApolloClient
import com.example.focusongraphql.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMainRepository(apolloClient: ApolloClient): MainRepository {
        return MainRepository(apolloClient)
    }

}