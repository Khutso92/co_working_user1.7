package com.example.khutsomatlala.hackaton_user11.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khutsomatlala.hackaton_user11.R;
import com.example.khutsomatlala.hackaton_user11.model_for_user_app.FriendlyMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {
        super(context, resource, objects);
    }
FirebaseAuth mAuth;

    Context context;
    CircleImageView  profilePicture;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        TextView timeTextView =   convertView.findViewById(R.id.tv_time);
        TextView messageTextView =   convertView.findViewById(R.id.messageTextView);

           profilePicture = convertView.findViewById(R.id.profilePicMessage);
        TextView authorTextView =   convertView.findViewById(R.id.nameTextView);


mAuth = FirebaseAuth.getInstance();


String user_uid = mAuth.getCurrentUser().getUid();
        FriendlyMessage message = getItem(position);




        messageTextView.setText(message.getText());
        authorTextView.setText(message.getName());
        timeTextView.setText( message.getMessageTime());

        DatabaseReference db;
          FirebaseDatabase mFirebaseDatabase;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //pic
        db = mFirebaseDatabase.getReference();

        db.child("profile").child(user_uid).child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               // Toast.makeText(ReviewActivity.this, ""+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();


                Log.v("Pic",dataSnapshot.getValue().toString() );

                if (dataSnapshot.hasChildren()) {

                    String image = dataSnapshot.child(dataSnapshot.getValue().toString()).getValue().toString();

                    Glide.with(context)
                            .load(image)
                            .centerCrop()
                            //.override(100, 100)
                            .into(profilePicture);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return convertView;
    }
}
