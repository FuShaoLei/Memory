package github.fushaolei.app;

import android.app.Application;
import android.content.Context;

import com.tencent.mmkv.MMKV;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/11
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
