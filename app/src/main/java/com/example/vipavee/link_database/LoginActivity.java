package com.example.vipavee.link_database;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText EditUser, EditPassword;
    private Button Btn_Login, Btn_Regis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditUser = (EditText) findViewById(R.id.e_name);
        EditPassword = (EditText) findViewById(R.id.e_pass);
        Btn_Login = (Button) findViewById(R.id.btn_login);
        Btn_Regis = (Button) findViewById(R.id.btn_regis);

        Btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainDetail.class));
            }//LoginActivity.this กดปุ่ม Login จะไปหน้า MainDetail.class));
        });//Login

        Btn_Regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainRegister.class));
            }//หน้า Login คลิก ปุ่ม Register ไปหน้ากรอกข้อมูลสมัคร MainRegister
        });
    }//on create

}//Main


