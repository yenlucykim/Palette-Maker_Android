package com.example.palette_maker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Favorites extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);
    }
    public void makePalette(View view) {
        Intent intent = new Intent(this, PaletteMaker.class);
        startActivity(intent);
    }

    public void gotoColorPicker(View view) {
        Intent intent = new Intent(this, ImageColorSelector.class);
        startActivity(intent);
    }

    public void gotoPopular(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
