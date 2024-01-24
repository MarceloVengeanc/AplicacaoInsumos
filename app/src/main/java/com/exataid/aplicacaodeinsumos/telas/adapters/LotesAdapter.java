package com.exataid.aplicacaodeinsumos.telas.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exataid.aplicacaodeinsumos.R;
import com.exataid.aplicacaodeinsumos.banco.modelos.Lotes;
import com.exataid.aplicacaodeinsumos.utils.CliqueRecycler;
import com.exataid.aplicacaodeinsumos.utils.OnItemClickListener;

import java.util.List;

public class LotesAdapter extends RecyclerView.Adapter<LotesAdapter.ViewHolder> {

    private List<Lotes> localDataSet;
    private Context ctx;
    private Resources res;
    private OnItemClickListener<Lotes> itemClickListener;

    public LotesAdapter(List<Lotes> localDataSet, Context ctx, Resources res, OnItemClickListener<Lotes> itemClickListener) {
        this.localDataSet = localDataSet;
        this.ctx = ctx;
        this.res = res;
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView loteTalhao, areaTalhao, concluido;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            loteTalhao = itemView.findViewById(R.id.txtLote);
            areaTalhao = itemView.findViewById(R.id.txtArea);
            concluido = itemView.findViewById(R.id.txtConc);
        }

        public TextView getLoteTalhao() {
            return loteTalhao;
        }

        public TextView getAreaTalhao() {
            return areaTalhao;
        }

        public TextView getConcluido() {
            return concluido;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_lista_lotes, parent, false
        );
        return new ViewHolder(v);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

//        if (position % 2 != 0) {
//            holder.itemView.setBackgroundColor(ctx.getColor(R.color.temaCinza));
//        } else {
//            holder.itemView.setBackgroundColor(ctx.getColor(R.color.temaBranco));
//        }
        holder.getLoteTalhao().setText(res.getString(R.string.plantio, localDataSet.get(position).getCodTalhao()));
        holder.getAreaTalhao().setText(res.getString(R.string.plantio, localDataSet.get(position).getAreaTalhao()));
        holder.getConcluido().setText(res.getString(R.string.plantio, localDataSet.get(position).getConcluido()));

        holder.itemView.setOnClickListener(v ->
                itemClickListener.onItemClick(localDataSet.get(position)));

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
