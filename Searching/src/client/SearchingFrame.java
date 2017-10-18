package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;

import log.FileProcessing;
import log.TextProcessing;
import net.miginfocom.swing.MigLayout;

public class SearchingFrame extends SimpleFrame{

	private static JPanel mainPanel;
	
	private static JPanel panFolder;
	private static JPanel panText;
	private static JPanel panResultPath;
	private static JPanel panResultContents;
	private static JPanel panBtn;
	
	private static SearchingFrame search;
	
	private static FileProcessing fileProcessing;
	private static TextProcessing textProcessing;
	
	private static JLabel lblPath;
	private static JList listFiles;
	private static JComboBox boxType;
	private static JTextField txtText;
	private static JTextPane txtContent = new JTextPane();
	private static JLabel lblAboutContent = new JLabel();
	
	public SearchingFrame(){
		super("����� ������");
		search = this;
		fileProcessing = new FileProcessing();
		textProcessing = new TextProcessing();
		txtContent.setEditable(false);
	}
	
	public static JPanel getMainPanel(){
		if(mainPanel == null){
			mainPanel = new JPanel();
			mainPanel.setLayout(new MigLayout("wrap, ins 1", "[fill,grow]", null));
			
			mainPanel.add(getFolderPanel());
			mainPanel.add(getTextPanel());
			
			JButton btnSearch = new JButton("�����");
			btnSearch.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {
					txtContent.setText("");
					Object[] files;
					try {
						files = fileProcessing.getFileList(lblPath.getText(), 
								boxType.getSelectedItem().toString(),
								txtText.getText()).toArray();
						fileProcessing.setIsNeedToClear(true);
						listFiles.setListData((Integer.valueOf(0)).equals(files.length) ? new String[]{"��� �����������"}: files);
					} catch (IOException e1) {
						e1.printStackTrace();
					} 
					
					if(!((listFiles.getModel().getElementAt(0))).equals("��� �����������"))
						lblAboutContent.setText("�������� ���� �� ������, ����� ������� ��� ����������");
					else
						lblAboutContent.setText("");
				}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				@Override
				public void mouseClicked(MouseEvent e) {}
			});
			mainPanel.add(btnSearch);
			
			mainPanel.add(getResultPathPanel());
			mainPanel.add(getResultContentsPanel());
			mainPanel.add(getBtn());
		}
		return mainPanel;
	}

	/**
	 * �������� ���� ��� ������ �����
	 * @return
	 */
	public static JPanel getFolderPanel(){
	
		if(panFolder == null){
			panFolder = new JPanel();
			panFolder.setLayout(new MigLayout("wrap", "[r][fill,grow]"));
			panFolder.setBorder(new TitledBorder("������� �����"));
			
			JButton btnChoose = new JButton("������� �����");
			lblPath = new JLabel("�������� ����� ��� ������");
			btnChoose.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {
					lblPath.setText(search.getFolderChooser());
					}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				@Override
				public void mouseClicked(MouseEvent e) {}
			});
			panFolder.add(btnChoose);
			panFolder.add(lblPath);
		}
		return panFolder;
	}
	
	/**
	 * ������� ���� ������ ����� ��� ������
	 */
	private String getFolderChooser(){
		JFileChooser fileChooser = new JFileChooser();
		setUpdateUI(fileChooser).showOpenDialog(this);

		if(fileChooser.getSelectedFile() != null){
		String path = fileChooser.getSelectedFile().getPath();
		return path;
		}
		
		return "";
	}
	
	/**
	 * �������� ������ ��� ����� �������� ������
	 * @return
	 */
	private static JPanel getTextPanel(){
		if(panText == null){
			panText = new JPanel();
			panText.setLayout(new MigLayout("wrap", "[r][fill,grow]"));
			panText.setBorder(new TitledBorder("������� ������� �����"));
			
			panText.add(new JLabel("�����"));
			txtText = new JTextField();
			panText.add(txtText);
			
			panText.add(new JLabel("����������"));
			boxType = new JComboBox(new String[] {".log", ".txt", ".doc"});
			boxType.setEditable(true);
			panText.add(boxType);
		}
		return panText;
	}
	
	/**
	 * �������� ������ � �������� ������� ����� � ������ � ��������� ��������� 
	 * @return
	 */
	private static JPanel getResultPathPanel(){
		if(panResultPath == null){
			panResultPath = new JPanel();
			panResultPath.setLayout(new MigLayout("wrap", "[fill,grow]"));
			panResultPath.setBorder(new TitledBorder("���������"));
			
			listFiles = new JList(new String[] {});
			listFiles.addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if(listFiles.getSelectedValue() != null){
						txtContent.setBackground(Color.WHITE);
						String path = listFiles.getSelectedValue().toString();
						File file = new File(path);
						
						try {
							txtContent.setText(fileProcessing.getContent(file).toString());
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
						
						textProcessing.cleanHighlight(txtContent);
					}
					
				}
			});
			JScrollPane scroll = new JScrollPane(listFiles);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			panResultPath.add(scroll);
		}
		return panResultPath;
	}
	
	/**
	 * �������� ���������� ���������� � ������ �����
	 * @return
	 */
	private static JPanel getResultContentsPanel(){
		if(panResultContents == null){
			panResultContents = new JPanel();
			panResultContents.setLayout(new MigLayout("wrap", "[fill,grow]"));
			panResultContents.setBorder(new TitledBorder("����������"));
			
			panResultContents.add(lblAboutContent);
			txtContent.setPreferredSize(new Dimension(0, 200));
			JScrollPane scroll = new JScrollPane(txtContent);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			panResultContents.add(scroll);
		}
		return panResultContents;
	}
	
	/**
	 * �������� ������ � ������������ ��������
	 * @return
	 */
	private static JPanel getBtn(){
		if(panBtn == null){
			panBtn = new JPanel();
			panBtn.setLayout(new MigLayout("wrap", "[fill,grow][fill,grow][fill,grow][fill,grow]"));
			
			JButton btnCleanAll = new JButton("����� ���������");
			btnCleanAll.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {
					textProcessing.cleanHighlight(txtContent);
				}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				@Override
				public void mouseClicked(MouseEvent e) {}
			});
			
			JButton btnSelectAll = new JButton("�������� ���");
			btnSelectAll.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {
					try {
						textProcessing.selectAll�oincidence(txtContent, txtText.getText());
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				@Override
				public void mouseClicked(MouseEvent e) {}
			});
			
			JButton btnSelectNext = new JButton("���������");
			btnSelectNext.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {
					textProcessing.selectNext�oincidence(txtContent, txtText.getText());
				}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});
			
			JButton btnSelectPrevious = new JButton("����������");
			btnSelectPrevious.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {
					textProcessing.selectNext�oincidence(txtContent, txtText.getText());
				}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				@Override
				public void mouseClicked(MouseEvent e) {}
			});
			
			panBtn.add(btnCleanAll);
			panBtn.add(btnSelectAll);
			panBtn.add(btnSelectNext);
			panBtn.add(btnSelectPrevious);
		}
		return panBtn;
		
	}
	
	public JFileChooser setUpdateUI(JFileChooser choose) {
		choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		choose.setDialogTitle("�������� ����������");

		UIManager.put("FileChooser.openButtonText", "�������");
		UIManager.put("FileChooser.cancelButtonText", "������");
		UIManager.put("FileChooser.folderNameLabelText", "��� �����");
		UIManager.put("FileChooser.filesOfTypeLabelText", "��� ������");

		choose.updateUI();

		return choose;
		}
}
