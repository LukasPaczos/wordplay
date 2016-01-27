package com.paczos.wordplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class GamePanel extends AppCompatActivity {

    DatabaseHelper db;
    TextView test;
    String tab[], url, solution;
    int n, numLetters, numLinesInFile, sameRequired;
    Random generator = new Random();
    TextView hintLettters[], infoBox, popupText;
    int chances = 4;
    Animation in, out;
    LinearLayout popupBox;
    boolean hearts[] = new boolean[4];
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_panel);

        db = new DatabaseHelper(this);

        toast = Toast.makeText(this, "Wrong word length", Toast.LENGTH_SHORT);

        //test.setText(difficulty);

        Arrays.fill(hearts, true);

        test = (TextView)findViewById(R.id.test);

        infoBox = (TextView) findViewById(R.id.info_box);

        popupText = (TextView) findViewById(R.id.popup_text);
        popupBox = (LinearLayout) findViewById(R.id.popup_box);

        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                infoBox.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animation == out)
                    infoBox.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1000);
        in.setAnimationListener(animationListener);

        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(1000);
        out.setStartOffset(2000);
        out.setAnimationListener(animationListener);




        switch(NewMastermind.difficulty) {
            case "EASY" : {
                n = 10;
                numLetters = 4;
                numLinesInFile = 3903;
                sameRequired = 1;
                url = "words4_english.txt";
                break;
            }
            case "MEDIUM" : {
                n = 16;
                numLetters = 6;
                numLinesInFile = 15232;
                sameRequired = 2;
                url = "words6_english.txt";
                break;
            }
            case "HARD" : {
                n = 22;
                numLetters = 8;
                numLinesInFile = 28420;
                sameRequired = 4;
                url = "words8_english.txt";
                break;
            }
            case "EXTREME" : {
                n = 30;
                numLetters = 10;
                numLinesInFile = 20300;
                sameRequired = 7;
                url = "words10_english.txt";
                break;
            }
        }
        tab = new String[n];
        getWords();
    }

    public void getWords() {
        String word = "";
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(getAssets().open(url)));
            int line = generator.nextInt(numLinesInFile) + 1;

            for (int j = 0; j < line - 1; j++)
                word = br.readLine();

            tab[0] = word;

            char firstWord[] = word.toCharArray();

            for (int i = 1; i < n; i++) {
                word = br.readLine();

                int ifagain = 0;

                if (tab[0].equals(word))
                    sameRequired--;

                if (word == null) {
                    br.close();
                    br = new BufferedReader(
                            new InputStreamReader(getAssets().open(url)));
                    i--;
                    continue;
                }

                int numSameLetters = 0;
                char secondWord[] = word.toCharArray();
                for (int k = 0; k < numLetters; k++) {
                    if (firstWord[k] == secondWord[k])
                        numSameLetters++;
                }

                for (int k = 0; k < i; k++) {
                    if (tab[k].equals(word)) {
                        i--;
                        ifagain = 1;
                        break;
                    }
                }

                if (numSameLetters < sameRequired) {
                    i--;
                    ifagain = 1;
                }

                if (ifagain == 1)
                    continue;

                tab[i] = word;
            }
            br.close();
        } catch (IOException e) {
            infoBox.setText("ERROR WHILE LOADING WORDS");
        }

        int position = generator.nextInt(n);
        solution = tab[position];

        displayWords();
        addHint();
    }

    public void displayWords() {
        StringBuilder sb = new StringBuilder();
        boolean appendSeparator = false;
        for(int y=0; y < n; y++){
            if (appendSeparator)
                sb.append("    ");
            appendSeparator = true;
            if((y + 1)  % 3 == 0)
                sb.append("\n");

            sb.append(tab[y]);
        }
        TextView words = (TextView)findViewById(R.id.words_inside);
        words.setMovementMethod(new ScrollingMovementMethod());
        words.setText(sb.toString());
    }

    public void addHint() {
        hintLettters = new TextView[numLetters];
        for (int i = 0; i < numLetters; i++) {
            View linearLayout = findViewById(R.id.hint);

            TextView textView = new TextView(this);
            textView.setText("_");
            textView.setTextSize(40);
            textView.setId(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            textView.setLayoutParams(params);

            ((LinearLayout) linearLayout).addView(textView);
            hintLettters[i] = textView;
        }
    }

    public void checkAnswer(View view) {
        //test.setText(solution);
        EditText answerField = (EditText) findViewById(R.id.insert_answer);
        String answer = answerField.getText().toString().toLowerCase();

        if (answer.length() != numLetters) {
            toast.show();
            /*infoBox.setText(R.string.wrong_number_of_letters);
            infoBox.startAnimation(in);
            infoBox.startAnimation(out);*/

            return;
        }

        int correctLetters[] = new int[n];

        char solutionChar[] = solution.toCharArray();
        char answerChar[] = answer.toLowerCase().toCharArray();

        for (int i = 0; i < numLetters; i++) {
            if (solutionChar[i] == answerChar[i]) {
                correctLetters[i] = 1;
                //numCorrectLetters++;
            }
            else
                correctLetters[i] = 0;
        }

        for (int i = 0; i < numLetters; i++) {
            if (correctLetters[i] == 1)
                hintLettters[i].setText(String.valueOf(answerChar[i]));
            else
                hintLettters[i].setText("_");
        }

        if (answer.equals(solution)) {
            popupText.setText(R.string.you_won);
            popupBox.setVisibility(View.VISIBLE);
            Button button = (Button) findViewById(R.id.button_answer);
            button.setClickable(false);
            db.updateData(NewMastermind.difficulty, "WON");
        }
        else {
            infoBox.setText(R.string.wrong_answer);
            infoBox.startAnimation(in);
            infoBox.startAnimation(out);
            chances--;
            ImageView heart;
            if (chances == 3) {
                heart = (ImageView) findViewById(R.id.hearth1);
                heart.setImageResource(R.drawable.red_heart_empty2);
            }
            if (chances == 2) {
                heart = (ImageView) findViewById(R.id.hearth2);
                heart.setImageResource(R.drawable.red_heart_empty2);
            }
            if (chances == 1) {
                heart = (ImageView) findViewById(R.id.hearth3);
                heart.setImageResource(R.drawable.red_heart_empty2);
            }
            if (chances == 0) {
                heart = (ImageView) findViewById(R.id.hearth4);
                heart.setImageResource(R.drawable.red_heart_empty2);
            }

            if (chances <= 0) {
                popupText.setText(R.string.you_lost);
                popupBox.setBackgroundColor(getResources().getColor(R.color.czerowny));
                popupBox.setVisibility(View.VISIBLE);
                Button button = (Button) findViewById(R.id.button_answer);
                button.setClickable(false);
                db.updateData(NewMastermind.difficulty, "LOST");
            }
        }
    }

    public void endGame(View View) {
        finish();
    }
}
