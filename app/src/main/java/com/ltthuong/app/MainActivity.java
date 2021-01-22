package com.ltthuong.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    public ListView lstView;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public ArrayList<Object> arrayList = new ArrayList<>();
    public ArrayList<String> arrayList_name = new ArrayList<>();
    public ArrayList<String> arrayList_key = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connect firebase database
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("object");
        lstView = (ListView) findViewById(R.id.lstView);
        CustomListViewAdapter arrayAdapter = new CustomListViewAdapter(getApplicationContext(), R.layout.one_inlist_layout, arrayList);
        lstView.setAdapter(arrayAdapter);
        //show database of table
        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Object value = dataSnapshot.getValue(Object.class);
                arrayList.add(value);

                arrayList_key.add(value.key);
                arrayList_name.add(value.name);
                arrayAdapter.notifyDataSetChanged();
                System.out.println("Add value is: " + value);
                //open form delete or update item  from listview
                lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                        intent.putExtra("key", arrayList_key.get(position));
                        intent.putExtra("name", arrayList_name.get(position));
                        startActivity(intent);

                    }
                });
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Object value = dataSnapshot.getValue(Object.class);
                int position_itr = 0;
                int position_itr_of_value = 0;

                Iterator<Object> itr = arrayList.iterator();
                Iterator<String> itr_key = arrayList_key.iterator();
                Iterator<String> itr_name = arrayList_name.iterator();
                // Bat su kien change: remove value de add lai tu dau voi thong tin moi
                //Remove value va get position
                while (itr.hasNext()) {
                    Object object = itr.next();
                    if (object.getKey().equals(value.getKey())) {
                        itr.remove();
                        position_itr_of_value = position_itr;
                    }
                    position_itr++;
                }
                // Add lai vao arraylist cua object
                arrayList.add(position_itr_of_value, value);
                // Remove va add lai o arraylist string cua key va name tuong ung cua object
                //bien position ben duoi = position_itr_of_value, xai cai nao cung duoc

                arrayList_key.remove(position_itr_of_value);
                arrayList_name.remove(position_itr_of_value);
                arrayList_key.add(position_itr_of_value,value.key);
                arrayList_name.add(position_itr_of_value,value.name);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue(Object.class);
                // Bat su kien remove: remove value hoan toan
                //Remove value
                Iterator<Object> itr = arrayList.iterator();
                Iterator<String> itr_key = arrayList_key.iterator();
                Iterator<String> itr_name = arrayList_name.iterator();
                while (itr.hasNext()) {
                    Object object = itr.next();
                    if (object.getKey().equals(value.getKey())) {
                        itr.remove();
                    }
                }
                // Remove o arraylist string cua key va name tuong ung cua object
                int position = arrayList_key.indexOf(value.key);
                arrayList_key.remove(position);
                arrayList_name.remove(position);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Di den giao dien add activity
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });

        //Tim kiem
        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        EditText txtSearch = (EditText) findViewById(R.id.txtSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = txtSearch.getText().toString();
                //connect firebase database
                mFirebaseInstance = FirebaseDatabase.getInstance();
                mFirebaseDatabase = mFirebaseInstance.getReference("object");
                Query query;
                if (!txt.equals(""))
                {
                    query = mFirebaseDatabase.orderByChild("name").startAt(txt).endAt(txt+"\uf8ff");
                }
                else query = mFirebaseDatabase;
                lstView = (ListView) findViewById(R.id.lstView);
                CustomListViewAdapter arrayAdapter = new CustomListViewAdapter(getApplicationContext(), R.layout.one_inlist_layout, arrayList);
                lstView.setAdapter(arrayAdapter);
                arrayList.clear();
                arrayList_key.clear();
                arrayList_name.clear();
                //show database of table
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        Object value = dataSnapshot.getValue(Object.class);
                        arrayList.add(value);
                        arrayList_key.add(value.key);
                        arrayList_name.add(value.name);
                        arrayAdapter.notifyDataSetChanged();
                        System.out.println("Add value is: " + value);
                        //open form delete or update item  from listview
                        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                                intent.putExtra("key", arrayList_key.get(position));
                                intent.putExtra("name", arrayList_name.get(position));
                                startActivity(intent);

                            }
                        });
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Object value = dataSnapshot.getValue(Object.class);
                        int position_itr = 0;
                        int position_itr_of_value = 0;

                        Iterator<Object> itr = arrayList.iterator();
                        Iterator<String> itr_key = arrayList_key.iterator();
                        Iterator<String> itr_name = arrayList_name.iterator();
                        // Bat su kien change: remove value de add lai tu dau voi thong tin moi
                        //Remove value va get position
                        while (itr.hasNext()) {
                            Object object = itr.next();
                            if (object.getKey().equals(value.getKey())) {
                                itr.remove();
                                position_itr_of_value = position_itr;
                            }
                            position_itr++;
                        }
                        // Add lai vao arraylist cua object
                        arrayList.add(position_itr_of_value, value);
                        // Remove va add lai o arraylist string cua key va name tuong ung cua object
                        //bien position ben duoi = position_itr_of_value, xai cai nao cung duoc

                        arrayList_key.remove(position_itr_of_value);
                        arrayList_name.remove(position_itr_of_value);
                        arrayList_key.add(position_itr_of_value,value.key);
                        arrayList_name.add(position_itr_of_value,value.name);
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Object value = dataSnapshot.getValue(Object.class);
                        // Bat su kien remove: remove value hoan toan
                        //Remove value
                        Iterator<Object> itr = arrayList.iterator();
                        Iterator<String> itr_key = arrayList_key.iterator();
                        Iterator<String> itr_name = arrayList_name.iterator();
                        while (itr.hasNext()) {
                            Object object = itr.next();
                            if (object.getKey().equals(value.getKey())) {
                                itr.remove();
                            }
                        }
                        // Remove o arraylist string cua key va name tuong ung cua object
                        int position = arrayList_key.indexOf(value.key);
                        arrayList_key.remove(position);
                        arrayList_name.remove(position);

                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        //Reload
        Button btnReload = (Button) findViewById(R.id.btnReload);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
    }

}