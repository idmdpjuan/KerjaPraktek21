package com.kpws.kpprojectwarehousestok.model.data;

import java.util.List;

public class Barang {
    private int kode;
    private String pesan;
    private List<DataBarang> data;

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

    public List<DataBarang> getData() {
        return data;
    }

    public void setData(List<DataBarang> data) {
        this.data = data;
    }
}
