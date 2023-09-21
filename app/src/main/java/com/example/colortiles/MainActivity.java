package com.example.colortiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int darkColor;
    int brightColor;
    Resources r;
    Integer[] colors;
    View[][] tiles = new View[4][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r = getResources();
        darkColor = r.getColor(R.color.dark);
        brightColor = r.getColor(R.color.bright);
        colors = new Integer[]{darkColor, brightColor};
//        tiles[0][0] = findViewById(R.id.t00);
        initializeTiles();
    }
    public void initializeTiles(){
        for(int i = 0; i < 4; i++){
            for(int j=0; j<4;j++){
                Random rand = new Random();
                tiles[i][j] = findViewById(r.getIdentifier(String.format("t%d%d", i,j), "id", getApplicationContext().getPackageName() ));
                tiles[i][j].setBackgroundColor(colors[rand.nextInt(2)]);
            }
        }
        if (checkEnd()){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You are a lucky guy!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void changeColor(View v) {
        ColorDrawable d = (ColorDrawable) v.getBackground();
        if (d.getColor() == brightColor) {
            v.setBackgroundColor(darkColor);
        } else {
            v.setBackgroundColor(brightColor);
        }
    }
    public boolean checkEnd(){
        int fval = ((ColorDrawable)tiles[0][0].getBackground()).getColor();
        for(int i = 0; i < 4; i++){
            for(int j=0; j<4;j++){
                int val = ((ColorDrawable)tiles[i][j].getBackground()).getColor();
                if(val != fval) return false;
            }
        }
        return true;
    }
    public void onClick(View v) {
        // получаем тэг на кнопке
        String id = r.getResourceEntryName(v.getId());
        int x = Integer.parseInt(String.valueOf(id.charAt(1)));
        int y = Integer.parseInt(String.valueOf(id.charAt(2)));
//        int x = 1, y = 1; // координаты тайла и строки вида "00"
//
//        // изменить цвет на самом тайле и всех тайлах
//        // с таким же x и таким же y
        changeColor(v);
        for (int i = 0; i < 4; i++) {
            changeColor(tiles[x][i]);
            changeColor(tiles[i][y]);
        }
        if (checkEnd()){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You won!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}