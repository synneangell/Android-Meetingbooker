package com.example.s331153s333975mappe2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

//Denne er for land når man har trykket seg inn på et møte og skal se innholdet/deltagerne og informasjon om møtet
public class Aktivitet_MoteInnhold extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceBundle){
        super.onCreate(saveInstanceBundle);

        Intent i = this.getIntent();
        String bnavn = i.getExtras().getString("scriptnavn");
        Fragment_MoteInnhold visMoter = new Fragment_MoteInnhold();
        visMoter.init(bnavn);
        FragmentManager fm;
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(android.R.id.content, visMoter).commit();
    }
}
