package com.example.jaielalondon.googlebooks;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

public class BookInfoActivity extends Activity {

    private static final String LOG_TAG = BookInfoActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info);

        Book book = MainActivity.currentBook;

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(book.getTitle());

    }
}
