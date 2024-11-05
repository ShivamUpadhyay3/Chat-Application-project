import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client extends JFrame {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    // Declare components
    private JLabel heading = new JLabel("Client Area");
    private JTextArea messageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    private Font font = new Font("Roboto", Font.PLAIN, 20);

    public Client() {
        try {
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1", 8080); // Ensure the server is running on this port
            System.out.println("Connection done.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            createGUI();
            handleEvents();
            startReading();
            // startWriting(); // Optional if you want to enable console-based input

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
        this.setTitle("Client Messenger [END]");
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
        // Thread to read messages from the server
        Runnable r1 = () -> {
            System.out.println("Reader started...");

            while (true) {
                try {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server terminated the chat");
                        JOptionPane.showMessageDialog(this, "Server terminated the chat");
                        messageInput.setEnabled(false); // Disable input if the server ends the chat
                        break;
                    }

                    // Append the server's message to the chat area
                    messageArea.append("Server: " + msg + "\n");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(r1).start(); // Start the reading thread
    }

    public void startWriting() {
        // Optional: Thread to write messages via console input
        Runnable r2 = () -> {
            System.out.println("Writer started...");
            while (true) {
                try {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println("Client: " + content);
                    out.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(r2).start(); // Start the writing thread
    }

    public static void main(String[] args) {
        System.out.println("This is client...");
        new Client(); // Start the client
    }
}