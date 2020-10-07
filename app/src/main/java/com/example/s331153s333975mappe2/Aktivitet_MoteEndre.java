package com.example.s331153s333975mappe2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class Aktivitet_MoteEndre extends AppCompatActivity {
    EditText navn, sted, tid, dato;
    Button endre;
    DBHandler db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote_endre);
        navn = findViewById(R.id.txtNavn);
        sted = findViewById(R.id.txtSted);
        dato = findViewById(R.id.dato);
        tid = findViewById(R.id.tid);
        endre = (Button) findViewById(R.id.btnEndreKontakt);
        db = new DBHandler(this);

        sp = getApplicationContext().getSharedPreferences("Aktivitet_MoteDeltagelse", Context.MODE_PRIVATE);

        navn.setText(sp.getString("moteNavn", "feil"));
        sted.setText(sp.getString("moteSted", "feil"));
        //tidspunkt.setText(sp.getString("moteTidspunkt", "feil"));


        /**------------- METODER FOR LAGRE-KNAPPEN --------------**/
        endre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long MId = sp.getLong("MId", 0);
                Log.d("MoteId i endre:", Long.toString(MId));

                Mote mote = new Mote();
                mote.set_MID(MId);
                mote.setNavn(navn.getText().toString());
                mote.setSted(sted.getText().toString());
                mote.setDato(dato.getText().toString());
                mote.setTid(tid.getText().toString());
                db.oppdaterMote(mote);

                Intent intent = new Intent(Aktivitet_MoteEndre.this, Aktivitet_Mote.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("\t\t Endre møteinformasjon ");
        toolbar.inflateMenu(R.menu.menu_mote);
        setActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Aktivitet_MoteEndre.this, Aktivitet_Mote.class);
                startActivity(intent);
            }
        });
    }
}