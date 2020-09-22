package com.example.s331153s333975mappe2;

import java.util.ArrayList;

public class Møtedeltagelse {
    long DID;
    long MID;
    long KID;
    ArrayList<Kontakt> deltagere = new ArrayList<>();

    public Møtedeltagelse(long DID, long MID, long KID, ArrayList<Kontakt> deltagere){
        this.DID = DID;
        this.MID = MID;
        this.KID = KID;
        this.deltagere = deltagere;
    }
}
