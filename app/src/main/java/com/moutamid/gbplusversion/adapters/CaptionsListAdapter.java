package com.moutamid.gbplusversion.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.gbplusversion.CaptionsActivity;
import com.moutamid.gbplusversion.CaptionsListActivity;
import com.moutamid.gbplusversion.R;

public class CaptionsListAdapter extends RecyclerView.Adapter<CaptionsListAdapter.CaptionsListVH> {

    Context context;
    String[] logos;

    public CaptionsListAdapter(Context context, String[] logos) {
        this.context = context;
        this.logos = logos;
    }

    @NonNull
    @Override
    public CaptionsListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.captions_card, parent, false);
        return new CaptionsListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaptionsListVH holder, int position) {
        holder.captions.setText(logos[holder.getAdapterPosition()]);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CaptionsActivity.class);
            intent.putExtra("image", logos[holder.getAdapterPosition()]);
            intent.putExtra("P", holder.getAdapterPosition());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return logos.length;
    }

    public class CaptionsListVH extends RecyclerView.ViewHolder{
        TextView captions;
        public CaptionsListVH(@NonNull View itemView) {
            super(itemView);
            captions = itemView.findViewById(R.id.caption);
        }
    }
}
