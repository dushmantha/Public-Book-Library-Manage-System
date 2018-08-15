package DataBase;

import java.io.Serializable;
import java.util.Date;

public class Cart implements Serializable {


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAdminAccept() {
        return isAdminAccept;
    }

    public void setAdminAccept(boolean adminAccept) {
        isAdminAccept = adminAccept;
    }

    public Cart(String id, Book book, User user, boolean isAdminAccept, Date orderDate, Date acceptDate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.isAdminAccept = isAdminAccept;
        this.orderDate = orderDate;
        this.acceptDate = acceptDate;
    }

    private String id;
    private Book book;
    private User user;
    private boolean isAdminAccept;
    private Date orderDate;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    private Date acceptDate;
    public Cart(){

    }


}
