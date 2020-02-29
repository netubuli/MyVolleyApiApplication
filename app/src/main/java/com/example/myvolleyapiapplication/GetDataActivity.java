package com.example.myvolleyapiapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetDataActivity extends AppCompatActivity {
Button queryButton;
private ProgressDialog progressDialog;
    private static final String Tag=GetDataActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        queryButton=findViewById(R.id.querybutton);
       queryButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getData("12345");
           }
       }
       );
    }

    private void getData(final String enteredId) {
        String tag_request = "get_data";
        progressDialog.setMessage("fetching data...");
        progressDialog.show();
        String url="http://bornekasacco.com/android_db_scripts/getuser.php";
        StringRequest myrequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(Tag,"response"+response);
                progressDialog.dismiss();
                try {
                    JSONObject myjsonobject = new JSONObject(response);
                    boolean success = myjsonobject.getBoolean("success");

                    String retrieveddata = myjsonobject.getString("retrieveddata");
                    if (success) {
                        //Toast.makeText("successfully submitted")
                        Toast.makeText(getApplicationContext(), retrieveddata, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError errorMessage){
                Log.d(Tag,"errorMessage"+errorMessage);
                Toast.makeText(getApplicationContext(), errorMessage.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        }){
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("idnumber",enteredId);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(myrequest,tag_request);
    }
}
