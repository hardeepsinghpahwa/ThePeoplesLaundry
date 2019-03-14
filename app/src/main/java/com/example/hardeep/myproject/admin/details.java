package com.example.hardeep.myproject.admin;

/**
 * Created by Hardeep on 21-03-2018.
 */

public class details {

    String username_of_user, name_of_user;

    public details(String username_of_user, String name_of_user) {
        this.username_of_user = username_of_user;
        this.name_of_user = name_of_user;
    }

    public details() {
    }

    public String getUsername_of_user() {
        return username_of_user;
    }

    public void setUsername_of_user(String username_of_user) {
        this.username_of_user = username_of_user;
    }

    public String getName_of_user() {
        return name_of_user;
    }

    public void setName_of_user(String name_of_user) {
        this.name_of_user = name_of_user;
    }
}