//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIClient {
    public static void main(String[] args) {
        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        final JFrame frame = new JFrame("Ordering system");
        frame.setDefaultCloseOperation(3);
        frame.setSize(600, 300);
        frame.setLayout(new GridLayout(1, 2, 15, 10));
        JPanel registration_items = new JPanel();
        registration_items.setLayout(new BoxLayout(registration_items, 1));
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, 1));
        JPanel row1 = new JPanel(new GridLayout(1, 2));
        JLabel buisness_name = new JLabel("Buisness name:");
        final JTextField nameField = new JTextField();
        row1.add(buisness_name);
        row1.add(nameField);
        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JPanel row2 = new JPanel(new GridLayout(1, 2));
        JLabel buisness_id = new JLabel("Buisness ID:");
        final JTextField idField = new JTextField(5);
        row2.add(buisness_id);
        row2.add(idField);
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JPanel row3 = new JPanel(new GridLayout(1, 2));
        final JCheckBox sunglassesBox = new JCheckBox("Sunglasses");
        final JTextField sunglassesField = new JTextField("0");
        row3.add(sunglassesBox);
        row3.add(sunglassesField);
        row3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JPanel row4 = new JPanel(new GridLayout(1, 2));
        final JCheckBox beltBox = new JCheckBox("belt");
        final JTextField beltField = new JTextField("0");
        row4.add(beltBox);
        row4.add(beltField);
        row4.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JPanel row5 = new JPanel(new GridLayout(1, 2));
        final JCheckBox scarfBox = new JCheckBox("Scarf");
        final JTextField scarfField = new JTextField("0");
        row5.add(scarfBox);
        row5.add(scarfField);
        row5.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JButton sendButton = new JButton("send");
        final JButton diconnectButton = new JButton("disconnect");
        registration_items.add(row1);
        registration_items.add(Box.createRigidArea(new Dimension(0, 5)));
        registration_items.add(row2);
        registration_items.add(Box.createRigidArea(new Dimension(0, 5)));
        registration_items.add(row3);
        registration_items.add(Box.createRigidArea(new Dimension(0, 5)));
        registration_items.add(row4);
        registration_items.add(Box.createRigidArea(new Dimension(0, 5)));
        registration_items.add(row5);
        registration_items.add(Box.createRigidArea(new Dimension(0, 10)));
        registration_items.add(sendButton);
        registration_items.add(Box.createVerticalGlue());
        detailsPanel.add(Box.createVerticalGlue());
        detailsPanel.add(diconnectButton);
        frame.add(registration_items);
        frame.add(detailsPanel);
        diconnectButton.setVisible(false);
        frame.setVisible(true);
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Socket kkSocket = new Socket("127.0.0.1", 9999);
                    PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
                    diconnectButton.setVisible(true);
                    String type = "0";
                    String quantity = "0";
                    if (sunglassesBox.isSelected()) {
                        type = "1";
                        quantity = sunglassesField.getText();
                    } else if (beltBox.isSelected()) {
                        type = "2";
                        quantity = beltField.getText();
                    } else if (scarfBox.isSelected()) {
                        type = "3";
                        quantity = scarfField.getText();
                    }

                    String var10001 = nameField.getText();
                    out.println(var10001 + "#" + idField.getText() + "#" + type + "#" + quantity);
                    String fromServer = in.readLine();
                    if (fromServer != null) {
                        if (fromServer.equals("100")) {
                            JOptionPane.showMessageDialog(frame, "Success (100)");
                            diconnectButton.setVisible(true);
                        } else if (fromServer.equals("200")) {
                            JOptionPane.showMessageDialog(frame, "Error 200: Missing/Invalid Data");
                        } else if (fromServer.equals("201")) {
                            JOptionPane.showMessageDialog(frame, "Error 201: Name/ID Mismatch");
                        } else if (fromServer.equals("202")) {
                            JOptionPane.showMessageDialog(frame, "Error 202: Negative Quantity");
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "שגיאה בהתחברות לשרת: " + ex.getMessage());
                }

            }
        });
        diconnectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });
    }
}
