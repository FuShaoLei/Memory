package github.fushaolei.base

import github.fushaolei.base.BasePresenter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc:
 */
abstract class BaseActivity<P : BasePresenter<*>?> : AppCompatActivity() {
    @JvmField
    protected var rootPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        rootPresenter = initPresenter()
        initialize()
        initView()
    }

    /**
     * 初始化
     * 进行一些 findViewById ，初始化数据 等操作
     */
    protected abstract fun initialize()

    /**
     * 初始化界面
     * 用于还原一些数据到视图啊之类的
     */
    protected abstract fun initView()

    /**
     * 初始化presenter
     */
    protected abstract fun initPresenter(): P?

    /**
     * 获取视图id
     */
    protected abstract fun getLayoutId():Int


    override fun onDestroy() {
        if (rootPresenter != null) {
            rootPresenter!!.clearView()
        }
        super.onDestroy()
    }
}