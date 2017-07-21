package com.example.vipavee.link_database;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainRegister extends AppCompatActivity {

    private EditText EditUsername, EditPassword, EditName;
    private Button Btn_register;
    private String StrUser, StrPass, StrName; //กำหนดไว้เผื่อไว้เช็คค่า java ไม่สามารถเช็คโดยตรงได้ Check ค่าว่าง
    private ImageView imageView;
    private Uri uri; //dataต่างๆที่ดึงมา

    //tag Monitor
    private String PathPic, NamePic;
    private String Tag = "FindPic";
    private boolean CheckPic = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);

        EditUsername = (EditText) findViewById(R.id.e_username);
        EditPassword = (EditText) findViewById(R.id.e_password);
        EditName = (EditText) findViewById(R.id.e_fullname);
        Btn_register = (Button) findViewById(R.id.btn_register1);
        imageView = (ImageView) findViewById(R.id.imageView4);

        Btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrUser = EditUsername.getText().toString().trim();
                StrPass = EditPassword.getText().toString().trim();
                StrName = EditName.getText().toString().trim();


                //check String

                if (StrUser.equals("") || StrPass.equals("") || StrName.equals("")) {
                    //dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainRegister.this);
                    builder.setCancelable(false);
                    builder.setTitle("Warning");
                    builder.setMessage("Please Input Data");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else if (CheckPic == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainRegister.this);
                    builder.setCancelable(false);
                    builder.setTitle("Warning");
                    builder.setMessage("Please Select Photo");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });//ถ้าไม่ได้เลือกรูป dialog จะขึ้นเตือน checkว่ามีการเลือกรูปหรือยัง
                    builder.show();
                } else {
                    //your select photo
                }


            }
        });

        //Set Image
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//funtion select image
                intent.setType("image/*");//Type to choose
                startActivityForResult(intent.createChooser(intent, "Please Select"), 1);
                //startActivityForResult เพื่อต้องหารใฟ้ผลลัพธ์กลับมา int ข้างหลัง ใส่ไว้เพื่อ returnค่าเฉยๆ
            }//select image
        });


    }//create

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            //Show PIC
            uri = data.getData();
            Show_Pic(uri);

            //getPath and Name

            PathPic = FindPathPic(uri);
            //cut string
            NamePic = PathPic.substring(PathPic.lastIndexOf("/"));
            Log.d(Tag, "Path>>>>" + PathPic); //Pathของรูผ
            Log.d(Tag, "Name>>>>" + NamePic);//Name Pic
            CheckPic = true;
        }//ถ้า Result OK จะโชว์ รูป
    }

    private String FindPathPic(Uri uri) {
        String result = null;
        String[] getstrings = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, getstrings, null, null, null); //cursor ประมวลผลในRAM //getContentResolver getData มาQuery Dataที่เอามา Dataเอามาจาก MediaStore (ประกาศไว้เป็น getstrings)
        if (cursor != null) {
            cursor.moveToFirst(); //หาจากบนลงล่าง
            int i = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            return cursor.getString(i);
        } else {
            result = uri.getPath();
        }
        return result;
    }//getPath

    private void Show_Pic(Uri uri) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));//แปลงภาพ เป็น bitmap  มาเก็บไว้ในตัวแปร bitmap
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace(); //e.printStackTrace(); เป็นการแสดง Error ไปยัง throwable เ

        }
    }//fuction ShowPic


}//Main

