package github.fushaolei;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/9
 * @desc:
 */
public class ImageAdapter extends BaseQuickAdapter<FileEntity, ImageAdapter.ViewHolder> {

    public ImageAdapter(int layoutResId, @Nullable List<FileEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(ViewHolder helper, FileEntity item) {
        Glide.with(mContext)
                .load("https://cdn.jsdelivr.net/gh/fushaolei/img2/" + item.getPath())
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