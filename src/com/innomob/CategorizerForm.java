package com.innomob;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by Christoph on 03.06.2016.
 */
public class CategorizerForm {

    private JTextArea result;
    private JButton calculateButton;
    private JTextField input;
    private JPanel categorizerView;
    private JButton clearButton;
    private CategorizerForm _this;
    private Properties properties;

    private CategorizerForm() {

        _this = this;
        try {
            properties = readProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        calculateButton.addActionListener(e -> {
            List<Integer> inputList = Arrays.asList(input.getText().replaceAll("\\s+", "").split(",")).stream().mapToInt
                    (_this::convertStringToInteger).boxed().filter(x -> x != 0).collect(Collectors.toList());

            List<Integer> glitterList = readStickerListByName("glitter");
            List<Integer> captainList = readStickerListByName("captain");
            List<Integer> startingList = readStickerListByName("starting");
            List<Integer> emList = readStickerListByName("em");

            int numNormal = 0;
            int numGlitters = 0;
            int numCaptains = 0;
            int numStarting = 0;
            int numEm = 0;

            StringBuilder sb = new StringBuilder();

            for (Integer i : inputList) {
                if (glitterList.contains(i)) {
                    numGlitters++;
                } else if (captainList.contains(i)) {
                    numCaptains++;
                } else if (startingList.contains(i)) {
                    numStarting++;
                } else if (emList.contains(i)) {
                    numEm++;
                } else {
                    numNormal++;
                }
            }


            sb.append("Normal: ").append(numNormal).append("\n");
            sb.append("Glitters: ").append(numGlitters).append("\n");
            sb.append("EM: ").append(numEm).append("\n");
            sb.append("Captains: ").append(numCaptains).append("\n");
            sb.append("Starting: ").append(numStarting).append("\n");

            result.setText(sb.toString());
        });
        clearButton.addActionListener(e -> {
            input.setText("");
            input.requestFocus();
        });
        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calculateButton.doClick();
                }
            }
        });
    }

    static void open() {
        JFrame frame = new JFrame("Calculator");
        frame.setContentPane(new CategorizerForm().categorizerView);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width / 2, height / 2);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private Properties readProperties() throws IOException {

        Properties properties = new Properties();
        InputStream propIn = getClass().getClassLoader().getResourceAsStream("sticker.properties");
        properties.load(propIn);

        return properties;
    }

    private List<Integer> readStickerListByName(String name) {
        return convertPropertyStringToList(properties.getProperty(name));
    }

    private List convertPropertyStringToList(String prop) {
        if (prop == null) {
            return Collections.EMPTY_LIST;
        }
        List<String> items = Arrays.asList(prop.replace(" ", "").split(","));
        return items.stream().mapToInt(this::convertStringToInteger).boxed().filter(x -> x != 0).collect(Collectors
                .toList());
    }

    private Integer convertStringToInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
