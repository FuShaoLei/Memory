package github.fushaolei.module.detail;

import github.fushaolei.entity.DeleteBody;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc:
 */
public interface DetailContract {
    interface View {
        void showLoading();
        void hideLoading();
        void deleteSuccess();
        void deleteFail();
    }
    interface Presenter {
        void delete(String path, String sha);
    }
}
