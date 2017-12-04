package com.example.khutsomatlala.hackaton_user11.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.khutsomatlala.hackaton_user11.model.FriendlyMessage;
import com.example.khutsomatlala.hackaton_user11.R;

import java.util.List;



public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        TextView timeTextView =   convertView.findViewById(R.id.tv_time);
        TextView messageTextView =   convertView.findViewById(R.id.messageTextView);
        TextView authorTextView =   convertView.findViewById(R.id.nameTextView);

        FriendlyMessage message = getItem(position);




        messageTextView.setText(message.getText());
        authorTextView.setText(message.getName());
        timeTextView.setText( message.getMessageTime());


        return convertView;
    }
}