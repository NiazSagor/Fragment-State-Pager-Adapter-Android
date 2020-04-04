package com.angik.collectiondemofragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<ResItemDetailClass> mResCollection;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mItemName;
        public TextView mItemPrice;

        public MyViewHolder(View itemView, final OnItemClickListener mListener) {
            super(itemView);

            mItemName = itemView.findViewById(R.id.itemName);
            mItemPrice = itemView.findViewById(R.id.itemPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    if (mListener != null) {
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position, view);
                        }
                    }
                }
            });
        }
    }


    public MyAdapter(List<ResItemDetailClass> resCollection) {
        this.mResCollection = resCollection;
        //this.mResImages = resImage;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.res_item_layout, viewGroup, false);
        return new MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.mItemName.setText(mResCollection.get(i).getItemName());
        myViewHolder.mItemPrice.setText(mResCollection.get(i).getItemPrice());

        //myViewHolder.resImage.setImageDrawable(mResImages.get(i));

    }

    @Override
    public int getItemCount() {
        return mResCollection.size();
    }
}
