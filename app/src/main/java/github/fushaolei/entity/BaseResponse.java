package github.fushaolei.entity;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/11
 * @desc:
 */
public class BaseResponse {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
