package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.APIUrlConstant;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by muvi on 25/7/17.
 * This class need to initialise first for setiing up API SDK.
 *
 */

public class SDKInitializer {

    private static SDKInitializer _instance;
    private static String hashKey = "";

    public static String getHashKey() {
        return hashKey;
    }

    public static void setHashKey(String hashKey) {
        SDKInitializer.hashKey = hashKey;
    }

    public static String getUser_Package_Name_At_Api() {
        return user_Package_Name_At_Api;
    }

    public static void setUser_Package_Name_At_Api(String user_Package_Name_At_Api) {
        SDKInitializer.user_Package_Name_At_Api = user_Package_Name_At_Api;
    }

    private static String user_Package_Name_At_Api = "";

    private SDKInitializer(){}

    public static SDKInitializer getInstance(){
        if(_instance==null){
            return new SDKInitializer();
        }
            return _instance;
    }


    /**
     * Call this method first to initialise the sdk setup process.
     * @param sdkInitializerListner
     * @param context
     * @param authToken
     */
    public void init(SDKInitializerListner sdkInitializerListner,
                     Context context,
                     String authToken){
        this.sdkInitializerListner=sdkInitializerListner;
        this.context = context;
        this.authToken=authToken;
        new InitAsync().execute();
    }

    /**
     * Call this method first to initialise the sdk setup process.
     * @param context
     * @param authToken
     */

    public void init(Context context,
                     String authToken){
        this.context = context;
        this.authToken=authToken;
        new InitAsync().execute();
    }


    private SDKInitializerListner sdkInitializerListner;
    private Context context;
    private String authToken;
    private String message,responseStr;
    private int status ;


    /**
     * Interface to impliment for oveerideing method to invoke pre & post work.
     */
    public interface SDKInitializerListner{
        /**
         * Ovveride this method to listen pre-work before init API SDk.
         */
        public void onPreExexuteListner();
        /**
         * Ovveride this method to listen post-work after init API SDk.
         */
        public void onPostExecuteListner();
    }



    private class InitAsync extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sdkInitializerListner.onPreExexuteListner();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            sdkInitializerListner.onPostExecuteListner();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {


                HttpClient httpclient=new DefaultHttpClient();
                HttpPost httppost = new HttpPost(APIUrlConstant.getInitializationUrl());
                httppost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
                httppost.addHeader(HeaderConstants.AUTH_TOKEN,authToken);

                // Execute HTTP Post Request
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    responseStr = EntityUtils.toString(response.getEntity());


                } catch (org.apache.http.conn.ConnectTimeoutException e){

                    status = 0;
                    message = "Error";



                }catch (IOException e) {
                    status = 0;
                    message = "Error";
                }

                JSONObject myJson =null;
                if(responseStr!=null){
                    myJson = new JSONObject(responseStr);
                    status = Integer.parseInt(myJson.optString("code"));
                }

                if (status > 0) {

                    if (status == 200) {
                        if ((myJson.has("pkgname")) && myJson.getString("pkgname").trim() != null && !myJson.getString("pkgname").trim().isEmpty() && !myJson.getString("pkgname").trim().equals("null") && !myJson.getString("pkgname").trim().matches("")) {
                            String PNAME=myJson.getString("pkgname");
                           setUser_Package_Name_At_Api(PNAME);
                            Log.v("MUVI", "pkgname at class="+(myJson.getString("pkgname")));
                        }


                        if ((myJson.has("hashkey")) && myJson.getString("hashkey").trim() != null && !myJson.getString("hashkey").trim().isEmpty() && !myJson.getString("hashkey").trim().equals("null") && !myJson.getString("hashkey").trim().matches("")) {
                            String hashKey = (myJson.getString("hashkey"));
                            setHashKey(hashKey);
                            Log.v("MUVI", "Hash Key at class=" + (myJson.getString("hashkey")));
                        }
                    }
                }else{

                    responseStr = "0";
                    status = 0;
                    message = "Error";
                }
            } catch (Exception e)
            {

                responseStr = "0";
                status = 0;
                message = "Error";
            }
            return null;
        }
    }

}
