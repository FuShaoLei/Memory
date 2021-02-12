package github.fushaolei.service;

import github.fushaolei.constant.HttpConstant;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc: Retrofit单例
 */
public class ServiceManager {
    private volatile static ServiceManager dataManager;
    private GitHubService apiService;

    public static ServiceManager getInstance() {
        if (dataManager == null) {
            synchronized (Object.class) {
                if (dataManager == null) {
                    dataManager = new ServiceManager();
                }
            }
        }
        return dataManager;
    }

    public GitHubService getService() {
        return apiService;
    }

    private ServiceManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpConstant.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(GitHubService.class);
    }
}
