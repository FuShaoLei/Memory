package github.fushaolei.entity;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/11
 * @desc:
 */
public class DeleteBody {
    private String message;
    private String sha;

    public DeleteBody(String message, String sha) {
        this.message = message;
        this.sha = sha;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    @Override
    public String toString() {
        return "CreateBody{" +
                "message='" + message + '\'' +
                ", content='" + sha + '\'' +
                '}';
    }
}