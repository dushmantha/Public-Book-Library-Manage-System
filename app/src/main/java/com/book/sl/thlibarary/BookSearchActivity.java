package com.book.sl.thlibarary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import DataBase.Book;
import RecycleView.SearchAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookSearchActivity  extends AppCompatActivity {
    @BindView(R.id.lvProducts) ListView lvProducts;
    @BindView(R.id.etSearch)  EditText etSearch;

    private ArrayList<Book> bookArrayList = new ArrayList<Book>();
    private SearchAdapter searchAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_serchlist);
        ButterKnife.bind(this);



        // Add Text Change Listener to EditText
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                searchAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        Intent intent = getIntent();
        bookArrayList = (ArrayList<Book>) intent
                .getSerializableExtra("books");
        searchAdapter = new SearchAdapter(BookSearchActivity.this, bookArrayList);
        lvProducts.setAdapter(searchAdapter);

    }
}
