package github.fushaolei.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/11
 * @desc:
 */
public class User implements Parcelable {
    private String name;
    private String repo;
    private String token;

    protected User(Parcel in) {
        name = in.readString();
        repo = in.readString();
        token = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(String name, String repo, String token) {
        this.name = name;
        this.repo = repo;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", repo='" + repo + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(repo);
        dest.writeString(token);
    }
}
