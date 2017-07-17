package com.example.deepthasenanayake.quickclaim;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Deeptha Senanayake on 5/6/2017.
 */

public class ThumbInfoAdapter extends RecyclerView.Adapter<ThumbInfoAdapter.ThumbViewHolder> {

    List<ThumbModel> objectList;
    LayoutInflater inflater;

    public ThumbInfoAdapter(Context context, List<ThumbModel> objectList) {
        inflater = LayoutInflater.from(context);
        this.objectList = objectList;
    }

    @Override
    public ThumbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.photo_list_item, parent, false);
        ThumbViewHolder viewHolder = new ThumbViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ThumbViewHolder holder, int position) {
        ThumbModel model = objectList.get(position);
        holder.setData(model, position);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public class ThumbViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgThumb;
        ImageButton btnRemove;
        int position;
        ThumbModel currentObject;

        public ThumbViewHolder(View itemView) {
            super(itemView);

            imgThumb = (ImageView) itemView.findViewById(R.id.thumb_img);
            btnRemove = (ImageButton) itemView.findViewById(R.id.thumb_remove_btn);
        }

        public void setData(ThumbModel currentObject, int position) {

            try {
                Bitmap captureBmp = MediaStore.Images.Media.getBitmap(inflater.getContext().getContentResolver(), currentObject.getFileName());

                imgThumb.setImageBitmap(Bitmap.createScaledBitmap(captureBmp, captureBmp.getWidth()/5, captureBmp.getHeight()/5, false));
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.position = position;
            this.currentObject = currentObject;
        }

        public void setListeners() {
            btnRemove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.thumb_remove_btn)
                removeItem(position);
        }

        private void removeItem(int position) {
            objectList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, objectList.size());
        }
    }
}
