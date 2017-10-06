package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import activity.Operations;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton button = new JButton("Start Simulation");
	private JPanel panel = new JPanel();

	public Gui(Operations op) {
		Simulation s = new Simulation(op);
		panel.setLayout(null);
		button.setBounds(0, 0, 200, 100);
		panel.add(button);
		panel.setPreferredSize(new Dimension(200, 100));
		add(panel);
		pack();
		Thread t = new Thread(new Runnable() {
			public void run() {
				s.run();
			}
		});

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove(panel);
				add(s);
				pack();
				t.start();
			}
		});
	}
}
