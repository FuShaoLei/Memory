package github.fushaolei.base;

import github.fushaolei.service.GitHubService;
import github.fushaolei.service.ServiceManager;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc:
 */
public class BasePresenter<V> {
    protected V rootView;
    protected GitHubService service
            = ServiceManager.getInstance().getService();

    public BasePresenter(V rootView) {
        this.rootView = rootView;
    }

    public void clearView() {
        if (rootView != null) {
            rootView = null;
        }
    }
}
