package github.fushaolei.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc:
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected P rootPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        rootPresenter = initPresenter();
        initialize();
        initView();
    }


    /**
     * 初始化
     * 进行一些 findViewById ，初始化数据 等操作
     */
    protected abstract void initialize();

    /**
     * 初始化界面
     * 用于还原一些数据到视图啊之类的
     */
    protected abstract void initView();

    /**
     * 初始化presenter
     */
    protected abstract P initPresenter();

    /**
     * 获取视图id
     */
    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        if (rootPresenter != null) {
            rootPresenter.clearView();
        }
        super.onDestroy();
    }
}
