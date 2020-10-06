package com.example.s331153s333975mappe2;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Aktivitet_MoteReg extends AppCompatActivity {
    EditText navn, sted, tidspunkt;
    DBHandler db;
    Button btnReg;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote_registrer);
        navn = (EditText) findViewById(R.id.txtNavnMote);
        sted = (EditText) findViewById(R.id.txtSted);
        tidspunkt = (EditText) findViewById(R.id.txtTidspunkt);
        btnReg = (Button) findViewById(R.id.btnRegMote);
        db = new DBHandler(this);
    }

    public void regMote(View v){
        Mote mote = new Mote(navn.getText().toString(), sted.getText().toString(), tidspunkt.getText().toString());
        db.leggTilMote(mote);
        Log.d("Legger inn møte", "Navn: "+mote.navn + ", Sted: "+mote.sted + ", Tidspunkt: "+mote.tidspunkt);
        Intent intent = new Intent(this, Aktivitet_Mote.class);
        startActivity(intent);
    }
}
