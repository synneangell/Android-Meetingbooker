package com.example.s331153s333975mappe2;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class Aktivitet_KontaktReg extends AppCompatActivity {
    EditText navn, telefonnr;
    Button reg;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontakt_registrer);
        navn = findViewById(R.id.txtNavn);
        telefonnr = findViewById(R.id.txtTelefonnr);
        reg = (Button) findViewById(R.id.btnRegKontakt);
        db = new DBHandler(this);

        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Kontakt kontakt = new Kontakt(navn.getText().toString(), telefonnr.getText().toString());
                if(kontakt.navn != null && kontakt.telefon != null) {
                    db.leggTilKontakt(kontakt);
                    Log.d("Legger inn kontakt", "Navn : "+kontakt.navn+", Telefonnr : "+kontakt.telefon);
                    Intent intent = new Intent(Aktivitet_KontaktReg.this, Aktivitet_Kontakt.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Aktivitet_KontaktReg.this, "Du må fylle ut alle feltene", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("\t\t Registrer kontakt ");
        toolbar.inflateMenu(R.menu.menu_kontaktinfo);
        setActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aktivitet_KontaktReg.this, Aktivitet_Kontakt.class);
                startActivity(intent);
            }
        });
    }
}
