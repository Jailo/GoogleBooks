package com.example.jaielalondon.googlebooks;

public class Book {

    /** Title of book */
    private String mTitle;

    /** Books Author */
    private String mAuthor;

    /** Publish date */
    private String mDatePublished;

    /** Average rating */
    private int mAverageRating;

    /** Number of people who rated the book */
    private int mRatingCount;

    /**
     * Creates a new Book object
     *
     * @param title         is the name of the book
     * @param author        is the books author
     * @param datePublished is the date when the book was published
     */
    public Book(String title, String author, String datePublished) {
        mTitle = title;
        mAuthor = author;
        mDatePublished = datePublished;
    }

    /**
     * Creates a new Book object
     * @param title is the name of the book
     * @param author is the books author
     * @param datePublished is the date when the book was published
     * @param averageRating is the average rating of the book
     * @param ratingCount is the number of ratings
     */
    public Book(String title, String author, String datePublished, int averageRating, int ratingCount){
        mTitle = title;
        mAuthor = author;
        mDatePublished = datePublished;
        mAverageRating = averageRating;
        mRatingCount = ratingCount;
    }


    /** @return the title of the book */
    public String getTitle() {return mTitle;}

    /** @return the book's author */
    public String getAuthor() { return mAuthor; }

    /** @return the book's publish date */
    public String getDatePublished() { return mDatePublished; }

    /** @return the average rating of the book */
    public int getAverageRating() { return mAverageRating; }

    /** @return the number of how many people rated the book */
    public int getRatingCount() { return mRatingCount; }
}
