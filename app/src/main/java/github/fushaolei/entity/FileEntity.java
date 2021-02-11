package github.fushaolei.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/9
 * @desc:
 */
public class FileEntity implements Parcelable {
    private String name;
    private String path;
    private String sha;
    private String type;

    protected FileEntity(Parcel in) {
        name = in.readString();
        path = in.readString();
        sha = in.readString();
        type = in.readString();
    }

    public static final Creator<FileEntity> CREATOR = new Creator<FileEntity>() {
        @Override
        public FileEntity createFromParcel(Parcel in) {
            return new FileEntity(in);
        }

        @Override
        public FileEntity[] newArray(int size) {
            return new FileEntity[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(sha);
        dest.writeString(type);
    }
}
