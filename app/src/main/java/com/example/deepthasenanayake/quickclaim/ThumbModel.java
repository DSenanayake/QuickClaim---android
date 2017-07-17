package com.example.deepthasenanayake.quickclaim;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deeptha Senanayake on 5/6/2017.
 */

public class ThumbModel {
    private Bitmap photo;
    private Uri fileName;

    public ThumbModel() {

    }

    public ThumbModel(Uri fileName) {
        this.setFileName(fileName);
    }

//    public static List<ThumbModel> getObjectList(){
//        List<ThumbModel> objectList = new ArrayList<>();
//
//        objectList.add(new ThumbModel("android.resource://com.example.deepthasenanayake.quickclaim/"+R.drawable.hero_honda));
//        objectList.add(new ThumbModel("android.resource://com.example.deepthasenanayake.quickclaim/"+R.drawable.toyota_corolla));
//        objectList.add(new ThumbModel("android.resource://com.example.deepthasenanayake.quickclaim/"+R.drawable.toyota_crown));
//
//        return objectList;
//    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Uri getFileName() {
        return fileName;
    }

    public void setFileName(Uri fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        String encoded = null;
        try {
            photo = ImageLoader.init().from(fileName.getPath()).requestSize(1024, 1024).getBitmap();
            encoded = ImageBase64.encode(photo);
            Log.d("PHOTO",encoded);
        } catch (IOException e) {
            Log.e("BASE64", e.getMessage());
        }
        return encoded;
    }
}
