package com.infotrends.in.InfoTrendsIn.user;

import java.math.BigDecimal;
import java.util.Comparator;

interface T1 {
	int add(int i, int j);
}
class T {
	int add(int i, int j) {
		return i+j;
	}
}
public class Test extends T {
	public static void main(String[] args) {
		System.out.println(3.0 + 3 + new Byte("3"));
		Comparator<String> c = (a, b) -> {
            BigDecimal b1 = new BigDecimal(a);
            BigDecimal b2 = new BigDecimal(b);
            return b2.compareTo(b1); 
        };
				
	}
}
