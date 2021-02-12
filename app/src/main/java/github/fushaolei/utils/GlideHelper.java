package github.fushaolei.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import github.fushaolei.app.App;
import github.fushaolei.constant.HttpConstant;
import github.fushaolei.entity.User;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/12
 * @desc:
 */
public class GlideHelper {

    //TODO 判断path的格式是不是图片的
    public static void loadImage(String path, ImageView imageView) {
        if (!MMKVHelper.isUser()) {
            return;
        }
        User user = MMKVHelper.getUser();
        Glide.with(App.getContext())
                .load(HttpConstant.DEFAULT_CDN + user.getName() + "/" + user.getRepo() + "/" + path)
                .into(imageView);
    }
}
