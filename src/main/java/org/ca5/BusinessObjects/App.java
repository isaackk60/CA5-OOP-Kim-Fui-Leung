package org.ca5.BusinessObjects;
/**

 * Author:  Aoife Murphy
 * Other contributors:  Kim Fui Leung, Jamie Duffy Creagh
 * Date: 9-03-24

 */

import org.ca5.DAOs.MySqlBooks;

import org.ca5.DAOs.BookDaoInterface;
import org.ca5.DTOs.Book;
import org.ca5.Exceptions.DaoException;

import java.util.Scanner;

public class App {

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
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

    }

}
