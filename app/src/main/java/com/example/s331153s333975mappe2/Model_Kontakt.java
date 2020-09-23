package com.example.s331153s333975mappe2;

public class Model_Kontakt {
    String navn;
    String telefon;
    Long _ID;

    public Model_Kontakt() {
    }
    public Model_Kontakt(String navn, String telefon) {
    }
    public Model_Kontakt(Long _ID, String navn, String telefon) {
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Long get_ID() {
        return _ID;
    }

    public void set_ID(Long _ID) {
        this._ID = _ID;
    }
}
