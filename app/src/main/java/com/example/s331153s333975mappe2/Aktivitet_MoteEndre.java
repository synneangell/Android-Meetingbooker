package com.example.s331153s333975mappe2;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Aktivitet_MoteEndre extends AppCompatActivity {
    EditText navn, sted, tidspunkt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote_endre);
    }
}
