package com.example.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class HomeActivity extends AppCompatActivity {
    private Button logout;
    private TextView hello, tv;
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        hello= findViewById(R.id.hello);
        tv = findViewById(R.id.tv);
        logout=findViewById(R.id.btn_logout);
        mImageView =  findViewById(R.id.iv);

        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");
        String id = intent.getStringExtra("id");
        String token = intent.getStringExtra("token");
        Boolean isTutor = intent.getBooleanExtra("isTutor", false);

        hello.setText("Hello "+firstName+"!");

        if (isTutor){
            tv.setText("As a tutor you have no QR Code！");
        }else {
            Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap("id="+id+" token="+token, 256, 256);
            mImageView.setImageBitmap(mBitmap);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                logout();
            }
        });
    }

    private void logout() {
        //创建一个POST请求
        String url_logout = "https://my-first-project-222110.appspot.com/rest/logout";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_logout, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(HomeActivity.this, "Logout successful!",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "ERROR",Toast.LENGTH_LONG).show();
            }
        });
        //创建一个请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //将创建的请求添加到队列中
        requestQueue.add(stringRequest);
    }
}
