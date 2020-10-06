package com.example.s331153s333975mappe2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Aktivitet_MoteEndre extends AppCompatActivity {
    EditText navn, sted, tidspunkt;
    Button endre;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote_endre);
        navn = findViewById(R.id.txtNavn);
        sted = findViewById(R.id.txtSted);
        tidspunkt = findViewById(R.id.txtTidspunkt);
        endre = (Button) findViewById(R.id.btnEndreKontakt);
        db = new DBHandler(this);


        navn.setText(getIntent().getStringExtra("navn"));
        sted.setText(getIntent().getStringExtra("sted"));
        tidspunkt.setText(getIntent().getStringExtra("tidspunkt"));


        /**------------- METODER FOR LAGRE-KNAPPEN --------------**/
        endre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                long MId = intent.getLongExtra("MId", 0);
                Log.d("MoteId i endre:", Long.toString(MId));

                Mote mote = new Mote();
                mote.set_MID(MId);
                mote.setNavn(navn.getText().toString());
                mote.setSted(sted.getText().toString());
                mote.setTidspunkt(tidspunkt.getText().toString());
                db.oppdaterMote(mote);

                Intent intent2 = new Intent(Aktivitet_MoteEndre.this, Aktivitet_Mote.class);
                startActivity(intent2);
            }
        });
    }
}