package org.americanwhitewater.americanwhitewaterandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.Instant;

public class Article {
    @Expose @SerializedName("title")
    private String title = "";
    @Expose @SerializedName("contents")
    private String contents = "";
    @Expose @SerializedName("author")
    private String author = "";
    @Expose @SerializedName("abstract")
    private String _abstract = "";
//    @Expose @SerializedName("abstractphoto")
//    private String abstractphoto = "";
    @Expose @SerializedName("abstract_photo")
    private String abstractPhoto = "";
    @Expose @SerializedName("hascontents")
    private String hasContents = "";
    @Expose @SerializedName("contact")
    private String contact = "";
    @Expose @SerializedName("isoposted")
    private String isoPosted = "";
    @Expose @SerializedName("posted")
    private String posted = "";
    @Expose @SerializedName("postedepoch")
    private String postedEpoch;
    @Expose @SerializedName("shortname")
    private String shortName = "";
    @Expose @SerializedName("articleid")
    private String articleId = "";
    @Expose @SerializedName("uid")
    private String uid = "";
    @Expose @SerializedName("deleted")
    private String deleted = "";

//    @Expose @SerializedName("contentsphoto")
//    private Integer contentsphoto;
//    @Expose @SerializedName("contents_photo")
//    private Integer contentsPhoto;
//    @Expose @SerializedName("icon")
//    private Object icon;
//    @Expose @SerializedName("releasedate")
//    private String releaseDate = "";
//    @Expose @SerializedName("releaseepoch")
//    private String releaseEpoch = "";

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getAuthor() {
        return author;
    }

    public String get_abstract() {
        return _abstract;
    }

    public String getAbstractPhoto() {
        return abstractPhoto;
    }

    public String getHasContents() {
        return hasContents;
    }

    public String getContact() {
        return contact;
    }

    public String getPostedDisplay() {
        return posted;
    }

    public Instant getPostedInstant() {
        return Instant.ofEpochSecond(Integer.parseInt(postedEpoch));
    }

    public String getShortName() {
        return shortName;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getUid() {
        return uid;
    }

    public String getDeleted() {
        return deleted;
    }
}
