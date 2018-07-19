package com.example.jaielalondon.googlebooks;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class BookInfoActivity extends Activity {

    private static final String LOG_TAG = BookInfoActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info);

        // Create a reference to the current book we want to display info on
        Book book = MainActivity.currentBook;

        // Get resource for the title text view, and set text to the book's title
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(book.getTitle());

        //Find resource for the author text view, and set its text to be the current books author
        TextView author = (TextView) findViewById(R.id.author);
        author.setText(book.getAuthor());

        //Find resourse for ratings bar, and set stars to the books average rating (I.e 4/5 Stars)
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(((float) book.getAverageRating()));

        // Find resource for ratings count text view, and set text to the current books ratings count
        TextView ratingsCount = (TextView) findViewById(R.id.ratingsCount);
        ratingsCount.setText(String.valueOf(book.getRatingCount()));

        //Find resource for image view and set to the current books image
        ImageView imageView = findViewById(R.id.image);
        imageView.setImageDrawable(book.getmThumbnailImage());

        //Find resource for Genre text view and set to the current books genre
        TextView genre = findViewById(R.id.genre);
        genre.setText("Some Genre");

        //Find resource for description and set to the current books description
        TextView description = (TextView) findViewById(R.id.description);
        description.setText("Some long Description");

    }
}
