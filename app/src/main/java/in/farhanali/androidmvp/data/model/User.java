package in.farhanali.androidmvp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Farhan Ali
 */
public class User {

    @Expose @SerializedName("id")
    private long id;
    @Expose @SerializedName("email")
    private String email;
    @Expose @SerializedName("password")
    private String password;
    @Expose @SerializedName("accessToken")
    private String accessToken;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
