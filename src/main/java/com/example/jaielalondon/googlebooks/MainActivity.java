package com.example.jaielalondon.googlebooks;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    /**
     * Search bar text
     */
    private static String searchText = "Green Eggs And Ham";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find resource for the progress bar spinner
        final ProgressBar progressBar = findViewById(R.id.progress_bar);

        // Find resource for the List View
        ListView listView = findViewById(R.id.list_view);

        // Find resource for error text view
        TextView errorTextView = findViewById(R.id.error_text_view);
        errorTextView.setText("Can't show any books right now, sorry");

        listView.setEmptyView(errorTextView);

        // Get referance to Connectivity Manager to check the state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        Boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            // Create Resouce for Loader Manager
            final LoaderManager loaderManager = getLoaderManager();

//            // Initiate Loader
//            loaderManager.initLoader(0, null, this);

            // Find resource for search bar
            SearchView searchBar = findViewById(R.id.search_bar);

            searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchText = query;

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
            errorTextView.setText("Not connected to the internet");

            // Make progress bar invisible because we have finished loading
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {

        return new BookLoader(this, searchText);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        // Make progress bar invisible because we have finished loading
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        Log.v(LOG_TAG, "On Load Finished");


    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

        loader.reset();

    }
}
