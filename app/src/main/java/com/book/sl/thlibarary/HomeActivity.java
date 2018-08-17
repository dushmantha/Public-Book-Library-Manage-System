package com.book.sl.thlibarary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import DataBase.Book;
import LocalDataSave.LocalDataManager;
import RecycleView.RecyclerViewAdapter;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    RecyclerViewAdapter myAdapter;
    List<Book> lstBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.setTitle("BOOK STORE");
        databaseReference = FirebaseDatabase.getInstance().getReference("book");
        storageReference = FirebaseStorage.getInstance().getReference();
        lstBook = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstBook.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    final Book book = userSnapshot.getValue(Book.class);
                    storageReference.child("images/" + book.getCoverImageUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String filePath = uri.toString();
                            book.setThumbnail(filePath);
                            setValueToRecyclerView();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                    lstBook.add(book);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        //myAdapter.filter(text);
        return false;
    }

    private void setValueToRecyclerView(){
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        myAdapter = new RecyclerViewAdapter(this,lstBook);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu); //your file name
        LocalDataManager localDataManager = new LocalDataManager();
        if (localDataManager.getUserObject(this) != null){
            if (localDataManager.getUserObject(this).isAdministrator()){
                menu.findItem(R.id.menu_list).setTitle("SHOW NOTIFICATION");
            }else {
                menu.findItem(R.id.menu_list).setTitle("MY HISTORY");
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.menu_list){

            Intent intent = new Intent(this, ListShowActivity.class);
            intent.putExtra("books", (Serializable) lstBook);
            startActivity(intent);
        }

        if (item.getItemId()==R.id.menu_search){
            LocalDataManager localDataManager = new LocalDataManager();
            if (localDataManager.getUserObject(this) == null){
                Toast.makeText(this, "Please login account", Toast.LENGTH_SHORT).show();
            } else {
                if (localDataManager.getUserObject(this).isAdministrator()){

                }else {
                    Intent intent = new Intent(this, BookSearchActivity.class);
                    intent.putExtra("books", (Serializable) lstBook);
                    startActivity(intent);
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
