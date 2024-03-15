package org.ca5.BusinessObjects;


import org.ca5.DAOs.MySqlBooks;

import org.ca5.DAOs.BookDaoInterface;
import org.ca5.DTOs.Book;
import org.ca5.Exceptions.DaoException;

import java.util.List;
import java.util.Scanner;

public class App {

    /**

     * Author:  Jamie Duffy Creagh
     * Other contributors:  Kim Fui Leung, Aoife Murphy
     * Date: 8-03-24

     */
    public static void main(String[] args) throws DaoException {

        BookDaoInterface IBookDao = new MySqlBooks();

        // Book book = IBookDao.getBookById(2);
        // System.out.println(book.toString());

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. View Database");
            System.out.println("2. Add to Database");
            System.out.println("3. Delete from Database");
            System.out.println("4. Get Book from Database Using ID");
            System.out.println("5. Update book from Database Using ID");
            System.out.println("6. Filter book from Database");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println(IBookDao.getAllBooks());
                    break;
                case 2:
                    IBookDao.insertBook(scanner);
                    break;
                case 3:
                    System.out.println("Enter the ID of the book to delete:");
                    int deleteId = scanner.nextInt();
                    IBookDao.deleteBookById(deleteId);
                    break;
                case 4:
                    System.out.println("Enter the ID of the book to display details:");
                    int bookId = scanner.nextInt();

                    Book book = IBookDao.getBookById(bookId);
                    if (book != null) {
                        System.out.println(book.toString());
                    } else {
                        System.out.println("No Book found with the id " + bookId);
                    }

                    break;
                case 5:
                    System.out.println("Enter the ID of the book to update details:");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    // Prompt user for updated book details
                    System.out.println("Enter new book title:");
                    String title = scanner.nextLine();

                    System.out.println("Enter new book genre:");
                    String genre = scanner.nextLine();

                    System.out.println("Enter new book author:");
                    String author = scanner.nextLine();

                    System.out.println("Enter new number of pages:");
                    int pages = scanner.nextInt();

                    System.out.println("Is the book part of a series? (true/false):");
                    boolean series = scanner.nextBoolean();

                    System.out.println("Enter new stock quantity:");
                    int stock = scanner.nextInt();

                    System.out.println("Enter new book rating:");
                    double rating = scanner.nextDouble();

                    scanner.nextLine();

                    System.out.println("Enter new book description:");
                    String description = scanner.nextLine();

                    System.out.println("Enter new book publisher:");
                    String publisher = scanner.nextLine();

                    try {
                        Book updatedBook = new Book(id, title, genre, author, pages, series, stock, rating, description,
                                publisher);
                        Book b = IBookDao.updateBook(id, updatedBook);
                        if (b != null) {
                            System.out.println("Book details updated successfully:");
                            System.out.println(b.toString());
                        } else {
                            System.out.println("No Book found with the id " + id);
                        }
                    } catch (DaoException e) {
                        System.out.println("Error updating book: " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("\nMenu:");
                    System.out.println("1. Filter by page over 400");
                    System.out.println("2. Filter by page below 400");
                    System.out.print("Enter your choice: ");
                    int filterChoice=scanner.nextInt();
                    switch (filterChoice){
                        case 1:
                            List<Book> booksOver400Pages = IBookDao.findBooksUsingFilter(new BookPageComparatorOver400());
                            if(!booksOver400Pages.isEmpty()){
                                for(Book bookFilter:booksOver400Pages){
                                    System.out.println(bookFilter.toString());
                                }
                            }else{
                                System.out.println("No book found with page over 400");
                            }
                            break;
                        case 2:
                            List<Book> booksUnder400Pages = IBookDao.findBooksUsingFilter(new BookPageComparatorUnder400());
                            if(!booksUnder400Pages.isEmpty()){
                                for(Book bookFilter:booksUnder400Pages){
                                    System.out.println(bookFilter.toString());
                                }
                            }else{
                                System.out.println("No book found with page below 400");
                            }
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                    }
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

    }

}
