package com.home.apisdk.apiModel.apiController;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.home.apisdk.apiModel.APIUrlConstant;
import com.home.apisdk.apiModel.CommonConstants;
import com.home.apisdk.apiModel.apiModel.LoadRegisteredDevicesInput;
import com.home.apisdk.apiModel.apiModel.LoadRegisteredDevicesOutput;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by MUVI on 7/5/2017.
 */

public class LoadRegisteredDevicesAsync extends AsyncTask<LoadRegisteredDevicesInput, Void, Void> {
    private LoadRegisteredDevicesInput loadRegisteredDevicesInput;
    String responseStr;
    int status;
    String message, PACKAGE_NAME;

    public interface LoadRegisteredDevices {
        void onLoadRegisteredDevicesPreExecuteStarted();

        void onLoadRegisteredDevicesPostExecuteCompleted(ArrayList<LoadRegisteredDevicesOutput> loadRegisteredDevicesOutputs, int status, String message);
    }

    public LoadRegisteredDevicesAsync(LoadRegisteredDevicesInput loadRegisteredDevicesInput, LoadRegisteredDevices listener, Context context) {
        this.listener = listener;
        this.context = context;  


        this.loadRegisteredDevicesInput = loadRegisteredDevicesInput;
        Log.v("MUVISDK", "LoginAsynTask");
        PACKAGE_NAME = context.getPackageName();
        Log.v("MUVISDK", "pkgnm :" + PACKAGE_NAME);

    }

    private LoadRegisteredDevices listener;
    private Context context;
    ArrayList<LoadRegisteredDevicesOutput> loadRegisteredDevicesOutputArrayList = new ArrayList<LoadRegisteredDevicesOutput>();



    @Override
    protected Void doInBackground(LoadRegisteredDevicesInput... params) {

        try {

            try {
                URL url = new URL(APIUrlConstant.getManageDevices());
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter(CommonConstants.AUTH_TOKEN, this.loadRegisteredDevicesInput.getAuthToken())
                        .appendQueryParameter(CommonConstants.USER_ID, this.loadRegisteredDevicesInput.getUser_id())
                        .appendQueryParameter(CommonConstants.DEVICE, this.loadRegisteredDevicesInput.getDevice())
                        .appendQueryParameter(CommonConstants.LANG_CODE, this.loadRegisteredDevicesInput.getLang_code());
                String query = builder.build().getEncodedQuery();


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                InputStream ins = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(ins);
                BufferedReader in = new BufferedReader(isr);

                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                    responseStr = inputLine;
                    Log.v("MUVISDK", "responseStr" +responseStr);

                }
                in.close();


            } catch (org.apache.http.conn.ConnectTimeoutException e){

                status = 0;
                message = "Error";



            }catch (IOException e) {
                status = 0;
                message = "Error";
            }

            JSONObject myJson = null;
            if (responseStr != null) {
                myJson = new JSONObject(responseStr);
                status = Integer.parseInt(myJson.optString("code"));
                if (myJson.has("msg")) {
                    message = myJson.optString("msg");
                }

                if (status == 200) {
                    myJson = new JSONObject(responseStr);
                    JSONArray jsonMainNode = myJson.getJSONArray("device_list");

                    int lengthJsonArr = jsonMainNode.length();
                    for (int i = 0; i < lengthJsonArr; i++) {
                        JSONObject jsonChildNode;
                        try {
                            jsonChildNode = jsonMainNode.getJSONObject(i);
                            LoadRegisteredDevicesOutput loadRegisteredDevicesOutput = new LoadRegisteredDevicesOutput();

                            if ((jsonChildNode.has("device")) && jsonChildNode.optString("device").trim() != null && !jsonChildNode.optString("device").trim().isEmpty() && !jsonChildNode.optString("device").trim().equals("null") && !jsonChildNode.optString("device").trim().matches("")) {
                                loadRegisteredDevicesOutput.setDevice(jsonChildNode.optString("device"));

                            }
                            if ((jsonChildNode.has("device_info")) && jsonChildNode.optString("device_info").trim() != null && !jsonChildNode.optString("device_info").trim().isEmpty() && !jsonChildNode.optString("device_info").trim().equals("null") && !jsonChildNode.optString("device_info").trim().matches("")) {
                                loadRegisteredDevicesOutput.setDevice_info(jsonChildNode.optString("device_info"));
                            }
                            if ((jsonChildNode.has("flag")) && jsonChildNode.optString("flag").trim() != null && !jsonChildNode.optString("flag").trim().isEmpty() && !jsonChildNode.optString("flag").trim().equals("null") && !jsonChildNode.optString("flag").trim().matches("")) {
                                loadRegisteredDevicesOutput.setFlag(jsonChildNode.optString("flag"));
                            }


                            loadRegisteredDevicesOutputArrayList.add(loadRegisteredDevicesOutput);
                        } catch (Exception e) {
                            status = 0;
                            message = "";
                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.v("MUVISDK", "Exception" + e.toString());

            status = 0;
            message = "";
        }
        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onLoadRegisteredDevicesPreExecuteStarted();
        status = 0;
        if (!PACKAGE_NAME.equals(CommonConstants.user_Package_Name_At_Api)) {
            this.cancel(true);
            message = "Packge Name Not Matched";
            listener.onLoadRegisteredDevicesPostExecuteCompleted(loadRegisteredDevicesOutputArrayList,status,responseStr);
            return;
        }
        if (CommonConstants.hashKey.equals("")) {
            this.cancel(true);
            message = "Hash Key Is Not Available. Please Initialize The SDK";
            listener.onLoadRegisteredDevicesPostExecuteCompleted(loadRegisteredDevicesOutputArrayList,status,responseStr);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        listener.onLoadRegisteredDevicesPostExecuteCompleted(loadRegisteredDevicesOutputArrayList,status,responseStr);
    }
}