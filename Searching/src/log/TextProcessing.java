package log;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class TextProcessing {
	private static AttributeSet setForHighlight;
	private static AttributeSet setForJustInsert;

	public TextProcessing(){
		setForHighlight = StyleContext.getDefaultStyleContext().addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, Color.GRAY);
		setForJustInsert = StyleContext.getDefaultStyleContext().addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, Color.WHITE);
	}
	
	public void selectAll�oincidence(JTextPane text, String coincidence) throws BadLocationException{
		String strText = text.getText().toLowerCase();
		coincidence = coincidence.toLowerCase();
		String rest = "";
		ArrayList<String> arrText = new ArrayList<String>();
		
		while(true){
			if(strText.indexOf(coincidence) != -1){
				arrText.add(strText.substring(0, strText.indexOf(coincidence)));
				arrText.add(coincidence);
				
				strText = strText.substring(strText.indexOf(coincidence) + coincidence.length());
			}
			else{
				arrText.add(strText);
				break;
			}
		}
		
		text.setText("");
		
		for(String s : arrText){
			text.getStyledDocument().insertString(text.getStyledDocument().getLength(), s, s.equals(coincidence) ? setForHighlight : setForJustInsert);
		}
		text.getStyledDocument().insertString(text.getStyledDocument().getLength(), "", setForJustInsert);
	}
	
	public void cleanHighlight(JTextPane text){
		text.grabFocus();
		text.getStyledDocument().setCharacterAttributes(0, text.getText().length(), setForJustInsert, true);
		text.select(0, 0);
	}
	
	public void selectNext�oincidence(JEditorPane text, String coincidence){
		coincidence = coincidence.toLowerCase();
		//����������� �����
		String startText = text.getText().replace("\r\n", " ").toLowerCase();
		//����� ������ ����� ������ ���������/�������
		String rest = "";
		//����� ������ ����� ���������/�������
		String subStartText = "";
		//���� ���� ���������� �����
		if(text.getSelectedText() != null){
			rest = startText.substring(0, text.getSelectionEnd());
			subStartText = startText.substring(text.getSelectionEnd(), startText.length());
			//���� � ������ ����� ��������� ���� ����������
			if(subStartText.indexOf(coincidence) != -1){
				//�� �������� ���
				text.grabFocus();
				text.select(rest.length() + subStartText.indexOf(coincidence), rest.length() + subStartText.indexOf(coincidence)+coincidence.length());
			}
			//���� ����� ����������� ��������� ���������� �� �������
			else{
				//�� ���� �� � ������ ������ ������, � ���� �������
				if(startText.indexOf(coincidence) != -1){
					//�� ��������
					text.grabFocus();
					text.select(startText.indexOf(coincidence), startText.indexOf(coincidence) + coincidence.length());
				}
			}
		}
		//���� ����������� ��������� ������ �� ���������
		else {
			//�� ���� �� ���� �����, ��� ��������� ������
			rest = startText.substring(0, text.getCaretPosition());
			subStartText = startText.substring(text.getCaretPosition(), startText.length());
			//���� � ������ ����� ������� ���� ����������
			if(subStartText.indexOf(coincidence) != -1){
				//�� �������� ���
				text.grabFocus();
				text.select(rest.length()+subStartText.indexOf(coincidence), rest.length()+subStartText.indexOf(coincidence)+coincidence.length());
			}
			//���� ����� ������� ���������� �� �������
			else{
				//�� ���� �� � ������ ������ ������, � ���� �������
				if(startText.indexOf(coincidence) != -1){
					//�� ��������
					text.grabFocus();
					text.select(startText.indexOf(coincidence), startText.indexOf(coincidence) + coincidence.length());
				}
			}
		}
	}
	
	public void selectPrevious�oincidence(JEditorPane text, String coincidence){
		//����������� �����
		String startText = text.getText().replace("\r\n", " ");
		//����� ������ ����� ������ ���������/�������
		String rest = "";
		//����� ������ ����� ����������/��������
		String subStartText = "";
		//���� ���� ���������� �����
		if(text.getSelectedText() != null){
			rest = startText.substring(text.getSelectionStart(), startText.length());
			subStartText = startText.substring(0, text.getSelectionStart());
			//���� � ������ ����� ���������� ���� ����������
			if(subStartText.lastIndexOf(coincidence) != -1){
				//�� �������� ���
				text.grabFocus();
				text.select(subStartText.lastIndexOf(coincidence), subStartText.lastIndexOf(coincidence) + coincidence.length());
			}
			//���� ����� ���������� ���������� ���������� �� �������
			else{
				//�� ���� �� � ������ ����� ������, � ���� �������
				if(startText.lastIndexOf(coincidence) != -1){
					//�� ��������
					text.grabFocus();
					text.select(startText.lastIndexOf(coincidence), startText.lastIndexOf(coincidence) + coincidence.length());
				}
			}
		}
		//���� ����������� ��������� ������ �� ���������
		else {
			//�� ���� ����� ��� ������, ��� ��������� ������
			rest = startText.substring(text.getCaretPosition(), startText.length());
			subStartText = startText.substring(0, text.getCaretPosition());
			//���� � ������ ����� �������� ���� ����������
			if(subStartText.lastIndexOf(coincidence) != -1){
				//�� �������� ���
				text.grabFocus();
				text.select(subStartText.lastIndexOf(coincidence), subStartText.lastIndexOf(coincidence) + coincidence.length());
			}
			//���� ����� ������� ���������� �� �������
			else{
				//�� ���� �� � ������ ����� ������, � ���� �������
				if(startText.lastIndexOf(coincidence) != -1){
					//�� ��������
					text.grabFocus();
					text.select(startText.lastIndexOf(coincidence), startText.lastIndexOf(coincidence) + coincidence.length());
				}
			}
		}
	}
}
