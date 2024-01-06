package com.example.palette_maker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular);
    }

    public void makePalette(View view) {
        Intent intent = new Intent(this, PaletteMaker.class);
        startActivity(intent);
    }

    public void gotoColorPicker(View view) {
        Intent intent = new Intent(this, ImageColorSelector.class);
        startActivity(intent);
    }

    public void gotoFavorites(View view) {
        Intent intent = new Intent(this, Favorites.class);
        startActivity(intent);
    }
}