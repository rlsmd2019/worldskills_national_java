package frame;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.OrderlistManager;
import db.SessionManager;
import dto.Top5;
import dto.User;
import util.SwingHelper;

public class Top5Frame extends FrameBase {

	private static final Color[] COLOR_LIST = {
			Color.RED,
			Color.ORANGE,
			Color.YELLOW,
			Color.GREEN, 
			Color.CYAN
	};
	private static final Stroke STROKE = new BasicStroke(2);
	
	private class ChartPanel extends JPanel {
		ArrayList<Top5> top5List; 
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setStroke(STROKE);
			
			g2d.drawLine(45, 40, 45, getHeight() - 60);
			
			if (top5List.size() == 0) {
				return;
			}
			
			int maxCount = top5List.get(0).count;
			int maxWidth = getWidth() - 45 - 80;
			
			g2d.setFont(new Font("돋움", Font.BOLD, 12));
			
			for (int i = 0; i < top5List.size(); i++) {
				Rectangle2D rect = new Rectangle2D.Float(45, 60 + (i * 60), (float)maxWidth * ((float)top5List.get(i).count / maxCount), 30);
				
				g2d.setColor(COLOR_LIST[i]);
				g2d.fill(rect);
				
				g2d.setColor(Color.BLACK);
				g2d.draw(rect);
				
				g2d.drawString(String.format("%s-%d개", top5List.get(i).menuName,
						top5List.get(i).count), 50, 105 + (i * 60));
			}
			
		}
		
		public void update(String group) {
			top5List = OrderlistManager.getTop5List(group);
			
			repaint();
		}
		
	}
	
	public Top5Frame() {
		super(440, 500, "인기상품 Top5");
		
		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JComboBox<String> cbGroup = new JComboBox<String>(new String[] { "음료", "푸드", "상품" });
		
		northPanel.setBackground(Color.LIGHT_GRAY);
		northPanel.add(cbGroup);
		northPanel.add(SwingHelper.createComponent(new JLabel("인기상품 Top5"), lb -> {
			lb.setFont(new Font("굴림", Font.BOLD, 20));
		}));
		
		add(northPanel, BorderLayout.NORTH);
		
		ChartPanel chart = new ChartPanel();
		
		cbGroup.addActionListener(e -> chart.update((String)cbGroup.getSelectedItem()));
		chart.update("음료");
		
		add(chart, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter() {
			
			public void windowClosed(java.awt.event.WindowEvent e) {
				new StarBoxFrame().setVisible(true);
			};
		});
		
	}
	
	public static void main(String[] args) {
		SessionManager.login(new User(1, "coffee1", "1234", "이기민", "2019-01-01", 0, "Gold"));
		
		new Top5Frame().setVisible(true);
	}

}
