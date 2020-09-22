package com.example.s331153s333975mappe2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrerKontakt extends AppCompatActivity {
    Button regKontakt;
    TextView navn, telefon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrer_kontakt);

        regKontakt = (Button) findViewById(R.id.btnRegKontakt);
        navn = (TextView) findViewById(R.id.txtNavn);
        telefon = (TextView)findViewById(R.id.txtTelefon);
    }
}
