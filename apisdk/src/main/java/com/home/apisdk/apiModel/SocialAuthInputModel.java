package com.home.apisdk.apiModel;

/**
 * Created by Muvi on 10/4/2016.
 */
public class SocialAuthInputModel {
    String authToken;
    String email;
    String password;
    String name;

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    String fb_userid;
    String Language;
    String device_type;
    String device_id;

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getFb_userid() {
        return fb_userid;
    }

    public void setFb_userid(String fb_userid) {
        this.fb_userid = fb_userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
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
}
