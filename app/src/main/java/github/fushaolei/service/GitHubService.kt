package github.fushaolei.service

import github.fushaolei.entity.FileEntity
import github.fushaolei.entity.CreateBody
import github.fushaolei.entity.BaseResponse
import github.fushaolei.entity.DeleteBody
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/9
 * @desc: 接口服务
 */
interface GitHubService {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{user}/{repo}/contents/")
    fun getRepoList(
        @Path("user") name: String?,
        @Path("repo") repo: String?
    ): Observable<Response<List<FileEntity?>?>?>?

    @Headers("Accept: application/vnd.github.v3+json", "Content-Type: application/json")
    @PUT("/repos/{user}/{repo}/contents/{path}")
    fun insertRepo(
        @Header("Authorization") token: String?,
        @Path("user") user: String?,
        @Path("repo") repo: String?,
        @Path("path") path: String?,
        @Body createBody: CreateBody?
    ): Observable<Response<BaseResponse?>?>?

    @Headers("Accept: application/vnd.github.v3+json")
    @HTTP(method = "DELETE", path = "/repos/{owner}/{repo}/contents/{path}", hasBody = true)
    fun deleteFile(
        @Header("Authorization") token: String?,
        @Path("owner") user: String?,
        @Path("repo") repo: String?,
        @Path("path") path: String?,
        @Body body: DeleteBody?
    ): Observable<Response<BaseResponse?>?>?
}