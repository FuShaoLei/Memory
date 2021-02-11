package github.fushaolei.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import github.fushaolei.R;
import github.fushaolei.entity.FileEntity;
import github.fushaolei.entity.User;
import github.fushaolei.utils.MMKVHelper;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/9
 * @desc: image适配器
 */
public class ImageAdapter extends BaseQuickAdapter<FileEntity, ImageAdapter.ViewHolder> {

    //TODO 这里其实要做一个过滤，过滤掉那些不是图片的东西
    public ImageAdapter(int layoutResId, @Nullable List<FileEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ViewHolder helper, FileEntity item) {
        User user = MMKVHelper.getUser();
        Glide.with(mContext)
                .load("https://cdn.jsdelivr.net/gh/" + user.getName() + "/" + user.getRepo() + "/" + item.getPath())
                .into(helper.imageView);
    }

    public class ViewHolder extends BaseViewHolder {
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
        }
    }
}
