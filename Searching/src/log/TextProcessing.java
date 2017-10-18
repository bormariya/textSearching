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
	
	public void selectAllСoincidence(JTextPane text, String coincidence) throws BadLocationException{
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
	
	public void selectNextСoincidence(JEditorPane text, String coincidence){
		coincidence = coincidence.toLowerCase();
		//изначальный текст
		String startText = text.getText().replace("\r\n", " ").toLowerCase();
		//часть текста перед концом выделения/курсора
		String rest = "";
		//часть текста после выделения/курсора
		String subStartText = "";
		//если есть выделенный текст
		if(text.getSelectedText() != null){
			rest = startText.substring(0, text.getSelectionEnd());
			subStartText = startText.substring(text.getSelectionEnd(), startText.length());
			//если в тексте после выделения есть совпадение
			if(subStartText.indexOf(coincidence) != -1){
				//то выделяем его
				text.grabFocus();
				text.select(rest.length() + subStartText.indexOf(coincidence), rest.length() + subStartText.indexOf(coincidence)+coincidence.length());
			}
			//если после выделенного фрагмента совпадения не нашлись
			else{
				//то ищем их с самого начала текста, и если находим
				if(startText.indexOf(coincidence) != -1){
					//то выделяем
					text.grabFocus();
					text.select(startText.indexOf(coincidence), startText.indexOf(coincidence) + coincidence.length());
				}
			}
		}
		//если выделенного фрагмента текста не оказалось
		else {
			//то ищем от того места, где находится курсор
			rest = startText.substring(0, text.getCaretPosition());
			subStartText = startText.substring(text.getCaretPosition(), startText.length());
			//если в тексте после курсора есть совпадение
			if(subStartText.indexOf(coincidence) != -1){
				//то выделяем его
				text.grabFocus();
				text.select(rest.length()+subStartText.indexOf(coincidence), rest.length()+subStartText.indexOf(coincidence)+coincidence.length());
			}
			//если после курсора совпадения не нашлись
			else{
				//то ищем их с самого начала текста, и если находим
				if(startText.indexOf(coincidence) != -1){
					//то выделяем
					text.grabFocus();
					text.select(startText.indexOf(coincidence), startText.indexOf(coincidence) + coincidence.length());
				}
			}
		}
	}
	
	public void selectPreviousСoincidence(JEditorPane text, String coincidence){
		//изначальный текст
		String startText = text.getText().replace("\r\n", " ");
		//часть текста после начала выделения/курсора
		String rest = "";
		//часть текста перед выделением/курсором
		String subStartText = "";
		//если есть выделенный текст
		if(text.getSelectedText() != null){
			rest = startText.substring(text.getSelectionStart(), startText.length());
			subStartText = startText.substring(0, text.getSelectionStart());
			//если в тексте перед выделением есть совпадение
			if(subStartText.lastIndexOf(coincidence) != -1){
				//то выделяем его
				text.grabFocus();
				text.select(subStartText.lastIndexOf(coincidence), subStartText.lastIndexOf(coincidence) + coincidence.length());
			}
			//если перед выделенным фрагментом совпадения не нашлись
			else{
				//то ищем их с самого конца текста, и если находим
				if(startText.lastIndexOf(coincidence) != -1){
					//то выделяем
					text.grabFocus();
					text.select(startText.lastIndexOf(coincidence), startText.lastIndexOf(coincidence) + coincidence.length());
				}
			}
		}
		//если выделенного фрагмента текста не оказалось
		else {
			//то ищем перед тем местом, где находится курсор
			rest = startText.substring(text.getCaretPosition(), startText.length());
			subStartText = startText.substring(0, text.getCaretPosition());
			//если в тексте перед курсором есть совпадение
			if(subStartText.lastIndexOf(coincidence) != -1){
				//то выделяем его
				text.grabFocus();
				text.select(subStartText.lastIndexOf(coincidence), subStartText.lastIndexOf(coincidence) + coincidence.length());
			}
			//если после курсора совпадения не нашлись
			else{
				//то ищем их с самого конца текста, и если находим
				if(startText.lastIndexOf(coincidence) != -1){
					//то выделяем
					text.grabFocus();
					text.select(startText.lastIndexOf(coincidence), startText.lastIndexOf(coincidence) + coincidence.length());
				}
			}
		}
	}
}
