package com.example.s331153s333975mappe2;

public class Kontakt {
    String navn;
    String telefon;
    Long _KID;

    public Kontakt() {
    }
    public Kontakt(String navn, String telefon) {
    }
    public Kontakt(Long _KID, String navn, String telefon) {
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

    public Long get_KID() {
        return _KID;
    }

    public void set_KID(Long _KID) {
        this._KID = _KID;
    }
}
