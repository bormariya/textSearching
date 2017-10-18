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
	 * Получение списка файлов с переданными параметрами
	 * в исходной и вложенных папках
	 * @param directory
	 * @param type
	 * @return
	 * @throws IOException 
	 */
	public ArrayList<String> getFileList(String directory, String type, String text) throws IOException{
		//очищаем список
		clearList();
		File file = new File(directory);
		//если файл является папкой
		if(file.isDirectory())
			//и в ней есть подпапки/файлы
			if (file.listFiles() != null){
				//то получить список влодений и применить текущую функцию к ним
				for(File inFile : file.listFiles())
					getFileList(inFile.getPath(), type, text);
			}
			//ели внутри ничего нет, то и пропускаем этот файл
			else;
		//если файл - не папка (то есть файл, имеющий какое-нибо расширение
		else{
			//и не задано нужное расширение (то есть искать нужно в файле с любым расширением)
			if(type == null || type.equals(""))
				//проверяем существование в файле нужного текста, и если он там окажется
				if(getIsExistTextInFile(file, text))
					//то добавим его в список
					list.add(file.getPath());
				//иначе - пропустим файл
				else;
			//если нужное нам расширение все же задано, то проверяем его соответствие текущему файлу
			else if(!Integer.valueOf(-1).equals(file.getName().lastIndexOf(".")) &&
					file.getName().substring(file.getName().lastIndexOf("."), file.getName().length()).equals(type))
				//если с расширением все в порядке, то ищем в содержимом наш текст
				if(getIsExistTextInFile(file, text))
					//если текст нашелся, добавляем файл в список
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
