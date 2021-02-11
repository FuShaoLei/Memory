package github.fushaolei;

import com.tencent.mmkv.MMKV;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/11
 * @desc:
 */
public class MMKVHelper {
    private static MMKV kv = MMKV.defaultMMKV();

    public static void saveUser(User user) {
        kv.encode("user", user);
    }

    public static boolean isUser() {
        return kv.contains("user")
                && kv.decodeParcelable("user", User.class) != null;
    }

    public static User getUser() {
        return kv.decodeParcelable("user", User.class);
    }
}
