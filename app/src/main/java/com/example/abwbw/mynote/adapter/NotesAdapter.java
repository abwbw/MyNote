package com.example.abwbw.mynote.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abwbw.mynote.R;
import com.example.abwbw.mynote.util.ScreenUtil;

/**
 * Created by abwbw on 15-8-11.
 */
public class NotesAdapter extends RecyclerView.Adapter{
    private Context mContext;
    public NotesAdapter(Context context){
        this.mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_note_item,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.MATCH_PARENT));

        return new NoteHolder(view);
    }

    public void moveItem(int fromPosition,int toPostion){
        notifyItemMoved(fromPosition, toPostion);
    }

    public void removeItem(int position){
        notifyItemRemoved(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        NoteHolder holder = (NoteHolder) viewHolder;
        holder.mContentTv.setText("文本内容--\n" +
                "文本内容0-00-\n" +
                "文本内容666--\n" +
                "文本内容--\n" +
                "文本内容--\n" +
                "文本内容--\n");
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class NoteHolder extends RecyclerView.ViewHolder{
        public ImageView mPicIv;
        public TextView mContentTv;

        public NoteHolder(View itemView) {
            super(itemView);
            if(itemView != null){
                mPicIv = (ImageView) itemView.findViewById(R.id.note_pic_iv);
                mContentTv = (TextView) itemView.findViewById(R.id.note_content_tv);
            }
        }
    }
}
