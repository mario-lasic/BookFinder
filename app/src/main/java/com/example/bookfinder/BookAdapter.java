package com.example.bookfinder;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private View booksListView;
    TextView tvTitle,tvAuthor;
    String title, author,link;
    ImageView ivThumbnail;

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
        link = currentBook.getImgLink();
        initWidgets();
        setupTextView();
        if(!link.isEmpty()) {
            Picasso.get().load(link).into(ivThumbnail);
        }else{
            ivThumbnail.setBackgroundColor(000);
        }
        return booksListView;
    }

    private void setupTextView() {
        tvAuthor.setText(author);
        tvTitle.setText(title);
    }

    private void initWidgets() {
        tvTitle = (TextView) booksListView.findViewById(R.id.tvTitle);
        tvAuthor= (TextView) booksListView.findViewById(R.id.tvAuthor);
        ivThumbnail = (ImageView)booksListView.findViewById(R.id.ivThumbnail);

    }
}
