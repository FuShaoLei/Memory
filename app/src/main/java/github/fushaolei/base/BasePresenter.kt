package github.fushaolei.base

import github.fushaolei.service.GitHubService
import github.fushaolei.service.ServiceManager

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc:
 */
abstract class BasePresenter<V>(rootView: V) {
    @JvmField
    protected var rootView: V?
    @JvmField
    protected var service = ServiceManager.getInstance().service
    fun clearView() {
        if (rootView != null) {
            rootView = null
        }
    }

    init {
        this.rootView = rootView
    }
}