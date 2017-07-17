package com.example.deepthasenanayake.quickclaim;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    FloatingActionButton fabClaim,fabLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        fabLogout = (FloatingActionButton) findViewById(R.id.fabLogout);
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        fabClaim = (FloatingActionButton) findViewById(R.id.fabClaim);
        fabClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ClaimActivity.class);
                startActivity(intent);
            }
        });

        navigationView = (BottomNavigationView) findViewById(R.id.navBottom);
        if (navigationView!=null) {
            navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    selectFragment(item);
                    return false;
                }
            });
        }

        navigationView.setSelectedItemId(R.id.menu_history);
    }

    private void selectFragment(MenuItem item) {
        item.setChecked(true);
        selectFab(item.getItemId());
        switch (item.getItemId()){
            case R.id.menu_history:
                setTitle("Claim History");
                pushFragment(new HistoryFragment());
                break;
            case R.id.menu_vehicles:
                setTitle("Vehicle Info");
                pushFragment(new VehicleFragment());
                break;
            case R.id.menu_user:
                setTitle("My Info");
                pushFragment(new UserFragment());
                break;
        }
    }

    private void selectFab(int id){
        fabClaim.setVisibility(View.GONE);
        fabLogout.setVisibility(View.GONE);
        if (id==R.id.menu_history)
            fabClaim.setVisibility(View.VISIBLE);
        else if(id==R.id.menu_user)
            fabLogout.setVisibility(View.VISIBLE);
    }

    public void pushFragment(android.app.Fragment fragment){
        if (fragment==null)
            return;
        android.app.FragmentManager manager = getFragmentManager();
        if (manager!=null){
            android.app.FragmentTransaction transaction = manager.beginTransaction();
            if (transaction!= null){
                transaction.replace(R.id.rootLayout,fragment);
                transaction.commit();
            }
        }
    }
}
