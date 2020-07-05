package com.example.project_happier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ButtonActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image;
    private TextView quote, author;

    private boolean is_dog;
    private boolean is_cat;
    private boolean is_quote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        Intent intent = getIntent();
        is_dog = intent.getBooleanExtra(MainActivity.BOOL_DOG, true);
        is_cat = intent.getBooleanExtra(MainActivity.BOOL_CAT, true);
        is_quote = intent.getBooleanExtra(MainActivity.BOOL_QUOTE, true);

        image = findViewById(R.id.image);
        quote = findViewById(R.id.quote);
        author = findViewById(R.id.author);

        if (is_dog){
            image.setImageResource(R.drawable.dog);
            quote.setVisibility(View.GONE);
            author.setVisibility(View.GONE);
        } else if (is_cat){
            image.setImageResource(R.drawable.cat);
            quote.setVisibility(View.GONE);
            author.setVisibility(View.GONE);
        } else if (is_quote){
            quote.setText("Learn to let go. That is the key to happiness.");
            author.setText("Buddha");
            image.setVisibility(View.GONE);
        }

        image.setOnClickListener(this);
        quote.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (is_dog){
            getDogImage();
        } else if (is_cat){
            getCatImage();
        } else if (is_quote){
            getQuote();
        }
    }

    public void getDogImage(){
        String api_url = "https://dog.ceo/api/breeds/image/random";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(api_url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()){
                    String myResponse = response.body().string();
                    try {
                        JSONObject obj = new JSONObject(myResponse);
                        String img_url = obj.getString("message");
                        accessURL(img_url);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    public void getCatImage(){
        String api_url = "https://api.thecatapi.com/v1/images/search?format=json";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(api_url)
                .header("x-api-key", "c32fe060-cce3-462a-9674-09d23fa77b30")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()){
                    String myResponse = response.body().string();
                    try {
                        JSONArray array = new JSONArray(myResponse);
                        JSONObject object = array.getJSONObject(0);
                        String img_url = object.getString("url");
                        accessURL(img_url);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    public void getQuote(){
        String api_url = "https://quote-garden.herokuapp.com/api/v2/quotes/random";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(api_url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()){
                    String myResponse = response.body().string();
                    try {
                        JSONObject obj = new JSONObject(myResponse);
                        JSONObject object = obj.getJSONObject("quote");
                        String quoteText = object.getString("quoteText");
                        String quoteAuthor = object.getString("quoteAuthor");
                        setText(quote, quoteText);
                        setText(author, quoteAuthor);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void accessURL(final String str){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Picasso.get().load(str).into(image);
            }
        });
    }

    private void setText(final TextView text, final  String str){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(str);
            }
        });
    }
}