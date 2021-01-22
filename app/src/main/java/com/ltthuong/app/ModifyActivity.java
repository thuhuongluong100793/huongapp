package com.ltthuong.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ModifyActivity extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private Button btnDelete;
    private Button btnUpdate;
    private EditText txtModKey;
    private EditText txtModName;

    public ArrayList<Object> arrayList = new ArrayList<>();
    public ListView lstView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        //Khởi tạo biến
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        txtModKey = (EditText) findViewById(R.id.txtModKey);
        txtModName = (EditText) findViewById(R.id.txtModName);

        //Get du lieu tu putExtra ơ Main
        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("key")!= null)
        {
            txtModKey.setText(bundle.get("key").toString());
            txtModName.setText(bundle.get("name").toString());
        }
        //DB
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("object");
        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("HuongApp");
        // Alert confirm delete
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.setTitle("Delete entry");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        String key = txtModKey.getText().toString();
                        Delete(key);
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = txtModKey.getText().toString();
                String name = txtModName.getText().toString();
                Update(key, name);
            }
        });
    }
    private void Delete(String key) {
        mFirebaseDatabase.child(key).removeValue();
        Toast.makeText(this, "Deleting successfully!", Toast.LENGTH_SHORT).show();
        this.finish();
    }
    private void Update(String key, String name) {
        if (!name.isEmpty())
        {
            mFirebaseDatabase.child(key).child("name").setValue(name);
            Toast.makeText(this, "Updating successfully!", Toast.LENGTH_SHORT).show();
        }

        this.finish();
    }


    public void onClickBtnBack (View v) //Back to index of List
    {
        finish();
    }
}