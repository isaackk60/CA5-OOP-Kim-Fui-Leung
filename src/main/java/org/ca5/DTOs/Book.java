package org.ca5.DTOs;

public class Book {
    private int id;
    private String title;
    private String genre;
    private String author;
    private int pages;
    private boolean series;
    private int stock;
    private double rating;
    private String description;
    private String publisher;

    public Book(int id, String title, String genre, String author, int pages, boolean series, int stock,
                double rating, String description, String publisher) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.pages = pages;
        this.series = series;
        this.stock = stock;
        this.rating = rating;
        this.description = description;
        this.publisher = publisher;
    }

    public Book(String title, String genre, String author, int pages, boolean series, int stock,
                double rating, String description, String publisher) {
        this.id = 0;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.pages = pages;
        this.series = series;
        this.stock = stock;
        this.rating = rating;
        this.description = description;
        this.publisher = publisher;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isSeries() {
        return series;
    }

    public void setSeries(boolean series) {
        this.series = series;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", series=" + series +
                ", stock=" + stock +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", publisher='" + publisher + '\'' +
                "}";
    }
}

