package DataBase;

import java.io.Serializable;

public class Book  implements Serializable{
    private String id;
    private String title;
    private String author;
    private String description;
    private String quantity;
    private String rackNumber;
    private String coverImageUrl;
    private String thumbnail;


    public Book(String id, String title, String description, String author, String quantity, String rackNumber, String coverImageUrl, String thumbnail) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.quantity = quantity;
        this.rackNumber = rackNumber;
        this.coverImageUrl = coverImageUrl;
        this.thumbnail = thumbnail;

    }

    public Book(){

    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getRackNumber() {
        return rackNumber;
    }

    public void setRackNumber(String rackNumber) {
        this.rackNumber = rackNumber;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


