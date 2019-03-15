package com.automata.winbetadmin;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManagePosts
        extends Fragment {
    DatabaseReference dbRef;
    String item_key;
    LinearLayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    View v;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    @Nullable
    public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        this.v = paramLayoutInflater.inflate(R.layout.manage_posts, paramViewGroup, false);
        this.mRecyclerView = this.v.findViewById(R.id.recycler);
        this.mLayoutManager = new LinearLayoutManager(getContext());
        this.mLayoutManager.setOrientation(1);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        return this.v;
    }

    public void onStart() {
        super.onStart();
        this.dbRef = FirebaseDatabase.getInstance().getReference().child("winbet1");
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ItemViewHolder>(
                Model.class,
                R.layout.row,
                ItemViewHolder.class,
                dbRef
        ) {
            protected void populateViewHolder(ManagePosts.ItemViewHolder viewHolder, Model model, int position) {
                ManagePosts.this.item_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setBody(model.getBody());
                viewHolder.setId(ManagePosts.this.item_key);
            }
        };
        this.mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ItemViewHolder
            extends RecyclerView.ViewHolder {
        String item_key;
        DatabaseReference mRef;
        View mView;
        private ImageButton overflow;
        TextView txtId;

        public ItemViewHolder(View paramView) {
            super(paramView);
            this.mView = paramView;
            this.mRef = FirebaseDatabase.getInstance().getReference().child("winbet1");
            this.mRef.keepSynced(false);
            this.overflow = this.mView.findViewById(R.id.overflow);
            this.txtId = this.mView.findViewById(R.id.txtId);
            this.overflow.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem paramAnonymous2MenuItem) {
                            switch (paramAnonymous2MenuItem.getItemId()) {
                                case R.id.menu_edit:
                                    ManagePosts.ItemViewHolder.this.item_key = ManagePosts.ItemViewHolder.this.txtId.getText().toString();
                                    ManagePosts.ItemViewHolder.this.mView.getContext().startActivity(new Intent(ManagePosts.ItemViewHolder.this.mView.getContext(), EditPost.class).putExtra("item_id", ManagePosts.ItemViewHolder.this.item_key));
                                    break;
                                case R.id.menu_delete:
                                    ManagePosts.ItemViewHolder.this.item_key = ManagePosts.ItemViewHolder.this.txtId.getText().toString();
                                    ManagePosts.ItemViewHolder.this.mRef.child(ManagePosts.ItemViewHolder.this.item_key).removeValue();
                            }

                            return true;
                        }
                    });
                }
            });
        }


        public void setBody(String paramString) {
            ((TextView) this.mView.findViewById(R.id.post)).setText(paramString);
        }

        public void setId(String paramString) {
            ((TextView) this.mView.findViewById(R.id.txtId)).setText(paramString);
        }

        public void setTitle(String paramString) {
            ((TextView) this.mView.findViewById(R.id.postTitle)).setText(paramString.toUpperCase());
        }
    }

}
