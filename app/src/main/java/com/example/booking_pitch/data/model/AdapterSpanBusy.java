package com.example.booking_pitch.data.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booking_pitch.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterSpanBusy extends RecyclerView.Adapter<AdapterSpanBusy.SpanBusy>{
    Context context;
    List<SpanBusyClass> allBusyList;

    public AdapterSpanBusy(Context context, List<SpanBusyClass> allBusyList) {
        this.context = context;
        this.allBusyList = allBusyList;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public SpanBusy onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pitch_all_busy,parent,false);
        return new SpanBusy(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SpanBusy holder, int position) {
        SpanBusyClass spanBusyClass = allBusyList.get(position);
        if (spanBusyClass == null){
            return;
        }
        holder.pitch_name.setText(spanBusyClass.getPitchName());
        holder.span1.setBackgroundResource(R.drawable.all_busy);
        holder.span2.setBackgroundResource(R.drawable.all_busy);
        holder.span3.setBackgroundResource(R.drawable.all_busy);
        holder.span4.setBackgroundResource(R.drawable.all_busy);
        holder.span5.setBackgroundResource(R.drawable.all_busy);
        for (int i = 0; i < spanBusyClass.getSpanBusy().size() ; i++){
            if (spanBusyClass.getSpanBusy().get(i).equals("1")) {
                holder.span1.setBackgroundResource(R.drawable.red);
            }else if (spanBusyClass.getSpanBusy().get(i).equals("2")) {
                holder.span2.setBackgroundResource(R.drawable.red);
            }else if (spanBusyClass.getSpanBusy().get(i).equals("3")) {
                holder.span3.setBackgroundResource(R.drawable.red);
            }else if (spanBusyClass.getSpanBusy().get(i).equals("4")) {
                holder.span4.setBackgroundResource(R.drawable.red);
            }else if (spanBusyClass.getSpanBusy().get(i).equals("5")) {
                holder.span5.setBackgroundResource(R.drawable.red);
            }
        }
    }
    @Override
    public int getItemCount() {
        if (allBusyList!=null){
            return allBusyList.size();
        }
        return 0;
    }

    public class SpanBusy extends RecyclerView.ViewHolder{
        TextView pitch_name ;
        TextView span1,span2,span3,span4,span5 ;
        RecyclerView recyclerView;
        public SpanBusy(@NonNull @NotNull View itemView) {
            super(itemView);
            pitch_name = itemView.findViewById(R.id.pitch_name_busy);
            span1 = itemView.findViewById(R.id.ca1);
            span2 = itemView.findViewById(R.id.ca2);
            span3 = itemView.findViewById(R.id.ca3);
            span4 = itemView.findViewById(R.id.ca4);
            span5 = itemView.findViewById(R.id.ca5);
            recyclerView = itemView.findViewById(R.id.rcv_spanbusy1);
        }
    }
}
