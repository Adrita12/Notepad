import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class Notepad implements ActionListener {
    private JFrame frame;
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private File currentFile;

    Notepad() {
        frame = new JFrame("Notepad");
        textArea = new JTextArea();
        fileChooser = new JFileChooser();
        currentFile = null;

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Create File menu
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // File menu items
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        // Add action listeners to file menu items
        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        saveAsMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);

        // Add file menu items to file menu
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        // Create Edit menu
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        // Edit menu items
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");

        // Add action listeners to edit menu items
        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);

        // Add edit menu items to edit menu
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        // Create the frame content
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New":
                newFile();
                break;
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Save As":
                saveFileAs();
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Cut":
                textArea.cut();
                break;
            case "Copy":
                textArea.copy();
                break;
            case "Paste":
                textArea.paste();
                break;
        }
    }

    private void newFile() {
        textArea.setText("");
        currentFile = null;
    }

    private void openFile() {
        int returnVal = fileChooser.showOpenDialog(frame);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            readFromFile(file);
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            saveFileAs();
        } else {
            writeToFile(currentFile);
        }
    }

    private void saveFileAs() {
        int returnVal = fileChooser.showSaveDialog(frame);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            writeToFile(file);
            currentFile = file;
        }
    }

    private void readFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            textArea.setText("");
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
            currentFile = file;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading from file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void writeToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(textArea.getText());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error writing to file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Notepad::new);
    }
}
