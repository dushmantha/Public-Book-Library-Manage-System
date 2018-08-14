package com.book.sl.thlibarary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import DataBase.Book;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookViewOrEditActivity extends AppCompatActivity {
    @BindView(R.id.txttitle) TextView tvtitle;
    @BindView(R.id.txtDesc) TextView tvdescription;
    @BindView(R.id.quantity) TextView tvquantity;
    @BindView(R.id.rackNo) TextView tvrackNumber;
    @BindView(R.id.bookthumbnail) ImageView img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_book_view_or_edit);
        ButterKnife.bind(this);

        // Recieve data
        Intent intent = getIntent();
        Book book= (Book) intent.getSerializableExtra("book");
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
    }
}
