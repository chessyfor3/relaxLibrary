
package library.model;



public class Book {
    
    private String ISBN;
    private String title;
    private String author;
    private String publisher;
    private int quantity;
    private int availableCount;

    public Book(String ISBN, String bookTitle, String author, String publisher, int quantity) {
        this.ISBN = ISBN;
        this.title = bookTitle;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
        this.availableCount = quantity;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }
    
    
    
}
