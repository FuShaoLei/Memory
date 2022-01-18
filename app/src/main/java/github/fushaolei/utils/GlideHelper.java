package github.fushaolei.utils;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import github.fushaolei.app.App;
import github.fushaolei.constant.GlobalConstant;
import github.fushaolei.entity.User;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc:
 */
public class GlideHelper {

    //TODO 判断path的格式是不是图片的
    public static void loadImageCDN(String path, ImageView imageView) {
        if (!MMKVHelper.isUser()) {
            return;
        }
        User user = MMKVHelper.getUser();
        Glide.with(App.getContext())
                .load(GlobalConstant.INSTANCE.JSDELIVR_PREFIX + user.getName() + "/" + user.getRepo() + "/" + path)
                .into(imageView);
    }

    public static void loadImageByUri(Uri uri, ImageView imageView) {
        Glide.with(App.getContext())
                .load(uri)
                .into(imageView);
    }
}
