package github.fushaolei.module.setting;

import android.util.Log;

import github.fushaolei.base.BasePresenter;
import github.fushaolei.entity.User;
import github.fushaolei.utils.MMKVHelper;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/13
 * @desc:
 */
public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {
    public SettingPresenter(SettingContract.View rootView) {
        super(rootView);
    }

    @Override
    public void init() {
        if (MMKVHelper.isUser()) {
            rootView.updateView(MMKVHelper.getUser());
        }
    }

    @Override
    public void saveUser(User user) {
        Log.e("user => ", user.toString());
        MMKVHelper.saveUser(user);
        rootView.saveSuccess();
    }
}
