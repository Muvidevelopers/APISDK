package com.home.apisdk.apiModel.apiModel;

/**
 * Created by MUVI on 7/6/2017.
 */

public class ResumeVideoLogDetailsInput {

    String authToken;
    String user_id;
    String ip_address;
    String movie_id;
    String episode_id;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getEpisode_id() {
        return episode_id;
    }

    public void setEpisode_id(String episode_id) {
        this.episode_id = episode_id;
    }

    public String getPlayed_length() {
        return played_length;
    }

    public void setPlayed_length(String played_length) {
        this.played_length = played_length;
    }

    public String getWatch_status() {
        return watch_status;
    }

    public void setWatch_status(String watch_status) {
        this.watch_status = watch_status;
    }

    String played_length;
    String watch_status;
}
