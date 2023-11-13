import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Import the File class
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

class Library_admin extends JFrame implements ActionListener {
    private JLabel label1, label2, label3, label4, label5, label6, label7;
    private JTextField textField1, textField2, textField3, textField4, textField5, textField6, textField7;
    private JButton addButton, viewButton, editButton, deleteButton, clearButton, exitButton;
    private JPanel panel;
    private ArrayList<String[]> books = new ArrayList<String[]>();
    private String file_name;

    private void readFileUpdateArray() {
        String line = "";
        String splitBy = ",";
        try {
            try (// parsing a CSV file into BufferedReader class constructor
                    BufferedReader br = new BufferedReader(new FileReader(file_name))) {
                while ((line = br.readLine()) != null) // returns a Boolean value
                {
                    String[] data = line.split(splitBy); // use comma as separator
                    if (data.length == 7 && data[0] != "" && data[2] != "" && data[3] != "" && data[4] != ""
                            && data[5] != "" && data[6] != "" && data[1] != "") {
                        books.add(data);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeUpdateFileArray() {
        try (FileWriter fw = new FileWriter(file_name)) {
            for (int i = 0; i < books.size(); i++) {
                fw.append("\n"); // New line
                fw.append(books.get(i)[0] + "," + books.get(i)[1] + "," + books.get(i)[2] + "," + books.get(i)[3] + ","
                        + books.get(i)[4] + "," + books.get(i)[5] + "," + books.get(i)[6]);
            }
            ;
            System.out.println("Data successfully appended to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while appending data to the file.");
            e.printStackTrace();
        }
    }

    public Library_admin(String filename) {
        file_name = filename;
        readFileUpdateArray();
        setTitle("Library Management System");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        label1 = new JLabel("Book ID");
        label2 = new JLabel("Book Title");
        label3 = new JLabel("Author");
        label4 = new JLabel("Publisher");
        label5 = new JLabel("Year of Publication");
        label6 = new JLabel("ISBN");
        label7 = new JLabel("Number of Copies");

        textField1 = new JTextField(10);
        textField2 = new JTextField(20);
        textField3 = new JTextField(20);
        textField4 = new JTextField(20);
        textField5 = new JTextField(10);
        textField6 = new JTextField(20);
        textField7 = new JTextField(10);

        addButton = new JButton("Add");
        viewButton = new JButton("View");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        addButton.addActionListener(this);
        viewButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);

        panel = new JPanel(new GridLayout(10, 2));
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(textField3);
        panel.add(label4);
        panel.add(textField4);
        panel.add(label5);
        panel.add(textField5);
        panel.add(label6);
        panel.add(textField6);
        panel.add(label7);
        panel.add(textField7);
        panel.add(addButton);
        panel.add(viewButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(clearButton);
        panel.add(exitButton);
        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String[] book = new String[7];
            book[0] = textField1.getText();
            book[1] = textField2.getText();
            book[2] = textField3.getText();
            book[3] = textField4.getText();
            book[4] = textField5.getText();
            book[5] = textField6.getText();
            book[6] = textField7.getText();
            books.add(book);
            JOptionPane.showMessageDialog(this, "Book added successfully");
            clearFields();
        } else if (e.getSource() == viewButton) {
            String[] columns = { "Book ID", "Book Title", "Author", "Publisher", "Year of Publication", "ISBN",
                    "Number of Copies" };
            Object[][] data = new Object[books.size()][7];
            for (int i = 0; i < books.size(); i++) {
                data[i][0] = books.get(i)[0];
                data[i][1] = books.get(i)[1];
                data[i][2] = books.get(i)[2];
                data[i][3] = books.get(i)[3];
                data[i][4] = books.get(i)[4];
                data[i][5] = books.get(i)[5];
                data[i][6] = books.get(i)[6];
            }
            JTable table = new JTable(data, columns);
            JScrollPane scrollPane = new JScrollPane(table);
            JFrame frame = new JFrame("View Books");
            frame.add(scrollPane);
            frame.setSize(800, 400);
            frame.setVisible(true);
        } else if (e.getSource() == editButton) { // update
            String bookID = JOptionPane.showInputDialog(this, "Enter book ID to edit:");
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i)[0].equals(bookID)) {
                    String[] book = new String[7];
                    book[0] = bookID;
                    book[1] = textField2.getText();
                    book[2] = textField3.getText();
                    book[3] = textField4.getText();
                    book[4] = textField5.getText();
                    book[5] = textField6.getText();
                    book[6] = textField7.getText();
                    books.set(i, book);
                    JOptionPane.showMessageDialog(this, "Book edited successfully");
                    clearFields();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Book not found");
        } else if (e.getSource() == deleteButton) { // delete
            String bookID = JOptionPane.showInputDialog(this, "Enter book ID to delete:");
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i)[0].equals(bookID)) {
                    books.remove(i);
                    JOptionPane.showMessageDialog(this, "Book deleted successfully");
                    clearFields();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Book not found");
        } else if (e.getSource() == clearButton) { // clear
            clearFields();
        } else if (e.getSource() == exitButton) { // exit
            writeUpdateFileArray();
            try {// pause execution for 2 seconds
                Thread.sleep(2000);
            } catch (InterruptedException ex) {// handle the exception
                System.out.println("Sleep was interrupted");
            }
            System.exit(0);
        }
    }

    private void clearFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
        textField7.setText("");
    }
}

class Library_user extends JFrame implements ActionListener {
    private JLabel label1, label2;
    private JTextField textField1, textField2;
    private JButton issueButton, viewButton, clearButton, exitButton;
    private JPanel panel;
    private ArrayList<String[]> books = new ArrayList<String[]>();
    private String file_name;

    private void readFileUpdateArray() {
        String line = "";
        String splitBy = ",";
        try {
            try (// parsing a CSV file into BufferedReader class constructor
                    BufferedReader br = new BufferedReader(new FileReader(file_name))) {
                while ((line = br.readLine()) != null) // returns a Boolean value
                {
                    String[] data = line.split(splitBy); // use comma as separator
                    if (data.length == 7 && data[0] != "" && data[2] != "" && data[3] != "" && data[4] != ""
                            && data[5] != "" && data[6] != "" && data[1] != "") {
                        books.add(data);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeUpdateFileArray() {
        try (FileWriter fw = new FileWriter(file_name)) {
            for (int i = 0; i < books.size(); i++) {
                fw.append("\n"); // New line
                fw.append(books.get(i)[0] + "," + books.get(i)[1] + "," + books.get(i)[2] + "," + books.get(i)[3] + ","
                        + books.get(i)[4] + "," + books.get(i)[5] + "," + books.get(i)[6]);
            }
            ;
            System.out.println("Data successfully appended to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while appending data to the file.");
            e.printStackTrace();
        }
    }

    public Library_user(String filename) {
        file_name = filename;
        readFileUpdateArray();
        setTitle("Library Management System");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        label1 = new JLabel("Book ID");
        label2 = new JLabel("Book Title");

        textField1 = new JTextField(10);
        textField2 = new JTextField(20);

        issueButton = new JButton("Issue");
        viewButton = new JButton("View");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        issueButton.addActionListener(this);
        viewButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);

        panel = new JPanel(new GridLayout(10, 2));
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(issueButton);
        panel.add(viewButton);
        panel.add(clearButton);
        panel.add(exitButton);
        add(panel);
        setVisible(true);
    }

    private void issueBook(String[] book) {
        int copies = Integer.parseInt(book[6]);
        if (copies > 0) {
            copies--;
            book[6] = Integer.toString(copies);
            JOptionPane.showMessageDialog(this, "Book issued successfully");
        } else {
            JOptionPane.showMessageDialog(this, "Book not available");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == issueButton) {
            String[] book = new String[7];
            String idTitle = textField1.getText();
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i)[0].equals(idTitle)) {
                    issueBook(books.get(i));
                }
            }
            books.add(book);
            JOptionPane.showMessageDialog(this, "Book added successfully");
            clearFields();
        } else if (e.getSource() == viewButton) { // view
            String[] columns = { "Book ID", "Book Title", "Author", "Publisher", "Year of Publication", "ISBN",
                    "Number of Copies" };
            Object[][] data = new Object[books.size()][7];
            for (int i = 0; i < books.size(); i++) {
                data[i][0] = books.get(i)[0];
                data[i][1] = books.get(i)[1];
                data[i][2] = books.get(i)[2];
                data[i][3] = books.get(i)[3];
                data[i][4] = books.get(i)[4];
                data[i][5] = books.get(i)[5];
                data[i][6] = books.get(i)[6];
            }
            JTable table = new JTable(data, columns);
            JScrollPane scrollPane = new JScrollPane(table);
            JFrame frame = new JFrame("View Books");
            frame.add(scrollPane);
            frame.setSize(800, 400);
            frame.setVisible(true);
        } else if (e.getSource() == clearButton) { // clear
            clearFields();
        } else if (e.getSource() == exitButton) { // exit
            writeUpdateFileArray();
            try {// pause execution for 2 seconds
                Thread.sleep(2000);
            } catch (InterruptedException ex) {// handle the exception
                System.out.println("Sleep was interrupted");
            }
            System.exit(0);
        }
    }

    private void clearFields() { // clear
        textField1.setText("");
        textField2.setText("");
    }
}

class Library_login extends JFrame implements ActionListener {
    private JLabel label1, label2;
    private JTextField username, password;
    private JButton adminlogin, userlogin, clearButton;
    private JPanel panel;
    private ArrayList<String[]> user = new ArrayList<String[]>();
    private String file_name_records; // records
    private String file_name_user_data; // user data

    private void readFileUpdateArray() {
        try {
            File myObj = new File(file_name_user_data);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arrOfStr = data.split(",");
                user.add(arrOfStr);
            }
            myReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Library_login(String user_data, String records) {
        file_name_user_data = user_data;
        file_name_records = records;
        readFileUpdateArray();
        setTitle("Library Management System");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        label1 = new JLabel("Username");
        label2 = new JLabel("Password");

        username = new JTextField(20);
        password = new JTextField(20);

        adminlogin = new JButton("Admin Login");
        userlogin = new JButton("User Login");
        clearButton = new JButton("Clear");
        adminlogin.addActionListener(this);
        userlogin.addActionListener(this);
        clearButton.addActionListener(this);

        panel = new JPanel(new GridLayout(10, 2));
        panel.add(label1);
        panel.add(username);
        panel.add(label2);
        panel.add(password);
        panel.add(adminlogin);
        panel.add(userlogin);
        panel.add(clearButton);
        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == adminlogin) {
            String user_name = username.getText();
            String pass_word = password.getText();
            if (user_name.equals("admin") && pass_word.equals("admin")) {
                new Library_admin(file_name_records);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        } else if (e.getSource() == userlogin) {
            String user_name = username.getText();
            String pass_word = password.getText();
            for (int i = 0; i < user.size(); i++) {
                if (user.get(i)[0].equals(user_name) && user.get(i)[1].equals(pass_word)) {
                    new Library_user(file_name_records);
                    setVisible(false);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        } else if (e.getSource() == clearButton) {
            username.setText("");
            password.setText("");
        }
    }
}

public class library_management {
    public static void main(String[] args) {
        String user_data = "data/user_data.csv";
        String records = "data/book_records.csv";
        File user_data_file = new File(user_data);
        File records_data_file = new File(records);

        if (!records_data_file.exists()) {
            try {
                if (records_data_file.createNewFile()) {
                    System.out.println("The records data file has been created");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while creating the records data file");
                e.printStackTrace();
            }
        }

        if (user_data_file.exists()) {
            new Library_login(user_data, records);
        } else {
            System.out.println("The user data file does not exist please create it first");
        }
    }
}