package com.example.s331153s333975mappe2;

import java.util.ArrayList;

public class Model_MoteDeltagelse {
        Long MoteID;
        Long _ID;
        ArrayList<Model_Kontakt> deltagere = new ArrayList<>();

        public Model_MoteDeltagelse(){

        }

        public Model_MoteDeltagelse(String navn, String telefon) {
        }

        public Model_MoteDeltagelse(Long _ID, String navn, String telefon) {
        }

        public ArrayList<Model_Kontakt> getDeltagere() {
            return deltagere;
        }

        public void setDeltagere(ArrayList<Model_Kontakt> deltagere) {
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

