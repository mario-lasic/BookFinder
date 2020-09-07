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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private ListView booksListView;
    private static final String book_url = "https://www.googleapis.com/books/v1/volumes?q=horror";
    private BookAdapter mBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        booksListView = (ListView) findViewById(R.id.list);
        LoaderManager loaderManager = getSupportLoaderManager();
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