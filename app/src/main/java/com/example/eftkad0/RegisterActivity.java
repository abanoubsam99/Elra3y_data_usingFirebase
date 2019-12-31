package com.example.eftkad0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView titletoolbar;
    EditText username,email,password;
    Button btRegister;
    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Typeface typeface=Typeface.createFromAsset( this.getAssets(),"a-massir-ballpoint.ttf" );

        setupToolbar(R.id.toolbar,R.id.toolbar_title);

        username=findViewById(R.id.UserName);
        email=findViewById(R.id.Email);
        password=findViewById(R.id.Password);

        btRegister=findViewById(R.id.register);
        btRegister.setTypeface(typeface);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("جاري انشاء حساب جديد...");

        auth=FirebaseAuth.getInstance();


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_username=username.getText().toString();
                String text_email=email.getText().toString();
                String text_password=password.getText().toString();

                if (TextUtils.isEmpty(text_username) ||TextUtils.isEmpty(text_email) ||TextUtils.isEmpty(text_password)){
                    Toast.makeText(RegisterActivity.this, "يجب ملئ جميع البيانات ", Toast.LENGTH_SHORT).show();
                }else if (text_password.length()<6){
                    password.setError("يجب ان يكون الباسورد علي الاقل  6 ارقام");
                    password.setFocusable(true);
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(text_email).matches()){
                    email.setError(" يوجد خطأ في الايميل . تأكد انه صحيح  ");
                    email.setFocusable(true);
                }
                else if (text_username.length()<4){
                    username.setError("يجب كتابة اسمك وليس حروف ");
                    username.setFocusable(true);
                }
                else
                    register(text_username,text_email,text_password);
            }
        });

    }




    private void register(final String username, String email, String password){
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid=firebaseUser.getUid();
                            reference= FirebaseDatabase.getInstance().getReference("User").child(userid);
                            HashMap<String , String> hashMap=new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, " تم انشاء حساب جديد ", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this, "لا يمكن التسجيل برجاء راجع البيانات ", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "حدث خطأ في انشاء الاحساب حاول مرة اخري ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setupToolbar(int id,int idtext){
        Typeface typeface=Typeface.createFromAsset( this.getAssets(),"a-massir-ballpoint.ttf" );
        mToolbar = (Toolbar)findViewById(id);
        titletoolbar = (TextView) mToolbar.findViewById(idtext);
        titletoolbar.setText("  انشاء حساب جديد   ");
        titletoolbar.setTypeface(typeface);
        titletoolbar.setTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
    }
}
