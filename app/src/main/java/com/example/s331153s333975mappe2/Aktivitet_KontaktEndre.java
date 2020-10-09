package com.example.s331153s333975mappe2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class Aktivitet_KontaktEndre extends AppCompatActivity {
    EditText navn, telefonnr;
    Button endre;
    DBHandler db;
    SharedPreferences sp;

    public static final Pattern NAVN = Pattern.compile("[a-zæøåA-ZÆØÅ]{2,20}");
    public static final Pattern TELEFON = Pattern.compile("[0-9]{8}");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontakt_endre);
        navn = findViewById(R.id.txtNavn);
        telefonnr = findViewById(R.id.txtTelefonnr);
        endre = (Button) findViewById(R.id.btnEndreKontakt);
        db = new DBHandler(this);


        sp = getSharedPreferences("Aktivitet_KontaktInfo", Context.MODE_PRIVATE);
        navn.setText(sp.getString("kontaktNavn", "feil"));
        telefonnr.setText(sp.getString("kontaktTelefonnr", "feil"));


        /**------------- METODER FOR LAGRE-KNAPPEN --------------**/
        endre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                long kontaktId = sp.getLong("KId", 0);

                Kontakt kontakt = new Kontakt();
                kontakt.set_KID(kontaktId);
                kontakt.setNavn(navn.getText().toString());
                kontakt.setTelefon(telefonnr.getText().toString());
                db.oppdaterKontakt(kontakt);
                Intent intent = new Intent(Aktivitet_KontaktEndre.this, Aktivitet_Kontakt.class);
                startActivity(intent);
            }
        });

        /**---- TOOLBAR OPPRETTES ----**/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("\tEndre kontakt");
        toolbar.inflateMenu(R.menu.menu_mote);
        setActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_launcher_small);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aktivitet_KontaktEndre.this, Aktivitet_KontaktInfo.class);
                startActivity(intent);
            }
        });

    }


    public void endreKontakt(View v){
        if(!validerNavn() | !validerTelefonnr()){
            Toast.makeText(Aktivitet_KontaktEndre.this, "Alle felt må være riktig fylt inn", Toast.LENGTH_SHORT).show();
            return;
        } else {
            long kontaktId = sp.getLong("KId", 0);
            Kontakt kontakt = new Kontakt();
            kontakt.set_KID(kontaktId);
            kontakt.setNavn(navn.getText().toString());
            kontakt.setTelefon(telefonnr.getText().toString());
            db.oppdaterKontakt(kontakt);
            Intent intent = new Intent(Aktivitet_KontaktEndre.this, Aktivitet_Kontakt.class);
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

