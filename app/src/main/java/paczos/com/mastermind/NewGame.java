package paczos.com.mastermind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
    }

    public void onClick(View view) {
        Button button = (Button)view;
        String game = button.getText().toString();

        switch(game) {
            case "Mastermind" : {
                Intent intent = new Intent(this, NewMastermind.class);
                startActivity(intent);
                finish();
                break;
            }
            case "Cross Words" : {
                Intent intent = new Intent(this, NewCross.class);
                startActivity(intent);
                finish();
                break;
            }
        }
    }
}