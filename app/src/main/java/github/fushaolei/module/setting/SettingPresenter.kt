package github.fushaolei.module.setting

import android.util.Log
import github.fushaolei.utils.MMKVHelper.isUser
import github.fushaolei.utils.MMKVHelper.getUser
import github.fushaolei.base.BasePresenter
import github.fushaolei.entity.User
import github.fushaolei.utils.MMKVHelper

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/13
 * @desc:
 */
class SettingPresenter(rootView: SettingContract.View?) :
    BasePresenter<SettingContract.View?>(rootView), SettingContract.Presenter {
    override fun init() {
        if (isUser()) {
            rootView!!.updateView(getUser())
        }
    }

    override fun saveUser(user: User?) {
        Log.e("user => ", user.toString())
        MMKVHelper.saveUser(user)
        rootView!!.saveSuccess()
    }
}