package com.chus.clua.data.network

import com.chus.clua.data.network.model.PersonDetailApiModel
import com.chus.clua.data.network.model.PersonMovieCreditsApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface PersonApi {

    @GET(PERSON_PATH)
    suspend fun getPersonDetail(
        @Path(PERSON_ID_PATH) personId: Int
    ): Response<PersonDetailApiModel>

    @GET(PERSON_MOVIE_CREDITS_PATH)
    suspend fun getPersonMovieCredits(
        @Path(PERSON_ID_PATH) personId: Int
    ): Response<PersonMovieCreditsApiModel>

}

private const val PERSON_PATH = "/3/person/{person_id}"
private const val PERSON_MOVIE_CREDITS_PATH = "/3/person/{person_id}/movie_credits"

private const val PERSON_ID_PATH = "person_id"