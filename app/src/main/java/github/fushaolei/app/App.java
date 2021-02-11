package github.fushaolei.app;

import android.app.Application;

import com.tencent.mmkv.MMKV;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/11
 * @desc:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
    }
}
