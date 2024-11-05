import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Server extends JFrame {

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    // Declare components
    private JLabel heading = new JLabel("Server Area");
    private JTextArea messageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    private Font font = new Font("Roboto", Font.PLAIN, 20);

    // Constructor
    public Server() {
        try {
            server = new ServerSocket(8080); // Ensure the port matches the client's
            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting...");
            socket = server.accept(); // Wait for the client to connect

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            createGUI();
            handleEvents();
            startReading();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleEvents() {
        messageInput.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                // Empty implementation (optional)
            }

            public void keyPressed(KeyEvent e) {
                // Empty implementation (optional)
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { // Handle Enter key press
                    String contentToSend = messageInput.getText();
                    messageArea.append("Me: " + contentToSend + "\n");
                    out.println(contentToSend);
                    out.flush();
                    messageInput.setText(""); // Clear the input field after sending the message
                    messageInput.requestFocus(); // Refocus on the input field
                }
            }
        });
    }

    private void createGUI() {
        this.setTitle("Server Messenger [END]");
        this.setSize(600, 700);
        this.setLocationRelativeTo(null); // Center the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set fonts and styles
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);

        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        messageArea.setEditable(false); // Ensure the text area is not editable
        messageInput.setHorizontalAlignment(SwingConstants.CENTER);

        // Set the layout for the frame
        this.setLayout(new BorderLayout());

        // Add components to the frame
        this.add(heading, BorderLayout.NORTH);
        JScrollPane jScrollPane = new JScrollPane(messageArea); // Scroll for long chat history
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(messageInput, BorderLayout.SOUTH);

        this.setVisible(true); // Make the frame visible
    }

    public void startReading() {
        // Thread to read messages from the client
        Runnable r1 = () -> {
            System.out.println("Reader started...");

            while (true) {
                try {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat");
                        JOptionPane.showMessageDialog(this, "Client terminated the chat");
                        messageInput.setEnabled(false); // Disable input if the client ends the chat
                        break;
                    }

                    // Append the client's message to the chat area
                    messageArea.append("Client: " + msg + "\n");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(r1).start(); // Start the reading thread
    }

    public static void main(String[] args) {
        System.out.println("This is server... going to start server");
        new Server(); // Start the server
    }
}