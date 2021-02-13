package github.fushaolei.module.main;

import android.net.Uri;

import java.util.List;

import github.fushaolei.entity.FileEntity;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc:
 */
public interface MainContract {
    interface View {
        void updateRecycler(List<FileEntity> entityList);
        void showLoading();
        void hideLoading();
        void uploadSuccess();
        void uploadFail();

        void showRefresh();
        void hideRefresh();
    }

    interface Presenter {
        void getRepoList();
        void uploadFileThread(String path);
    }
}
