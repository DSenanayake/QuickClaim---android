package com.example.deepthasenanayake.quickclaim;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deeptha Senanayake on 5/6/2017.
 */

public class VehicleModel {
    private int imageID;
    private String title;
    private String description;

    public VehicleModel(){

    }

    public VehicleModel(int imageID,String title,String description){
        this.imageID = imageID;
        this.title = title;
        this.description = description;
    }


    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<VehicleModel> getObjectList(){
        List<VehicleModel> vehicles = new ArrayList<>();

        vehicles.add(new VehicleModel(R.drawable.toyota_crown,"CAA - 1584","Toyota Crown Athlete 2013"));
        vehicles.add(new VehicleModel(R.drawable.toyota_corolla,"18 - 7320","Toyota Corolla EE100 1994"));
        vehicles.add(new VehicleModel(R.drawable.hero_honda,"TE - 2512","Hero Honda CD-DAWN 2005"));

        return vehicles;
    }
}
