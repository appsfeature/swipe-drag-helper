package com.droidhelios.swipedraghelper.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Created by Abhijit Rao on 20-11-2019.
 */

public class User implements Cloneable{

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("ranking")
    @Expose
    private int ranking;

    @Expose
    private boolean changePosition = false;

    public User() {
    }

    public User(int id, String name, String imageUrl, String type) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    public User(User user) {
        this.id = user.id;
        this.name = user.name;
        this.imageUrl = user.imageUrl;
        this.type = user.type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getType() {
        return type;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public boolean isChangePosition() {
        return changePosition;
    }

    public void setChangePosition(boolean changePosition) {
        this.changePosition = changePosition;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public User getClone() {
        try {
            return (User) clone();
        } catch (CloneNotSupportedException e) {
            return new User();
        }
    }
}
