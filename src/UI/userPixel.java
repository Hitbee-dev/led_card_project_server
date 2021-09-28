package UI;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.net.Socket;
import java.util.HashMap;

public class userPixel extends JPanel {
    private Socket user = null;
    private int seat;
    private JTextField userstate;
    private String testMsg = Color.RED.getRGB() + "";
    private userThread ut = null;
    private HashMap<Integer, Integer> led_map = new HashMap<Integer, Integer>();
    private HashMap<Integer, Double> startTime_map = new HashMap<Integer, Double>();
    private HashMap<Integer, Double> durationTime_map = new HashMap<Integer, Double>();
    private HashMap<Integer, Double> finishTime_map = new HashMap<Integer, Double>();
    private int len = 0;

    public userPixel() {

        setBackground(Color.WHITE);
        setLayout(null);

        userstate = new JTextField();
        userstate.setFont(new Font("굴림", Font.BOLD, 12));
        userstate.setForeground(Color.RED);
        userstate.setBackground(Color.WHITE);
        userstate.setText("OFF");
        userstate.setEditable(false);
        userstate.setHorizontalAlignment(SwingConstants.CENTER);
        userstate.setBounds(0, 0, 100, 21);
        add(userstate);
        userstate.setColumns(10);
        userstate.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        setBackgroudColor(Color.WHITE);
    }

    public void setBackgroudColor(Color c) {
        setBackground(c);
        userstate.setBackground(c);
    }

    public void setBackgroudColor(int i) {
        Color c = new Color(led_map.get(i));
        setBackground(c);
        userstate.setBackground(c);
    }

    public void setUserState() {
        if (user != null) {
            userstate.setForeground(Color.GREEN);
            userstate.setText("ON");
        } else {
            userstate.setForeground(Color.RED);
            userstate.setText("OFF");
        }
    }

    public Socket getSocket() {
        this.setUserState();
        return this.user;
    }

    public void setSocket(Socket s) {
        this.user = s;
        this.setUserState();
    }

    public int getSeat() {
        return this.seat;
    }

    public void setSeat(int s) {
        this.seat = s;
    }

    public HashMap<Integer, Double> getMap(String s) {
        return s.equals("start") ? startTime_map : durationTime_map;
    }

    public void initHashMap() {
        led_map = new HashMap<Integer, Integer>();
        startTime_map = new HashMap<Integer, Double>();
        durationTime_map = new HashMap<Integer, Double>();
        finishTime_map = new HashMap<Integer, Double>();
    }

    public void setMsg(int data, Double start, Double duration, Double finish, int index) {
        led_map.put(index, data);
        startTime_map.put(index, start);
        durationTime_map.put(index, duration);
        finishTime_map.put(index, finish);
        len++;
    }

    public void sendData(boolean flag, long offset) {
        ut = new userThread(user, flag, led_map, startTime_map, durationTime_map, finishTime_map, len, this, offset);
        ut.start();
    }
}
