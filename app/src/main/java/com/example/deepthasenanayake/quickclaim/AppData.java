package com.example.deepthasenanayake.quickclaim;

import android.location.Location;

public class AppData {
    public static final String HOST_ADDRESS = "http://172.20.10.10:8080";
    //	 public static final String HOST_ADDRESS = "http://192.168.43.14:8080";
//	public static final String HOST_ADDRESS = "http://10.0.2.2:8080";
    public static Location GPS_LOCATION;
    public static boolean debug = false;

    public static final String CLAIM_URL = HOST_ADDRESS + "/QuickClaim/Client/MakeClaim";
}
