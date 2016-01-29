package com.paczos.wordplay;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Word {
    String text;
    int numLetters;

    public static Word array[];

    public Word(){

    }

    public Word(String text, int numLetters) {
        this.text = text;
        this.numLetters = numLetters;
    }

    public static void generateWords(Context c, int n) {
        array = new Word[n];
        Random generator = new Random();
        for (int i = 0; i < n; i++) {
            int numLetters = 0, numLinesInFile = 0;
            String url = "";
            switch (generator.nextInt(3)) {
                case 0: {
                    numLetters = 4;
                    numLinesInFile = 3903;
                    url = "words4_english.txt";
                    break;
                }
                case 1: {
                    numLetters = 6;
                    numLinesInFile = 15232;
                    url = "words6_english.txt";
                    break;
                }
                case 2: {
                    numLetters = 8;
                    numLinesInFile = 28420;
                    url = "words8_english.txt";
                    break;
                }
                case 3: {
                    numLetters = 10;
                    numLinesInFile = 20300;
                    url = "words10_english.txt";
                    break;
                }
            }

            Word word = new Word();
            word.numLetters = numLetters;
            try {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(c.getAssets().open(url)));
                int line = generator.nextInt(numLinesInFile) + 1;

                for (int j = 0; j < line - 1; j++)
                    word.text = br.readLine();

                int ifagain = 0;

                for (int k = 0; k < i; k++) {
                    if (array[k].text.equals(word)) {
                        i--;
                        ifagain = 1;
                        break;
                    }
                }

                if (ifagain == 1)
                    continue;

                array[i] = word;

                br.close();
            } catch (IOException e) {
                Toast.makeText(c, "ERROR WHILE LOADING WORDS", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void replaceWord(Context c, int i) {
        Random generator = new Random();
        int numLetters = 0, numLinesInFile = 0;
        String url = "";
        switch (generator.nextInt(3)) {
            case 0: {
                numLetters = 4;
                numLinesInFile = 3903;
                url = "words4_english.txt";
                break;
            }
            case 1: {
                numLetters = 6;
                numLinesInFile = 15232;
                url = "words6_english.txt";
                break;
            }
            case 2: {
                numLetters = 8;
                numLinesInFile = 28420;
                url = "words8_english.txt";
                break;
            }
            case 3: {
                numLetters = 10;
                numLinesInFile = 20300;
                url = "words10_english.txt";
                break;
            }
        }

        Word word = new Word();
        word.numLetters = numLetters;
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(c.getAssets().open(url)));
            int line = generator.nextInt(numLinesInFile) + 1;

            for (int j = 0; j < line - 1; j++)
                word.text = br.readLine();

            /*int ifagain = 0;

            for (int k = 0; k < i; k++) {
                if (array[k].text.equals(word)) {
                    i--;
                    ifagain = 1;
                    break;
                }
            }*/

            array[i] = word;

            br.close();
        } catch (IOException e) {
            Toast.makeText(c, "ERROR WHILE LOADING WORDS", Toast.LENGTH_LONG).show();
        }
    }
}
