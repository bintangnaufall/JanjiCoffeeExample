package com.bintangnaufal.uas;

public class User {
    private int id;
    private String username; // Gunakan huruf kecil di awal
    private String email;
    private String password;
    private boolean isAdmin;

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; } // Menggunakan username dengan huruf kecil
    public void setUsername(String username) { this.username = username; } // Menggunakan username dengan huruf kecil

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean getIsAdmin() { return isAdmin; }
    public void setIsAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }
}


