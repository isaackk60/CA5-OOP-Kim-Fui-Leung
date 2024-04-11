package org.ca5.ServerAndClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.ca5.DAOs.MySqlBooks;
import org.ca5.DTOs.Book;
import org.ca5.Exceptions.DaoException;
import com.google.gson.Gson;
public class Server {

    final int SERVER_PORT_NUMBER = 8888; // could be any port from 1024 to 49151 (that doesn't clash with other Apps)

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
            System.out.println("Server has started.");
            int clientNumber = 0; // a number sequentially allocated to each new client (for identification
            // purposes here)

            while (true) {
                System.out.println("Server: Listening/waiting for connections on port ..." + SERVER_PORT_NUMBER);
                clientSocket = serverSocket.accept();
                clientNumber++;
                System.out.println("Server: Listening for connections on port ..." + SERVER_PORT_NUMBER);

                System.out.println("Server: Client " + clientNumber + " has connected.");
                System.out.println("Server: Port number of remote client: " + clientSocket.getPort());
                System.out.println(
                        "Server: Port number of the socket used to talk with client " + clientSocket.getLocalPort());

                // create a new ClientHandler for the requesting client, passing in the socket
                // and client number,
                // pass the handler into a new thread, and start the handler running in the
                // thread.
                Thread t = new Thread(new ClientHandler(clientSocket, clientNumber));
                t.start();

                System.out.println("Server: ClientHandler started in thread " + t.getName() + " for client "
                        + clientNumber + ". ");

            }
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {
                if (clientSocket != null)
                    clientSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }

        }
        System.out.println("Server: Server exiting, Goodbye!");
    }
}

class ClientHandler implements Runnable // each ClientHandler communicates with one Client
{
    BufferedReader socketReader;
    PrintWriter socketWriter;
    Socket clientSocket;
    final int clientNumber;

    // Constructor
    public ClientHandler(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket; // store socket for closing later
        this.clientNumber = clientNumber; // ID number that we are assigning to this client
        try {
            // assign to fields
            this.socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * run() method is called by the Thread it is assigned to.
     * This code runs independently of all other threads.
     */
    @Override
    public void run() {
        String request;
        try {
            while ((request = socketReader.readLine()) != null) {
                System.out
                        .println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request);

                /**
                 * Author: Aoife Murphy
                 * Other contributors: Kim Fui Leung, Jamie Duffy Creagh
                 * Date 10-04-24
                 */
                if (request.startsWith("Display all books")) {
                    MySqlBooks books = new MySqlBooks();
                    List<Book> allBooks = books.getAllBooks();
                    String jsonBooks = books.booksListToJson(allBooks);
                    socketWriter.println(jsonBooks);
                    System.out.println("Server message: All Books sent to client.");
                }  else if (request.startsWith("Display Book by ID")) {
                    int bookId = Integer.parseInt(request.split(":")[1].trim());
                    MySqlBooks books = new MySqlBooks();
                    Book book = books.getBookById(bookId);
                    if (book != null) {
                        Gson gson = new Gson();
                        String jsonBook = gson.toJson(book);
                        socketWriter.println(jsonBook);
                        System.out.println("Server message: Book sent to client.");
                    } else {
                        socketWriter.println("Book with ID " + bookId + " not found.");
                        System.out.println("Server message: Book with ID " + bookId + " not found.");
                    }
                }
//                else if(request.contains("NewBook")){
////                    socketWriter.println(request);
//                    JsonParser parser = new JsonParser();
//                    JsonObject jsonObject = parser.parse(request).getAsJsonObject();
//                    JsonObject jsonBook=jsonObject.get("NewBook").getAsJsonObject();
////                    socketWriter.println(jsonBook.get("publisher").toString());
                else if(request.startsWith("NewBook:")){
                    String book=request.substring(request.indexOf("{"));
                                        JsonParser parser = new JsonParser();
                    JsonObject jsonBook = parser.parse(book).getAsJsonObject();

                    Gson gson = new Gson();
                    Book newBook = gson.fromJson(jsonBook, Book.class);
                    MySqlBooks books = new MySqlBooks();
                    Book insertedBook = books.insertBook(newBook);

                    if (insertedBook != null) {
                        socketWriter.println("Book inserted successfully: " + insertedBook.toString());
                        System.out.println("Server message: Book inserted successfully.");
                    } else {
                        socketWriter.println("Failed to insert book.");
                        System.out.println("Server message: Failed to insert Book.");
                    }


                }
                else {
                    socketWriter.println("error I'm sorry I don't understand your request");
                    System.out.println("Server message: Invalid request from client.");
                }
            }
        } catch (IOException | DaoException ex) {
            ex.printStackTrace();
        } finally {
            this.socketWriter.close();
            try {
                this.socketReader.close();
                this.clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
    }
}

