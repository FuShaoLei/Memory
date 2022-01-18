package github.fushaolei.utils

import android.net.Uri
import android.widget.ImageView
import github.fushaolei.utils.MMKVHelper
import com.bumptech.glide.Glide
import github.fushaolei.app.App
import github.fushaolei.constant.GlobalConstant

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc:
 */
object GlideHelper {
    //TODO 判断path的格式是不是图片的
    @JvmStatic
    fun loadImageCDN(path: String, imageView: ImageView?) {
        if (!MMKVHelper.isUser()) {
            return
        }
        val user = MMKVHelper.getUser()
        Glide.with(App.getContext())
            .load(GlobalConstant.JSDELIVR_PREFIX + user.name + "/" + user.repo + "/" + path)
            .into(imageView!!)
    }

    @JvmStatic
    fun loadImageByUri(uri: Uri?, imageView: ImageView?) {
        Glide.with(App.getContext())
            .load(uri)
            .into(imageView!!)
    }
}