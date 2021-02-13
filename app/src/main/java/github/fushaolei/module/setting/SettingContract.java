package github.fushaolei.module.setting;

import github.fushaolei.entity.User;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/13
 * @desc:
 */
public interface SettingContract {
    interface View {
        void updateView(User user);

        void saveSuccess();

        void saveFail();
    }

    interface Presenter {
        void init();

        void saveUser(User user);
    }
}
