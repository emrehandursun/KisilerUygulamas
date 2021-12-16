package com.emrehan.kisileruygulamasi;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText input_Ad, input_Soyad, input_Numara, input_Bolum;
    ListView liste_data;
    ProgressBar bar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference listeVeri;

    private List<Kullanici> list_kullaniciler = new ArrayList<>();
    private Kullanici secilikullanici; //listview de bir kullanıcı tıklandığında tutacak


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.menu_toolbar);
        toolbar.setTitle("Kişiler Uygulaması");
        setSupportActionBar(toolbar);

        input_Ad = findViewById(R.id.EdittextAd);
        input_Soyad = findViewById(R.id.EdittextSoyAd);
        input_Bolum = findViewById(R.id.EdittextBolum);
        input_Numara = findViewById(R.id.EdittextNumara);
        bar = findViewById(R.id.bar);
        liste_data = findViewById(R.id.list_data);


        //firebase
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        listeVeri = firebaseDatabase.getReference();

        bar.setVisibility(View.VISIBLE);//ilk basta görünürlük
        liste_data.setVisibility(View.INVISIBLE);//ilk basta görünmemezlik

        liste_data.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id) {
                
                Kullanici listedekiKullanici=(Kullanici) parent.getItemAtPosition(position);
                secilikullanici=listedekiKullanici;
                input_Ad.setText(listedekiKullanici.getAd());
                input_Soyad.setText(listedekiKullanici.getSoyad());
                input_Numara.setText(listedekiKullanici.getNumara());
                input_Bolum.setText(listedekiKullanici.getBolum());

            }
        });



        //firebase listener kodları

        listeVeri.child("kullacilar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (list_kullaniciler.size() > 0)
                    list_kullaniciler.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Kullanici kullanici = postSnapshot.getValue(Kullanici.class);
                    list_kullaniciler.add(kullanici);
                }
                ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, list_kullaniciler);
                liste_data.setAdapter(adapter);

                bar.setVisibility(View.INVISIBLE);
                liste_data.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_ekle) {
            kullanici_ekle();
        }
        else if (item.getItemId() == R.id.menu_guncelle) {
            kullanici_guncelle();
        }
        else if (item.getItemId() == R.id.menu_sil) {
            kullanici_sil(secilikullanici);
        }

        return true;

    }

    private void kullanici_sil(Kullanici secilikullanici) {
        listeVeri.child("kullaniciler").child(secilikullanici.getUid()).removeValue();

        kontroTemizle();
    }




    private void kullanici_guncelle() {
        Kullanici kullanici=new Kullanici(secilikullanici.getUid(),input_Ad.getText().toString(),
                input_Soyad.getText().toString(),
                input_Bolum.getText().toString(),
                input_Numara.getText().toString());
        listeVeri.child("kullanicilar").child(kullanici.getUid()).child("ad").setValue(kullanici.getAd());
        listeVeri.child("kullanicilar").child(kullanici.getUid()).child("soyad").setValue(kullanici.getSoyad());
        listeVeri.child("kullanicilar").child(kullanici.getUid()).child("numara").setValue(kullanici.getNumara());
        listeVeri.child("kullanicilar").child(kullanici.getUid()).child("bolum").setValue(kullanici.getBolum());
        kontroTemizle();
    }

    private void kullanici_ekle() {
        Kullanici kullanici=new Kullanici(UUID.randomUUID().toString(),
                input_Ad.getText().toString(),
                input_Soyad.getText().toString(),
                input_Bolum.getText().toString(),
                input_Numara.getText().toString());

        //veri tabanına gönderme
        listeVeri.child("kullanicilar").child(kullanici.getUid()).setValue(kullanici);
        kontroTemizle();

    }

    private void kontroTemizle() {
        input_Ad.setText("");
        input_Soyad.setText("");
        input_Bolum.setText("");
        input_Numara.setText("");

    }
}