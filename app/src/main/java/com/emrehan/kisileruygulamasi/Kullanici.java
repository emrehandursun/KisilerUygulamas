package com.emrehan.kisileruygulamasi;

public class Kullanici {
    private  String uid,ad,soyad,bolum,numara;

    public Kullanici() {

    }

    public Kullanici(String uid, String ad, String soyad, String bolum, String numara) {
        this.uid = uid;
        this.ad = ad;
        this.soyad = soyad;
        this.bolum = bolum;
        this.numara = numara;
    }

    public String getUid() {
        return uid;
    }

    public String getAd() {
        return ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public String getBolum() {
        return bolum;
    }

    public String getNumara() {
        return numara;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public void setBolum(String bolum) {
        this.bolum = bolum;
    }

    public void setNumara(String numara) {
        this.numara = numara;
    }
}
