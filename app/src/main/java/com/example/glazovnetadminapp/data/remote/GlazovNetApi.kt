package com.example.glazovnetadminapp.data.remote

import com.example.glazovnetadminapp.entity.AddressModelDto
import com.example.glazovnetadminapp.entity.ApiResponseDto
import com.example.glazovnetadminapp.entity.announcementsDto.AnnouncementModelDto
import com.example.glazovnetadminapp.entity.clientsDto.ClientModelDto
import com.example.glazovnetadminapp.entity.postsDto.PostModelDto
import com.example.glazovnetadminapp.entity.tariffsDto.TariffModelDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

private const val POSTS_PATH = "api/posts"
private const val TARIFFS_PATH = "api/tariffs"
private const val FILTERS_PATH = "api/filters"
private const val ADDRESS_PATH = "api/addressinfo"
private const val ANNOUNCEMENTS_PATH = "api/announcements"
private const val CLIENTS_PATH = "api/clients"

interface GlazovNetApi {

    //POSTS

    @GET("$POSTS_PATH/getall")
    suspend fun getAllPosts(): ApiResponseDto<List<PostModelDto>>

    @GET("$POSTS_PATH/getposts")
    suspend fun getPostsList(
        @Query("limit") limit: Int? = null,
        @Query("start_index") startIndex: Int? = null
    ): ApiResponseDto<List<PostModelDto>>

    @GET("$POSTS_PATH/get")
    suspend fun getPostById(
        @Query("post_id") postId: String
    ): ApiResponseDto<List<PostModelDto>>

    @POST("$POSTS_PATH/add")
    suspend fun addPost(
        @Body postModel: PostModelDto,
        @Query("api_key") apiKey: String
    ): ApiResponseDto<List<PostModelDto>>

    @PUT("$POSTS_PATH/edit")
    suspend fun editPost(
        @Body postModel: PostModelDto,
        @Query("api_key") apiKey: String
    ): ApiResponseDto<List<PostModelDto>>

    @DELETE("$POSTS_PATH/delete")
    suspend fun deletePostById(
        @Query("api_key") apiKey: String,
        @Query("post_id") postId: String
    ): ApiResponseDto<List<PostModelDto>>

    //TARIFFS

    @GET("$TARIFFS_PATH/getall")
    suspend fun getAllTariffs(): ApiResponseDto<List<TariffModelDto>>

    @POST("$TARIFFS_PATH/add")
    suspend fun addTariff(
        @Body newTariff: TariffModelDto,
        @Query("api_key") apiKey: String
    ): ApiResponseDto<List<TariffModelDto>>

    @PUT("$TARIFFS_PATH/edit")
    suspend fun editTariff(
        @Body tariff: TariffModelDto,
        @Query("api_key") apiKey: String
    ): ApiResponseDto<List<TariffModelDto>>

    @DELETE("$TARIFFS_PATH/remove")
    suspend fun deleteTariffById(
        @Query("tariff_id") tariffId: String,
        @Query("api_key") apiKey: String
    ): ApiResponseDto<List<TariffModelDto>>

    //ADDRESSINFO

    @GET("$ADDRESS_PATH/getcitieslist")
    suspend fun getCitiesList(
        @Query("city") city: String,
        @Query("api_key") apiKey: String
    ): ApiResponseDto<List<String>>

    @GET("$ADDRESS_PATH/getstreetslist")
    suspend fun getStreetsList(
        @Query("city") city: String,
        @Query("street") street: String,
        @Query("api_key") apiKey: String
    ): ApiResponseDto<List<String>>

    @GET("$ADDRESS_PATH/getaddresses")
    suspend fun getAddresses(
        @Query("city") city: String,
        @Query("street") street: String,
        @Query("api_key") apiKey: String
    ): ApiResponseDto<List<AddressModelDto>>

    //ANNOUNCEMENTS

    @GET("$ANNOUNCEMENTS_PATH/getall")
    suspend fun getAnnouncements(): ApiResponseDto<List<AnnouncementModelDto>>

    @POST("$ANNOUNCEMENTS_PATH/create")
    suspend fun createNewAnnouncement(
        @Query("api_key") apiKey: String,
        @Body announcement: AnnouncementModelDto
    ): ApiResponseDto<List<AnnouncementModelDto>>

    @DELETE("$ANNOUNCEMENTS_PATH/delete")
    suspend fun deleteAnnouncement(
        @Query("api_key") apiKey: String,
        @Query("id") announcementId: String
    ): ApiResponseDto<List<AnnouncementModelDto>>

    //CLIENTS

    @POST("$CLIENTS_PATH/create")
    suspend fun createClient(
        @Query("api_key") apiKey: String,
        @Body client: ClientModelDto
    ): ApiResponseDto<ClientModelDto>

    @GET("$CLIENTS_PATH/getall")
    suspend fun getAllClients(
        @Query("api_key") apiKey: String
    ): ApiResponseDto<List<ClientModelDto>>

}