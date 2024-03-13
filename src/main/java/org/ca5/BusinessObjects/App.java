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
     * Date: 9-03-24

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
                case 6:
                    System.out.println("\nMenu:");
                    System.out.println("1. Filter by page over 400");
                    System.out.println("2. Filter by page below 400");
                    System.out.print("Enter your choice: ");
                    int filterChoice=scanner.nextInt();
                    switch (filterChoice){
                        case 1:
                        case 2:
                            List<Book> bookFilters=IBookDao.findBooksUsingFilter(filterChoice);
                            if(!bookFilters.isEmpty()){
                                for(Book bookFilter:bookFilters){
                                    System.out.println(bookFilter.toString());
                                }
                            }else{
                                if(filterChoice==1){
                                System.out.println("No book found with page over 400");}
                                else{
                                    System.out.println("No book found with page below 400");
                                }
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
