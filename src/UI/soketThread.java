package UI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class soketThread extends Thread {
    userPixel[][] user;
    ServerSocket server;
    boolean server_flag = true;

    soketThread(userPixel[][] user) {
        this.user = user;
    }

    public void setFlag(boolean b) {
        this.server_flag = b;
    }

    public void stopServer() {
        try {
            server.close();
            System.out.println("서버종료");
        } catch (Exception e) {
            System.out.println("서버종료 실패");
        }
    }

    public void run() {
        try {
            server = new ServerSocket(9870);
            System.out.println("서버오픈");
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (server_flag) {
            try {
                Socket user = server.accept();
                DataInputStream input = new DataInputStream(user.getInputStream());
                
                byte fseatbyte = input.readByte();
                byte tseatbyte = input.readByte();
                
                int fseat = fseatbyte&0xFF;
                int tseat = tseatbyte&0xFF;
                int fresult = fseat-48;
                int tresult = tseat-48;
                
                int sumresult = 0;
                String fs = "";
                String ts = "";
                String rs = "";
                
                if(fresult == 0) {
                	sumresult = fresult+tresult;
                } else {
                    fs = Integer.toString(fresult);
                    ts = Integer.toString(tresult);
                    rs = fs+ts;
                    sumresult = Integer.parseInt(rs);
                }

                
                this.user[fresult][tresult].setSocket(user);
                this.user[fresult][tresult].setSeat(sumresult);
                System.out.println(sumresult + "번 유저 접속");
              
//                int seat = input.readInt() - 1;
//                this.user[seat / 7][seat % 7].setSocket(user);
//                this.user[seat / 7][seat % 7].setSeat(seat);
//                System.out.println(seat + "번 유저 접속");
                
            } catch (Exception e) {
                if (server.isClosed()) {
                    server_flag = false;
                }
            }
        }
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++)
                this.user[i][j].setSocket(null);
        System.out.println("서버 스레드 종료");
    }

}