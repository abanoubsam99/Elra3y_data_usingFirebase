package com.example.eftkad0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChildrenActivity extends AppCompatActivity implements AdapterChildrenRecyclerView.FirebaseDataListener{
    FirebaseAuth auth;

    private Toolbar mToolbar;
    private TextView titletoolbar;
    private FloatingActionButton mFloatingActionButton;
    private EditText mEditfullname;
    private EditText mEditawho;
    private EditText mEditIdNumber;
    private EditText mEditbirthday;
    private EditText mEditstatus;
    private EditText mEditqualification;
    private EditText mEditwork;
    private EditText mEditphone;
    private EditText mEditdateofentry;

    private RecyclerView mChildrenRecycler;
    private TextView textFather;
    private AdapterChildrenRecyclerView mAdapter;

    private  DatabaseReference databasechildren;
    private  DatabaseReference databasechildrentotal;
    private ArrayList<Children> childrens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children);
        //android toolbar
        setupToolbar(R.id.toolbar,R.id.toolbar_title);

        Intent intent = getIntent();

        textFather=findViewById(R.id.textView_father);
        textFather.setText(intent.getStringExtra(MainActivity.FATHER_NAME));
        auth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=auth.getCurrentUser();
        assert firebaseUser != null;
        databasechildren = FirebaseDatabase.getInstance().getReference("Children"+firebaseUser.getUid()).child(intent.getStringExtra(MainActivity.FATHER_ID));
        mFloatingActionButton=findViewById(R.id.tambah_Children);
        mChildrenRecycler = (RecyclerView)findViewById(R.id.recycler_view_children);
        mChildrenRecycler.setHasFixedSize(true);
        mChildrenRecycler.setLayoutManager(new LinearLayoutManager(this));
        FirebaseApp.initializeApp(this);



        databasechildren.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                childrens = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Children children = postSnapshot.getValue(Children.class);
                    children.setKey(postSnapshot.getKey());
                    childrens.add(children);
                }
                mAdapter = new AdapterChildrenRecyclerView(ChildrenActivity.this, childrens);
                mChildrenRecycler.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChildrenActivity.this, databaseError.getDetails()+" "+databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTambahChildren();
            }
        });



    }

    @Override
    public void onDataClick(final Children children, int position) {
        //aksi ketika data di klik
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" اختيار الامر ");


        builder.setPositiveButton(" تعديل ", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                dialogUpdateChildren(children);
            }
        });
        builder.setNegativeButton(" مسح ", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                deletDataChildren(children);
            }
        });


        Dialog dialog = builder.create();
        dialog.show();
    }



    private void dialogTambahChildren() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" اضافة البيانات ");
        View view = getLayoutInflater().inflate(R.layout.layout_tambah_children, null);

        mEditfullname = (EditText)view.findViewById(R.id.fullname_children);
        mEditawho = (EditText)view.findViewById(R.id.who_children);
        mEditIdNumber = (EditText)view.findViewById(R.id.IdNumber_children);
        mEditbirthday = (EditText)view.findViewById(R.id.birthday_children);
        mEditstatus = (EditText)view.findViewById(R.id.status_children);
        mEditqualification = (EditText)view.findViewById(R.id.qualification_children);
        mEditwork = (EditText)view.findViewById(R.id.work_children);
        mEditphone = (EditText)view.findViewById(R.id.phone_children);
        mEditdateofentry = (EditText)view.findViewById(R.id.dateofentry_children);

        builder.setView(view);

        //button simpan barang / submit barang
        builder.setPositiveButton(" حفظ ", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){

                String fullnameChildren = mEditfullname.getText().toString();
                String whoChildren = mEditawho.getText().toString();
                String IdNumberChildren= mEditIdNumber.getText().toString();
                String birthdayChildren = mEditbirthday.getText().toString();
                String statusChildren = mEditstatus.getText().toString();
                String qualificationChildren = mEditqualification.getText().toString();
                String workChildren = mEditwork.getText().toString();
                String phoneChildren = mEditphone.getText().toString();
                String dateofentryChildren = mEditdateofentry.getText().toString();


                if(!fullnameChildren.isEmpty()){
                    submitDataChildren(new Children(fullnameChildren, whoChildren,IdNumberChildren,birthdayChildren,statusChildren,
                          qualificationChildren,workChildren,phoneChildren,dateofentryChildren));
                }
                else {
                    Toast.makeText(ChildrenActivity.this, " يجب ملئ البيانات ", Toast.LENGTH_LONG).show();
                }
            }
        });

        //button kembali / batal
        builder.setNegativeButton(" الغاء ", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void setupToolbar(int id,int idtext){
        Typeface typeface=Typeface.createFromAsset( this.getAssets(),"a-massir-ballpoint.ttf" );
        mToolbar = (Toolbar)findViewById(id);
        titletoolbar = (TextView) mToolbar.findViewById(idtext);
        titletoolbar.setText(" بــيــانــات الــعــائـلــة ");
        titletoolbar.setTypeface(typeface);
        titletoolbar.setTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
    }






    //dialog update barang / update data barang
    private void dialogUpdateChildren(final Children children){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" تعديل البيانات ");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_children, null);

        mEditfullname = (EditText)view.findViewById(R.id.fullname_children);
        mEditawho = (EditText)view.findViewById(R.id.who_children);
        mEditIdNumber = (EditText)view.findViewById(R.id.IdNumber_children);
        mEditbirthday = (EditText)view.findViewById(R.id.birthday_children);
        mEditstatus = (EditText)view.findViewById(R.id.status_children);
        mEditqualification = (EditText)view.findViewById(R.id.qualification_children);
        mEditwork = (EditText)view.findViewById(R.id.work_children);
        mEditphone = (EditText)view.findViewById(R.id.phone_children);
        mEditdateofentry = (EditText)view.findViewById(R.id.dateofentry_children);

        mEditfullname.setText(children.getFullname());
        mEditawho.setText(children.getWho());
        mEditIdNumber.setText(children.getIdNumber());
        mEditbirthday.setText(children.getBirthday());
        mEditstatus.setText(children.getStatus());
        mEditqualification.setText(children.getQualification());
        mEditwork.setText(children.getWork());
        mEditphone.setText(children.getPhone());
        mEditdateofentry.setText(children.getDateofentry());


        builder.setView(view);

        //final Barang mBarang = (Barang)getIntent().getSerializableExtra("
        if (children != null){
            builder.setPositiveButton(" حفظ ", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id){
                    children.setFullname(mEditfullname.getText().toString());
                    children.setWho(mEditawho.getText().toString());
                    children.setIdNumber(mEditIdNumber.getText().toString());
                    children.setBirthday(mEditbirthday.getText().toString());
                    children.setStatus(mEditstatus.getText().toString());
                    children.setQualification(mEditqualification.getText().toString());
                    children.setWork(mEditwork.getText().toString());
                    children.setPhone(mEditphone.getText().toString());
                    children.setDateofentry(mEditdateofentry.getText().toString());
                    updateDataFather(children);
                }
            });
        }
        builder.setNegativeButton(" الغاء ", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();

    }

    private void submitDataChildren(Children children){
        databasechildren.push().setValue(children).addOnSuccessListener(this, new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void mVoid){
                Toast.makeText(ChildrenActivity.this, " تم حفظ البيانات بنجاح!! ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateDataFather(Children children){
        databasechildren.child(children.getKey()).setValue(children).addOnSuccessListener(new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void mVoid){
                Toast.makeText(ChildrenActivity.this, " تم نحديث البيانات بنجاح!! ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deletDataChildren(Children children) {
        if(databasechildren != null){
            databasechildren.child(children.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>(){
                @Override
                public void onSuccess(Void mVoid){
                    Toast.makeText(ChildrenActivity.this," تم حذف البيانات بنجاح !! ", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ChildrenActivity.this,Start.class));
                finish();
                return true;
            case R.id.information:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = getLayoutInflater().inflate(R.layout.information, null);
                builder.setView(view);
                //button kembali / batal
                builder.setPositiveButton(" الخروج ", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        dialog.dismiss();
                    }
                });

                builder.create().show();
                return true;
        }
        return false;

    }



}
