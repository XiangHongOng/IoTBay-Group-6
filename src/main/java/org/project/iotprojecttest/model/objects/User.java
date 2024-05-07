package org.project.iotprojecttest.model.objects;

import java.io.Serializable;

public class User implements Serializable {
    private int userId;
    private String email;
    private String password;
    private String phone;

    public User() {
    }

    public User(User other) {
        this.userId = other.userId;
        this.email = other.email;
        this.password = other.password;
        this.phone = other.phone;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}