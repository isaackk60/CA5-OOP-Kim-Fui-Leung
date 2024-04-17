package org.ca5.ServerAndClient;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonParser;
import org.ca5.DTOs.Book;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {

        try ( // create socket to connect to the server
              Socket socket = new Socket("localhost", 8888);
              // get the socket's input and output streams, and wrap them in writer and
              // readers
              PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
              BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
            System.out.println("Client message: The Client is running and has connected to the server");
            // ask user to enter a command
            Gson gson = new Gson();
            Scanner consoleInput = new Scanner(System.in);
//            System.out.println(
//                    "Valid commands are: 1.\"Display all books\" or 2.\"Insert books\" or \"echo <message>\" to get message echoed back, \"quit\"");


            while (true) {
                System.out.println("====================================================");
                System.out.println("                       Menu:                        ");
                System.out.println("====================================================");
                System.out.println("=            1. Display Book by ID                  =");
                System.out.println("=            2. Display all Books                   =");
                System.out.println("=            3. Insert a Book                       =");
                System.out.println("=            6. Exit                                =");
                System.out.println("====================================================");
                System.out.println("Please enter a command: ");
                String userRequest = consoleInput.nextLine();

                /**
                 * Main author: Jamie Duffy Creagh
                 * Date: 11-04-2024
                 **/
                if (userRequest.equals("1")) {
                    System.out.println("Enter Book ID:");
                    int bookId = consoleInput.nextInt();
                    consoleInput.nextLine();
                    out.println("Display Book by ID: " + bookId);

                    String responseBook = in.readLine();
                    if (!responseBook.equals("Book with ID " + bookId + " not found.")) {
                        responseBook = responseBook.substring(responseBook.indexOf("{"));
                        JsonParser parser = new JsonParser();
                        JsonObject jsonBook = parser.parse(responseBook).getAsJsonObject();
                        Book searchedBook = gson.fromJson(jsonBook, Book.class);
                        System.out.println("Client message: Response from server after displaying book: ");
                        System.out.println(searchedBook.toString());
                    } else {
                        System.out.println(responseBook);
                    }
                }

                /**
                 * Main author: Aoife Murphy
                 * Date: 10-04-2024
                 **/
                else if (userRequest.equals("2")) {
                    out.println("Display all books");

                    String books = in.readLine();
                    books = books.substring(books.indexOf("{"));
                    String[] jsonObjects = books.split("\\},\\s*\\{");


                    JsonParser parser = new JsonParser();
                    List<Book> bookList = new ArrayList<>();

                    for (String json : jsonObjects) {
                        json = json.replaceAll("[\\{\\}\\[\\]]", "");
                        json = "{" + json + "}";

                        JsonObject jsonObject = parser.parse(json).getAsJsonObject();

                        Book book = gson.fromJson(jsonObject, Book.class);

                        bookList.add(book);
                    }

                    System.out.println(
                            "Client message: Response from server after \"Display all books\" request: ");
                    for (Book book : bookList) {
                        System.out.println(book.toString());
                    }
                }
                /**
                 * Main author: Kim Fui Leung
                 * Date: 10-04-2024
                 **/
                else if (userRequest.equals("3")) {
                    System.out.println("Enter book Title:");
                    String newTitle = consoleInput.nextLine().trim();
                    while (newTitle.trim().isEmpty()) {
                        System.out.println("Enter book Title:");
                        newTitle = consoleInput.nextLine().trim();

                        if (newTitle.trim().isEmpty()) {
                            System.out.println("Invalid input please try again");
                        }
                    }


                    String newGenre = "";
                    while (newGenre.trim().isEmpty()) {
                        System.out.println("Enter book Genre:");
                        newGenre = consoleInput.nextLine().trim();

                        if (newGenre.trim().isEmpty()) {
                            System.out.println("Invalid input please try again");
                        }
                    }

                    String newAuthor = "";
                    while (newAuthor.trim().isEmpty()) {
                        System.out.println("Enter book Author:");
                        newAuthor = consoleInput.nextLine().trim();
                        if (newAuthor.trim().isEmpty()) {
                            System.out.println("Invalid input please try again");
                        }
                    }


                    int newPages = -1;
                    while (newPages < 0) {
                        System.out.println("Enter number of pages:");
                        if (consoleInput.hasNextInt()) {
                            newPages = consoleInput.nextInt();
                        } else {
                            System.out.println("Invalid input. Please enter a valid number.");
                            consoleInput.next(); // clear the invalid input
                        }
                    }


                    boolean newSeries = false;
                    System.out.println("Is the book part of a series? (true/false):");
                    while (!consoleInput.hasNextBoolean()) {
                        System.out.println("Invalid input. Please enter true or false.");
                        consoleInput.next(); // clear the invalid input
                    }
                    newSeries = consoleInput.nextBoolean();


                    int newStock = -1;
                    while (newStock < 0) {
                        System.out.println("Enter stock quantity:");
                        if (consoleInput.hasNextInt()) {
                            newStock = consoleInput.nextInt();
                        } else {
                            System.out.println("Invalid input. Please enter a valid number.");
                            consoleInput.next(); // clear the invalid input
                        }
                    }

                    double newRating = -1;
                    while (newRating < 0) {
                        System.out.println("Enter book rating:");
                        if (consoleInput.hasNextDouble()) {
                            newRating = consoleInput.nextDouble();
                        } else {
                            System.out.println("Invalid input. Please enter a valid number.");
                            consoleInput.next(); // clear the invalid input
                        }
                    }

                    consoleInput.nextLine();

                    String newDescription = "";
                    while (newDescription.trim().isEmpty()) {
                        System.out.println("Enter book description:");
                        newDescription = consoleInput.nextLine().trim();
                        if (newDescription.trim().isEmpty()) {
                            System.out.println("Invalid input please try again");
                        }
                    }

                    String newPublisher = "";
                    while (newPublisher.trim().isEmpty()) {
                        System.out.println("Enter book publisher:");
                        newPublisher = consoleInput.nextLine().trim();
                        if (newPublisher.trim().isEmpty()) {
                            System.out.println("Invalid input please try again");
                        }
                    }

                    Book newBook = new Book(newTitle, newGenre, newAuthor, newPages, newSeries, newStock, newRating, newDescription, newPublisher);
//                    JsonObject wrappedJson = new JsonObject();
//                    wrappedJson.add("NewBook", gson.toJsonTree(newBook));
////                    System.out.println(wrappedJson.toString());
//                    out.println(wrappedJson.toString());
//                    System.out.println("NewBook:"+gson.toJson(newBook));
                    out.println("Insert a New Book: " + gson.toJson(newBook));
                    String response = in.readLine();
                    response = response.substring(response.indexOf("{"));
                    JsonParser parser = new JsonParser();
                    JsonObject jsonBook = parser.parse(response).getAsJsonObject();
                    Book responseBook = gson.fromJson(jsonBook, Book.class);

                    System.out.println("Client message: Response from server after \"Insert a New book\" request: ");
                    System.out.println(responseBook.toString());
                }/**
                 * Main author: Kim Fui Leung
                 * Date: 17-04-2024
                 **/
                else if (userRequest.equals("6")) {
                    out.println("Exit");
                    String response = in.readLine();
                    System.out.println("Client message: Response from server: \"" + response + "\"");
                    break;
                } else {
                    System.out.println("Command unknown. Try again.");
                }

            }
        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
        // sockets and streams are closed automatically due to try-with-resources
        // so no finally block required here.


        System.out.println("Exiting client, but server may still be running.");
    }
}
