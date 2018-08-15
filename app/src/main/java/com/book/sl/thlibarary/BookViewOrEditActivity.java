package com.book.sl.thlibarary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import DataBase.Book;
import DataBase.Cart;
import DataBase.User;
import LocalDataSave.LocalDataManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

public class BookViewOrEditActivity extends AppCompatActivity {
    @BindView(R.id.txttitle) TextView tvtitle;
    @BindView(R.id.txtDesc) TextView tvdescription;
    @BindView(R.id.quantity) TextView tvquantity;
    @BindView(R.id.rackNo) TextView tvrackNumber;
    @BindView(R.id.bookthumbnail) ImageView img;
     @BindView(R.id.edit_or_add)
     FancyButton addOrEdit;
    User user;
    Book book;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_book_view_or_edit);
        ButterKnife.bind(this);
        LocalDataManager localDataManager = new LocalDataManager();
        user = localDataManager.getUserObject(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("cart");
        if (user == null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        // Recieve data
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");
        String description = book.getDescription();
        String title = book.getTitle();
        String quantity = book.getQuantity();
        String rackNo = book.getRackNumber();
        String image = book.getThumbnail() ;

        tvtitle.setText("Book Title :" +title);
        tvdescription.setText("Book Description :" +description);
        tvquantity.setText("Book Quantity available :" + quantity);
        tvrackNumber.setText("Book Rack Number :" + rackNo);
        Glide.with(this).load(image).
                into(img);

        if(user != null){
            if (user.isAdministrator()){
                addOrEdit.setText("EDIT");
            } else if(!user.isAdministrator() && !book.getQuantity().equals("0")) {
                addOrEdit.setText("RESERVE");
            } else {
                addOrEdit.setText("CAN'T RESERVE BOOK. PLEASE GET HELP FROM ADMINISTOR");
            }
        }
    }

    @OnClick(R.id.edit_or_add)
    public void bookAdd(View view) {
        if(user.isAdministrator()){

        } else {
           if(book.getQuantity().equals("0")){
               Toast.makeText(this, "CAN'T RESERVE BOOK. PLEASE GET HELP FROM ADMINISTOR", Toast.LENGTH_LONG).show();
           } else {
               String id = databaseReference.push().getKey();
               Date date = Calendar.getInstance().getTime();
//               SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//               String formattedDate = df.format(date);
               Cart cart = new Cart(id,  book,  user, false, date, null);
               databaseReference.child(id).setValue(cart);
               Toast.makeText(this, "Book has being reserved", Toast.LENGTH_LONG).show();
               Intent intent = new Intent(this, HomeActivity.class);
               startActivity(intent);
           }
        }
    }

    @OnClick(R.id.cancel_button)
    public void cancel(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
