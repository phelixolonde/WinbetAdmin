package com.phelix.winbetadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;

public class NewPost
        extends Fragment {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Switch aSwitch;
    Button add,attach;
    EditText body;
    DatabaseReference dbRef;
    ProgressDialog pd;
    EditText title;
    private static final int GALLERY_REQUEST = 2;
    StorageReference filepath = null;
    Uri resultUri;
    Uri imageUri;
    TextView txtFilePath;
    private StorageReference mStorage;
    EditText txtLink;

    View v;
    @Nullable
    public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        this.v = paramLayoutInflater.inflate(R.layout.new_post, paramViewGroup, false);
        this.title = this.v.findViewById(R.id.txtTitle);
        this.body = this.v.findViewById(R.id.txtBody);
        this.add = this.v.findViewById(R.id.btnAdd);
        txtLink = v.findViewById(R.id.txtLink);
        this.aSwitch = this.v.findViewById(R.id.aSwitch);
        this.pd = new ProgressDialog(getActivity());
        attach=v.findViewById(R.id.attach);
        txtFilePath=v.findViewById(R.id.filepath);
        mStorage = FirebaseStorage.getInstance().getReference();

        this.add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (NewPost.this.aSwitch.isChecked()) {
                    NewPost.this.postTip("winbettest");
                    return;
                }
                NewPost.this.postTip("winbet3");
            }
        });


        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), GALLERY_REQUEST);
            }
        });
        return this.v;
    }


    private void postTip(String paramString) {
        this.pd.setMessage("Please Wait");
        this.pd.show();
        String str = Long.valueOf(1L - System.currentTimeMillis() / 1000L).toString();
        this.dbRef = FirebaseDatabase.getInstance().getReference().child(paramString).child(str);
        this.dbRef.child("title").setValue(this.title.getText().toString());
        this.dbRef.child("body").setValue(this.body.getText().toString());
        this.dbRef.child("id").setValue(str);
        if (txtLink.getText() != null && !txtLink.getText().toString().equals("")) {
            dbRef.child("link").setValue(txtLink.getText().toString());
        }
        this.dbRef.child("time").setValue(System.currentTimeMillis()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(NewPost.this.getContext(), "Tip added succesfully", Toast.LENGTH_LONG).show();
                if (!NewPost.this.aSwitch.isChecked()) {
                    NewPost.this.sendNotification(NewPost.this.title.getText().toString(), NewPost.this.body.getText().toString());
                }
                NewPost.this.pd.dismiss();
            }
        });
        if (imageUri != null) {

            filepath = mStorage.child("Items_Images").child(imageUri.getLastPathSegment());
        }
        if (filepath != null) {

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    assert downloadUrl != null;
                    dbRef.child("image").setValue(downloadUrl.toString());
                   // pd.dismiss();

                }
            });
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {


            if (requestCode == GALLERY_REQUEST) {
                imageUri = data.getData();
                txtFilePath.setText(imageUri.getLastPathSegment());


            }

        }
    }

    private void sendNotification(final String paramString1 ,final String paramString2) {

        new AsyncTask<Void, Void, Void>() {

            protected Void doInBackground(Void... params) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json = new JSONObject();
                    JSONObject dataJSON = new JSONObject();
                    dataJSON.put("body", paramString2);
                    dataJSON.put("title", paramString1);
                    json.put("notification", dataJSON);
                    json.put("to", "/topics/winbet3");
                    RequestBody body = RequestBody.create(NewPost.JSON, json.toString());
                    Request request = new Request.Builder()
                            .header("Authorization", "key=AIzaSyBDWCIZ23nOtCXNXHjm7PbnK-7T3nJIquk")
                            .url("https://fcm.googleapis.com/fcm/send").post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                } catch (Exception paramAnonymousVarArgs) {

                }
                return null;
            }
        }.execute();
    }

}