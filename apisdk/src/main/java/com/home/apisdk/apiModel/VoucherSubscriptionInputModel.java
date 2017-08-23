package com.home.apisdk.apiModel;

/**
 * This Model Class Holds All The Input Attributes For VoucherSubscriptionAsyntask
 *
 * @author MUVI
 */
public class VoucherSubscriptionInputModel {

    String authToken;
    String user_id;
    String movie_id;
    String stream_id;
    String purchase_type;
    String voucher_code;
    String is_preorder;
    String season;

    /**
     * This Method is use to Get the Season
     *
     * @return season
     */
    public String getSeason() {
        return season;
    }

    /**
     * This Method is use to Set the Season
     *
     * @param season
     */
    public void setSeason(String season) {
        this.season = season;
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

    /**
     * This Method is use to Get the User Id
     *
     * @return user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * This Method is use to Set the User Id
     *
     * @param user_id For Setting The User Id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * This Method is use to Get the Movie Id
     *
     * @return movie_id
     */
    public String getMovie_id() {
        return movie_id;
    }

    /**
     * This Method is use to Set the Movie Id
     *
     * @param movie_id For Setting The Movie Id
     */
    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    /**
     * This Method is use to Get the Stream Id
     *
     * @return stream_id
     */
    public String getStream_id() {
        return stream_id;
    }

    /**
     * This Method is use to Set the Stream Id
     *
     * @param stream_id For Setting The Stream Id
     */
    public void setStream_id(String stream_id) {
        this.stream_id = stream_id;
    }

    /**
     * This Method is use to Get the Purchase Type
     *
     * @return purchase_type
     */
    public String getPurchase_type() {
        return purchase_type;
    }

    /**
     * This Method is use to Set the Purchase Type
     *
     * @param purchase_type For Setting The Purchase Type
     */
    public void setPurchase_type(String purchase_type) {
        this.purchase_type = purchase_type;
    }

    /**
     * This Method is use to Get the Voucher Code
     *
     * @return voucher_code
     */
    public String getVoucher_code() {
        return voucher_code;
    }

    /**
     * This Method is use to Set the Voucher Code
     *
     * @param voucher_code For Setting The Voucher Code
     */
    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    /**
     * This Method is use to Get the Is Pre Order Details
     *
     * @return is_preorder
     */
    public String getIs_preorder() {
        return is_preorder;
    }

    /**
     * This Method is use to Set the Is Pre Order Details
     *
     * @param is_preorder For Setting The Is Pre Order Details
     */
    public void setIs_preorder(String is_preorder) {
        this.is_preorder = is_preorder;
    }


}
