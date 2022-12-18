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
    public static int sizeGraphX = 640, sizeGraphY = 480;
    public static int maxStep = 1000;
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
            //drawPaint(my_paint);
        }

    }

    public class myPoint {
        int x; int y; Color c;
        public myPoint(int x_, int y_, Color c_) {
            x=x_; y=y_;	c=c_;
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
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void drawPaintSketchImage(float amax_, float amin_, float bmax_, float bmin_) {

        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(imageView.getWidth(),
                    imageView.getHeight(),
                    Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            paint.setColor(Color.RED);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(8);
        }
        // ЛОГИКА ПОСТРОЕНИЯ ФРАКТАЛА
        ArrayList<myPoint> points = new ArrayList<>();
        float da = (amax_ - amin_) / (sizeGraphX - 1);
        float db = (bmax_ - bmin_) / (sizeGraphY - 1);
        myComplex z = new myComplex();
        myComplex c = new myComplex();
        Canvas canvas = new Canvas();
        Color col = new Color();
        int k = 0;double norm;
        for (int h = 0; h < sizeGraphX; h++)
            for (int w = 0; w < sizeGraphY; w++) {
                z.set(0.0f, 0.0f);
                c.set(amin_ + da * h, bmin_ + db * w);
                float re1, im1;
                do {
                    re1 = z.re() * z.re() - z.im() * z.im() + c.re();
                    im1 = 2.0f * z.re() * z.im() + c.im();
                    norm = Math.sqrt(re1 * re1 + im1 * im1);
                    z.set(re1, im1);
                    z.sqr(z, c);
                    norm = z.norm();
                    k++;
                    Log.d("DECOLOR", "K = " + k + "NORM = " + norm);
                } while (k < maxStep && norm < maxNorm);
                int alpha = 1;
                Color cooo = Color.valueOf(2f,3f,5f);
                Log.d("DECOLOR"," " + Math.round(255 - 10 * k)+ " " + Math.round(255 - 10 * k)+ " " + Math.round(255 - 10 * k));
                Color new_c = Color.valueOf(Color.BLUE);
                if (k < 25) {
                    //points.add(new myPoint(h, w, new Color(Math.round(255 - 10 * k), Math.round(255 - 10 * k), Math.round(255 - 10 * k))));
                    points.add(new myPoint(h, w, new_c));
                }
//                else if (k < 26)
//                    points.add(new myPoint(h, w, new Color(255, 255, 255)));
//                else if (k < 27)
//                    points.add(new myPoint(h, w, new Color(5, 5, 5)));
//                else if (k < 28)
//                    points.add(new myPoint(h, w, new Color(190, 5, 5)));
//                else if (k < 29)
//                    points.add(new myPoint(h, w, new Color(255, 255, 0)));
//                else if (k < 30)
//                    points.add(new myPoint(h, w, new Color(0, 0, 0)));
//                else if (k < 32)
//                    points.add(new myPoint(h, w, new Color(0, 100, 100)));
//                else if (k < 34)
//                    points.add(new myPoint(h, w, new Color(0, 0, 0)));
//                else if (k < 36)
//                    points.add(new myPoint(h, w, new Color(255, 0, 0)));
//                else if (k < 38)
//                    points.add(new myPoint(h, w, new Color(50, 0, 0)));
//                else if (k < 39)
//                    points.add(new myPoint(h, w, new Color(0, 0, 50)));
//                else if (k < 120)
//                    points.add(new myPoint(h, w, new Color(0, 0, 0)));
//                else if (k < 140)
//                    points.add(new myPoint(h, w, new Color(255, 0, 0)));
//                else if (k < 150)
//                    points.add(new myPoint(h, w, new Color(255, 255, k)));
//                else if (k < maxStep)
//                    points.add(new myPoint(h, w, new Color(0, 255, Math.round(255 / (maxStep / k)))));
                else {
                    Color new_c1 = Color.valueOf(Color.RED);
                    points.add(new myPoint(h, w, new_c1));
                }
                //ОТРИСОВКА
                for(myPoint point : points) {
                   // paint.setColor(point.c);
                    canvas.drawOval(point.x, point.y, 1, 1,paint);
                }
                //canvas.drawOval(200f, 500f, 230f, 300f, paint);
                imageView.setImageBitmap(bitmap);
            }
    }

}