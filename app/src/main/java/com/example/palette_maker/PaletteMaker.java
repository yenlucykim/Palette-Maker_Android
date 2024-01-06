package com.example.palette_maker;

import static java.lang.Math.round;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class PaletteMaker extends AppCompatActivity {
    ArrayList<Integer> COLOR_BUTTONS = new ArrayList<>(Arrays.asList(
            R.id.button0_p,
            R.id.button1_p,
            R.id.button2_p,
            R.id.button3_p,
            R.id.button4_p,
            R.id.button5_p,
            R.id.button6_p
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.palette_maker);
    }
    public void gotoFavorites(View view) {
        Intent intent = new Intent(this, Favorites.class);
        startActivity(intent);
    }

    public void gotoContrast(View view) {
        Intent intent = new Intent(this, ColorContrast.class);
        startActivity(intent);
    }

    public void clickRandom(View view) {
        setRandomPalette();
    }

    public String formatHexColor(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    private void setRandomPalette() {
        int red = (int) (Math.random()*255);
        int green = (int) (Math.random()*255);
        int blue = (int) (Math.random()*255);
        int base_color = Color.rgb(red, green, blue);
        ArrayList<String> palette = generatePalette(formatHexColor(base_color));
        for (int i = 0; i < 7; ++i) {
            TextView colorBox = findViewById(COLOR_BUTTONS.get(i));
            colorBox.setText(palette.get(i));
            colorBox.setBackgroundColor(Color.parseColor(palette.get(i)));
        }
    }

    public ArrayList<String> generatePalette(String color_hex) {
        //Generates a color palette based on triadic principles
        float[][] hsv_colors = new float[7][3];
        for (int i = 0; i < 7; ++i) {
            Color.colorToHSV(Color.parseColor(color_hex), hsv_colors[i]);
        }
        //First two colors are same hue with different saturation and value
        hsv_colors[1][1] *= 1+Math.random()/2;
        hsv_colors[1][2] *= 1+Math.random()/2;
        hsv_colors[2][1] /= 1+Math.random()/2;
        hsv_colors[2][2] /= 1+Math.random()/2;
        //Determine angle of triadic split
        int split_angle = (int) round(30+Math.random()*30);
        //Second two colors are first triadic complement with varying saturation and value
        hsv_colors[3][0] = (hsv_colors[3][0]+180+split_angle)%360;
        hsv_colors[3][1] *= 1+Math.random()/2;
        hsv_colors[3][2] *= 1+Math.random()/2;
        hsv_colors[4][0] = (hsv_colors[4][0]+180+split_angle)%360;
        hsv_colors[4][1] /= 1+Math.random()/2;
        hsv_colors[4][2] /= 1+Math.random()/2;
        //Third two colors are second triadic complement with varying saturation and value
        hsv_colors[5][0] = (hsv_colors[5][0]+180-split_angle)%360;
        hsv_colors[5][1] *= 1+Math.random()/2;
        hsv_colors[5][2] *= 1+Math.random()/2;
        hsv_colors[6][0] = (hsv_colors[6][0]+180-split_angle)%360;
        hsv_colors[6][1] /= 1+Math.random()/2;
        hsv_colors[6][2] /= 1+Math.random()/2;
        ArrayList<String> hex_colors = new ArrayList<String>();
        for (int i = 0; i < 7; ++i) {
            hex_colors.add(formatHexColor(Color.HSVToColor(hsv_colors[i])));
        }
        return hex_colors;
    }

    public int saveFavoritePalette(ArrayList<String> hex_colors, String palette_name) {
        String[] files = this.fileList();
        for (String f: files) {
            if (String.format("%s_favorite", palette_name) == f) {
                return 1;
            }
        }
        String palette_save_string = palette_name;
        for (String c: hex_colors) {
            palette_save_string += String.format("\n%s", c);
        }
        try (FileOutputStream fos = this.openFileOutput(String.format("%s_favorite", palette_name), Context.MODE_PRIVATE)) {
            fos.write(palette_save_string.getBytes());
        } catch (FileNotFoundException e) {
            return 2;
        } catch (IOException e) {
            return 3;
        }
        return 0;
    }

    public ArrayList<String> loadFavoritePalette(String palette_name) {
        ArrayList<String> hex_colors = new ArrayList<String>();
        FileInputStream fis = null;
        try {
            fis = this.openFileInput(String.format("%s_favorite", palette_name));
        } catch (FileNotFoundException e) {
            //hex colors always start with #, so outside the function, this is an error
            hex_colors.set(0, "File Not Found");
            return hex_colors;
        }
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                if (line.charAt(0) == '#') {
                    hex_colors.add(line);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            hex_colors.set(0, "I/O Exception occurred while reading file");
            return hex_colors;
        }
        return hex_colors;
    }

    public ArrayList<String> loadPopularPalette(String palette_name) {
        ArrayList<String> hex_colors = new ArrayList<String>();
        FileInputStream fis = null;
        try {
            fis = this.openFileInput(String.format("%s_popular", palette_name));
        } catch (FileNotFoundException e) {
            //hex colors always start with #, so outside the function, this is an error
            hex_colors.set(0, "File Not Found");
            return hex_colors;
        }
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                if (line.charAt(0) == '#')
                    hex_colors.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            hex_colors.set(0, "I/O Exception occurred while reading file");
            return hex_colors;
        }
        return hex_colors;
    }
}
