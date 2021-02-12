package github.fushaolei.utils;

import android.app.ProgressDialog;
import android.content.Context;

import github.fushaolei.R;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc: ProgressDialog 帮助类
 */
public class ProgressDialogHelper {
    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context, String text) {
        progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        progressDialog.setMessage(text);
        progressDialog.show();
    }

    public static void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
