package com.example.jaielalondon.googlebooks;

import android.graphics.drawable.Drawable;

public class Book {

    /** Title of book */
    private String mTitle;

    /** Books Author */
    private String mAuthor;

    /** Publish date */
    private String mDatePublished;

    /** Average rating */
    private double mAverageRating;

    /** Number of people who rated the book */
    private int mRatingCount;

    /**
     * Thumbnail image for the book
     */
    private Drawable mThumbnailImage;

    /**
     * Book's price and currency
     */
    private String mPrice;

    /**
     * Book's Genre
     */
    private String mGenre;

    /**
     * Number of pages the book has
     */
    private int mPagesCount;

    /**
     * Url link to buy the book on google play store
     */
    private String mUrlLink;

    /**
     * Book's Description
     */
    private String mDescription;

    /**
     * Creates a new Book object
     * @param title is the name of the book
     * @param author is the books author
     * @param datePublished is the date when the book was published
     * @param averageRating is the average rating of the book
     * @param ratingCount is the number of ratings
     * @param price is the price of the book
     * @param genre is the book's genre
     * @param pagesCount number of pages in the book
     * @param urlLink link to buy the book on the google play store
     * @param description Description of the book
     *
     */
    public Book(String title, String author, String datePublished,
                Double averageRating, int ratingCount, Drawable thumbnailImage, String price, String
                        genre, int pagesCount, String urlLink, String description) {
        mTitle = title;
        mAuthor = author;
        mDatePublished = datePublished;
        mAverageRating = averageRating;
        mRatingCount = ratingCount;
        mThumbnailImage = thumbnailImage;
        mPrice = price;
        mGenre = genre;
        mPagesCount = pagesCount;
        mUrlLink = urlLink;
        mDescription = description;
    }


    /** @return the title of the book */
    public String getTitle() {return mTitle;}

    /** @return the book's author */
    public String getAuthor() { return mAuthor; }

    /** @return the book's publish date */
    public String getDatePublished() { return mDatePublished; }

    /** @return the average rating of the book */
    public double getAverageRating() {
        return mAverageRating;
    }

    /** @return the number of how many people rated the book */
    public int getRatingCount() {
        return mRatingCount;
    }

    /**
     * @return the thumbnail image of the book
     */
    public Drawable getThumbnailImage() {
        return mThumbnailImage;
    }

    /**
     * @return how much the book costs and its currency
     */
    public String getPrice() {
        return mPrice;
    }

    /**
     * @return the number of pages in the book
     */
    public int getPagesCount() {
        return mPagesCount;
    }

    /**
     * @return Genre or category of the book
     */
    public String getGenre() {
        return mGenre;
    }

    /**
     * @return the url where one can buy the book
     */
    public String getUrlLink() {
        return mUrlLink;
    }

    /** @return the description of the book */
    public String getDescription() { return mDescription; }

}
