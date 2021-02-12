package github.fushaolei.utils;

import android.widget.Toast;

import github.fushaolei.app.App;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc: Toast 帮助类
 */
public class ToastHelper {
    private static Toast toast;

    public static void show(String text) {
        Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
