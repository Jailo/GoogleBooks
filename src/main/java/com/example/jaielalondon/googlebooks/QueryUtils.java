package com.example.jaielalondon.googlebooks;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Preform network request to get back a list of books from Google Books API
 */
public final class QueryUtils {

    /**
     * Tag for the Log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static List<Book> getBookList(String searchBarText) {

        // Create url with the search bar text
        URL url = createUrl(searchBarText);

        List<Book> books = new ArrayList<>();

        try {
            // Call method that gets data from the JSON String that is provided
            //  by the ake HTTP Request that is made with the newly created url
            books = getJsonData(makeHttpRequest(url));


        } catch (IOException e) {
            Log.e(LOG_TAG, "Error making HTTP request", e);
        }

        return books;
    }
    /**
     * Creates a URL object from google books search URI and the search bar text
     */
    private static URL createUrl(String searchBarText) {
        URL url = null;

        //Create a url string with the google books http and the search bar text
        String googleBooksUrl = "https://www.googleapis.com/books/v1/volumes?q="
                + searchBarText.replaceAll("\\s+", "+");

        try {
            url = new URL(googleBooksUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL", e);
        }
        return url;
    }

    /**
     * Preforms URL request on given URL
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If URL is null return early
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* Milliseconds */);
            urlConnection.setConnectTimeout(15000 /* Milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the connection was successful (Success Code 200)
            // Read the input stream and parse the response
            if (urlConnection.getResponseCode() == 200) {
                Log.v(LOG_TAG, "Code is successful 200");

                // Get the inputStream and call the reamFromStream method
                // to set jsonResponse to a long string of JSON text
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                // Log error response code
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error creating HTTP request ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies that an IOException
                // could be thrown.
                inputStream.close();
            }
        }

        return jsonResponse;
    }


    /**
     * Convert Entire Json response from the server into one big string
     * so we can later parse through the string to get JSON data we want
     */
    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }

        }
        return stringBuilder.toString();
    }

    private static List<Book> getJsonData(String jsonString) {

        // If json string is empty or null, return early
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        List<Book> books = new ArrayList<>();

        try {
            // JSON root
            JSONObject jsonRoot = new JSONObject(jsonString);

            // Array called items that contains all the books
            JSONArray items = jsonRoot.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {

                // Get the item in the books array at index i
                JSONObject currentBook = items.getJSONObject(i);

                // get Object that holds info about the particular book
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                // Get the Book's Title, Author, Publish date, Average rating,
                // number of ratings, and the thumbnail image
                String title = volumeInfo.getString("title");

                JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                String author = authorsArray.get(0).toString();

                String publishedDate = volumeInfo.getString("publishedDate");

                Double averageRating = volumeInfo.optDouble("averageRating");

                int ratingsCount = volumeInfo.optInt("ratingsCount");

                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

                String thumbnailImageUrl = imageLinks.getString("thumbnail");

                //Create Drawable image from the thumbnail image url
                Drawable image = getImageFromUrl(thumbnailImageUrl);

                String description = volumeInfo.getString("description");

                JSONArray categoriesArray = volumeInfo.optJSONArray("categories");
                String genre = categoriesArray.getString(0);

                int pageCount = volumeInfo.getInt("pageCount");

                JSONObject saleInfo = currentBook.getJSONObject("saleInfo");

                String forSale = saleInfo.getString("saleability");

                String urlLink = volumeInfo.getString("infoLink");

                String price;

                if (forSale.equals("FOR_SALE")) {

                    JSONObject retailPrice = saleInfo.getJSONObject("retailPrice");
                    Double priceAmount = retailPrice.getDouble("amount");
                    String currencyCode = retailPrice.getString("currencyCode");

                    price = priceAmount + " " + currencyCode;

                } else {
                    // Book is not for sale, Make the price an empty string
                    price = "";
                }

                // Create new Book object and add it to the list
                books.add(new Book(title, author, publishedDate, averageRating,
                        ratingsCount, image, price, genre, pageCount, urlLink, description));

            }


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing JSON:", e);
        }

        return books;
    }

    /**
     * Creates an Drawable image from a url
     *
     * @param url is the url string of the image
     */
    private static Drawable getImageFromUrl(String url) {

        try {
            InputStream inputStream = (InputStream) new URL(url).getContent();
            Drawable drawable = Drawable.createFromStream(inputStream, url);

            return drawable;
        } catch (IOException e) {
            Log.v(LOG_TAG, "Error getting image from url: " + e);
        }

        return null;
    }


}
