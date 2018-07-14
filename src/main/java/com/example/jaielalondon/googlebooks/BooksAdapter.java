package com.example.jaielalondon.googlebooks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

        //Find resourse for ratings bar, and set stars to the books average rating (I.e 4/5 Stars)
        RatingBar ratingBar = (RatingBar) listItemView.findViewById(R.id.ratingBar);
        ratingBar.setRating(((float) currentBook.getAverageRating()));

        // Find resource for ratings count text view, and set text to the current books ratings count
        TextView ratingsCount = (TextView) listItemView.findViewById(R.id.ratingsCount);
        ratingsCount.setText(String.valueOf(currentBook.getRatingCount()));

        //Find resource for image view and set to the current books image
        ImageView imageView = listItemView.findViewById(R.id.image);
        imageView.setImageDrawable(currentBook.getmThumbnailImage());


        return listItemView;
    }
}
