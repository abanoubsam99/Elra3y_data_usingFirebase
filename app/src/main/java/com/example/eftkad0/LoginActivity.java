package com.example.eftkad0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN =100 ;
    GoogleSignInClient mGoogleSignInClient;
    private Toolbar mToolbar;
    private TextView titletoolbar , forgot_password;
    EditText email,password;
    Button bt_login;

   private FirebaseAuth auth;
    ProgressDialog progressDialog;
    SignInButton signInWith_google;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Typeface typeface=Typeface.createFromAsset( this.getAssets(),"a-massir-ballpoint.ttf" );


        setupToolbar(R.id.toolbar,R.id.toolbar_title);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);

        auth=FirebaseAuth.getInstance();

        email=findViewById(R.id.Email);
        password=findViewById(R.id.Password);
        bt_login=findViewById(R.id.bt_login);
        signInWith_google=findViewById(R.id.Sign_in_with_google);
        forgot_password=findViewById(R.id.forgot_password);
        bt_login.setTypeface(typeface);
        forgot_password.setTypeface(typeface);


        progressDialog=new ProgressDialog(this);


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email=email.getText().toString();
                String text_password=password.getText().toString();

                if (TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)){
                    Toast.makeText(LoginActivity.this, "يجب ملئ كل ابيانات ", Toast.LENGTH_SHORT).show();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(text_email).matches()){
                    email.setError(" يوجد خطأ في الايميل . تأكد انه صحيح  ");
                    email.setFocusable(true);
                }
                else if (text_password.length()<6){
                    password.setError("يجب ان يكون الباسورد علي الاقل  6 ارقام");
                    password.setFocusable(true);
                }
                else {
                    progressDialog.setMessage("جاري تسجيل الدخول ...");
                    progressDialog.show();
                    auth.signInWithEmailAndPassword(text_email,text_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "تم تأكيد الدخول  ", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "  تأكد من البيانات !!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });




        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_Recover_password();
            }
        });


        //signInWith_google
        signInWith_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });
    }

    private void show_Recover_password() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(" استعادة كلمة المرور ");

        LinearLayout linearLayout=new LinearLayout(this);

        final EditText editText=new EditText(this);
        editText.setHint("الايميل ");
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        editText.setMinEms(16);

        linearLayout.addView(editText);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);

        builder.setPositiveButton("استعادة كلمة المرور ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String Email=editText.getText().toString().trim();
                beginRecovery(Email);
            }
        });
        builder.setNegativeButton("الغاء ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }

    private void beginRecovery(String email) {
        progressDialog.setMessage("جاري  الارسال الي الايميل  ...");
        progressDialog.show();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "تم الارسال الي الايمل ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "حدث مشكلة برجاء حاول مرة اخري ", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setupToolbar(int id,int idtext){
        Typeface typeface=Typeface.createFromAsset( this.getAssets(),"a-massir-ballpoint.ttf" );
        mToolbar = (Toolbar)findViewById(id);
        titletoolbar = (TextView) mToolbar.findViewById(idtext);
        titletoolbar.setText("  تسجيل الدخول    ");
        titletoolbar.setTypeface(typeface);
        titletoolbar.setTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "حدث خطأ في تسجيل الدخول ", Toast.LENGTH_SHORT).show();

                            // updateUI(null);
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
