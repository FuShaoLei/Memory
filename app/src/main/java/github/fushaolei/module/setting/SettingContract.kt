package github.fushaolei.module.setting

import github.fushaolei.entity.User

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/13
 * @desc:
 */
interface SettingContract {
    interface View {
        fun updateView(user: User?)
        fun saveSuccess()
        fun saveFail()
    }

    interface Presenter {
        fun init()
        fun saveUser(user: User?)
    }
}