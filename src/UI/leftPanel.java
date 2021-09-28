package UI;

import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class leftPanel extends JPanel {
    static int state = -1;
    private userPixel[][] users;
    private int row = 0;
    private soketThread mainsocket = null;
    private HashMap<Integer, Double> startTime_map = new HashMap<Integer, Double>();
    private HashMap<Integer, Double> durationTime_map = new HashMap<Integer, Double>();

    public leftPanel(soketThread sock, userPixel[][] users) {
        this.mainsocket = sock;
        this.users = users;

        setBackground(Color.WHITE);
        setLayout(null);

        HashMap<String, String> csv_map = new HashMap<String, String>();

        JComboBox csvViewBox = new JComboBox();
        csvViewBox.setBackground(Color.LIGHT_GRAY);
        csvViewBox.setFont(new Font("함초롬돋움", Font.PLAIN, 12));
        csvViewBox.setModel(new DefaultComboBoxModel(new String[] {}));
        csvViewBox.setBounds(10, 20, 200, 40);
        add(csvViewBox);

        JButton csvLoadBtn = new JButton("csv Load");
        csvLoadBtn.setBackground(Color.LIGHT_GRAY);
        csvLoadBtn.setFont(new Font("함초롬돋움", Font.BOLD, 12));
        csvLoadBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser file = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("csv", "csv");
                file.setFileFilter(filter);
                int rat = file.showOpenDialog(null);
                if (rat == JFileChooser.APPROVE_OPTION) {
                    csvViewBox.addItem(file.getSelectedFile().getName());
                    csv_map.put(file.getSelectedFile().getName(), file.getSelectedFile().getPath());
                }
            }
        });
        csvLoadBtn.setBounds(10, 70, 200, 40);
        add(csvLoadBtn);

        JButton serverBtn = new JButton("Server OFF");
        serverBtn.setBackground(Color.LIGHT_GRAY);
        serverBtn.setFont(new Font("함초롬돋움", Font.BOLD, 12));
        serverBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                state *= -1;
                if (state == 1) {
                    mainsocket = new soketThread(users);
                    serverBtn.setText("Server ON");
                    mainsocket.setFlag(true);
                    mainsocket.start();
                } else {
                    serverBtn.setText("Server OFF");
                    mainsocket.setFlag(false);
                    mainsocket.stopServer();
                }

            }
        });
        serverBtn.setBounds(10, 120, 200, 40);
        add(serverBtn);

        JButton sendBtn = new JButton("Send Message");
        sendBtn.setBackground(Color.LIGHT_GRAY);
        sendBtn.setFont(new Font("함초롬돋움", Font.BOLD, 12));
        sendBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String[][] filedata = new String[5096][6];
                try {
                    row = 0;
                    File csv = new File(csv_map.get(csvViewBox.getSelectedItem()));
                    BufferedReader reader = new BufferedReader(new FileReader(csv));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        String[] token = line.split(",", -1);
                        for (int i = 0; i < 6; i++) {
                            filedata[row][i] = token[i];
                        }
                        row++;
                    }
                    reader.close();
                    int count = 0;
                    int index = 0;
                    for (int i = 0; i < 7; i++)
                        for (int j = 0; j < 7; j++)
                            users[i][j].initHashMap();
                    while (count != row) {
                        Double startTime = Double.parseDouble(
                                filedata[count][1].replaceAll("\"", "").replaceAll("ms", "").replaceAll(" ", ""));
                        Double durationTime = Double.parseDouble(
                                filedata[count][2].replaceAll("\"", "").replaceAll("ms", "").replaceAll(" ", ""))
                                * 1000;
                        Double finishTime = startTime - durationTime;
                        int color_R = Integer.parseInt(filedata[count][5].substring(2, 4), 16);
                        int color_G = Integer.parseInt(filedata[count][5].substring(4, 6), 16);
                        int color_B = Integer.parseInt(filedata[count][5].substring(6, 8), 16);
                        StringtoPixelConverter spc = new StringtoPixelConverter();
                        int[][][] ledData = spc.convert(filedata[count][0], new Color(color_R, color_G, color_B));
                        for (int l = 0; l < filedata[count][0].length(); l++) {
                            for (int i = 0; i < 7; i++) {
                                for (int j = 0; j < 7; j++) {
                                    users[i][j].setMsg(ledData[l][i][j], startTime + (durationTime * l), durationTime,
                                            (startTime + durationTime * l) + durationTime, index);
                                }
                            }
                            index++;
                        }

                        count++;
                    }
                    for (int i = 0; i < 7; i++) {
                        for (int j = 0; j < 7; j++) {
                            users[i][j].sendData(false, 0);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("전송실패");
                }

            }
        });
        sendBtn.setBounds(10, 170, 200, 40);
        add(sendBtn);

        JButton startBtn = new JButton("Start");
        startBtn.setBackground(Color.LIGHT_GRAY);
        startBtn.setFont(new Font("함초롬돋움", Font.BOLD, 12));
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                startTime_map = users[0][0].getMap("start");
                durationTime_map = users[0][0].getMap("duration");
                long time = System.currentTimeMillis();
                long offset = System.currentTimeMillis();
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        time = System.currentTimeMillis();
                        users[i][j].sendData(true, Long.valueOf(5000) - (time - offset));
                    }
                }
            }
        });
        startBtn.setBounds(10, 220, 200, 40);
        add(startBtn);
    }
}
