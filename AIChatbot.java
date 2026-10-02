import javax.swing.*;
import java.awt.*;
public class AIChatbot extends JFrame {

    JTextArea chatArea;
    JTextField inputField;
    JButton sendButton;

    AIChatbot() {
        setTitle("AI Chatbot");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 16));

        // Input field
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));

        // Send button
        sendButton = new JButton("Send");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Welcome message
        chatArea.append("Bot: Hello! I am your AI Chatbot.\n");
        chatArea.append("Bot: How can I help you?\n\n");

        // Button action
        sendButton.addActionListener(e -> sendMessage());

        // Enter key action
        inputField.addActionListener(e -> sendMessage());

        setVisible(true);
    }

    void sendMessage() {

        String userMessage = inputField.getText().toLowerCase().trim();

        if (userMessage.isEmpty()) {
            return;
        }

        chatArea.append("You: " + userMessage + "\n");

        String response = getResponse(userMessage);

        chatArea.append("Bot: " + response + "\n\n");

        inputField.setText("");
    }

    String getResponse(String message) {

        // NLP-style keyword matching

        if (message.contains("hello") ||
            message.contains("hi") ||
            message.contains("hey")) {

            return "Hello! Nice to meet you.";
        }

        else if (message.contains("how are you")) {

            return "I am fine. Thank you for asking!";
        }

        else if (message.contains("name")) {

            return "My name is Java AI Chatbot.";
        }

        else if (message.contains("college")) {

            return "I can help you with basic college-related questions.";
        }

        else if (message.contains("java")) {

            return "Java is a popular object-oriented programming language.";
        }

        else if (message.contains("ai") ||
                 message.contains("artificial intelligence")) {

            return "AI means Artificial Intelligence. It enables computers to perform intelligent tasks.";
        }

        else if (message.contains("npl") ||
                 message.contains("nlp")) {

            return "NLP stands for Natural Language Processing. It helps computers understand human language.";
        }

        else if (message.contains("thank")) {

            return "You're welcome!";
        }

        else if (message.contains("bye")) {

            return "Goodbye! Have a nice day.";
        }

        else {
            return "Sorry, I don't understand that. Please ask another question.";
        }
    }

    public static void main(String[] args) {
        new AIChatbot();
    }
}