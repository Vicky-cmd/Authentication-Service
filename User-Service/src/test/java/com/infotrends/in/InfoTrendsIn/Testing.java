package com.infotrends.in.InfoTrendsIn;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class Testing {

	
	@Test
	public void should_print_the_fibonacci_series_till_the_count_of_10() {
		
		String expectedOutput = "0 1 1 2 3 5 8 13 21 34";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		FibonacciExample1.main(null);
		
		assertEquals(expectedOutput, outputStream.toString());

	}
}

class FibonacciExample1 {
	public static void main(String[] args) {
		int a=0,b=1,count=10,c=0;
		System.out.print(a + " " + b);
		for(int i=2; i<count; i++) {
			c=a+b;
			System.out.print(" " + c);
			a=b;
			b=c;
		}
	}
}