package com.example.s331153s333975mappe2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class Aktivitet_KontaktEndre extends AppCompatActivity {
    EditText navn, telefonnr;
    Button endre;
    DBHandler db;
    SharedPreferences sp;

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
        toolbar.setTitle("Endre kontakt");
        toolbar.inflateMenu(R.menu.menu_mote);
        setActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aktivitet_KontaktEndre.this, Aktivitet_KontaktInfo.class);
                startActivity(intent);
            }
        });
    }
}

