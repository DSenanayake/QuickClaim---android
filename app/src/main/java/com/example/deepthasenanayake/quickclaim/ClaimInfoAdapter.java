package com.example.deepthasenanayake.quickclaim;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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

public class ClaimInfoAdapter extends RecyclerView.Adapter<ClaimInfoAdapter.ClaimViewHolder> {

    List<ClaimModel> objectList;
    LayoutInflater inflater;

    public ClaimInfoAdapter(Context context, List<ClaimModel> objectList) {
        inflater = LayoutInflater.from(context);
        this.objectList = objectList;
    }

    @Override
    public ClaimViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.claim_list_item, parent, false);
        ClaimViewHolder viewHolder = new ClaimViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ClaimViewHolder holder, int position) {
        ClaimModel model = objectList.get(position);
        holder.setData(model, position);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public class ClaimViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgmap;
        TextView txtDate, txtStatus;
        int position;
        ClaimModel currentObject;

        public ClaimViewHolder(View itemView) {
            super(itemView);

            imgmap = (ImageView) itemView.findViewById(R.id.claim_map);
            txtStatus = (TextView) itemView.findViewById(R.id.claim_status);
            txtDate = (TextView) itemView.findViewById(R.id.claim_date);
        }

        public void setData(ClaimModel currentObject, int position) {
            setStatus(currentObject.getClaimStatus());
            txtDate.setText(currentObject.getClaimDate());
            imgmap.setImageResource(currentObject.getMapId());

            this.position = position;
            this.currentObject = currentObject;
        }

        private void setStatus(int claimStatus) {
            switch (claimStatus) {
                case 1:
                    txtStatus.setText("Sent");
                    txtStatus.setBackgroundColor(ContextCompat.getColor(inflater.getContext(), R.color.stausSentColor));
                    break;
                case 2:
                    txtStatus.setText("Inspecting");
                    txtStatus.setBackgroundColor(ContextCompat.getColor(inflater.getContext(), R.color.stausInspColor));
                    break;
                case 3:
                    txtStatus.setText("Completed");
                    txtStatus.setBackgroundColor(ContextCompat.getColor(inflater.getContext(), R.color.stausCompletedColor));
                    break;
                case 4:
                    txtStatus.setText("Denied");
                    txtStatus.setBackgroundColor(ContextCompat.getColor(inflater.getContext(), R.color.stausDeniedColor));
                    break;
            }
        }

        public void setListeners() {
            imgmap.setOnClickListener(this);
            txtDate.setOnClickListener(this);
            txtStatus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(inflater.getContext(),ClaimInfoActivity.class);
            inflater.getContext().startActivity(intent);
        }
    }
}
