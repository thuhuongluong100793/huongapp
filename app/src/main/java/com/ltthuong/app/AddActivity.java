package com.ltthuong.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddActivity extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String key;
    private Button btnAdd;
    private EditText txtName;
    private static final String TAG = AddActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //Khởi tạo biến
        btnAdd = (Button) findViewById(R.id.btnAdd);
        txtName = (EditText) findViewById(R.id.txtAddName);
        //DB
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("object");
        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("HuongApp");
        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");
                String appTitle = dataSnapshot.getValue(String.class);
                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });
        // Add new
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                add(name);
            }
        });
    }
    private void add(String name) {
        key = mFirebaseDatabase.push().getKey();
        Object object = new Object(name, key);

        mFirebaseDatabase.child(key).setValue(object);
        object.setKey(key);

        addChangeListener();
        this.finish();
        Toast.makeText(this, "Adding new object successfully!", Toast.LENGTH_SHORT).show();
    }

    public void addChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object object = dataSnapshot.getValue(Object.class);
                // Failed to read value
                Log.e(TAG, "Adding new object successfully!");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read object", error.toException());
            }
        });
    }
    public void onClickBtnBack (View v) //Back to main
    {
        finish();
    }
}