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
import com.exataid.aplicacaodeinsumos.banco.modelos.Insumos;
import com.exataid.aplicacaodeinsumos.utils.CliqueRecycler;
import com.exataid.aplicacaodeinsumos.utils.OnItemClickListener;

import java.util.List;

public class InsumosAdapter extends RecyclerView.Adapter<InsumosAdapter.ViewHolder> {

    private List<Insumos> localDataSet;
    private Context ctx;
    private Resources res;
   private OnItemClickListener<Insumos>listener;

    public InsumosAdapter(List<Insumos> localDataSet, Context ctx, Resources res, OnItemClickListener<Insumos> listener) {
        this.localDataSet = localDataSet;
        this.ctx = ctx;
        this.res = res;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView codInsumo, nomeInsumo, programado, aplicado, deposito;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codInsumo = itemView.findViewById(R.id.txtCodInsumo);
            nomeInsumo = itemView.findViewById(R.id.txtNomeInsumo);
            programado = itemView.findViewById(R.id.txtProg);
            aplicado = itemView.findViewById(R.id.txtAplic);
            deposito = itemView.findViewById(R.id.txtDp);
        }

        public TextView getCodInsumo() {
            return codInsumo;
        }

        public TextView getNomeInsumo() {
            return nomeInsumo;
        }

        public TextView getProgramado() {
            return programado;
        }

        public TextView getAplicado() {
            return aplicado;
        }

        public TextView getDeposito() {
            return deposito;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_lista_insumos, parent, false
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
        holder.getCodInsumo().setText(res.getString(R.string.plantio, localDataSet.get(position).getCodInsumo()));
        holder.getNomeInsumo().setText(res.getString(R.string.plantio, localDataSet.get(position).getDescInsumo()));
        holder.getProgramado().setText(res.getString(R.string.plantio, localDataSet.get(position).getQuantidadeProgramado()));
        holder.getAplicado().setText(res.getString(R.string.plantio, localDataSet.get(position).getQuantidadeAplicado()));
        holder.getDeposito().setText(res.getString(R.string.plantio, localDataSet.get(position).getCodDp()));

        holder.itemView.setOnClickListener(v ->
                listener.onItemClick(localDataSet.get(position)));


    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
