package com.paczos.wordplay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

public class GamePanelCross extends AppCompatActivity {

    public static final int n = 5, rows = 8, columns = 8;
    public int tries = 0;
    public Random generator;
    String alphabet[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                            "k", "l", "m", "n", "o", "p", "r", "s", "t", "u",
                            "w", "y", "z"};

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_panel_cross);

        tableLayout = (TableLayout)findViewById(R.id.cross_word_layout);
        generator = new Random();
        fillTable();
    }

    public void fillTable() {
        Word.generateWords(this, n);
        Word array[] = Word.array;

        for (int i = 0; i < rows; i++) {
            TableRow tableRow = new TableRow(this);
            for (int j = 0; j < columns; j++) {
                TextView textView = new TextView(this);
                TableRow.LayoutParams params = new TableRow.LayoutParams(0);
                textView.setText("");
                textView.setTextSize(30);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setLayoutParams(params);
                new LetterView(textView, i, j);
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }

        //filling with random letters
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                LetterView.array[(i * GamePanelCross.columns + j)]
                        .textView.setText(alphabet[generator.nextInt(23)]);
            }
        }

        tries = 0;
        //adding proper words between random letters (+ "." for testing purposes)
        for (int i = 0; i < n; i++) {

            if (tries > 20) {
                Word.replaceWord(this, i);
                tries = 0;
            }
            char cArray[] = array[i].text.toCharArray();
            int r = generator.nextInt(2);
            //horizontal
            if (r == 0) {
                int y = generator.nextInt(columns);
                int x = generator.nextInt(rows - array[i].numLetters + 1);
                for (int j = 0; j < array[i].numLetters; j++) {
                    LetterView letterView = LetterView.array[(x * GamePanelCross.columns + y + (j * columns))];
                    if (letterView.taken) {
                        if ((String.valueOf(cArray[j]) + ".").equals(letterView.textView.getText().toString())) {
                            letterView.textView.setText(String.valueOf(cArray[j]) + ".");
                            tries = 0;
                        }
                        else {
                            tries++;
                            i--;
                            for (int k = j - 1; k >= 0; k--) {
                                letterView = LetterView.array[(x * GamePanelCross.columns + y + (k * columns))];
                                letterView.textView.setText(String.valueOf(cArray[k]));
                                letterView.taken = false;
                            }
                            break;
                        }
                    }
                    else {
                        letterView.textView.setText(String.valueOf(cArray[j]) + ".");
                        letterView.taken = true;
                    }
                }
            }
            //vertical
            else {
                int y = generator.nextInt(columns - array[i].numLetters + 1);
                int x = generator.nextInt(rows);
                for (int j = 0; j < array[i].numLetters; j++) {
                    LetterView letterView = LetterView.array[(x * GamePanelCross.columns + y + j)];
                    if (letterView.taken) {
                        if ((String.valueOf(cArray[j]) + ".").equals(letterView.textView.getText().toString())) {
                            letterView.textView.setText(String.valueOf(cArray[j]) + ".");
                            tries = 0;
                        }
                        else {
                            tries++;
                            i--;
                            for (int k = j - 1; k >= 0; k--) {
                                letterView = LetterView.array[(x * GamePanelCross.columns + y + k)];
                                letterView.textView.setText(String.valueOf(cArray[k]));
                                letterView.taken = false;
                            }
                            break;
                        }
                    }
                    else {
                        letterView.textView.setText(String.valueOf(cArray[j]) + ".");
                        letterView.taken = true;
                    }
                }
            }
        }
    }
}