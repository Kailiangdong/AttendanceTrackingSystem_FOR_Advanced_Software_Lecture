package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.knifestone.hyena.currency.InputFilterAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.net.CookieHandler;
import java.net.CookieManager;

import static com.example.login.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    private EditText ETaccount, ETpassword;
    private String firstName,token,id;
    private int weekNum;
    private MessageDao messageDao;
    private String last_update="2019-01-15%2000:00:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        CookieHandler.setDefault(new CookieManager());
        messageDao= new MessageDao(this);

        Spinner spinner = findViewById(R.id.spnWeek);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.weeks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerOnSelectedListener());

        ETaccount= findViewById(R.id.ETaccount);
        ETpassword= findViewById(R.id.ETpassword);
        Button BTlogin = findViewById(R.id.BTlogin);

        InputFilterAdapter inputFilter = new InputFilterAdapter
                .Builder()
                .filterEmoji(true)
                .filterChinese(true)
                .builder();
        ETaccount.setFilters(new InputFilter[]{inputFilter});
        ETpassword.setFilters(new InputFilter[]{inputFilter});

        BTlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Account = ETaccount.getText().toString().trim();
                String Password = ETpassword.getText().toString().trim();
                login(Account, Password);
            }
        });
    }

    class SpinnerOnSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            weekNum = parent.getSelectedItemPosition();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
    private void login(final String email, final String password) {
        String url_login = "https://my-first-project-222110.appspot.com/rest/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    id = jsonObject.optString("id");
                    firstName = jsonObject.optString("first_name");
                    String status=jsonObject.optString("status");
                    String reason = jsonObject.optString("reason");
                    if (status.equals("SUCCESS")){
                        Toast.makeText(MainActivity.this, "Login successful!",Toast.LENGTH_LONG).show();
                        boolean isTutor= jsonObject.optBoolean("is_tutor");
                        getToken(isTutor);
                    }else {
                        Toast.makeText(MainActivity.this, reason,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("password", password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getToken(final boolean isTutor) {
        //创建一个GET请求
        String url_getToken = "https://my-first-project-222110.appspot.com/rest/attendance/get/json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url_getToken, null,
          new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (!isTutor){
                    try {
                        JSONArray tokens=response.optJSONArray("token");
                        JSONObject token_week1=tokens.getJSONObject(weekNum);
                        token = token_week1.optString("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                cloud();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("firstName", firstName);
                intent.putExtra("token", token);
                intent.putExtra("isTutor", isTutor);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "GetToken ERROR!",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void cloud() {
        String url_cloud = "https://my-first-project-222110.appspot.com/rest/message?student_id=5644406560391168&date="+last_update;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url_cloud, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (!response.isNull("attendance_log")){
                    JSONArray attendance_log=response.optJSONArray("attendance_log");
                    messageDao.insert(attendance_log);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd%HH:mm:ss");
        last_update = dateFormat.format(date);
    }
}
