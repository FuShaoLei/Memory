package github.fushaolei.module.main;

import android.net.Uri;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import github.fushaolei.base.BasePresenter;
import github.fushaolei.constant.CommitConstant;
import github.fushaolei.entity.BaseResponse;
import github.fushaolei.entity.CreateBody;
import github.fushaolei.entity.FileEntity;
import github.fushaolei.entity.User;
import github.fushaolei.utils.MMKVHelper;
import github.fushaolei.utils.RxJavaHelper;
import github.fushaolei.utils.UploadHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc:
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    public MainPresenter(MainContract.View rootView) {
        super(rootView);
    }

    @Override
    public void getRepoList() {
        rootView.showRefresh();
        User user = MMKVHelper.getUser();
        Observable<Response<List<FileEntity>>> observable
                = service.getRepoList(user.getName(), user.getRepo());
        observable = RxJavaHelper.toSubsribe(observable);
        observable.subscribe(new Observer<Response<List<FileEntity>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<List<FileEntity>> listResponse) {
                rootView.hideRefresh();
                if (listResponse.code() == 200 || listResponse.code() == 201) {
                    List<FileEntity> entityList = listResponse.body();
                    Collections.reverse(entityList);
                    rootView.updateRecycler(entityList);
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

    @Override
    public void uploadFileThread(String path) {
        rootView.showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("absolute path =>", path);
                uploadFile(path);
            }
        }).start();
    }


    private void uploadFile(String path) {
//        rootView.showLoading();
        CreateBody body = new CreateBody(CommitConstant.UPLOAD, UploadHelper.encodeImageToBase64(path));
        User user = MMKVHelper.getUser();
        String fileName = UploadHelper.getFileName(path);

        Observable<Response<BaseResponse>> observable =
                service.insertRepo("token " + user.getToken(),
                        user.getName(),
                        user.getRepo(),
                        fileName,
                        body);
        observable = RxJavaHelper.toSubsribe(observable);
        observable.subscribe(new Observer<Response<BaseResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<BaseResponse> baseResponseResponse) {
                rootView.hideLoading();
                if (baseResponseResponse.code() == 200 || baseResponseResponse.code() == 201) {
                    rootView.uploadSuccess();
                } else {
                    rootView.uploadFail();
                }
            }

            @Override
            public void onError(Throwable e) {
                rootView.hideLoading();
                rootView.uploadFail();
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
