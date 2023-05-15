package com.example.hotelbooking.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.hotelbooking.Activities.ChangeProfileActivity;
import com.example.hotelbooking.Activities.LoginActivity;
import com.example.hotelbooking.Model.User;
import com.example.hotelbooking.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class FragmentAccount extends Fragment {
    private Button bt_changeprofile, bt_logout;
    private TextView tv_name;
    private DatabaseReference databaseReference;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        bt_changeprofile=view.findViewById(R.id.bt_changeprofile);
        bt_logout=view.findViewById(R.id.bt_logout);

        //bat su kien cho nut Chinh Sua Ho So
        bt_changeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                        .setContentHolder(new ViewHolder(R.layout.profile))
                        .setExpanded(true, 1200)
                        .create();

                View view = dialogPlus.getHolderView();
                EditText edt_ten = view.findViewById(R.id.edt_ten);
                EditText edt_sdt = view.findViewById(R.id.edt_sdt);
                Button bt_submit = view.findViewById(R.id.bt_submit);
                Button bt_cancle = view.findViewById(R.id.bt_cancle);

                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(userID);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            String ten = snapshot.child("ten").getValue(String.class);
                            String sdt = snapshot.child("sdt").getValue(String.class);
                            edt_ten.setText(ten);
                            edt_sdt.setText(sdt);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                dialogPlus.show();
                //bat su kien cho nut Luu thay doi
                bt_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("ten",edt_ten.getText().toString());
                        map.put("sdt",edt_sdt.getText().toString());

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(userID);
                        databaseReference.updateChildren(map);
                        dialogPlus.dismiss();
                    }
                });
                //bat su kien huy trong DialogPlus
                bt_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogPlus.dismiss();
                    }
                });
            }
        });
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //hiển thị tên người dùng đang đăng nhập
        tv_name = view.findViewById(R.id.tv_name);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userId);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userName = snapshot.child("ten").getValue(String.class);
                    tv_name.setText(userName);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return view;
    }


}
