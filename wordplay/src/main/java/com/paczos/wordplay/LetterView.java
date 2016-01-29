package com.paczos.wordplay;

import android.widget.TextView;

public class LetterView {

    public static LetterView array[] = new LetterView[GamePanelCross.rows * GamePanelCross.columns];

    boolean hovered;
    int posRow, posCol;
    TextView textView;
    public boolean taken;

    public LetterView(TextView textView, int x, int y) {
        this.textView = textView;
        this.hovered = false;
        this.taken = false;
        this.posRow = x;
        this.posCol = y;
        array[(x * GamePanelCross.columns + y)] = this;
    }
}
