package paczos.com.mastermind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewCross extends AppCompatActivity {

    public static String difficulty = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cross);
    }

    public void onClick(View view) {
        Button button = (Button)view;
        difficulty = button.getText().toString();
        Intent intent = new Intent(this, GamePanelCross.class);
        startActivity(intent);
        finish();
    }
}