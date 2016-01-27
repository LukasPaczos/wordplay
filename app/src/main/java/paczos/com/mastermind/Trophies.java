package paczos.com.mastermind;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Trophies extends AppCompatActivity {

    DatabaseHelper db;
    TextView statsEasy, statsMedium, statsHard, statsExtreme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophies);

        db = new DatabaseHelper(this);
        statsEasy = (TextView) findViewById(R.id.stats_easy);
        statsMedium = (TextView) findViewById(R.id.stats_medium);
        statsHard = (TextView) findViewById(R.id.stats_hard);
        statsExtreme = (TextView) findViewById(R.id.stats_extreme);
        displayStats();
    }

   public void displayStats() {
        Cursor cursor = db.getData();
        cursor.moveToNext();
        for (int i = 0; i < 4; i++) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(cursor.getString(0) + "\n" + cursor.getString(1) + "/" + cursor.getString(2));
            if (i == 0)
                statsEasy.setText(buffer);
            if (i == 1)
                statsMedium.setText(buffer);
            if (i == 2)
                statsHard.setText(buffer);
            if (i == 3)
                statsExtreme.setText(buffer);

            cursor.moveToNext();
        }
    }

    public void resetStats(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        db.reset();
                        recreate();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("YES", dialogClickListener)
                .setNegativeButton("NO", dialogClickListener).show();
    }
}