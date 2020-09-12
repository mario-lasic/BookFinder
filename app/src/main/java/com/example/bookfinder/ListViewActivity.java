package com.example.bookfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private ListView booksListView;
    private String book_url = "", book_url2;
    private BookAdapter mBookAdapter;
    private Button btnMore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        booksListView = (ListView) findViewById(R.id.list);
        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        String key = intent.getStringExtra("key").toLowerCase();
        if (key.contains(" ")){
            key = key.replaceAll(" ", "+");
        }
        String base_url = "https://www.googleapis.com/books/v1/volumes?q=";
        book_url = base_url + key;
        final LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(1,null, this);
        mBookAdapter = new BookAdapter(this, new ArrayList<Book>());
    }

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        return new BookLoader(this,book_url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> data) {
        mBookAdapter.clear();

        if(data != null && !data.isEmpty()){
            mBookAdapter.addAll(data);
            updateUi(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {
        mBookAdapter.clear();
    }

    private void updateUi(List<Book> earthquakes) {
        booksListView.setAdapter(mBookAdapter);

    }
}