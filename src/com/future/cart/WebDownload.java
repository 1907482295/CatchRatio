package com.future.cart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebDownload {
	public static void main(String[] args) {
		GPInfoGet.getAllSnake();
		gotAllSnakeFuture();
		
	}
	public static boolean downloadFuture(SnakeInfo snake) {
		String url = "http://stgy.upchina.com/jsonpredit/GET_1" + snake.code + ".html";
		String s = HttpRequest.sendGet(url, null);
		if(s == null || s.isEmpty()) {
			return false;
		}
		System.out.println(s);
		List<FutureInfo> list = Parser.parse(s);
//		snake.futureList = list;
		if(list == null || list.size() < 4)
			return false;
		writeFutureInfo(snake, s);
		return true;
		// System.out.println(list.size());
	}

	public static void gotAllSnakeFuture() {
		ArrayList<SnakeInfo> list = GPInfoGet.mSnakeInfos;
        if(list == null) {
        	    return;
        }
        int i = 0;
		while(true) {
			if(i >= list.size()) {
				break;
			}
			SnakeInfo snake = list.get(i);
			if(snake == null) {
				list.remove(i);
				continue;
			}
			if(!downloadFuture(snake)){
				list.remove(i);
				continue;
			}
			i++;
		}
	}

	public static void gotAllSnakesCategory() {
		GPInfoGet.getAllSnake();
		//gotAllSnakeFuture();
		//writeNewSnakeInfo();
		readAllFutureFromFile();
		CompareUtil.sort(GPInfoGet.mSnakeInfos, CompareUtil.DAY_1);
		writeLog(CompareUtil.DAY_1);
		CompareUtil.sort(GPInfoGet.mSnakeInfos, CompareUtil.DAY_5);
		writeLog(CompareUtil.DAY_5);
		CompareUtil.sort(GPInfoGet.mSnakeInfos, CompareUtil.DAY_10);
		writeLog(CompareUtil.DAY_10);
		CompareUtil.sort(GPInfoGet.mSnakeInfos, CompareUtil.DAY_20);
		writeLog(CompareUtil.DAY_20);
		CompareUtil.sort(GPInfoGet.mSnakeInfos, CompareUtil.DAY_ALL);
		writeLog(CompareUtil.DAY_ALL);
		System.out.println("Finish All");
	}
	public static void readAllFutureFromFile(){
		ArrayList<SnakeInfo> list = GPInfoGet.mSnakeInfos;
        if(list == null) {
        	    return;
        }
        int i = 0;
		while(true) {
			if(i >= list.size()) {
				break;
			}
			SnakeInfo snake = list.get(i);
			if(snake == null) {
				list.remove(i);
				continue;
			}
			String str = readFutureFile(snake);
			if(str == null || str.isEmpty()){
				list.remove(i);
				continue;
			}
			List<FutureInfo> info = Parser.parse(str);
			if(info == null) {
				list.remove(i);
				continue;
			}
			i++;
			snake.futureList = info;
		}
	}
	public static String readFutureFile(SnakeInfo snake) {
		StringBuilder sb = new StringBuilder();
		try {
			String encoding = "UTF-8";
			String filePath = "//Users//wangpeng//Project//Java//CatchRatio//future//" + snake.code;
			File file = new File(filePath);			
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					sb.append(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return null;
		}
		return sb.toString();

	}
	public static void writeFutureInfo(SnakeInfo snake, String str) {
		try {
			String path = "//Users//wangpeng//Project//Java//CatchRatio//future//"+snake.code;
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream out = new FileOutputStream(file, false); // 如果追加方式用true
			out.write(str.getBytes("utf-8"));// 注意需要转换对应的字符集
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public static void writeNewSnakeInfo() {
		try {
			String path = "//Users//wangpeng//Project//Java//CatchRatio//SnakeAll.txt";
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream out = new FileOutputStream(file, false); // 如果追加方式用true
			StringBuffer sb = new StringBuffer();
			ArrayList<SnakeInfo> list = GPInfoGet.mSnakeInfos;
			for (int i = 0; i < list.size(); i++) {
				sb = new StringBuffer();
				SnakeInfo info = list.get(i);
				sb.append(info.name).append(" (").append(info.code).append(")\n");
			    out.write(sb.toString().getBytes("utf-8"));// 注意需要转换对应的字符集
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	public static void writeLog(int day) {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String fileName = sdf.format(new Date());
			String path = "//Users//wangpeng//Project//Java//CatchRatio//" + fileName+"_"+getDayStr(day);
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream out = new FileOutputStream(file, false); // 如果追加方式用true
			StringBuffer sb = new StringBuffer();
			ArrayList<SnakeInfo> list = GPInfoGet.mSnakeInfos;
			for (int i = 0; i < list.size(); i++) {
				sb = new StringBuffer();
				SnakeInfo info = list.get(i);
				sb = sb.append(info.name).append(" ").append(info.code).append(" ");
				if (info.futureList != null && info.futureList.size() >= 4) {
					if (day <= CompareUtil.DAY_20) {
						sb = sb.append(String.valueOf(info.futureList.get(day).UP_PROB)).append(" ");
					}
					for (int j = 0; j < 4; j++) {
						if (j != day) {
							sb = sb.append(String.valueOf(info.futureList.get(j).UP_PROB)).append(" ");
						}
					}
					sb = sb.append(String.valueOf(info.futureList.get(0).PRICE_PROG)).append("\n");
				}
				out.write(sb.toString().getBytes("utf-8"));// 注意需要转换对应的字符集
			}
			out.close();
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace());
		}
	}
	
	public static String getDayStr(int day){
		switch(day){
		case 0:
			return "1";
		case 1:
			return "5";
		case 2:
			return "10";
		case 3:
			return "20";
		case 4:
			return "ALL";
		}
		return "err";
	}
}
