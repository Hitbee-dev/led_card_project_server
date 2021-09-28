package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import java.awt.Window.Type;
import java.awt.FlowLayout;
import java.awt.Dimension;

public class mainUI extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainUI frame = new mainUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public mainUI() {
		setTitle("LED SERVER");
		setBackground(UIManager.getColor("Button.background"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 775);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		userPixel[][] users = new userPixel[7][7];

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				users[i][j] = new userPixel();
				users[i][j].setBounds(230 + (j * 105), 0 + (i * 105), 100, 100);
				add(users[i][j]);
			}
		}

		soketThread mainThread = new soketThread(users);

		leftPanel panel = new leftPanel(mainThread, users);
		panel.setBounds(0, 0, 220, 730);
		contentPane.add(panel);
	}

}