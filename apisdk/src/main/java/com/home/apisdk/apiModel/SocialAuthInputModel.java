package com.home.apisdk.apiModel;


/**
 * This Model Class Holds All The Input Attributes For SocialAuthAsynTask
 *
 * @author MUVI
 */

public class SocialAuthInputModel {
    String authToken;
    String email;
    String password;
    String name;
    String fb_userid;
    String Language;

    /**
     * This Method  is use to Get the Language
     *
     * @return Language
     */

    public String getLanguage() {
        return Language;
    }
    /**
     * This Method is use to Set the Language
     *
     * @param language For Setting The Language
     */
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

    /**
     * This Method is use to Get the Auth Token
     *
     * @return authToken
     */

    public String getAuthToken() {
        return authToken;
    }

    /**
     * This Method is use to Set the Auth Token
     *
     * @param authToken For Setting The Auth Token
     */

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
