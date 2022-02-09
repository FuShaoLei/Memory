package github.fushaolei.adapter

import android.view.View
import android.widget.ImageView
import github.fushaolei.utils.MMKVHelper.getUser
import github.fushaolei.entity.FileEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import github.fushaolei.utils.MMKVHelper
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import github.fushaolei.R

/**
 * image适配器
 * @Auther: fushaolei
 * @datetime: 2021/2/9
 */
class ImageAdapter(layoutResId: Int, data: List<FileEntity?>?) :
    BaseQuickAdapter<FileEntity, ImageAdapter.ViewHolder>(layoutResId, data) {
    override fun convert(helper: ViewHolder, item: FileEntity) {
        val user = getUser()
        Glide.with(mContext)
            .load("https://cdn.jsdelivr.net/gh/" + user.name + "/" + user.repo + "/" + item.path)
            .into(helper.imageView)
    }

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.image)
            private set
    }
}