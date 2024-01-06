package com.example.palette_maker;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ColorContrast extends AppCompatActivity {
    ArrayList<Integer> COLOR_BUTTONS = new ArrayList<>(Arrays.asList(
            R.id.button0_p,
            R.id.button1_p,
            R.id.button2_p,
            R.id.button3_p,
            R.id.button4_p,
            R.id.button5_p,
            R.id.button6_p
    ));
    int selected_button_1 = 0;
    int selected_button_2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_contrast);
        updateColorContrastInfo();
    }

    public void makePalette(View view) {
        Intent intent = new Intent(this, PaletteMaker.class);
        startActivity(intent);
    }


    private void updateColorContrastInfo() {
        String contrastMessage = "";
        TextView selectedColorView1 = findViewById(COLOR_BUTTONS.get(selected_button_1));
        TextView selectedColorView2 = findViewById(COLOR_BUTTONS.get(selected_button_2));
        String selectedColor1 = selectedColorView1.getText().toString();
        String selectedColor2 = selectedColorView2.getText().toString();
        double contrast = colorContrast(
                selectedColor1,
                selectedColor2
        );
        DecimalFormat df = new DecimalFormat("#.####");
        contrastMessage = ("Selected Colors: "
                + selectedColor1 + ", "
                + selectedColor2
                + "\nColor Contrast: "
                + df.format(contrast)
        );
        TextView textView = findViewById(R.id.contrastInfo);
        textView.setText(contrastMessage);
    }

    private void updateColorContrastText() {
        TextView selectedColorView1 = findViewById(COLOR_BUTTONS.get(selected_button_1));
        TextView selectedColorView2 = findViewById(COLOR_BUTTONS.get(selected_button_2));
        String selectedColor1 = selectedColorView1.getText().toString();
        String selectedColor2 = selectedColorView2.getText().toString();
        EditText user_text = findViewById(R.id.inputText);
        TextView preview_text = findViewById(R.id.previewText);
        String message = user_text.getText().toString();
        preview_text.setTextColor(Color.parseColor(selectedColor1));
        preview_text.setBackgroundColor(Color.parseColor(selectedColor2));
        preview_text.setText(message);
    }

    public void updateColorSelection(View view) {
        selected_button_2 = selected_button_1;
        selected_button_1 = COLOR_BUTTONS.indexOf(view.getId());
        updateColorContrastInfo();
        updateColorContrastText();
    }

    public double colorContrast(String color1, String color2) {
        //whoops, should just use the below :)
        double contrast_ratio = ColorUtils.calculateContrast(
                Color.parseColor(color1),
                Color.parseColor(color2)
        );
        /*
        int RGB1_raw = Color.parseColor(color1);
        int RGB2_raw = Color.parseColor(color2);
        double R1S = Color.red(RGB1_raw)/255;
        double G1S = Color.green(RGB1_raw)/255;
        double B1S = Color.blue(RGB1_raw)/255;
        double R2S = Color.red(RGB2_raw)/255;
        double G2S = Color.green(RGB2_raw)/255;
        double B2S = Color.blue(RGB2_raw)/255;
        double R1 = (R1S <= 0.03928)? R1S/12.92: Math.pow((R1S+0.055)/1.055, 2);
        double G1 = (G1S <= 0.03928)? G1S/12.92: Math.pow((G1S+0.055)/1.055, 2);
        double B1 = (B1S <= 0.03928)? B1S/12.92: Math.pow((B1S+0.055)/1.055, 2);
        double R2 = (R2S <= 0.03928)? R2S/12.92: Math.pow((R2S+0.055)/1.055, 2);
        double G2 = (G2S <= 0.03928)? G2S/12.92: Math.pow((G2S+0.055)/1.055, 2);
        double B2 = (B2S <= 0.03928)? B2S/12.92: Math.pow((B2S+0.055)/1.055, 2);
        double L1 = 0.2126*R1 + 0.7152*G1 + 0.0722*B1;
        double L2 = 0.2126*R2 + 0.7152*G2 + 0.0722*B2;
        double contrast_ratio = (L1>=L2)? (L1+0.05)/(L2+0.05): (L2+0.05)/(L1+0.05);
        */
        return contrast_ratio;
    }
}
