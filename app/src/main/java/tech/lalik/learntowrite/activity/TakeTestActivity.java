package tech.lalik.learntowrite.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import tech.lalik.learntowrite.LetterCanvas;
import tech.lalik.learntowrite.R;

public class TakeTestActivity extends AppCompatActivity {
    private LetterCanvas letterCanvas;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_take_test);

        letterCanvas = findViewById(R.id.letterCanvas);
        imageView = findViewById(R.id.imageWithLetter);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Bitmap templateLetter = ((BitmapDrawable) imageView.getDrawable()).getBitmap();


                    int pixel = templateLetter.getPixel((int) event.getX(), (int) event.getY());
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

    public void onCompare(View view) throws ExecutionException, InterruptedException {
        Toast toast = Toast.makeText(
                getApplicationContext(),
                "Comparing...",
                Toast.LENGTH_LONG
        );
        toast.show();

        imageView.buildDrawingCache();
        Bitmap templateLetter = imageView.getDrawingCache();
        Bitmap writtenLetter = letterCanvas.getBitmap();

        double result = new CompareTask().execute(templateLetter, writtenLetter).get();
        Toast toast2 = Toast.makeText(getApplicationContext(), String.format("Difference %f", result), Toast.LENGTH_LONG);
        toast2.show();
        letterCanvas.invalidate();
    }

    class CompareTask extends AsyncTask<Bitmap, Void, Double> {
        @Override
        protected Double doInBackground(Bitmap... bitmaps) {
            Bitmap templateLetter = bitmaps[0];
            Bitmap writtenLetter = bitmaps[1];

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

                    if (templateColor == 0 && letterColor == 0) {
                        writtenLetter.setPixel(i, j, Color.rgb(0, 255, 0));
                    }

                    if (templateColor == 0 && letterColor != 0) {
                        writtenLetter.setPixel(i, j, Color.rgb(255, 0, 0));
                    }

                    diff += Math.pow(templateColor - letterColor, 2);
                }
            }

            return diff / (width * height);
        }
    }
}