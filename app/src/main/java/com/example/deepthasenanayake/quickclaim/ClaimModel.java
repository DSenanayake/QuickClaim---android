package com.example.deepthasenanayake.quickclaim;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deeptha Senanayake on 5/6/2017.
 */

public class ClaimModel {
    private int mapId;
    private int claimStatus;
    private String claimDate;
    private String longitude;
    private String latitude;
    private String vehicle;
    private List<String> photos;

    public ClaimModel(){

    }

    public ClaimModel(int mapId,int claimStatus,String claimDate){
        this.mapId = mapId;
        this.claimStatus = claimStatus;
        this.claimDate = claimDate;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(int claimStatus) {
        this.claimStatus = claimStatus;
    }

    public String getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(String claimDate) {
        this.claimDate = claimDate;
    }

    public static List<ClaimModel> getObjectList(){
        List<ClaimModel> objectList = new ArrayList<>();

        objectList.add(new ClaimModel(R.drawable.static_map,1,"05 Jan 2017"));
        objectList.add(new ClaimModel(R.drawable.static_map_2,2,"01 Feb 2016"));
        objectList.add(new ClaimModel(R.drawable.static_map_3,4,"02 Mar 2016"));
        objectList.add(new ClaimModel(R.drawable.static_map_4,3,"04 April 2013"));

        return  objectList;
    }


}
