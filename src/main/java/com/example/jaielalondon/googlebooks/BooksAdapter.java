package com.example.jaielalondon.googlebooks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BooksAdapter extends ArrayAdapter<Book> {
    public BooksAdapter(@NonNull Context context, ArrayList<Book> books) {
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
        datePublished.setText(currentBook.getDatePublished());

        return listItemView;
    }
}
