package com.future.cart;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CompareUtil {
	public static final int DAY_1 = 0;
	public static final int DAY_5 = 1;
	public static final int DAY_10 = 2;
	public static final int DAY_20 = 3;
	public static final int DAY_ALL_SUM = 4;
	public static final int DAY_ALL_MULTI = 5;

	public static void sort(List<SnakeInfo> list, int day) {
		Collections.sort(list, new Comparator<SnakeInfo>() {
			public int compare(SnakeInfo arg0, SnakeInfo arg1) {
				if (arg0.futureList == null || arg1.futureList == null) {
					return 1;
				}
				if (arg0.futureList.size() < 4 || arg1.futureList.size() < 4) {
					return 1;
				}
				if (day <= DAY_20) {
					if (arg0.futureList.get(day).UP_PROB > arg1.futureList.get(day).UP_PROB) {
						return -1;
					} else if (arg0.futureList.get(day).UP_PROB < arg1.futureList.get(day).UP_PROB) {
						return 1;
					} else {
						return 0;
					}
				} else {
					double d0;
					double d1;
					if (day == DAY_ALL_SUM) {
						d0 = getSumProb(arg0.futureList);
						d1 = getSumProb(arg1.futureList);
					} else {
						d0 = getMultiProb(arg0.futureList);
						d1 = getMultiProb(arg1.futureList);
					}
					if (d0 > d1) {
						return -1;
					} else if (d0 < d1) {
						return 1;
					} else {
						return 0;
					}
				}
			}
		});
	}
	public static double getSumProb(List<FutureInfo> info){
		double d = 0;
		for(int i=0;i<info.size();i++){
			d += info.get(i).UP_PROB;
		}
		return d;
	}
	public static double getMultiProb(List<FutureInfo> info){
		double d = 1;
		for(int i=0;i<info.size();i++){
			d *= info.get(i).UP_PROB;
		}
		return d;
	}
}
