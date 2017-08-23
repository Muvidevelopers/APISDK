package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For GetMenuListAsynctask
 *
 * @author MUVI
 */
public class MenuListInput {

    String authToken, country, lang_code;

    /**
     * This Method is use to Get Country
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * This Method is use to Set Country
     *
     * @param country For Setting The Country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * This Method is use to Get the Language Code
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

    /**
     * This Method is use to Set the Auth Token
     *
     * @param authToken For Setting The Auth Token
     */

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * This Method is use to Get the Auth Token
     *
     * @return authToken
     */
    public String getAuthToken() {
        return authToken;
    }

}
