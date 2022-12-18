package com.example.fractals;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText aMin;
    private EditText aMax;
    private EditText bMin;
    private EditText bMax;

    private ImageView imageView;

    private float floatStartX = -1, floatStartY = -1,
            floatEndX = -1, floatEndY = -1;

    //CONST
    public static int sizeGraphX = 10, sizeGraphY = 10;
    public static int maxStep = 70;
    public static float maxNorm = 1.0e6f;

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint = new Paint();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aMin = (EditText) findViewById(R.id.a_min);
        aMax = (EditText) findViewById(R.id.a_max);
        bMin = (EditText) findViewById(R.id.b_min);
        bMax = (EditText) findViewById(R.id.b_max);

        aMin.setText("-0.713");
        aMax.setText("-0.4084");
        bMin.setText("0.49216");
        bMax.setText("0.71429");

        imageView = findViewById(R.id.imageView);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Color translucentRed = Color.valueOf(1.0f, 0.0f, 0.0f, 0.5f);
            Paint my_paint = new Paint();
        }
    }
    public class myPoint {
        int x; int y;
        float r,g,b ;
        public myPoint(int x_, int y_, float r_, float g_, float b_ ) {
            x=x_; y=y_;
            r = r_;
            g = g_;
            b = b_;
        }
    }
    public void onPaintClick(View view) {
         float a_Min = Float.parseFloat(String.valueOf(aMin.getText()));
         float a_Max = Float.parseFloat(String.valueOf(aMax.getText()));
         float b_Min = Float.parseFloat(String.valueOf(bMin.getText()));
         float b_Max = Float.parseFloat(String.valueOf(bMax.getText()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            drawPaintSketchImage(a_Min , a_Max ,b_Min ,b_Max );
        }
    }
    public void onClean(View view) {
        aMin.setText("");
        aMax.setText("");
        bMin.setText("");
        bMax.setText("");

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void drawPaintSketchImage(float amax_, float amin_, float bmax_, float bmin_) {

        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(imageView.getWidth(),
                    imageView.getHeight(),
                    Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            Color new_c = Color.valueOf(58, 199, 95);
            paint.setColor(new_c.toArgb());
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(8);
            imageView.setImageBitmap(bitmap);
        }
        // ЛОГИКА ПОСТРОЕНИЯ ФРАКТАЛА
        ArrayList<myPoint> points = new ArrayList<>();
        float da = (amax_ - amin_) / (sizeGraphX - 1);
        float db = (bmax_ - bmin_) / (sizeGraphY - 1);
        myComplex z = new myComplex();
        myComplex c = new myComplex();
        double norm;
        for( int h=0; h<sizeGraphX; h++)
            for( int w=0; w<sizeGraphY; w++) {
                c.set(amin_+da*h, bmin_+db*w);
                int k = 0;z.set(0.0f,  0.0f);
                //float re1, im1;
                do {
                    z.sqr(z, c);
                    norm = z.norm();
                    k++;
                }  while (k < maxStep && norm < maxNorm);
                if (k < 25) {
                    points.add(new myPoint(h, w, Math.round(255 - 10 * k), Math.round(255 - 10 * k), Math.round(255 - 10 * k)));
                }
                else if (k < 26)
                    points.add(new myPoint(h, w, 255, 255, 255));
                else if (k < 27)
                    points.add(new myPoint(h, w, 5, 5, 5));
                else if (k < 28)
                    points.add(new myPoint(h, w, 190, 5, 5));
                else if (k < 29)
                    points.add(new myPoint(h, w, 255, 255, 0));
                else if (k < 30)
                    points.add(new myPoint(h, w, 0, 0, 0));
                else if (k < 32)
                    points.add(new myPoint(h, w, 0, 100, 100));
                else if (k < 34)
                    points.add(new myPoint(h, w, 0, 0, 0));
                else if (k < 36)
                    points.add(new myPoint(h, w,255, 0, 0));
                else if (k < 38)
                    points.add(new myPoint(h, w, 50, 0, 0));
                else if (k < 39)
                    points.add(new myPoint(h, w, 0, 0, 50));
                else if (k < 120)
                    points.add(new myPoint(h, w, 0, 0, 0));
                else if (k < 140)
                    points.add(new myPoint(h, w, 255, 0, 0));
                else if (k < 150)
                    points.add(new myPoint(h, w,255, 255, k));
                else if (k < maxStep)
                    points.add(new myPoint(h, w, 0, 255, Math.round(255 / (maxStep / k))));
                else {
                    points.add(new myPoint(h, w, 0, 0, 0));
                }
            }
        //ОТРИСОВКА
        for(myPoint point : points) {
            Color new_c = Color.valueOf(point.r, point.g, point.b);
            paint.setColor(new_c.toArgb());
            Log.d("DECOLOR", " " + point.r + " " + point.g+ " " + point.b);
            canvas.drawPoint(point.x, point.y, paint);
        }
        imageView.setImageBitmap(bitmap);
    }
}