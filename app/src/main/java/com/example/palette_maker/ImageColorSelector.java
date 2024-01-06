package com.example.palette_maker;

import static java.lang.Math.round;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ImageColorSelector extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_palette_maker);
    }

    public void gotoFavorites(View view) {
        Intent intent = new Intent(this, Favorites.class);
        startActivity(intent);
    }

    public void gotoContrast(View view) {
        Intent intent = new Intent(this, ColorContrast.class);
        startActivity(intent);
    }
}
