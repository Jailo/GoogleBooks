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

    private List<Book> mBookList;

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

        if (mBookList != null) {
            deliverResult(mBookList);
            Log.v("BookLoader", "book list is not null, get old booklist");
        } else {
            forceLoad();
            Log.v("BookLoader", "book list is null, create a new booklist");
        }
    }

    public void deliverResult(List<Book> books) {
        mBookList = books;
        super.deliverResult(books);
    }
}
