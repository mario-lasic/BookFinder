package com.example.bookfinder;

public class Book {

    private String mTitle;
    private String mAuthor;
    private String mImgLink;
    private String mPreviewLink;
    private String mDescription;

    //Constructor
    public Book(String title, String author, String imgLink, String previewLink, String description) {
        mTitle = title;
        mAuthor = author;
        mImgLink = imgLink;
        mPreviewLink = previewLink;
        mDescription = description;
    }
    // Setters and getters
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getImgLink() {
        return mImgLink;
    }

    public void setImgLink(String imgLink) {
        mImgLink = imgLink;
    }

    public String getPreviewLink() {
        return mPreviewLink;
    }

    public void setPreviewLink(String previewLink) {
        mPreviewLink = previewLink;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
