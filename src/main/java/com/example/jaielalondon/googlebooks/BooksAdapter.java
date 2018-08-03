package com.example.jaielalondon.googlebooks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends ArrayAdapter<Book> {

    private final String LOG_TAG = getClass().getName();
    public BooksAdapter(@NonNull Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Check if current view is being used, otherwise inflate the view
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listitem, parent, false);
        }

        Book currentBook = getItem(position);

        //Find resource for the title text view, and set its text to be the current books title
        TextView title = (TextView) listItemView.findViewById(R.id.title_TextView);
        title.setText(currentBook.getTitle());

        //Find resource for the author text view, and set its text to be the current books author
        TextView author = (TextView) listItemView.findViewById(R.id.author_TextView);
        author.setText(currentBook.getAuthor());

        //Find resource for the published date text view, and set its text to be the current books published date
        TextView datePublished = (TextView) listItemView.findViewById(R.id.date_TextView);

        //Set datePublished date to be a substring of the datepublished that only shows the year
        datePublished.setText(currentBook.getDatePublished().substring(0, 4));

        //Find resourse for ratings bar
        RatingBar ratingBar = (RatingBar) listItemView.findViewById(R.id.ratingBar);

        // Find resource for ratings count text view
        TextView ratingsCount = (TextView) listItemView.findViewById(R.id.ratingsCount);

        // If current book as 0 ratings, then hide the ratings bar and ratings count views
        if (currentBook.getRatingCount() == 0) {
            ratingBar.setVisibility(View.GONE);
            ratingsCount.setVisibility(View.GONE);
        } else {
            // set ratings bar stars to the books average rating (I.e 4/5 Stars)
            ratingBar.setRating(((float) currentBook.getAverageRating()));

            // Set text to the current books ratings count
            ratingsCount.setText(String.valueOf(currentBook.getRatingCount()));
        }

        //Find resource for image view and set to the current books image
        ImageView imageView = listItemView.findViewById(R.id.image);
        imageView.setImageDrawable(currentBook.getThumbnailImage());

        return listItemView;
    }


}
