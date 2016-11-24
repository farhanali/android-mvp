package in.farhanali.androidmvp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Farhan Ali
 */
public class Task {

    @Expose @SerializedName("id")
    private long id;
    @Expose @SerializedName("userId")
    private long userId;
    @Expose @SerializedName("name")
    private String name;
    @Expose @SerializedName("isActive")
    private boolean active;

    public Task() {
    }

    public Task(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public Task(long userId, String name, boolean active) {
        this.userId = userId;
        this.name = name;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void toggleActive() {
        setActive(! active);
    }

}
