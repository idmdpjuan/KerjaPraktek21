package com.kpws.kpprojectwarehousestok.model.stok;


import java.util.List;

public class Stok {
    private int kode;
    private String pesan;
    private List<StokBarang> data;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<StokBarang> getData() {
        return data;
    }

    public void setData(List<StokBarang> data) {
        this.data = data;
    }
}
