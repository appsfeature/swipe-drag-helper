package com.droidhelios.swipedrag.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RankModel implements Serializable {
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName(value="rank", alternate={"ranking"})
    private int rank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
