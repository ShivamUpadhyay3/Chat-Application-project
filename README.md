# Chat-Application-project

Java Client-Server Chat Application

This project implements a simple Client-Server chat application in Java using Swing for a GUI-based chat interface. The application allows for bi-directional communication between a server and a client, using sockets to establish a TCP connection over localhost.

Getting Started

Prerequisites

Ensure that you have:

Java Development Kit (JDK) installed (version 8 or higher).

An IDE or command-line environment to compile and run Java applications.


Running the Application

1. Start the Server:

Run the Server class to initiate the server. This will create a server socket listening on localhost:8080.

The server will display a waiting message until a client connects.



2. Start the Client:

Run the Client class to start a client instance.

The client will attempt to connect to the server at localhost:8080.




Both applications will open in a GUI window, allowing you to send and receive messages in real time.

Code Overview

Server

The Server class initializes a server socket and waits for a client connection. When connected:

It displays the received messages in the messageArea.

Listens for key events to send messages when the Enter key is pressed.

If the client sends "exit", the server will terminate the chat.


Client

The Client class establishes a connection to the server. Similar to the Server:

It shows messages in a read-only area (messageArea).

Allows sending messages via the Enter key in messageInput.

Terminates if the server sends "exit".


Key Features

GUI: Uses JFrame with JTextArea and JTextField for the chat interface.

Threading: Separate threads for reading and writing, ensuring the application remains responsive.

Socket Communication: TCP-based communication using Java Socket and ServerSocket classes.

Cross-Platform: Can run on any platform that supports Java.


Important Classes and Methods

ServerSocket and Socket: Used for establishing and managing the network connection.

BufferedReader and PrintWriter: Handle incoming and outgoing messages.

startReading(): A method in both Client and Server classes that runs in a separate thread to listen for incoming messages.

handleEvents(): Adds a key listener to detect the Enter key for sending messages.


Notes

Both the server and client should be run on the same machine for this demo. The IP (127.0.0.1) and port (8080) can be changed if needed.

To terminate the chat, either side can send "exit," and the connection will close.


Example Output

Server is ready to accept connection
Waiting...
Client connected.
Reader started...

Server: Hello!
Client: Hi there!
