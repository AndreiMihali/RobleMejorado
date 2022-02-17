package com.example.roblemejorado.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roblemejorado.R;
import com.example.roblemejorado.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ViewHolder> {
    private Context context;
    private ArrayList<Chat> data;
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    private FirebaseUser firebaseUser;

    public AdapterChat(Context context,ArrayList<Chat> data){
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType==MSG_TYPE_LEFT){
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
        } else{
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.txt_message);
        }

        void bind(Chat chat){
            message.setText(chat.getTextMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        if(data.get(position).getSender().equals(firebaseUser.getUid())){
            if(position == (data.size()-1)){
                setMessage(firebaseUser.getEmail(),data.get(position).getTextMessage());
                setMessage("usuario.prueba@educa.madrid.org",data.get(position).getTextMessage());
            }
            return MSG_TYPE_RIGHT;
        }else{
            if(position == (data.size()-1)){
                setMessage("usuario.prueba@educa.madrid.org",data.get(position).getTextMessage());
                setMessage(firebaseUser.getEmail(),data.get(position).getTextMessage());
            }
            return MSG_TYPE_LEFT;
        }
    }

    private void setMessage(String user,String message){
        HashMap<String,Object> map=new HashMap<>();
        map.put("mensaje",message);
        FirebaseFirestore.getInstance().collection("users").document(user).update(map);
    }
}
