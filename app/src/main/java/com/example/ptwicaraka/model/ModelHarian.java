package com.example.ptwicaraka.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelHarian implements Parcelable {

    private String alamat_pembeli, id_harian, jumlah_pembelian_tabung,
            kategori, keterangan, nama_pembeli, tanggal;

    private String pangkalan;
    public ModelHarian() {
    }

    public String getPangkalan() {
        return pangkalan;
    }

    public void setPangkalan(String pangkalan) {
        this.pangkalan = pangkalan;
    }

    public String getAlamat_pembeli() {
        return alamat_pembeli;
    }

    public void setAlamat_pembeli(String alamat_pembeli) {
        this.alamat_pembeli = alamat_pembeli;
    }

    public String getId_harian() {
        return id_harian;
    }

    public void setId_harian(String id_harian) {
        this.id_harian = id_harian;
    }

    public String getJumlah_pembelian_tabung() {
        return jumlah_pembelian_tabung;
    }

    public void setJumlah_pembelian_tabung(String jumlah_pembelian_tabung) {
        this.jumlah_pembelian_tabung = jumlah_pembelian_tabung;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNama_pembeli() {
        return nama_pembeli;
    }

    public void setNama_pembeli(String nama_pembeli) {
        this.nama_pembeli = nama_pembeli;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.alamat_pembeli);
        dest.writeString(this.id_harian);
        dest.writeString(this.jumlah_pembelian_tabung);
        dest.writeString(this.kategori);
        dest.writeString(this.keterangan);
        dest.writeString(this.nama_pembeli);
        dest.writeString(this.tanggal);
        dest.writeString(this.pangkalan);
    }

    protected ModelHarian(Parcel in) {
        this.alamat_pembeli = in.readString();
        this.id_harian = in.readString();
        this.jumlah_pembelian_tabung = in.readString();
        this.kategori = in.readString();
        this.keterangan = in.readString();
        this.nama_pembeli = in.readString();
        this.tanggal = in.readString();
        this.pangkalan = in.readString();
    }

    public static final Parcelable.Creator<ModelHarian>
            CREATOR = new Parcelable.Creator<ModelHarian>() {
        @Override
        public ModelHarian createFromParcel(Parcel source) {
            return new ModelHarian(source);
        }

        @Override
        public ModelHarian[] newArray(int size) {
            return new ModelHarian[size];
        }
    };
}
