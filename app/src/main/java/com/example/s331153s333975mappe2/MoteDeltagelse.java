package com.example.s331153s333975mappe2;

import java.util.ArrayList;

public class MoteDeltagelse {
        Long MoteID;
        Long _ID;
        ArrayList<Kontakt> deltagere = new ArrayList<>();

        public MoteDeltagelse(){

        }

        public MoteDeltagelse(String navn, String telefon) {
        }

        public MoteDeltagelse(Long _ID, String navn, String telefon) {
        }

        public ArrayList<Kontakt> getDeltagere() {
            return deltagere;
        }

        public void setDeltagere(ArrayList<Kontakt> deltagere) {
            this.deltagere = deltagere;
        }

        public Long get_ID() {
            return _ID;
        }

        public void set_ID(Long _ID) {
            this._ID = _ID;
        }

        public Long get_MoteID() {
        return MoteID;
    }

        public void set_MoteID(Long _ID) {
        this.MoteID = MoteID;
    }
}

