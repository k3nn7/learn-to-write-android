package tech.lalik.learntowrite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import tech.lalik.learntowrite.R;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void openPracticeMenu(View view) {
        Intent intent = new Intent(this, PracticeMenuActivity.class);
        startActivity(intent);

    }

    public void openTakeTestMenu(View view) {
        Intent intent = new Intent(this, TakeTestMenuActivity.class);
        startActivity(intent);
    }
}
