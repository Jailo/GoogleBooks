package com.example.jaielalondon.googlebooks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Gets List of books in the background thread
 */
public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String mSearchBarText;

    public BookLoader(Context context, String searchBarText) {
        super(context);
        mSearchBarText = searchBarText;

    }


    @Override
    public List<Book> loadInBackground() {
        Log.v("BookLoader", "ON LOAD in background has begun YO!");

        // If search bar text is null then do nothing
        if (mSearchBarText == null) {
            return null;
        }

        List<Book> books = QueryUtils.getBookList(mSearchBarText);
        return books;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
