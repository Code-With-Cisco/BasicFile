package basicfile;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
/**
 * @author Francisco Garcia
 * PID# 5767275
 * Programming Assignment 3
 * Professor Joslyn Smith
 */
public class BasicFile {
    
    private File inputFile;
    
    public BasicFile() {
        // Default constructor
    }
    
    public void selectFile() {
        // Open a file dialog to select a file
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            inputFile = fileChooser.getSelectedFile();
        }
    }
    
    public void copyFile() {
        // Make a copy of the selected file
        if (inputFile == null) {
            System.out.println("Please select a file first.");
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(inputFile.getName() + ".copy"));
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File outputFile = fileChooser.getSelectedFile();
            try {
                FileInputStream inputStream = new FileInputStream(inputFile);
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                inputStream.close();
                outputStream.close();
                System.out.println("File copied successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void writeToFile(String text, boolean append) {
        // Write text to a file, either overwriting or appending
        if (inputFile == null) {
            System.out.println("Please select a file first.");
            return;
        }
        try {
            FileWriter fileWriter = new FileWriter(inputFile, append);
            fileWriter.write(text);
            fileWriter.close();
            System.out.println("Text written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void displayAttributes() {
        // Display the attributes of the selected file
        if (inputFile == null) {
            System.out.println("Please select a file first.");
            return;
        }
        String path = inputFile.getAbsolutePath();
        long size = inputFile.length() / 1024;
        String[] pathElements = path.split(File.separator);
        String dir = "";
        for (int i = 0; i < pathElements.length - 1; i++) {
            dir += pathElements[i] + File.separator;
        }
        String message = "Path: " + path + "\nDirectory: " + dir + "\nSize: " + size + " KB\n";
        if (inputFile.isFile()) {
            int lines = countLines();
            message += "Lines: " + lines + "\n";
        }
        JTextArea textArea = new JTextArea(message);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "File Attributes", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void displayContents() {
        // Display the contents of the selected file
        if (inputFile == null) {
            System.out.println("Please select a file first.");
            return;
        }
        String contents = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                contents += line + "\n";
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
                JTextArea textArea = new JTextArea(contents);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "File Contents", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void searchFile(String searchString) {
        // Search the selected file for a given string
        if (inputFile == null) {
            System.out.println("Please select a file first.");
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            int lineNumber = 1;
            String results = "";
            while ((line = reader.readLine()) != null) {
                if (line.contains(searchString)) {
                    results += lineNumber + ": " + line + "\n";
                }
                lineNumber++;
            }
            reader.close();
            JTextArea textArea = new JTextArea(results);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(null, scrollPane, "Search Results", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
public void tokenizeFile() {
    // Tokenize the contents of the selected file
    if (inputFile == null) {
        System.out.println("Please select a file first.");
        return;
    }
    try {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        String tokens = "";
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                tokens += word + "\n";
            }
        }
        reader.close();
        JTextArea textArea = new JTextArea(tokens);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "Tokenized File Contents", JOptionPane.PLAIN_MESSAGE);
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    
    private int countLines() {
        // Count the number of lines in the selected file
        int lines = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            while (reader.readLine() != null) lines++;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    
    public static void main(String[] args) {
        // Create a BasicFile object and test its methods
        BasicFile basicFile = new BasicFile();
        basicFile.selectFile();
        basicFile.displayAttributes();
        basicFile.displayContents();
        basicFile.searchFile("Java");
        basicFile.tokenizeFile();
    }
}
