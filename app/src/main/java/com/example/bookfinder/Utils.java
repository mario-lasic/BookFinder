package com.example.bookfinder;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static List<Book> fetchBookData(String mUrl) {
        URL url = createUrl(mUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG,"Error closing input stream",e);
        }
        List<Book> book = extractBook(jsonResponse);
        return book;
    }

    private static List<Book> extractBook(String jsonResponse) {
        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        List<Book> books = new ArrayList<>();

        try{

            JSONObject root = new JSONObject(jsonResponse);

            JSONArray jsonArray = root.getJSONArray("items");

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject currentBook = jsonArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                JSONObject img = volumeInfo.getJSONObject("imageLinks");

                String title = volumeInfo.getString("title");
                String author = volumeInfo.getString("authors");
                String imgLink = img.getString("smallThumbnail");
                String preview = volumeInfo.getString("previewLink");
                String description = volumeInfo.getString("description");

                Book book = new Book(title,author,imgLink,preview,description);
                books.add(book);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG,"Problem parsing the book data",e);
        }

        return books;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG,"Error response code: " + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Proplem retrieving tge books JSON data",e);
        }
        finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        return null;
    }

    private static URL createUrl(String mUrl) {
        URL url = null;

        try {
            url = new URL(mUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Problem with creating URL", e);
        }
        return url;
    }
}
