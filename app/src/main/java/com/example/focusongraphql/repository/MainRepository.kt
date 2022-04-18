package com.example.focusongraphql.repository

import com.apollographql.apollo3.ApolloClient
import com.example.focusongraphql.FindContriesOfAContinentQuery
import com.example.focusongraphql.GetContinentsQuery

class MainRepository(private val apolloClient: ApolloClient) {

    suspend fun getContinents() =
        apolloClient.query(GetContinentsQuery()).execute()

    suspend fun getCountriesOfSelectedContinent(continentCode: String) =
        apolloClient.query(FindContriesOfAContinentQuery(continentCode)).execute()

}