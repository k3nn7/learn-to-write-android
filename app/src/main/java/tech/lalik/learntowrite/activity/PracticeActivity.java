package tech.lalik.learntowrite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import tech.lalik.learntowrite.R;

public class PracticeActivity extends AppCompatActivity {
    public static final String PRACTICE_TYPE = "tech.lalik.learntowrite.PRACTICE_TYPE";
    public static final int SMALL_LETTERS = 1;
    public static final int CAPITAL_LETTERS = 2;
    public static final int ALL_LETTERS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
    }
}
