package github.fushaolei;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/9
 * @desc:
 */
public interface GitHubService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/fushaolei/img2/contents/")
    Call<List<FileEntity>> getRepoList();
}
