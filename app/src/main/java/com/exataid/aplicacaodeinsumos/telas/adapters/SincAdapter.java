package com.exataid.aplicacaodeinsumos.telas.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.exataid.aplicacaodeinsumos.R;
import com.exataid.aplicacaodeinsumos.banco.modelos.LogSync;

import java.util.List;

public class SincAdapter extends RecyclerView.Adapter<SincAdapter.ViewHolder> {

    private final List<LogSync> localDataSet;
    private final Resources res;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tipo;
        private final TextView data;
        private final TextView registro;
        private final TextView status;

        public ViewHolder(View view) {
            super(view);

            tipo = view.findViewById(R.id.sync_item_nome);
            data = view.findViewById(R.id.sync_item_data);
            registro = view.findViewById(R.id.sync_item_matricula);
            status = view.findViewById(R.id.sync_item_status);
        }

        public TextView getTipo() {
            return tipo;
        }

        public TextView getData() {
            return data;
        }

        public TextView getRegistro() {
            return registro;
        }

        public TextView getStatus() {
            return status;
        }
    }

    public SincAdapter(Context ctx, List<LogSync> dataSet) {
        localDataSet = dataSet;
        res = ctx.getResources();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_sinc, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTipo().setText(res.getString(R.string.msg_sync_item_tipo,
                localDataSet.get(position).getTipo().replace("_", " ").replace("CADASTROS", "").replace(
                        "APONTAMENTOS", "")));
        viewHolder.getData().setText(res.getString(R.string.msg_sync_item_data,
                localDataSet.get(position).getData().toString()));
        viewHolder.getRegistro().setText(res.getString(R.string.msg_sync_item_registros,
                localDataSet.get(position).getRegistros().toString()));
        viewHolder.getStatus().setText(res.getString(R.string.msg_sync_item_status,
                localDataSet.get(position).getStatus().toString()));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

