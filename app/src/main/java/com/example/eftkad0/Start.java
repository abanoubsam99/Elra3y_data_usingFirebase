package com.example.eftkad0;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Start extends AppCompatActivity {
    FirebaseUser firebaseUser;
    TextView textView;
    Button Sign_in,Sign_up;
    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        // Check if user is null
        if (firebaseUser!=null){
            Intent intent=new Intent(Start.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Typeface typeface=Typeface.createFromAsset( this.getAssets(),"a-massir-ballpoint.ttf" );

        Sign_in=findViewById(R.id.Sign_in);
        Sign_up=findViewById(R.id.Sign_up);
        textView=findViewById(R.id.textView2);

        textView.setTypeface(typeface);
        Sign_in.setTypeface(typeface);
        Sign_up.setTypeface(typeface);

    }

    public void Login(View view) {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void Regist(View view) {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("هل تريد المغادرة ؟")
                .setCancelable(false)
                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Start.this.finish();
                    }
                })
                .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
