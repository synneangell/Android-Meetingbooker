package com.example.s331153s333975mappe2;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Aktivitet_KontaktEndre extends AppCompatActivity {
    EditText navn, telefonnr;
    Button endre;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontakt_endre);
        navn = findViewById(R.id.txtNavn);
        telefonnr = findViewById(R.id.txtTelefonnr);
        endre = (Button) findViewById(R.id.btnEndreKontakt);
        db = new DBHandler(this);

        navn.setText(getIntent().getStringExtra("navn"));
        telefonnr.setText(getIntent().getStringExtra("telefonnr"));

        /**------------- METODER FOR LAGRE-KNAPPEN --------------**/
        endre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Kontakt kontakt = new Kontakt();
                kontakt.setNavn(navn.getText().toString());
                kontakt.setTelefon(telefonnr.getText().toString());
                db.oppdaterKontakt(kontakt);
                //må muligens bruke sharedPreferences for å få hentet den konkrete kontakten og dataene som følger med?
            }
        });
    }
}
