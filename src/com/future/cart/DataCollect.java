package com.future.cart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataCollect {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getAllDirs();
	}

	public static void getAllDirs(){
		File file = new File(Constant.PATH_DATA);
		if(!file.isDirectory()) {
			return;
		}
		String[] pathList = file.list();
		
		boolean append = false;
		for(int i=0;i<pathList.length;i++) {
		    System.out.println(pathList[i]);
		    if(pathList[i].charAt(0) == '.'){
		    	    continue;
		    }
		    String newPath = Constant.PATH_DATA + pathList[i] +"//";
			ArrayList<SnakeInfo> list = GPInfoGet.getAllSnake();
			WebDownload.readAllFutureFromFile(newPath, list);
			for(int j=0;j<list.size();j++){
				writeCollection(list.get(j), list.get(j).futureList, append);
			}
			append = true;
		}
	    System.out.println("finish!!!");
	}
	public static void writeCollection(SnakeInfo snake,List<FutureInfo> futureList, boolean append) {
		try {
			String path = Constant.PATH_COLLECTION + snake.code;
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			} else {
				if (!append) {
					System.out.println(path);
					file.delete();
					file.createNewFile();
				}
			}
			FileOutputStream out = new FileOutputStream(file, append); // 如果追加方式用true
			StringBuffer sb = new StringBuffer();
			sb = sb.append(snake.name).append(" ").append(futureList.get(0).END_DATE).append(" ").append(futureList.get(0).PRICE_PROG).append(" ");
			for (int i = 0; i < futureList.size(); i++) {
				FutureInfo info = futureList.get(i);
				sb = sb.append(info.UP_PROB).append(" ");
			}
			sb = sb.append("\n");
			out.write(sb.toString().getBytes("utf-8"));// 注意需要转换对应的字符集
			out.close();
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace());
		}
	}
}
