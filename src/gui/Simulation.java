package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import activity.MonitoredData;
import activity.Operations;

public class Simulation extends JPanel {

	private static final long serialVersionUID = 1L;
	List<MonitoredData> list;
	private Integer x = 0, y = 0;
	private String activity, date;

	public Simulation(Operations op) {
		setLayout(null);
		list = op.getList();
		setBackground(Color.WHITE);
		ImageIcon tv = new ImageIcon(getClass().getResource("tv.gif"));
		JLabel label = new JLabel(tv);
		label.setBounds(650, 350, 100, 100);
		add(label);
		setPreferredSize(new Dimension(900, 550));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon image = new ImageIcon(getClass().getResource("House.png"));
		image.paintIcon(this, g, 0, 50);
		g.fillOval(x, y, 30, 30);
		g.setFont(new Font("Times Roman", Font.BOLD, 18));
		g.drawString(date, 250, 20);
		g.drawString(activity, 320, 50);

	}

	public void run() {
		list.stream().forEach(s -> {
			try {
				date = s.getStartTime() + "   " + s.getEndTime();
				activity = "Activity: " + s.getActivity();
				if (s.getActivity().equals("Sleeping")) {
					setXY(700, 100);
				} else if (s.getActivity().equals("Toileting")) {
					setXY(110, 140);
				} else if (s.getActivity().equals("Showering")) {
					setXY(90, 370);
				} else if (s.getActivity().equals("Breakfast") || s.getActivity().equals("Lunch")
						|| s.getActivity().equals("Snack")) {
					setXY(400, 350);
				} else if (s.getActivity().equals("Grooming")) {
					setXY(350, 130);
				} else if (s.getActivity().equals("Spare_Time/TV")) {
					setXY(600, 400);
				} else if (s.getActivity().equals("Leaving")) {
					setXY(800, 400);
				}
				Integer d = increment(s.getDuration() / 60);
				System.out.println(d);
				Thread.sleep(d.intValue() * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
		repaint();
	}

	public Integer increment(Integer d) {
		if (d == 0) {
			return d + 2;
		}
		return d;
	}

}
