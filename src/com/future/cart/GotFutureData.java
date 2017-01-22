package com.future.cart;

import java.util.List;

public class GotFutureData {
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String s=HttpRequest.sendGet("http://stgy.upchina.com/jsonpredit/GET_1000002.html",null);
//        System.out.println(s);
//        List<FutureInfo> list = Parser.parse(s);
//        System.out.println(list.size());
        
        WebDownload.gotAllSnakesCategory();
	}
	
	
}
