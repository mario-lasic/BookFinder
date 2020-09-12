package com.example.bookfinder;

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

    // Parsing json
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

                String link = "";
                String author = "";
                String title = volumeInfo.getString("title");
                if(volumeInfo.has("authors")) {
                    author = volumeInfo.getString("authors");
                }
                if(volumeInfo.has("imageLinks")){
                    JSONObject img = volumeInfo.optJSONObject("imageLinks");
                    String imgLink = img.getString("thumbnail");
                    //adding S to http
                    StringBuilder stringBuilder = new StringBuilder(imgLink);
                    stringBuilder.insert(4,"s");
                    link = stringBuilder.toString();}
                String preview = volumeInfo.getString("previewLink");
                //String genre = volumeInfo.getString("categories");*/

                author = author.replaceAll("[^a-z ^A-Z ^,]", "");





                    Book book = new Book(title,author,link,preview);
                    books.add(book);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG,"Problem parsing the book data",e);
        }

        return books;
    }
    // Connecting with http
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

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
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
