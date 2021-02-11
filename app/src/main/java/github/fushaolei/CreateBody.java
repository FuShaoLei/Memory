package github.fushaolei;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/11
 * @desc:
 */
public class CreateBody {
    private String message;
    private String content;

    public CreateBody(String message, String content) {
        this.message = message;
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CreateBody{" +
                "message='" + message + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}