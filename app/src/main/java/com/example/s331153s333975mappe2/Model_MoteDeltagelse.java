package com.example.s331153s333975mappe2;

import java.util.ArrayList;

public class Model_MoteDeltagelse {
        Long _MDID;
        Long _MID;
        Long _KID;
        ArrayList<Model_Kontakt> deltagere = new ArrayList<>();

        public Model_MoteDeltagelse(){

        }

        public Model_MoteDeltagelse(Long _MID, Long _KID) {
        }

        public Model_MoteDeltagelse(Long _MDID, Long _MID, Long _KID) {
        }

        public Long get_MDID() {
            return _MDID;
        }

        public void set_MDID(Long _MDID) {
            this._MDID = _MDID;
        }

        public Long get_MID() {
        return _MID;
    }

        public void set_MID(Long _MID) {
        this._MID = _MID;
    }

        public Long get_KID() {
        return _KID;
    }

        public void set_KID(Long _KID) {
        this._KID = _KID;
    }


}

