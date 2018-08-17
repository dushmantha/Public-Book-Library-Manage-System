package com.book.sl.thlibarary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import DataBase.Cart;
import DataBase.User;
import LocalDataSave.LocalDataManager;
import RecycleView.ListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListShowActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
     ArrayList<Cart> carts;
    @BindView(R.id.lvBook)
    ListView lvBook;
    User user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        ButterKnife.bind(this);


    }


    @Override
    protected void onStart() {
        super.onStart();
        LocalDataManager localDataManager = new LocalDataManager();
        user = localDataManager.getUserObject(this);
        carts = new ArrayList<Cart>();
        if(user != null){
            databaseReference = FirebaseDatabase.getInstance().getReference("cart");
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    final Cart cart = userSnapshot.getValue(Cart.class);
                    if (cart.getUser().getId().equals(user.getId())) {
                        carts.add(cart);
                    }
                }
                setValueToRecyclerView();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setValueToRecyclerView(){
        ListAdapter listAdapter = new ListAdapter(ListShowActivity.this,carts);
        lvBook.setAdapter(listAdapter);
    }
}
