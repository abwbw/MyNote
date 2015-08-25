package com.example.abwbw.mynote.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abwbw.mynote.R;
import com.example.abwbw.mynote.model.NotesDesrcModel;
import com.example.abwbw.mynote.util.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by abwbw on 15-8-11.
 */
public class NotesAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<NotesDesrcModel> mNoteDesrcs;
    public NotesAdapter(Context context){
        this.mContext = context;
    }

    private List<NotesDesrcModel> getData(){
        if(mNoteDesrcs == null){
            mNoteDesrcs = new ArrayList<NotesDesrcModel>();
        }
        return mNoteDesrcs;
    }


    public void setData(List<NotesDesrcModel> data){
        if(data != null) {
            mNoteDesrcs = data;
            notifyDataSetChanged();
        }
    }

    public void addData(List<NotesDesrcModel> data){
        if(data != null && data.size() > 0){
            int startPosition = getData().size();
            getData().addAll(data);
            notifyItemRangeInserted(startPosition, data.size());
        }
    }

    public void addData(int position,List<NotesDesrcModel> data){
        if(checkoutDataPosition(position,true) && data != null && data.size() > 0){
            int startPosition = position;
            getData().addAll(position, data);
            notifyItemRangeInserted(startPosition, data.size());
        }
    }

    public void addData(NotesDesrcModel data){
        if(data != null){
            int startPosition = getData().size();
            getData().add(data);
            notifyItemInserted(startPosition);
        }
    }

    public void addData(int position,NotesDesrcModel data){
        if(checkoutDataPosition(position,true) && data != null){
            int startPosition = position;
            getData().add(position, data);
            notifyItemInserted(startPosition);
        }
    }

    public boolean checkoutDataPosition(int position,boolean isIgnoreEnd){
        int dataSize = getData().size();
        if(isIgnoreEnd){
            if(position >= 0 && position <= dataSize) return true;
        } else {
            if(position >= 0 && position < dataSize) return true;
        }
        return false;
    }



    public void swapItem(int fromPosition,int toPostion){
        if(checkoutDataPosition(fromPosition,false) && checkoutDataPosition(toPostion,false)) {
            notifyItemMoved(fromPosition, toPostion);
            Collections.swap(getData(), fromPosition, toPostion);
            LogUtil.info("swapItem");
        } else {
            LogUtil.info("no swapItem");
        }
    }

    public void removeItem(int position){
        if(checkoutDataPosition(position,false)) {
            notifyItemRemoved(position);
            getData().remove(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_note_item,null);
        RecyclerView.LayoutParams lp  = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.MATCH_PARENT);
        lp.setMargins(0,2,0,2);
        view.setLayoutParams(lp);
        return new NoteHolder(view);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        List<NotesDesrcModel> dataList = getData();
        if(checkoutDataPosition(i,false)) {
            NotesDesrcModel dataModel = dataList.get(i);
            NoteHolder holder = (NoteHolder) viewHolder;

            if(dataModel != null && holder != null){
                holder.mTitleTv.setText(TextUtils.isEmpty(dataModel.getTitle())?"":dataModel.getTitle());
                holder.mAutorTv.setText(TextUtils.isEmpty(dataModel.getAutor())?"":dataModel.getAutor());
                holder.mDesrcTv.setText(TextUtils.isEmpty(dataModel.getDesrc()) ? "" : dataModel.getDesrc());
                holder.mPicIv.setImageBitmap(dataModel.getDesrcPic() != null?dataModel.getDesrcPic():null);
            }
        }
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    public static class NoteHolder extends RecyclerView.ViewHolder{
        public ImageView mPicIv;
        public TextView mDesrcTv;
        public TextView mTitleTv;
        public TextView mAutorTv;

        public NoteHolder(View itemView) {
            super(itemView);
            if(itemView != null){
                mPicIv = (ImageView) itemView.findViewById(R.id.note_pic_iv);
                mDesrcTv = (TextView) itemView.findViewById(R.id.note_desrc_tv);
                mTitleTv = (TextView) itemView.findViewById(R.id.note_title_tv);
                mAutorTv = (TextView) itemView.findViewById(R.id.note_autor_tv);
            }
        }
    }
}
