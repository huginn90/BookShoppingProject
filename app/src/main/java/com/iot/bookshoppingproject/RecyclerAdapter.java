package com.iot.bookshoppingproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecycleViewHolder> {

    List<Book> bookSet = new ArrayList();

    public RecyclerAdapter(List<Book> bookSet) {
        this.bookSet = bookSet;
    }



    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecycleViewHolder holder, final int position) {
        Book book = bookSet.get(position);
        holder.textViewTitle.setText(book.getBookTitle());
        holder.textViewPrice.setText(book.getBookPrice()+" 원");

    }

    @Override
    public int getItemCount() {
        return bookSet.size();
    }

    // 리사이클러뷰 업데이트를 위한 코드
    public void upDateItemList(List<Book> bookset) {
        this.bookSet = bookset;
        notifyDataSetChanged();
    }

    class RecycleViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        TextView textViewPrice;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewPrice = (TextView) itemView.findViewById(R.id.textViewPrice);
        }


    }
}
