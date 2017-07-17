package com.example.deepthasenanayake.quickclaim;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.EachExceptionsHandler;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ClaimActivity extends AppCompatActivity {

    private int SEL_VEHICLE = 0;
    TextView txtVehicle, txtSelect;
    EditText txtDesc;
    Button btnAttach, btnClaim;
    private RecyclerView thumbList;

    GPSTracker tracker;
    private Uri filePath;
    private int CAPTURE_IMAGE_REQ = 5000;
    private List<ThumbModel> photoList;
    private TrackGPS gps;
    private String[] vehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_claim);

        vehicles = new String[3];

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);

        photoList = new ArrayList<>();

        View.OnClickListener vListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVehicleDialog().show();
            }
        };

        btnAttach = (Button) findViewById(R.id.btnAttach);
        btnClaim = (Button) findViewById(R.id.btnClaim);
        txtDesc = (EditText) findViewById(R.id.txt_claim_desc);

        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoList.size() < 5) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    filePath = getGeneratedPath();

//                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, filePath);

                    startActivityForResult(intent, CAPTURE_IMAGE_REQ);
                } else {
                    Toast.makeText(ClaimActivity.this, "Cannot Add more Photos.!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFields()) {
                    return;
                }
                //Get Location
                gps = new TrackGPS(ClaimActivity.this);


                if (gps.canGetLocation()) {

                    double longitude = gps.getLongitude();
                    double latitude = gps.getLatitude();

                    String vehicleId = vehicles[SEL_VEHICLE].split("_")[0];
                    String description = txtDesc.getText().toString();
                    Object[] photos = photoList.toArray();

                    HashMap<String, String> postData = new HashMap<String, String>();

                    postData.put("vehicle", vehicleId);
                    postData.put("description", description);
                    postData.put("longitude", String.valueOf(longitude));
                    postData.put("latitude", String.valueOf(latitude));

                    for (int i = 0; i < photos.length; i++) {
                        postData.put("photo" + (1 + i), photos[i].toString());
                    }

                    PostResponseAsyncTask task = new PostResponseAsyncTask(ClaimActivity.this, postData, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if (s.equalsIgnoreCase("OK")) {
                                Toast.makeText(ClaimActivity.this, "Claim Success!", Toast.LENGTH_LONG).show();
                                gps.stopUsingGPS();
                                finish();
                            } else {
                                Toast.makeText(ClaimActivity.this, "Not Ok!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    task.execute(AppData.CLAIM_URL);
                    task.setEachExceptionsHandler(new EachExceptionsHandler() {
                        @Override
                        public void handleIOException(IOException e) {
                            Toast.makeText(ClaimActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleMalformedURLException(MalformedURLException e) {
                            Toast.makeText(ClaimActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleProtocolException(ProtocolException e) {
                            Toast.makeText(ClaimActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                            Toast.makeText(ClaimActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    gps.showSettingsAlert();
                }

            }
        });

        txtVehicle = (TextView) findViewById(R.id.txtVehicle);
        txtSelect = (TextView) findViewById(R.id.txtSelect);
        txtVehicle.setOnClickListener(vListener);
        txtSelect.setOnClickListener(vListener);


//        selectVehicleDialog().show();

        thumbList = (RecyclerView) findViewById(R.id.thumb_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        thumbList.setLayoutManager(gridLayoutManager);

        selectVehicleDialog().show();
    }

    private boolean validateFields() {
        if (photoList.size() <= 0) {
            Snackbar snackbar = Snackbar
                    .make((CoordinatorLayout) findViewById(R.id.activity_claim), "Please Attach some photos!", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        if (txtDesc.getText().equals("")) {
            Snackbar snackbar = Snackbar
                    .make((CoordinatorLayout) findViewById(R.id.activity_claim), "Provide a Description!", Snackbar.LENGTH_LONG);
            snackbar.show();
            return false;
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_REQ) {
            if (resultCode == RESULT_OK) {
                ThumbModel model = new ThumbModel(filePath);
                photoList.add(model);
                ThumbInfoAdapter adapter = new ThumbInfoAdapter(ClaimActivity.this, photoList);
                thumbList.setAdapter(adapter);
            } else if (resultCode == RESULT_CANCELED) {
//				filePath = null;
            }
        }
    }

    private Uri getGeneratedPath() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                "QuickClaim/ClaimPhotos");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("ERRRRRRRR!", "Oops! Failed to create directory");
                return null;
            }
        }

        File file = new File(mediaStorageDir.getPath()
                + File.separator
                + new SimpleDateFormat("yyyy-MM-dd hh_mm_ss",
                Locale.getDefault()).format(new Date()) + "_IMG.jpg");
        Log.d("PATH", file.getPath());
        return Uri.fromFile(file);
    }


    private Dialog selectVehicleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select_a_vehicle);

        vehicles[0] = "CAA-1584_Toyota Crown Athlete 2013";
        vehicles[1] = "18-7320_Toyota Corolla EE100 1994";
        vehicles[2] = "TE-2512_Hero Honda CD-DAWN 2005";

        builder.setSingleChoiceItems(vehicles, SEL_VEHICLE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SEL_VEHICLE = which;
                txtVehicle.setText(vehicles[SEL_VEHICLE]);
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showProgress(Integer value) {
        Toast.makeText(this, value + "% Completed.", Toast.LENGTH_SHORT).show();
    }

    public void setEnableButtons(boolean b) {
        btnClaim.setEnabled(b);
        btnAttach.setEnabled(b);
    }

    public void parseRespond(String result) {
        if (result != null) {
            Log.d("RESPOND", result);
            if (result.equalsIgnoreCase("OK")) {
                Toast.makeText(getApplicationContext(),
                        "Claim reported successfully !", Toast.LENGTH_LONG)
                        .show();
//                setResult(DONE);
                finish();
            } else if (result.equalsIgnoreCase("BAD_GPS")) {
//                txtStatus
//                        .setText("Cannot get you location.\nPlease try again !.");
                setEnableButtons(true);
            } else if (result.equalsIgnoreCase("NOT_RESPOND")) {
//                txtStatus
//                        .setText("Not responding from the server !\nPlease try again later.");
                setEnableButtons(true);
            } else if (result.equalsIgnoreCase("ERROR")) {
//                txtStatus.setText("Something went wrong !");
                setEnableButtons(true);
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Something Went Wrong !..\nPlease try again later !",
                    Toast.LENGTH_SHORT).show();
            setEnableButtons(true);
        }
    }
}
