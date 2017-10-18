package log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class FileProcessing {
	ArrayList<String> list = new ArrayList<String>();
	Boolean isNeedToClear = true;
	private Boolean getIsNeedToClear(){
		return this.isNeedToClear;
	}
	public void setIsNeedToClear(boolean isNeed){
		this.isNeedToClear = isNeed;
	}
	
	public FileProcessing(){
		
	}
	
	/**
	 * ��������� ������ ������ � ����������� �����������
	 * � �������� � ��������� ������
	 * @param directory
	 * @param type
	 * @return
	 * @throws IOException 
	 */
	public ArrayList<String> getFileList(String directory, String type, String text) throws IOException{
		//������� ������
		clearList();
		File file = new File(directory);
		//���� ���� �������� ������
		if(file.isDirectory())
			//� � ��� ���� ��������/�����
			if (file.listFiles() != null){
				//�� �������� ������ �������� � ��������� ������� ������� � ���
				for(File inFile : file.listFiles())
					getFileList(inFile.getPath(), type, text);
			}
			//��� ������ ������ ���, �� � ���������� ���� ����
			else;
		//���� ���� - �� ����� (�� ���� ����, ������� �����-���� ����������
		else{
			//� �� ������ ������ ���������� (�� ���� ������ ����� � ����� � ����� �����������)
			if(type == null || type.equals(""))
				//��������� ������������� � ����� ������� ������, � ���� �� ��� ��������
				if(getIsExistTextInFile(file, text))
					//�� ������� ��� � ������
					list.add(file.getPath());
				//����� - ��������� ����
				else;
			//���� ������ ��� ���������� ��� �� ������, �� ��������� ��� ������������ �������� �����
			else if(!Integer.valueOf(-1).equals(file.getName().lastIndexOf(".")) &&
					file.getName().substring(file.getName().lastIndexOf("."), file.getName().length()).equals(type))
				//���� � ����������� ��� � �������, �� ���� � ���������� ��� �����
				if(getIsExistTextInFile(file, text))
					//���� ����� �������, ��������� ���� � ������
					list.add(file.getPath());
			
		}
		
		return list;
	}
	
	public Boolean getIsExistTextInFile(File file , String text) throws IOException{
		
		text = text.toLowerCase();
//		ArrayList<String> fullList = getFileList(directory, type);
		
//		for(int i = 0; i < fullList.size(); i++){
//			File file = new File(fullList.get(i));
//					
//			if(!getContent(file).toString().toLowerCase().contains(text)){
//				fullList.remove(i);
//				i--;
//			}
//		}
		
		return getContent(file).toString().toLowerCase().contains(text);
	}
	
	public StringBuilder getContent(File file) throws IOException{
		char[] CB = new char[(int) file.length()];
		Reader reader = new InputStreamReader(new FileInputStream(file), "cp1251");
		reader.read(CB);
		reader.close();
		StringBuilder content = new StringBuilder();
			
		for(char c : CB){
			content.append(c);
		}
		
		return content;
	}
	
	private void clearList(){
		if(getIsNeedToClear()){
			list.clear();
			setIsNeedToClear(false);
		}
	}
	
}
