package tech.lalik.learntowrite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import tech.lalik.learntowrite.R;

public class PracticeMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_menu);
    }

    public void onSmallLetters(View view) {
        Intent intent = new Intent(this, PracticeActivity.class);
        intent.putExtra(PracticeActivity.PRACTICE_TYPE, PracticeActivity.SMALL_LETTERS);
        startActivity(intent);
    }

    public void onCapitalLetters(View view) {
        Intent intent = new Intent(this, PracticeActivity.class);
        intent.putExtra(PracticeActivity.PRACTICE_TYPE, PracticeActivity.CAPITAL_LETTERS);
        startActivity(intent);
    }

    public void onAllLetters(View view) {
        Intent intent = new Intent(this, PracticeActivity.class);
        intent.putExtra(PracticeActivity.PRACTICE_TYPE, PracticeActivity.ALL_LETTERS);
        startActivity(intent);
    }
}
