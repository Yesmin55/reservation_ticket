package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class principale extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawar_layout_principale ;
    private NavigationView navigation_view_principale;
    private ImageView menu_principal;
    private EditText namedevice ;
    private EditText  valuedevice ;
    private Button addDevice ;
    private DatabaseReference reference ;
    private FirebaseDatabase firebaseDatabase;
    private ListView listedevice ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);
        drawar_layout_principale=findViewById(R.id.drawar_layout_principale);
        navigation_view_principale=findViewById(R.id.navigation_view_principale);
        menu_principal=findViewById(R.id.menu_principal);
        namedevice=findViewById(R.id.namedevice);
        valuedevice=findViewById(R.id.valuedevice);
        addDevice=findViewById(R.id.addDevice);
        listedevice=findViewById(R.id.listedevice);

        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();

        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String devicename = namedevice.getText().toString();
                String devicevalue = valuedevice.getText().toString();

                if(devicename.isEmpty()){
                    namedevice.setError("name device should not be empty!");
                }else if (devicevalue.isEmpty()){
                    valuedevice.setError("Value device should not be empty !");
                }else {
                    addDeviceMethode(devicename,devicevalue);
                }
            }
        });

        ArrayList<String> deviceArrayList =new ArrayList<>();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(principale.this,R.layout.list_item,deviceArrayList);
        listedevice.setAdapter(adapter);
        DatabaseReference deviceReference = firebaseDatabase.getReference().child("devices");
   deviceReference.addValueEventListener(new valueEventListener(){


         public void onDataChange(DataSnapshot snapshot){
               deviceArrayList.clear();
                 for(DataSnapshot devicesnapshot : snapshot.getChildren()){
                     Device device=deviceSnapshot.getValue(Device.class);
                     deviceArrayList.add(device.getName()+":"+device.getValue());
                 }
                 adapter.notifyDataSetChanged();
         }
         public void onCancelled (DatabaseError error){
             Toast.makeText(principale.this, "Error!!!!!!!!!!", Toast.LENGTH_SHORT).show();
         }
   });



        navigationDrawer();

        navigation_view_principale.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.addDevice:
                        drawar_layout_principale.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ticket:
                        startActivity(new Intent(principale.this,ticket_electrique.class));
                        drawar_layout_principale.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
    }

    private void addDeviceMethode(String devicename, String devicevalue) {
        HashMap<String,String> deviceMap = new HashMap<>();
        deviceMap.put("name",namedevice);
        deviceMap.put("value",valuedevice);
        reference.child("Devices").push().setValue(deviceMap);
        namedevice.setText();
        valuedevice.setText();
        namedevice.clearFocus();
        valuedevice.clearFocus();
        Toast.makeText(this, "new device added successfuly", Toast.LENGTH_SHORT).show();


    }

    private void navigationDrawer() {
        navigation_view_principale.bringToFront();
        navigation_view_principale.setNavigationItemSelectedListener(this);
        navigation_view_principale.setCheckedItem(R.id.addDevice);

        menu_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawar_layout_principale.isDrawerVisible(GravityCompat.START)){
                    drawar_layout_principale.closeDrawer(GravityCompat.START);
                }else{
                    drawar_layout_principale.openDrawer(GravityCompat.START);
                }
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawar_layout_principale.isDrawerVisible(GravityCompat.START)){
            drawar_layout_principale.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }
}