package github.fushaolei.entity

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/11
 * @desc:
 */
class DeleteBody(var message: String, var sha: String) {
    override fun toString(): String {
        return "CreateBody{" +
                "message='" + message + '\'' +
                ", content='" + sha + '\'' +
                '}'
    }
}