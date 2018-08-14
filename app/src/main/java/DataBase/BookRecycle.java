package DataBase;

import android.graphics.Bitmap;

public class BookRecycle {

    private String id;
    private Book book;
    private Bitmap thumbnail;

    public BookRecycle(String id, Book book, Bitmap thumbnail) {
        this.id = id;
        this.book = book;
        this.thumbnail = thumbnail;
    }

    public BookRecycle() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}

