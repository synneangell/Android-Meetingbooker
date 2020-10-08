package com.example.s331153s333975mappe2;

public class Mote {
    String navn;
    String sted;
    String dato;
    String tid;
    Long _MID;

    public Mote(){

    }

    public Mote(String navn, String sted, String dato, String tid) {
        this.navn = navn;
        this.sted = sted;
        this.dato = dato;
        this.tid = tid;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getSted() {
        return sted;
    }

    public void setSted(String sted) {
        this.sted = sted;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public Long get_MID() {
        return _MID;
    }

    public void set_MID(Long _MID) {
        this._MID = _MID;
    }
}
