package com.example.jaielalondon.googlebooks;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toolbar;

import org.w3c.dom.Text;

public class BookInfoActivity extends AppCompatActivity {

    private static final String LOG_TAG = BookInfoActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info);

        //Enable the Up button in the nav bar
        getSupportActionBar().setHomeButtonEnabled(true);


        // Create a reference to the current book we want to display info on
        final Book book = MainActivity.currentBook;

        // Get resource for the title text view, and set text to the book's title
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(book.getTitle());

        //Find resource for the author text view, and set its text to be the current books author
        TextView author = (TextView) findViewById(R.id.author);
        author.setText(book.getAuthor());

        TextView date = (TextView) findViewById(R.id.date);
        date.setText(book.getDatePublished().substring(0, 4));

        //Find resourse for ratings bar
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        // Find resource for ratings count text view
        TextView ratingsCount = (TextView) findViewById(R.id.ratingsCount);

        // If current book as 0 ratings, then hide the ratings bar and ratings count views
        if (book.getRatingCount() == 0) {
            ratingBar.setVisibility(View.GONE);
            ratingsCount.setVisibility(View.GONE);
        } else {
            // set ratings bar stars to the books average rating (I.e 4/5 Stars)
            ratingBar.setRating(((float) book.getAverageRating()));

            // Set text to the current books ratings count
            ratingsCount.setText(String.valueOf(book.getRatingCount()));
        }


        //Find resource for image view and set to the current books image
        ImageView imageView = findViewById(R.id.image);
        imageView.setImageDrawable(book.getThumbnailImage());

        //Find resource for Genre text view and set to the current books genre
        TextView genre = findViewById(R.id.genre);
        genre.setText(book.getGenre());

        // Find resource for price text view and set to books price
        TextView price = findViewById(R.id.price);

        //If the book's price is empty, meaning it is NOT for sale
        if (book.getPrice().isEmpty()) {

            // find and set the price text view visibility to gone
            TextView priceTextView = findViewById(R.id.price);
            price.setVisibility(View.GONE);

            // find and then set the first verticle line view visibility to gone
            View verticleLine = findViewById(R.id.verticle_line);
            verticleLine.setVisibility(View.GONE);

        } else {
            price.setText(book.getPrice());
        }

        // Find resources for pages text view and set text to the books number of pages
        TextView pages = findViewById(R.id.pages);
        String pagesText = String.valueOf(book.getPagesCount()) + " " + getString(R.string.pages);
        pages.setText(pagesText);

        //Find resource for description and set to the current books description
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(book.getDescription());

        // Find resource for buy button and set an on click listener
        // that goes to the books online info page on the google play store
        Button buyButton = findViewById(R.id.buy_button);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(book.getUrlLink()));
                startActivity(intent);
            }
        });



    }
}
