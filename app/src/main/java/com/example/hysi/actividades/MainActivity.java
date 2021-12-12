package com.example.hysi.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hysi.R;

public class MainActivity extends AppCompatActivity {


    public void ejecutarAnyadirAnuncio(View view) {
        Intent i = new Intent(this, AnyadirAnuncioActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}