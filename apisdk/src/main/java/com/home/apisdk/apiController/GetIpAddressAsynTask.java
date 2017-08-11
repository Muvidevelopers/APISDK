package com.home.apisdk.apiController;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.home.apisdk.APIUrlConstant;
import com.home.apisdk.HeaderConstants;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by MUVI on 1/20/2017.
 * Class to get IP Address details.
 */

public class GetIpAddressAsynTask extends AsyncTask<Void, Void, Void> {

    private String PACKAGE_NAME;
    private String ipAddressStr = "";
    private String responseStr;
    private int statusCode;
    private String message;
    private IpAddressListener listener;
    private Context context;

    /**
     * Interface used to allow the caller of a GetIpAddressAsynTask to run some code when get
     * responses.
     */

    public interface IpAddressListener {

        /**
         * This method will be invoked before controller start execution.
         * This method to handle pre-execution work.
         */

        void onIPAddressPreExecuteStarted();

        /**
         * This method will be invoked after controller complete execution.
         * This method to handle post-execution work.
         *
         * @param message
         * @param statusCode
         * @param ipAddressStr

         */
        void onIPAddressPostExecuteCompleted(String message, int statusCode, String ipAddressStr);
    }

    /**
     * Constructor to initialise the private data members.
     *
     * @param listener
     * @param context
     */

    public GetIpAddressAsynTask(IpAddressListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        PACKAGE_NAME = context.getPackageName();

    }

    @Override
    protected Void doInBackground(Void... params) {

        try {

            // Execute HTTP Post Request
            try {
                URL url = new URL(APIUrlConstant.getIpAddressUrl());
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                InputStream ins = con.getInputStream();
                InputStreamReader isr = new InputStreamReader(ins);
                BufferedReader in = new BufferedReader(isr);

                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    responseStr = inputLine;
                }

                in.close();


            } catch (org.apache.http.conn.ConnectTimeoutException e) {
                ipAddressStr = "";
                statusCode = 0;
                message = "Failure";
            } catch (UnsupportedEncodingException e) {

                ipAddressStr = "";
                statusCode = 0;
                message = "Failure";


            } catch (IOException e) {
                ipAddressStr = "";
                statusCode = 0;
                message = "Failure";

            }
            if (responseStr != null) {
                Object json = new JSONTokener(responseStr).nextValue();
                if (json instanceof JSONObject) {
                    statusCode = 200;
                    message = "Success";
                    ipAddressStr = ((JSONObject) json).optString("ip");

                }

            }

        } catch (Exception e) {
            ipAddressStr = "";
            statusCode = 0;
            message = "Failure";


        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onIPAddressPreExecuteStarted();
        statusCode = 0;
        Log.v("BKS1", "ip value==" + ipAddressStr);
        if (!PACKAGE_NAME.equals(HeaderConstants.user_Package_Name_At_Api)) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onIPAddressPostExecuteCompleted(message, statusCode, ipAddressStr);
            return;
        }
        if (HeaderConstants.hashKey.equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onIPAddressPostExecuteCompleted(message, statusCode, ipAddressStr);
        }
        //listener.onIPAddressPostExecuteCompleted(message,statusCode,ipAddressStr);

    }

    @Override
    protected void onPostExecute(Void result) {
        listener.onIPAddressPostExecuteCompleted(message, statusCode, ipAddressStr);
    }
}
