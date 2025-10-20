package com.example.housedrawing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    
    private HouseView houseView;
    private Button btnTheme;
    private boolean isNightMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();
        setupListeners();
    }
    
    private void initViews() {
        houseView = findViewById(R.id.houseView);
        btnTheme = findViewById(R.id.btnTheme);
    }
    
    private void setupListeners() {
        btnTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNightMode = !isNightMode;
                houseView.toggleTheme(isNightMode);
                btnTheme.setText(isNightMode ? "☀️ День" : "🌙 Ночь");
                Toast.makeText(MainActivity.this, 
                    isNightMode ? "Переключено на ночной режим" : "Переключено на дневной режим", 
                    Toast.LENGTH_SHORT).show();
            }
        });
    }
}