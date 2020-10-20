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

import java.util.regex.Pattern;

public class Aktivitet_KontaktReg extends AppCompatActivity {
    EditText navn, telefonnr;
    Button reg;
    DBHandler db;

    public static final Pattern NAVN = Pattern.compile("[a-zæøåA-ZÆØÅ ]{2,20}");
    public static final Pattern TELEFON = Pattern.compile("[0-9]{8}");

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
                    Intent intent = new Intent(Aktivitet_KontaktReg.this, Aktivitet_Kontakt.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Aktivitet_KontaktReg.this, "Du må fylle ut alle feltene", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_kontaktinfo);
        setActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher_small);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aktivitet_KontaktReg.this, Aktivitet_Kontakt.class);
                startActivity(intent);
            }
        });
    }

    public void regKontakt(View v){
        if(!validerNavn() | !validerTelefonnr()){
            Toast.makeText(Aktivitet_KontaktReg.this, "Alle felt må være riktig fylt inn", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Kontakt kontakt = new Kontakt(navn.getText().toString(), telefonnr.getText().toString());
            db.leggTilKontakt(kontakt);
            Intent intent = new Intent(Aktivitet_KontaktReg.this, Aktivitet_Kontakt.class);
            startActivity(intent);
        }
    }

    public boolean validerNavn(){
        String navnInput = navn.getText().toString().trim();
        if(navnInput.isEmpty()){
            navn.setError("Navn kan ikke være tomt");
            return false;
        } else if (!NAVN.matcher(navnInput).matches()){
            navn.setError("Navnet må bestå av mellom 2 og 20 tegn");
            return false;
        } else {
            navn.setError(null);
            return true;
        }
    }

    public boolean validerTelefonnr(){
        String telefonnrInput = telefonnr.getText().toString().trim();
        if(telefonnrInput.isEmpty()){
            telefonnr.setError("Telefonnummer kan ikke være tomt");
            return false;
        } else if(!TELEFON.matcher(telefonnrInput).matches()){
            telefonnr.setError("Telefonnummer må bestå av 8 tall");
            return false;
        } else {
            telefonnr.setError(null);
            return true;
        }
    }
}
