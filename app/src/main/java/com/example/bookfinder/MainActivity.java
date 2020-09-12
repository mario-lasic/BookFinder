package com.example.bookfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button btnFind;
    EditText etKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        startNewActivity();
    }

    private void startNewActivity() {
        final Intent intent = new Intent(this, ListViewActivity.class);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = etKey.getText().toString();
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });
    }

    private void initWidgets() {
        btnFind = (Button)findViewById(R.id.btnFind);
        etKey = (EditText)findViewById(R.id.etKey);

    }
}