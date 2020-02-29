package com.example.myvolleyapiapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
private EditText editTextName;
private Button submitButton;
private ProgressDialog progressDialog;
private static final String Tag=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName=findViewById(R.id.editTextName);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        submitButton =findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                               String name=editTextName.getText().toString().trim();
                                               PostData(name);

                                            }
                                        }
        );
    }
    public void PostData(final String enteredName){
        String tag_request = "post_data";
        progressDialog.setMessage("sending...");
        progressDialog.show();
        String url="http://bornekasacco.com/android_db_scripts/insertuser.php";
        StringRequest myrequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(Tag,"response"+response);
                progressDialog.dismiss();
                try {
                    JSONObject myjsonobject = new JSONObject(response);
                    boolean success = myjsonobject.getBoolean("success");
                    String message = myjsonobject.getString("message");
                    if (success) {
                        //Toast.makeText("successfully submitted")
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

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
                params.put("name",enteredName);

                return params;
              }

        };

AppController.getInstance().addToRequestQueue(myrequest,tag_request);

    }

}
