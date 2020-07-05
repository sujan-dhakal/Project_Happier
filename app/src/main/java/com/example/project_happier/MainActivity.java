package com.example.project_happier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button dog_button, cat_button, quote_button;
    private boolean is_dog;
    private boolean is_cat;
    private boolean is_quote;

    public static final String BOOL_DOG = "com.example.project_happier.BOOL_DOG";
    public static final String BOOL_CAT = "com.example.project_happier.BOOL_CAT";
    public static final String BOOL_QUOTE = "com.example.project_happier.BOOL_QUOTE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dog_button = (Button) findViewById(R.id.dog_button);
        cat_button = (Button) findViewById(R.id.cat_button);
        quote_button = (Button) findViewById(R.id.quote_button);

        dog_button.setOnClickListener(this);
        cat_button.setOnClickListener(this);
        quote_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dog_button:
                is_dog = true;
                is_cat = false;
                is_quote = false;
                openButtonActivity();
                break;
            case R.id.cat_button:
                is_cat = true;
                is_dog = false;
                is_quote = false;
                openButtonActivity();
                break;
            case R.id.quote_button:
                is_quote = true;
                is_dog = false;
                is_cat = false;
                openButtonActivity();
                break;
        }
    }

    public void openButtonActivity(){
        Intent intent = new Intent(this, ButtonActivity.class);
        intent.putExtra(BOOL_DOG, is_dog);
        intent.putExtra(BOOL_CAT, is_cat);
        intent.putExtra(BOOL_QUOTE, is_quote);
        startActivity(intent);
    }
}