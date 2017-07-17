package com.example.deepthasenanayake.quickclaim;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Deeptha Senanayake on 5/6/2017.
 */

public class VehicleInfoAdapter extends RecyclerView.Adapter<VehicleInfoAdapter.VehicleViewHolder> {

    private List<VehicleModel> objectList;
    LayoutInflater inflater;

    public VehicleInfoAdapter(Context context,List<VehicleModel> objectList){
        inflater = LayoutInflater.from(context);
        this.objectList = objectList;
    }

    @Override
    public VehicleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.vehicle_list_item,parent,false);
        VehicleViewHolder viewHolder = new VehicleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VehicleViewHolder holder, int position) {
        VehicleModel model = objectList.get(position);
        holder.setData(model,position);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class VehicleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imgBackground;
        private TextView txtTitle,txtDesc;
        private int position;
        private VehicleModel currentObject;

        public VehicleViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtDesc = (TextView) itemView.findViewById(R.id.txt_desc);
            imgBackground = (ImageView) itemView.findViewById(R.id.img_back);
        }

        public void setData(VehicleModel currentObject, int position) {
            txtTitle.setText(currentObject.getTitle());
            txtDesc.setText(currentObject.getDescription());
            imgBackground.setImageResource(currentObject.getImageID());
            this.position = position;
            this.currentObject = currentObject;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(inflater.getContext(),VehicleInfoActivity.class);
            inflater.getContext().startActivity(intent);
        }

        public void setListeners() {
            txtDesc.setOnClickListener(this);
            txtTitle.setOnClickListener(this);
            imgBackground.setOnClickListener(this);
        }
    }
}
