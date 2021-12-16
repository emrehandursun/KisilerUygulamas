package com.emrehan.kisileruygulamasi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    Activity activity;
    List<Kullanici> kullaniciList;
    LayoutInflater layoutInflater;

    public ListViewAdapter(Activity activity, List<Kullanici> kullaniciList) {
        this.activity = activity;
        this.kullaniciList = kullaniciList;
    }

    @Override
    public int getCount() {
        return kullaniciList.size();
    }

    @Override
    public Object getItem(int position) {

        return kullaniciList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        layoutInflater= (LayoutInflater) activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=layoutInflater.inflate(R.layout.listview,null);
        TextView txtKullanici=itemView.findViewById(R.id.EdittextAd);
        TextView txtSoyad=itemView.findViewById(R.id.EdittextSoyAd);
        TextView txtBolum=itemView.findViewById(R.id.EdittextBolum);
        TextView txtNumara=itemView.findViewById(R.id.EdittextNumara);

        txtKullanici.setText(kullaniciList.get(position).getAd());
        txtSoyad.setText(kullaniciList.get(position).getSoyad());
        txtNumara.setText(kullaniciList.get(position).getNumara());
        txtBolum.setText(kullaniciList.get(position).getBolum());

        return itemView;
    }
}
