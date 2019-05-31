package frame.lecture;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import frame.FrameBase;
import util.lecture.SwingHelper;

public class StartBoxFrame extends FrameBase {

	JLabel lbCaption = new JLabel();
	
	private class PictureBox extends JPanel {
		
		public PictureBox(String menu) {
			setLayout(new BorderLayout());
			
			JLabel lbPicture = new JLabel();
			JLabel lbText = new JLabel(menu, JLabel.CENTER);
			
			lbPicture.setBorder(new LineBorder(Color.BLACK));
			
			lbPicture.setIcon(SwingHelper.getIcon(menu, 145, 155));
			setPreferredSize(new Dimension(150, 175));
			add(lbPicture, BorderLayout.CENTER);
			add(lbText, BorderLayout.SOUTH);
		}
		
	}
	
	public StartBoxFrame() {
		super(800, 500, "STARBOX");
		
		JPanel northPanel = new JPanel(new BorderLayout());
		JPanel north_southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		lbCaption.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		north_southPanel.add(SwingHelper.creawteButton("���ų���", e -> {
			dispose();
			// asdasd
		}));
		north_southPanel.add(SwingHelper.creawteButton("��ٱ���", null));
		north_southPanel.add(SwingHelper.creawteButton("�α��ǰ Top5", null));
		north_southPanel.add(SwingHelper.creawteButton("Logout", null));
		
		northPanel.add(lbCaption, BorderLayout.CENTER);
		northPanel.add(north_southPanel, BorderLayout.SOUTH);
		add(northPanel, BorderLayout.NORTH);
		
		JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
		
		// Layout�� ������ ������ �������� ũ��
		// westPanel.setSize(width, height);
		
		// Layout�� ������ ������ ����ϴ� ũ��
		westPanel.setPreferredSize(new Dimension(70, 0));
		
		westPanel.add(SwingHelper.creawteButton("����", null, 70, 40));
		westPanel.add(SwingHelper.creawteButton("Ǫ��", null, 70, 40));
		westPanel.add(SwingHelper.creawteButton("��ǰ", null, 70, 40));
		
		add(westPanel, BorderLayout.WEST);
		
		JPanel centerPanel = new JPanel(null);
		JPanel center_subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane center_subScrollPane = new JScrollPane(center_subPanel);
		
		center_subPanel.setPreferredSize(new Dimension(480, 1000));
		
		center_subPanel.add(new PictureBox("���̽� īǪġ��"));
		center_subPanel.add(new PictureBox("�ݵ� ���"));
		center_subPanel.add(new PictureBox("�ݵ� ���"));
		
		center_subScrollPane.setBounds(0, 0, 500, 380);
		
		centerPanel.add(center_subScrollPane);
		
		add(centerPanel, BorderLayout.CENTER);
		
		SwingHelper.creawteButton("�����ϱ�", this::clickBuy, 70, 40);
		refreshCaption();
	}
	
	private void clickBuy(ActionEvent e) {
		refreshCaption();
	}
	
	private void refreshCaption() {
		lbCaption.setText(String.format("ȸ���� : %s / ȸ����� : %s / ���� ����Ʈ : %d", "�̱��", "�Ϲ�", 15000));
	}
	
	public static void main(String[] args) {
		new StartBoxFrame().setVisible(true);
	}

}
