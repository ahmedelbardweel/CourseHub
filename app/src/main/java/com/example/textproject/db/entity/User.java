package com.example.textproject.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private long userId;
    private String name;
    private String email;
    private String password;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] userImage;

    public User() {
    }

    @Ignore
    public User(long userId, String name, String email, String password, byte[] userImage) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userImage = userImage;
    }

    @Ignore
    public User(long userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }


    @Ignore
    public User(String name, String email, String password, byte[] userImage) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userImage = userImage;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }
}
