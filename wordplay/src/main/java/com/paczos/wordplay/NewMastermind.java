package com.paczos.wordplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewMastermind extends AppCompatActivity {

    static String difficulty = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mastermind);
    }

    public void onClick(View view) {
        Button button = (Button)view;
        difficulty = button.getText().toString();
        Intent intent = new Intent(this, GamePanel.class);
        startActivity(intent);
        finish();
    }
}