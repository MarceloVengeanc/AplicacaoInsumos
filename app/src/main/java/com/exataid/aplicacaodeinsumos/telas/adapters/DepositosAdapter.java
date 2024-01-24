package com.exataid.aplicacaodeinsumos.telas.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exataid.aplicacaodeinsumos.R;
import com.exataid.aplicacaodeinsumos.banco.modelos.Depositos;
import com.exataid.aplicacaodeinsumos.utils.OnItemClickListener;

import java.util.List;

public class DepositosAdapter extends RecyclerView.Adapter<DepositosAdapter.ViewHolder> {

    private final List<Depositos> localDataSet;
    private final OnItemClickListener<Depositos> listener;

    public DepositosAdapter(List<Depositos> localDataSet, OnItemClickListener<Depositos> listener) {
        this.localDataSet = localDataSet;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView nomeDeposito, codDeposito;

        public TextView getNomeDeposito() {
            return nomeDeposito;
        }

        public TextView getCodDeposito() {
            return codDeposito;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeDeposito = itemView.findViewById(R.id.txtNomeItem);
            codDeposito = itemView.findViewById(R.id.txtCodItem);

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_listas, parent, false
        );
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNomeDeposito().setText(String.valueOf(localDataSet.get(position).getNomeDeposito()));
        holder.getCodDeposito().setText(String.valueOf(localDataSet.get(position).getCodDeposito()));
        holder.itemView.setOnClickListener(v ->
                listener.onItemClick(localDataSet.get(position)));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
