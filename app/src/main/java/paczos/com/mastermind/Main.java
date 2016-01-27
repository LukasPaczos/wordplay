package paczos.com.mastermind;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main extends AppCompatActivity {

    TextView rules, stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FontsOverride.setDefaultFont(this, "MONOSPACE", "ashley.ttf");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rules = (TextView)findViewById(R.id.rules_text);
        stats = (TextView)findViewById(R.id.stats_text);
    }

    public void newGameButton(View view) {
        Intent intent = new Intent(this, NewGame.class);
        startActivity(intent);
    }

    public void trophiesButton(View view) {
        Intent intent = new Intent(this, Trophies.class);
        startActivity(intent);
    }

    public void rulesButton(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.rules).setTitle("RULES").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
