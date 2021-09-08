package com.kpws.kpprojectwarehousestok.model.data;

public class DataBarang {
    private int id, jumlah;
    private long nobarcode;
    private String nofaktur, kodeBarang, namaItem, jenisitem, tanggal, suratjalan, keterangan;

    public String getNofaktur() {
        return nofaktur;
    }

    public void setNofaktur(String nofaktur) {
        this.nofaktur = nofaktur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public long getNobarcode() {
        return nobarcode;
    }

    public void setNobarcode(int nobarcode) {
        this.nobarcode = nobarcode;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNamaItem() {
        return namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }
    public String getJenisitem() {
        return jenisitem;
    }

    public void setJenisitem(String jenisitem) {
        this.jenisitem = jenisitem;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getSuratJalan() {
        return suratjalan;
    }

    public void setSuratJalan(String suratjalan) {
        this.suratjalan = suratjalan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

}
