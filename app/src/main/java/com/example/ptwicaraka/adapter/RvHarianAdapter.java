package com.example.ptwicaraka.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ptwicaraka.EditHarianAct;
import com.example.ptwicaraka.R;
import com.example.ptwicaraka.model.ModelHarian;

import java.util.ArrayList;

public class RvHarianAdapter extends RecyclerView.Adapter<RvHarianAdapter.ViewHolder> {

    private Context context;
    ArrayList<ModelHarian> harians;

    public RvHarianAdapter(Context context, ArrayList<ModelHarian> harians) {
        this.context = context;
        this.harians = harians;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_harian, viewGroup, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ModelHarian harian = harians.get(i);
        viewHolder.tvTanggal.setText("Tanggal : " + harian.getTanggal());
        viewHolder.tvNama.setText("Nama Pembeli : " + harian.getNama_pembeli());
        viewHolder.tvAlamat.setText("Alamat Pembeli : " + harian.getAlamat_pembeli());
        viewHolder.tvKategori.setText("Kategori : " + harian.getKategori());
        viewHolder.tvKeterangan.setText("Keterangan : " + harian.getKeterangan());
        viewHolder.tvPembelian
                .setText("Jumlah Pembelian Tabung : " + harian.getJumlah_pembelian_tabung());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoedit = new Intent(context, EditHarianAct.class);
                gotoedit.putExtra("pangkalan", harian.getPangkalan());
                gotoedit.putExtra("tanggal", harian.getTanggal());
                gotoedit.putExtra("id", harian.getId_harian());
                context.startActivity(gotoedit);

            }
        });
    }

    @Override
    public int getItemCount() {
        return harians.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTanggal, tvNama, tvAlamat, tvKategori, tvKeterangan, tvPembelian;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTanggal = itemView.findViewById(R.id.rv_tanggal);
            tvNama = itemView.findViewById(R.id.rv_nama);
            tvAlamat = itemView.findViewById(R.id.rv_alamat);
            tvKategori = itemView.findViewById(R.id.rv_kategori);
            tvKeterangan = itemView.findViewById(R.id.rv_ket);
            tvPembelian = itemView.findViewById(R.id.rv_pembelian);
        }
    }
}
