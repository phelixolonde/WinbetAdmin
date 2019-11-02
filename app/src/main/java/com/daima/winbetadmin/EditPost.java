package com.daima.winbetadmin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditPost extends AppCompatActivity {
    EditText body;
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("wonbet");
    String item_key;
    EditText title;
    Button update;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_edit_post);
        this.title =  findViewById(R.id.title);
        this.body =  findViewById(R.id.body);
        this.update =  findViewById(R.id.button2);
        this.item_key = getIntent().getExtras().getString("item_id");
        this.dbRef.child(this.item_key).addValueEventListener(new ValueEventListener() {
            public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
            }

            public void onDataChange(DataSnapshot paramAnonymousDataSnapshot) {
                try {
                    EditPost.this.title.setText(paramAnonymousDataSnapshot.child("title").getValue().toString());
                    EditPost.this.body.setText(paramAnonymousDataSnapshot.child("body").getValue().toString());
                }catch (Exception e){
                    Toast.makeText(EditPost.this, "Something has changed, reload the app", Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                Map<String,Object> map = new HashMap<>();
                map.put("title", EditPost.this.title.getText().toString());
                map.put("body", EditPost.this.body.getText().toString());
                EditPost.this.dbRef.child(EditPost.this.item_key).updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(EditPost.this.getApplicationContext(), "Updated succesfully", Toast.LENGTH_LONG).show();
                    }

                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

