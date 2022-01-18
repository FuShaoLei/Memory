package github.fushaolei.entity

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/11
 * @desc:
 */
class CreateBody(var message: String, var content: String) {
    override fun toString(): String {
        return "CreateBody{" +
                "message='" + message + '\'' +
                ", content='" + content + '\'' +
                '}'
    }
}