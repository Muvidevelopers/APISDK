package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For RegistrationAsynTask
 *
 * @author MUVI
 */

public class Registration_input {
    String authToken;
    String email;
    String name;
    String password;
    String lang_code;
    String custom_country;

    /**
     * This Method  is use to Get the Language Code
     *
     * @return lang_code
     */

    public String getLang_code() {
        return lang_code;
    }

    /**
     * This Method is use to Set the Language Code
     *
     * @param lang_code For Setting The Language Code
     */
    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
    }

    public String getCustom_country() {
        return custom_country;
    }

    public void setCustom_country(String custom_country) {
        this.custom_country = custom_country;
    }

    public String getCustom_languages() {
        return custom_languages;
    }

    public void setCustom_languages(String custom_languages) {
        this.custom_languages = custom_languages;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    String custom_languages;
    String device_id;
    String google_id;
    String device_type;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
