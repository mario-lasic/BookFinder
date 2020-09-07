package com.example.bookfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private View booksListView;
    TextView tvTitle,tvAuthor;
    String title, author;

    public BookAdapter(@NonNull Context context, @NonNull List<Book> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        booksListView = convertView;
        if (booksListView == null){
            booksListView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Book currentBook = getItem(position);
        title = currentBook.getTitle();
        author = currentBook.getAuthor();

        initWidgets();
        setupTextView();

        return booksListView;
    }

    private void setupTextView() {
        tvAuthor.setText(author);
        tvTitle.setText(title);
    }

    private void initWidgets() {
        tvTitle = (TextView) booksListView.findViewById(R.id.tvTitle);
        tvAuthor= (TextView) booksListView.findViewById(R.id.tvAuthor);
    }
}
