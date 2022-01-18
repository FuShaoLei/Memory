package github.fushaolei.utils

import com.tencent.mmkv.MMKV
import github.fushaolei.entity.User
import github.fushaolei.utils.MMKVHelper

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/11
 * @desc: MMKV 存储帮助类
 */
object MMKVHelper {
    private val kv = MMKV.defaultMMKV()
    @JvmStatic
    fun saveUser(user: User?) {
        kv.encode("user", user)
    }
    @JvmStatic
    fun isUser(): Boolean {
        return (kv.contains("user")
                && kv.decodeParcelable("user", User::class.java) != null)
    }


    @JvmStatic
    fun getUser(): User {
        return kv.decodeParcelable("user", User::class.java)
    }
}