package com.moutamid.gb.adapters;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.moutamid.gb.R;

public class AngryAdapter extends RecyclerView.Adapter<AngryAdapter.MyViewHolder> {
    private String[] AngryAscii;
    private Context context;

    public AngryAdapter(String[] angryAscii, Context context) {
        AngryAscii = angryAscii;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.emotions_card, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.emoj.setText(AngryAscii[holder.getAdapterPosition()]);

        holder.copy.setOnClickListener(v -> {
            String str = AngryAscii[holder.getAdapterPosition()];
            ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Copied Text", str));
            Toast.makeText(context, "Copied To Clipboard", Toast.LENGTH_SHORT).show();
        });

        holder.share.setOnClickListener(v -> {
            String str = AngryAscii[holder.getAdapterPosition()];
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setFlags(335544320);
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", str);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.AngryAscii == null ? 0 : this.AngryAscii.length;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView emoj;
        MaterialCardView share, copy;

        MyViewHolder(View itemView) {
            super(itemView);
            emoj = itemView.findViewById(R.id.emoj);
            share = itemView.findViewById(R.id.shareBtn);
            copy = itemView.findViewById(R.id.copyBtn);
        }
    }
}
