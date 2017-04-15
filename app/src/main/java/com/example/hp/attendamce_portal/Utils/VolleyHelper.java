package com.example.hp.attendamce_portal.Utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hp.attendamce_portal.Main;
import com.example.hp.attendamce_portal.pojo.DataPart;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



public class VolleyHelper {
    public static void postRequestVolley(final Context ctx, String url, final HashMap<String, String> hm, final int request_code, boolean cache) throws NullPointerException {

        if (ctx == null)
            return;
        //Disable the onclick event of the view element

        FLog.d("Request",url);
        final VolleyInterface vi = (VolleyInterface) ctx;
        printHashMapValues(hm);
        vi.requestStarted(request_code);

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                // calculate the duration in milliseconds
                vi.requestCompleted(request_code, response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // calculate the duration in milliseconds
                vi.requestEndedWithError(request_code, error);

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                return checkParams(hm);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        RetryPolicy retryPolicy=new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        };
        sr.setShouldCache(cache);
        sr.setRetryPolicy(retryPolicy);
        Main.getInstance().add(sr);
    }
    public static void postRequestVolley(final Context ctx,VolleyInterface volleyInterface, String url, final HashMap<String, String> hm, final int request_code,boolean cache) throws NullPointerException {

        if (ctx == null)
            return;
        //Disable the onclick event of the view element

        FLog.d("Request",url);
        final VolleyInterface vi = volleyInterface;
        printHashMapValues(hm);
        vi.requestStarted(request_code);

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // calculate the duration in milliseconds
                vi.requestCompleted(request_code, response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // calculate the duration in milliseconds
                vi.requestEndedWithError(request_code, error);

            }
        }
        )
        {
            @Override
            protected Map<String,String> getParams(){
                return checkParams(hm);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        RetryPolicy retryPolicy=new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
//phone lga ok lga app run krio manually phone mei
            }
        };
        sr.setRetryPolicy(retryPolicy);
        Main.getInstance().add(sr);
    }
    private static Map<String, String> checkParams(Map<String, String> map){
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
            if(pairs.getValue()==null){
                map.put(pairs.getKey(), "");
            }
        }
        return map;
    }
    private static void printHashMapValues(HashMap<String, String> hm) {
        Set setOfKeys = hm.keySet();
        Iterator iterator = setOfKeys.iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = hm.get(key);
            FLog.d("API", "Key: " + key + ", Value: " + value);
        }
    }
    public static void uploadImage(final Context ctx, String url, final HashMap<String, String> hm, final int request_code, final Bitmap bitmap) throws NullPointerException {

        if (ctx == null)
            return;


        final VolleyInterface vi = (VolleyInterface) ctx;

        FLog.d("API", "url: " + url);

        printHashMapValues(hm);

        vi.requestStarted(request_code);

        VolleyMultipartRequest sr = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                // calculate the duration in milliseconds
                FLog.d("API", "response: " + new String(response.data));


                vi.requestCompleted(request_code,new String(response.data));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // calculate the duration in milliseconds

                FLog.d("API", "message: " + error.getMessage());


                vi.requestEndedWithError(request_code, error);

            }
        }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("file", new DataPart("file.jpg", getFileDataFromDrawable(bitmap), "image/jpeg"));

                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                return checkParams(hm);
            }

            //code sent by Rajneesh
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // params.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                //params.put("Accept", "application/x-www-form-urlencoded");   //application/json
                //params.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.0.3; en-us; google_sdk Build/MR1) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
                return params;
            }
        };


        //increases the request time out time to 2.5 seconds and sets the number of retry to 1
        sr.setRetryPolicy(getmyownDefautRetryPolicy(16000));
        Main.getInstance().add(sr);
    }

    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static DefaultRetryPolicy getmyownDefautRetryPolicy(int timeout) {
        return new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }
}
