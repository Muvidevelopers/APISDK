package com.home.apisdk.apiModel;

/**
 * Created by Muvi on 9/29/2016.
 */
public class Search_Data_input {
    String limit="10";
    String authToken;
    String offset="0";
    String q;

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    String language_code;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    String country;

   /* public Search_Data_input(String limit, String authToken, String offset, String q) {
        this.limit = limit;
        this.authToken = authToken;
        this.offset = offset;
        this.q = q;
    }*/

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
