package github.fushaolei;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/9
 * @desc:
 */
public interface GitHubService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{user}/{repo}/contents/")
    Call<List<FileEntity>> getRepoList(@Path("user") String name, @Path("repo") String repo);


    @Headers({
            "Accept: application/vnd.github.v3+json",
            "Content-Type: application/json"
    })
    @PUT("/repos/{user}/{repo}/contents/{path}")
    Call<BaseResponse> insertRepo(@Header("Authorization") String token,
                                  @Path("user") String user,
                                  @Path("repo") String repo,
                                  @Path("path") String path,
                                  @Body CreateBody createBody);
}
