package tech.lalik.learntowrite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import tech.lalik.learntowrite.LetterCanvas;
import tech.lalik.learntowrite.R;

public class MainActivity extends AppCompatActivity {
    private LetterCanvas letterCanvas;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        letterCanvas = findViewById(R.id.letterCanvas);
        imageView = findViewById(R.id.imageWithLetter);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Bitmap templateLetter = ((BitmapDrawable)imageView.getDrawable()).getBitmap();


                    int pixel = templateLetter.getPixel((int)event.getX(), (int)event.getY());
                    String color = String.format(
                            "(%d, %d, %d, %d)",
                            Color.alpha(pixel),
                            Color.red(pixel),
                            Color.green(pixel),
                            Color.blue(pixel)
                    );

                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            String.format("(%f, %f) %s", event.getX(), event.getY(), color),
                            Toast.LENGTH_LONG
                    );
                    toast.show();
                }

                return true;
            }
        });
    }

    public void onClear(View view) {
        letterCanvas.clear();
    }

    public void onCompare(View view) {
        Bitmap templateLetter = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Bitmap writtenLetter = letterCanvas.getBitmap();

        int width = Math.min(templateLetter.getWidth(), writtenLetter.getWidth());
        int height = Math.min(templateLetter.getHeight(), writtenLetter.getHeight());
        double diff = 0.0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int templatePixel = templateLetter.getPixel(i, j);
                int letterPixel = writtenLetter.getPixel(i, j);

                int templateColor = 1;
                if (Color.alpha(templatePixel) == 255) {
                    templateColor = 0;
                }

                int letterColor = 1;
                if (Color.red(letterPixel) == 0) {
                    letterColor = 0;
                }


                diff += Math.pow(templateColor - letterColor, 2);
            }
        }

        Toast toast = Toast.makeText(getApplicationContext(), String.format("Difference %f", diff), Toast.LENGTH_LONG);
        toast.show();
    }
}