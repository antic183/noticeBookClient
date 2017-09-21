package ch.webing.ntb_client.model;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by antic-software-ing on 20.09.2017.
 */

public class Notice {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("text")
    private String text;

    @SerializedName("createdAtUtc")
    private String createdAtUtc;

    public Notice(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAtUtc() {
        return createdAtUtc;
    }

    public void setCreatedAtUtc(String createdAtUtc) throws ParseException {
        this.createdAtUtc = createdAtUtc;
    }
}
