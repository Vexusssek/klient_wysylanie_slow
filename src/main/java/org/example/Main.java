package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static List<String> words = new ArrayList<>();
    static JTextArea jTextArea = new JTextArea();
    static  JLabel jLabel = new JLabel();
    static DateTimeFormatter dtff = DateTimeFormatter.ofPattern("HH:mm:ss");

    static int count = 1;

    public static void main(String[] args) {

        JFrame jFrame = new JFrame("Words");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(500,500);
        jTextArea.setSize(300,300);
        jLabel.setSize(50,50);
        JPanel panel = new JPanel();
        panel.add(jTextArea, BorderLayout.CENTER);
        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(jLabel,BorderLayout.NORTH);
        jFrame.setVisible(true);



        //łączenie
    new Thread(() ->
    {
        try {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                String finalServerMessage = serverMessage;
                SwingUtilities.invokeLater(() ->
                {
                    LocalDateTime alddd = LocalDateTime.now();
                    jTextArea.append(dtff.format(alddd) + " " + finalServerMessage + "\n");
                    jLabel.setText("Total words: " + String.valueOf(count));
                    count++;
                });
            }

            in.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }).start();

    }



}