package com.example.s331153s333975mappe2;

public class Møte {
    public int MId;
    public int tidspunkt;
    public String sted;
    public String type;

    public Møte(int MId, int tidspunkt, String sted, String type){
        this.MId = MId;
        this.tidspunkt = tidspunkt;
        this.sted = sted;
        this.type = type;
    }

    public Møte(int MId){
        this.MId = MId;
    }

    public int getMId() {
        return MId;
    }

    public int getTidspunkt() {
        return tidspunkt;
    }

    public String getSted() {
        return sted;
    }

    public String getType() {
        return type;
    }

    public void setMId(int MId) {
        this.MId = MId;
    }

    public void setTidspunkt(int tidspunkt) {
        this.tidspunkt = tidspunkt;
    }

    public void setSted(String sted) {
        this.sted = sted;
    }

    public void setType(String type) {
        this.type = type;
    }
}
