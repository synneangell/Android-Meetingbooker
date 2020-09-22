package com.example.s331153s333975mappe2;

public class Kontakt
{
    public int KId;
    public String navn;
    public String telefonnr;

    public Kontakt (int KId, String navn, String telefonnr){
        this.KId = KId;
        this.navn = navn;
        this.telefonnr = telefonnr;
    }

    public Kontakt(int KId){
        this.KId = KId;
    }

    public int getKId() {
        return KId;
    }

    public String getNavn() {
        return navn;
    }

    public String getTelefonnr() {
        return telefonnr;
    }

    public void setKId(int KId) {
        this.KId = KId;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setTelefonnr(String telefonnr) {
        this.telefonnr = telefonnr;
    }
}
