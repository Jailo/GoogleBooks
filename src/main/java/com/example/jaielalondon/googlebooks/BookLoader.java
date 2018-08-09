package com.example.jaielalondon.googlebooks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Gets List of books in the background thread
 */
public class BookLoader extends AsyncTaskLoader<List<Book>> {

    /**
     * text that was in the search bar when the user clicked submit
     */
    private String mSearchBarText;

    /** thisb list will contain a list a books the user searched for */
    private List<Book> mBookList;

    public BookLoader(Context context, String searchBarText) {
        super(context);
        // set mSearchBarText to the text the user inputs, and
        // only return books in the users language using R.string.language_code,
        // and only return 40 books maximum
        mSearchBarText = searchBarText;

    }


    @Override
    public List<Book> loadInBackground() {
        Log.v("BookLoader", "ON LOAD in background has begun YO!");

        // If search bar text is null then do nothing
        if (mSearchBarText == null) {
            return null;
        }

        // Create & return a list of books by calling QueryUtils' get books list method
        List<Book> books = QueryUtils.getBookList(mSearchBarText);
        return books;
    }

    @Override
    protected void onStartLoading() {

        // If list of books is NOT null, deliver the existing list of books
        if (mBookList != null) {
            deliverResult(mBookList);
            Log.v("BookLoader", "book list is not null, get old booklist");
        } else {
            // else, call force load to create a new list
            forceLoad();
            Log.v("BookLoader", "book list is null, create a new booklist");
        }
    }

    /**
     * Save book list data for later retrieval.
     * @param books is the list of books
     */
    public void deliverResult(List<Book> books) {
        mBookList = books;
        //Returns cached book list data
        super.deliverResult(books);
    }
}
