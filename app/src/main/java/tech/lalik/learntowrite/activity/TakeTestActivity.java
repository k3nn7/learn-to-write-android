package tech.lalik.learntowrite.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import tech.lalik.learntowrite.LetterCanvas;
import tech.lalik.learntowrite.R;
import tech.lalik.learntowrite.bitmap.BinaryBitmap;
import tech.lalik.learntowrite.bitmap.BoundingBox;

public class TakeTestActivity extends AppCompatActivity {
    private LetterCanvas letterCanvas;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_take_test);

        letterCanvas = findViewById(R.id.letterCanvas);
        imageView = findViewById(R.id.imageWithLetter);
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
        letterCanvas.invalidate();
        toast2.show();
    }

    static class CompareTask extends AsyncTask<Bitmap, Void, Double> {
        @Override
        protected Double doInBackground(Bitmap... bitmaps) {
            Bitmap templateLetter = bitmaps[0];
            Bitmap writtenLetter = bitmaps[1];

            BinaryBitmap templateLetterBitmap = new BinaryBitmap(templateLetter.getWidth(), templateLetter.getHeight());
            for (int i = 0; i < templateLetter.getWidth(); i++) {
                for (int j = 0; j < templateLetter.getHeight(); j++) {
                    if (templateLetter.getPixel(i, j) == Color.BLACK) {
                        templateLetterBitmap.turnOnPixel(i, j);
                    }
                }
            }

            BinaryBitmap writtenLetterBitmap = new BinaryBitmap(writtenLetter.getWidth(), writtenLetter.getHeight());
            for (int i = 0; i < writtenLetter.getWidth(); i++) {
                for (int j = 0; j < templateLetter.getHeight(); j++) {
                    if (writtenLetter.getPixel(i, j) == Color.BLACK) {
                        writtenLetterBitmap.turnOnPixel(i, j);
                    }
                }
            }

            BoundingBox templateBoundingBox = templateLetterBitmap.getBoundingBox();
            BoundingBox writtenLetterBoundingBox = writtenLetterBitmap.getBoundingBox();

            writtenLetterBitmap.translate(
                    templateBoundingBox.left - writtenLetterBoundingBox.left,
                    templateBoundingBox.top - writtenLetterBoundingBox.top
            );

            writtenLetterBoundingBox = writtenLetterBitmap.getBoundingBox();
            writtenLetterBitmap.scaleBox(writtenLetterBoundingBox, templateBoundingBox);

            writtenLetter.eraseColor(Color.WHITE);
            for (int i = 0; i < writtenLetter.getWidth(); i++) {
                for (int j = 0; j < writtenLetter.getHeight(); j++) {
                    if (writtenLetterBitmap.getPixel(i, j)) {
                        writtenLetter.setPixel(i, j, Color.BLACK);
                    }
                }
            }

            return templateLetterBitmap.distanceToBitmap(writtenLetterBitmap).distance;
        }
    }
}