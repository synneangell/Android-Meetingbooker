package com.example.s331153s333975mappe2;

public class Model_Mote {
    String navn;
    String sted;
    String tidspunkt;
    Long _ID;


    public Model_Mote(){

    }

    public Model_Mote(String sted, String tidspunkt) {
    }

    public Model_Mote(Long _ID, String sted, String tidspunkt) {
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

    public void setSted(String navn) {
        this.sted = sted;
    }

    public String getTidspunkt() {
        return tidspunkt;
    }

    public void setTidspunkt(String tidspunkt) {
        this.tidspunkt = tidspunkt;
    }

    public Long get_ID() {
        return _ID;
    }

    public void set_ID(Long _ID) {
        this._ID = _ID;
    }
}
