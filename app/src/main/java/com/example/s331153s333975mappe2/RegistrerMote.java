package com.example.s331153s333975mappe2;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.List;

public class RegistrerMote extends AppCompatActivity {
    EditText navn, sted, tidspunkt;
    DBHandler db;
    Button regMote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrer_mote);
        navn = (EditText) findViewById(R.id.txtNavnMote);
        sted = (EditText) findViewById(R.id.txtSted);
        tidspunkt = (EditText) findViewById(R.id.txtTidspunkt);
        regMote = (Button) findViewById(R.id.btnRegMote);
        db = new DBHandler(this); //for å kunne bruke databasen.
    }

    //---------- onClick fra layout på knapp for å registrere møte -------//
    public void regMote(View v){
        Mote mote = new Mote(navn.getText().toString(), sted.getText().toString(), tidspunkt.getText().toString());
        db.leggTilMote(mote);
        Log.d("Legger inn møte", mote.navn + " "+mote.sted + " "+mote.tidspunkt);
        Intent intent = new Intent(this, AktivitetMote.class);
        startActivity(intent);
    }

    //---- onClick fra layout på knapp for å vise mote i TextView i samme skjermbildet----//
/*    public void visMote(View v){
        String tekst = "";
        List<Mote> moter = db.finnAlleMoter();
        for (Mote mote : moter) {
            tekst = tekst + ",Navn: " +
                    mote.getNavn() + " ,Sted: " +
                    mote.getSted() + " , Tidspunkt " + mote.getTidspunkt();
            Log.d("Navn: ", tekst);
        }
        visMote.setText(tekst);
    }*/
}
