package com.kpws.kpprojectwarehousestok.model.login;

import java.util.List;

public class Login {
    private int kode;
    private String pesan;
    private List<DataLogin> data;

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

    public List<DataLogin> getData() {
        return data;
    }

    public void setData(List<DataLogin> data) {
        this.data = data;
    }
}
