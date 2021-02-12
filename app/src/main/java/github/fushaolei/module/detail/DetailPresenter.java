package github.fushaolei.module.detail;

import android.util.Log;

import github.fushaolei.base.BasePresenter;
import github.fushaolei.entity.BaseResponse;
import github.fushaolei.entity.DeleteBody;
import github.fushaolei.entity.User;
import github.fushaolei.utils.MMKVHelper;
import github.fushaolei.utils.RxJavaHelper;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc:
 */
public class DetailPresenter extends BasePresenter<DetailContract.View> implements DetailContract.Presenter {
    public DetailPresenter(DetailContract.View rootView) {
        super(rootView);
    }


    @Override
    public void delete(String path, String sha) {
        Log.e("=>", "presenter delete begin");
        DeleteBody body = new DeleteBody("GitHub delete api test 111", sha);
        rootView.showLoading();
        User user = MMKVHelper.getUser();
        Observable<Response<BaseResponse>> observable =
                service.deleteFile("token " + user.getToken()
                        , user.getName(), user.getRepo(), path, body);
        observable = RxJavaHelper.toSubsribe(observable);
        observable.subscribe(new Observer<Response<BaseResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<BaseResponse> baseResponse) {
                rootView.hideLoading();
                if (baseResponse.code() == 200) {
                    rootView.deleteSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
