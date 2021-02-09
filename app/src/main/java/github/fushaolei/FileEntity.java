package github.fushaolei;

import java.io.Serializable;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/9
 * @desc:
 */
public class FileEntity implements Serializable {
    private String name;
    private String path;
    private String sha;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
