package github.fushaolei.base;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc:
 */
public abstract class BaseObserver<T extends Response> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
        onBegin();
    }

    @Override
    public void onNext(T t) {
        if (t.code() == 200 || t.code() == 201) {
            onSuccess(t);
        } else {
            onFail();
        }
    }

    @Override
    public void onError(Throwable e) {
        onFail();
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onBegin();

    protected abstract void onSuccess(T t);

    protected abstract void onFail();
}
