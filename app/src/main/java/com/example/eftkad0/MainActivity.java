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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterFatherRecyclerView.FirebaseDataListener {
    public static final String FATHER_NAME = "net.simplifiedcoding.firebasedatabaseexample.artistname";
    public static final String FATHER_ID = "net.simplifiedcoding.firebasedatabaseexample.artistid";
    FirebaseAuth auth;

    //variabel fields
    private Toolbar mToolbar;
    private TextView titletoolbar;
    private FloatingActionButton mFloatingActionButton;
    private TextView textcount;
    private EditText mEditNama;
    private EditText mEditlocation;
    private EditText mEditstreet;
    private EditText mEditnumadress;
    private EditText mEditNum;
    private EditText mEditphone;
    private EditText msearch;
    private Button btSearch;
    private Spinner spinner_Search;
    private String spiner_chose="nama";
    private RecyclerView mRecyclerView;
    private AdapterFatherRecyclerView mAdapter;
    private ArrayList<Father> daftarfather;
    //variabel yang merefers ke Firebase Database
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //android toolbar
        setupToolbar(R.id.toolbar,R.id.toolbar_title);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        daftarfather = new ArrayList<>();
        mAdapter = new AdapterFatherRecyclerView(MainActivity.this, daftarfather);
        mRecyclerView.setAdapter(mAdapter);

        FirebaseApp.initializeApp(this);
        // mengambil referensi ke Firebase Database
        auth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=auth.getCurrentUser();
        assert firebaseUser != null;
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("Father"+firebaseUser.getUid());
        mDatabaseReference.addListenerForSingleValueEvent(valueEventListener);

        //FAB (FloatingActionButton) tambah barang
        mFloatingActionButton = (FloatingActionButton)findViewById(R.id.tambah_father);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //tambah barang
                dialogTambahFather();
            }
        });

        textcount=findViewById(R.id.count);


        //Button Search
        Typeface typeface=Typeface.createFromAsset( this.getAssets(),"a-massir-ballpoint.ttf" );
        btSearch=findViewById(R.id.btSearch);
        btSearch.setTypeface(typeface);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search();
            }
        });
   }



   // Add Data to Firebase
    ValueEventListener valueEventListener=new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           daftarfather.clear();
           for (DataSnapshot mDataSnapshot : dataSnapshot.getChildren()){
               Father father = mDataSnapshot.getValue(Father.class);
               father.setKey(mDataSnapshot.getKey());
               daftarfather.add(father);
           }
           //set adapter RecyclerView
           mAdapter = new AdapterFatherRecyclerView(MainActivity.this, daftarfather);
           mRecyclerView.setAdapter(mAdapter);
           textcount.setText( "عدد الـعـائـلات  :   " + dataSnapshot.getChildrenCount()+"" );

       }

       @Override
       public void onCancelled(@NonNull DatabaseError databaseError) {
           Toast.makeText(MainActivity.this, databaseError.getDetails()+" "+databaseError.getMessage(), Toast.LENGTH_LONG).show();

       }
   };


    @Override
    public void onDataClick(final Father father, final int position){
        //aksi ketika data di klik
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" اختيار الامر ");


        builder.setPositiveButton(" تعديل ", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                dialogUpdateFather(father);
            }
        });
        builder.setNegativeButton(" مسح ", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                hapusDataFather(father);
            }
        });
        builder.setNeutralButton(" فتح  ", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                openchilren(father,position);
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void openchilren(Father father , int position) {
        //getting the selected father

        Father father1 = daftarfather.get(position);

        //creating an intent
        Intent intent = new Intent(getApplicationContext(), ChildrenActivity.class);

        //putting artist name and id to intent
        intent.putExtra(FATHER_ID, father1.getKey());
        intent.putExtra(FATHER_NAME, father1.getNama());

        //starting the activity with intent
        startActivity(intent);

    }


    private void Search() {
        spinner_Search=findViewById(R.id.spinner_search);
        msearch=findViewById(R.id.mSearch);
        spinner_Search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (view != null) {
                    if (i == 1) {
                        spiner_chose="location";
                    }else if (i==2){
                        spiner_chose="street";
                    }
                    else if (i==3){
                        spiner_chose="phone";
                    }
                    else {
                        spiner_chose="nama";
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spiner_chose="nama";
            }
        });

        Query query=mDatabaseReference
                .orderByChild(spiner_chose)
                .startAt(msearch.getText().toString())
                .endAt(msearch.getText().toString() +"\uf8ff");
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    //setup android toolbar
    private void setupToolbar(int id,int idtext){
        Typeface typeface=Typeface.createFromAsset( this.getAssets(),"a-massir-ballpoint.ttf" );
        mToolbar = (Toolbar)findViewById(id);
        titletoolbar = (TextView) mToolbar.findViewById(idtext);
        titletoolbar.setText(" الــعــائــلات  ");
        titletoolbar.setTypeface(typeface);
        titletoolbar.setTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
    }

    //dialog tambah barang / alert dialog
    private void dialogTambahFather(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" اضافة البيانات ");
        View view = getLayoutInflater().inflate(R.layout.layout_tambah_father, null);

        mEditNama = (EditText)view.findViewById(R.id.nama_father);
        mEditlocation = (EditText)view.findViewById(R.id.local_father);
        mEditstreet = (EditText)view.findViewById(R.id.street_father);
        mEditnumadress = (EditText)view.findViewById(R.id.numadress_father);
        mEditNum = (EditText)view.findViewById(R.id.Num_father);
        mEditphone = (EditText)view.findViewById(R.id.phone_father);

        builder.setView(view);

        //button simpan barang / submit barang
        builder.setPositiveButton(" حفظ ", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                String namaFather = mEditNama.getText().toString();
                String locationFather = mEditlocation.getText().toString();
                String streetFather = mEditstreet.getText().toString();
                String numadressFather = mEditnumadress.getText().toString();
                String NumFather = mEditNum.getText().toString();
                String phoneFather = mEditphone.getText().toString();

                if(!namaFather.isEmpty()){
                    submitDataFather(new Father(namaFather, locationFather,streetFather, numadressFather,NumFather,phoneFather));
                }
                else {
                    Toast.makeText(MainActivity.this, " يجب ملئ البيانات ", Toast.LENGTH_LONG).show();
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



    //dialog update barang / update data barang
    private void dialogUpdateFather(final Father father){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" تعديل البيانات ");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_father, null);

        mEditNama = (EditText)view.findViewById(R.id.nama_father);
        mEditlocation = (EditText)view.findViewById(R.id.local_father);
        mEditstreet = (EditText)view.findViewById(R.id.street_father);
        mEditnumadress = (EditText)view.findViewById(R.id.numadress_father);
        mEditNum = (EditText)view.findViewById(R.id.Num_father);
        mEditphone = (EditText)view.findViewById(R.id.phone_father);


        mEditNama.setText(father.getNama());
        mEditlocation.setText(father.getLocation());
        mEditstreet.setText(father.getStreet());
        mEditnumadress.setText(father.getNumadress());
        mEditNum.setText(father.getNum());
        mEditphone.setText(father.getPhone());

        builder.setView(view);

        //final Barang mBarang = (Barang)getIntent().getSerializableExtra("
        if (father != null){
            builder.setPositiveButton(" حفظ ", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id){
                    father.setNama(mEditNama.getText().toString());
                    father.setLocation(mEditlocation.getText().toString());
                    father.setStreet(mEditstreet.getText().toString());
                    father.setNumadress(mEditnumadress.getText().toString());
                    father.setNum(mEditNum.getText().toString());
                    father.setPhone(mEditphone.getText().toString());
                    updateDataFather(father);
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

    /**
     * submit data barang
     * ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
     * set onSuccessListener yang berisi kode yang akan dijalankan
     * ketika data berhasil ditambahkan
     */
    private void submitDataFather(Father father){
        mDatabaseReference.addListenerForSingleValueEvent(valueEventListener);
        mDatabaseReference.push().setValue(father).addOnSuccessListener(this, new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void mVoid){
                Toast.makeText(MainActivity.this, " تم حفظ البيانات بنجاح!! ", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * update/edit data barang
     * ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
     * set onSuccessListener yang berisi kode yang akan dijalankan
     * ketika data berhasil ditambahkan
     */
    private void updateDataFather(Father father){
        mDatabaseReference.addListenerForSingleValueEvent(valueEventListener);
        mDatabaseReference.child(father.getKey()).setValue(father).addOnSuccessListener(new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void mVoid){
                Toast.makeText(MainActivity.this, " تم نحديث البيانات بنجاح!! ", Toast.LENGTH_LONG).show();
            }
        });
    }
    /**
     * hapus data barang
     * ini kode yang digunakan untuk menghapus data yang ada di Firebase Realtime Database
     * set onSuccessListener yang berisi kode yang akan dijalankan
     * ketika data berhasil dihapus
     * @param father
     */
    private void hapusDataFather(Father father){
        if(mDatabaseReference != null){
            mDatabaseReference.addListenerForSingleValueEvent(valueEventListener);
            FirebaseUser firebaseUser=auth.getCurrentUser();
            assert firebaseUser != null;
            DatabaseReference child =FirebaseDatabase.getInstance().getReference("Children"+firebaseUser.getUid()).child(father.getKey());
            child.removeValue();
            mDatabaseReference.child(father.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>(){
                @Override
                public void onSuccess(Void mVoid){

                    Toast.makeText(MainActivity.this," تم حذف البيانات بنجاح !! ", Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(MainActivity.this,Start.class));
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


    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("هل تريد المغادرة ؟")
                .setCancelable(false)
                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alert = builder.create();
        alert.show();

    }

}
