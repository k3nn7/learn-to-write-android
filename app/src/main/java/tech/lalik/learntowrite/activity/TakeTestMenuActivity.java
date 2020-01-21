package tech.lalik.learntowrite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import tech.lalik.learntowrite.R;

public class TakeTestMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_test_menu);
    }

    public void onSmallLetters(View view) {
        Intent intent = new Intent(this, TakeTestActivity.class);
        startActivity(intent);
    }

    public void onCapitalLetters(View view) {
        Intent intent = new Intent(this, TakeTestActivity.class);
        startActivity(intent);
    }

    public void onAllLetters(View view) {
        Intent intent = new Intent(this, TakeTestActivity.class);
        startActivity(intent);
    }

    public void onGoBack(View view) {
        finish();
    }
}
