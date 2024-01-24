package com.exataid.aplicacaodeinsumos.telas.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exataid.aplicacaodeinsumos.R;
import com.exataid.aplicacaodeinsumos.banco.modelos.OrdemServico;
import com.exataid.aplicacaodeinsumos.utils.OnItemClickListener;

import java.util.List;

public class OrdemServicoAdapter extends RecyclerView.Adapter<OrdemServicoAdapter.ViewHolder> {

    private final List<OrdemServico> localDataSet;
    private final OnItemClickListener<OrdemServico> listener;

    public OrdemServicoAdapter(List<OrdemServico> localDataSet, OnItemClickListener<OrdemServico> listener) {
        this.localDataSet = localDataSet;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView descOs, codOs;

        public TextView getDescOs() {
            return descOs;
        }

        public TextView getCodOs() {
            return codOs;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            descOs = itemView.findViewById(R.id.txtNome);
            codOs = itemView.findViewById(R.id.txtCod);

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_lista_os, parent, false
        );
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getDescOs().setText(String.valueOf(localDataSet.get(position).getNumOrdemServico()));
        holder.getCodOs().setText(String.valueOf(localDataSet.get(position).getNumOrdemServico()));
        holder.itemView.setOnClickListener(v ->
                listener.onItemClick(localDataSet.get(position)));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
