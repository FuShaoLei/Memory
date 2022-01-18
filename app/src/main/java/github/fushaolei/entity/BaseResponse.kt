package github.fushaolei.entity

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/11
 * @desc:
 */
class BaseResponse {
    var code = 0
    var msg: String? = null
    override fun toString(): String {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}'
    }
}