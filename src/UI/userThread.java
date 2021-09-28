package UI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.awt.Color;

public class userThread extends Thread {
    private Socket user = null;
    private userPixel up = null;
    private DataOutputStream output = null;
    private HashMap<Integer, Integer> led_map = new HashMap<Integer, Integer>();
    private HashMap<Integer, Double> startTime_map = new HashMap<Integer, Double>();
    private HashMap<Integer, Double> durationTime_map = new HashMap<Integer, Double>();
    private HashMap<Integer, Double> finishTime_map = new HashMap<Integer, Double>();
    private int len = 0;
    private boolean flag = false;
    private long offset = 0;
    
    userThread(Socket user, boolean flag, HashMap led_map, HashMap startTime_map, HashMap durationTime_map,
            HashMap finishTime_map, int len, userPixel up, long offset) {
        setUser(user);
        setFlag(flag);
        this.led_map = led_map;
        this.startTime_map = startTime_map;
        this.durationTime_map = durationTime_map;
        this.finishTime_map = finishTime_map;
        this.len = len;
        this.up = up;
        this.offset = offset;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setUser(Socket user) {
        this.user = user;
    }

    public void run() {
        if (user != null) {
            try {
                output = new DataOutputStream(user.getOutputStream());
                if (this.flag) {
                    long time = System.currentTimeMillis();
//                    output.writeUTF("start");
//                    output.writeUTF("" + offset);
                } else {
                    for (int i = 0; i < len; i++) {
                        String sendMsg = "QueueData/" + led_map.get(i) + "/" + startTime_map.get(i) + "/"
                                + durationTime_map.get(i) + "/" + finishTime_map.get(i) + "/" + i;
                        output.writeUTF(sendMsg);
                        System.out.println(sendMsg); //보내지는 QueueData 정보
                    }
                }
            } catch (Exception e) {}
            
        }
        if (this.flag) {
//        	System.out.println("RealData Send");
        	try {
        		Thread.sleep(offset);
        		offset = System.currentTimeMillis();
        		 for (int i = 0; i < len; i++) {
        			 long time = System.currentTimeMillis();
//        			 System.out.println(startTime_map.get(i).longValue() - (time - offset));
        			 try {
        				 Thread.sleep(startTime_map.get(i).longValue() - (time - offset));
        			 }catch (Exception e) {
        	            }
//        			 output = new DataOutputStream(user.getOutputStream()); // 이걸 추가함으로써 연결된 부분만 색 출력됨
                     up.setBackgroudColor(new Color(led_map.get(i)));
                     Thread.sleep(durationTime_map.get(i).longValue());
                     String sendMsg = ""+led_map.get(i);
                     output.writeUTF(sendMsg);
                     System.out.println(sendMsg);
        		 }
            } catch (Exception e) {
            }
        }
    }
}
