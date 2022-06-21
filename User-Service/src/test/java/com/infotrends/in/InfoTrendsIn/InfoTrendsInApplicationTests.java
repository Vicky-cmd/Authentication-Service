package com.infotrends.in.InfoTrendsIn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.infotrends.in.InfoTrendsIn.resources.UserResources;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class InfoTrendsInApplicationTests {

	@Autowired
	private UserResources userResource;
	
	@Test
	void contextLoads() {
	}

}
