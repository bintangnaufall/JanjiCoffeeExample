package com.bintangnaufal.uas;

public class Product {
    private int id;
    private String nama;
    private boolean is_Rekomended;
    private boolean is_Regular;
    private boolean is_Large;
    private String hargaRegular;
    private String hargaLarge;
    private String deskripsi;
    private String imagePath;
    private String waktu;

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getWaktu() { return waktu; }
    public void setWaktu(String waktu) { this.waktu = waktu; }
    public boolean getIsRokemended() { return is_Rekomended; }
    public void setIsRecommended(boolean isRecommended) {
        this.is_Rekomended = isRecommended; // Langsung gunakan boolean
    }
    public boolean getIsRegular() { return is_Regular; }
    public void setIsRegular(boolean isIsRegular) {
        this.is_Regular = isIsRegular; // Langsung gunakan boolean
    }
    public boolean getIsLarge() { return is_Large; }
    public void setisIsLarge(boolean isIsLarge) {
        this.is_Large = isIsLarge; // Langsung gunakan boolean
    }
    public String getHargaRegular() { return hargaRegular; }
    public void setHargaRegular(String hargaregular) { this.hargaRegular = hargaregular; }
    public String getHargaLarge() { return hargaLarge; }
    public void setHargaLarge(String hargalarge) { this.hargaLarge = hargalarge; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}

