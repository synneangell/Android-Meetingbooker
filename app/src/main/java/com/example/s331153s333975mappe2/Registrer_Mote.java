package com.example.s331153s333975mappe2;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Registrer_Mote extends AppCompatActivity {
    EditText navn, sted, tidspunkt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrer_mote);

        navn = (EditText) findViewById(R.id.txtNavnMote);
        sted = (EditText) findViewById(R.id.txtSted);
        tidspunkt = (EditText) findViewById(R.id.txtTidspunkt);

        String MoteNavn = navn.getText().toString();

    }
}
