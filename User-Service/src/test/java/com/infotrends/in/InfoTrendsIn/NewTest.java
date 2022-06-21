package com.infotrends.in.InfoTrendsIn;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NewTest {

	Source gradingScheme = new Source();
	
	@Test
	public void test() {
		assertEquals('X',gradingScheme.getGrades(101));
		assertEquals('A',gradingScheme.getGrades(100));
		assertEquals('A',gradingScheme.getGrades(80));
		assertEquals('B',gradingScheme.getGrades(79));
		assertEquals('B',gradingScheme.getGrades(60));
		assertEquals('C',gradingScheme.getGrades(59));
		assertEquals('C',gradingScheme.getGrades(40));
		assertEquals('F',gradingScheme.getGrades(39));
		assertEquals('X',gradingScheme.getGrades(-1));
	}
}

class Source{
  public char getGrades(int marks){
	  if(marks> 100 || marks<0)
		  return 'X';
	  else if(marks>=80)
		  return 'A';
	  else if(marks>=60)
		  return 'B';
	  else if(marks>=40)
		  return 'C';
	  else
		  return 'F';
  }	
}