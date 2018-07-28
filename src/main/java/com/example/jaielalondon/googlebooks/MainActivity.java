package com.example.jaielalondon.googlebooks;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.inputmethodservice.Keyboard;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {


    public static final String LOG_TAG = MainActivity.class.getName();

    /**
     * Public empty Book Object that we can send to the BookInfoActivity
     */
    public static Book currentBook;

    /**
     * Adapter for the list view
     */
    private BooksAdapter adapter;

    /**
     * Search bar text
     */
    private static String searchText;

    /**
     * Error text view
     */
    private TextView errorTextView;

    /**
     * Spinning progress bar
     */
    private ProgressBar progressBar;

    /**
     * Loader Manager
     */
    private LoaderManager loaderManager;


    /**
     * This method hides the keyboard
     */
    public static void hideKeyboard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager)
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        //Find the currently focused view, so we can grab the correct window token from it
        View view = activity.getCurrentFocus();

        //If view has no focus, create a new one, so we can get a window token from it
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {

        return new BookLoader(this, searchText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // KeyBoard is always hidden unless the user clicks on an editText field
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Create new adapter that takes an empty ArrayList
        adapter = new BooksAdapter(getBaseContext(), new ArrayList<Book>());

        // Find resource for the progress bar spinner
        progressBar = findViewById(R.id.progress_bar);

        // Find resource for the List View
        ListView listView = findViewById(R.id.list_view);

        // Set adapter on listView
        listView.setAdapter(adapter);

        // Find resource for error text view
        errorTextView = findViewById(R.id.error_text_view);

        // Set the adapter to the listView so that the list can be seen
        listView.setEmptyView(errorTextView);

        // Get referance to Connectivity Manager to check the state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        Boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            // Create Resouce for Loader Manager
            loaderManager = getLoaderManager();

            // Find resource for search bar
            SearchView searchBar = findViewById(R.id.search_bar);

            searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchText = query;

                    //Hide the keyboard
                    hideKeyboard(MainActivity.this);

                    progressBar.setVisibility(View.VISIBLE);

                    //Restart the loader when the search button is clicked
                    loaderManager.restartLoader(0, null, MainActivity.this).forceLoad();

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });


        } else {
            // If the user is NOT connected to the internet
            // Display error message in error text view
            errorTextView.setText(R.string.no_internet);

            // Make progress bar invisible because we have finished loading
            progressBar.setVisibility(View.GONE);
        }

        // When a book is clicked, show info page about that book
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // create new intent to the book info activity
                Intent i = new Intent(view.getContext(), BookInfoActivity.class);

                currentBook = adapter.getItem(position);

                startActivity(i);

            }
        });

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Clear the adapter, make loading progress bar visible, and reset the loader
        adapter.clear();
        progressBar.setVisibility(View.VISIBLE);
        loader.reset();


    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        // Make progress bar invisible because we have finished loading
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        Log.v(LOG_TAG, "On Load Finished");

        adapter.clear();

        if (data.isEmpty()) {
            //If the list of books is empty, set error text view text
            errorTextView.setText(R.string.cant_find_book);
        } else {
            // otherwise, if the list of books (aka data) is NOT empty
            // Then add all books to the adapter to show them in the  listview
            adapter.addAll(data);
        }

    }

}
