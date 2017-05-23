package com.iot.bookshoppingproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecycleViewHolder> {

    List<RecycleData> RecycleDataSet = new ArrayList();

    public RecyclerAdapter(List<RecycleData> recycleDataSet) {
        RecycleDataSet = recycleDataSet;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list, parent, false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecycleViewHolder holder, final int position) {
        RecycleData recycleData = RecycleDataSet.get(position);
        holder.button.setText("pos"+position);
        holder.textView.setText(recycleData.textview_text);

    }

    @Override
    public int getItemCount() {
        return RecycleDataSet.size();
    }

    class RecycleViewHolder extends RecyclerView.ViewHolder {

        Button button;
        TextView textView;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.button);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }


    }
}
