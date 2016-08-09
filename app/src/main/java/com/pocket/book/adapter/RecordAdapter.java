package com.pocket.book.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pocket.book.R;
import com.pocket.book.model.Record;
import com.pocket.book.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by anil on 8/3/2016.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{

    private static final String TAG = "RecordAdapter";

    private Context context;
    ArrayList<Record> recordArrayList;

    private static ItemClickListener clickListener;

    public RecordAdapter(Context context, ArrayList<Record> recordArrayList) {

        this.context = context;
        this.recordArrayList = recordArrayList;

    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView recordTitle, recordDate;
        public ImageView recordImage;

        public ViewHolder(View v) {
            super(v);

            recordTitle = (TextView)v.findViewById(R.id.record_title);
            recordDate = (TextView)v.findViewById(R.id.record_date);
            recordImage = (ImageView)v.findViewById(R.id.image);


        }


        @Override
        public void onClick(View v) {

            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());


        }
    }

    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_record, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordAdapter.ViewHolder holder, int position) {

        if(recordArrayList != null){

            Record record = recordArrayList.get(position);
            holder.recordTitle.setText(record.getTitle());
            holder.recordDate.setText(record.getCreatedDate());
            Picasso.with(context).load(record.getImagePath()).resize(120, 60).into(holder.recordImage);


        }

    }

    @Override
    public int getItemCount() {
        return recordArrayList.size();
    }


    public interface ItemClickListener {
        void onClick(View view, int position);
    }
}
