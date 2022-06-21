package com.infotrends.in.InfoTrendsIn.user;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.infotrends.in.InfoTrendsIn.business.UsersProcesses;
import com.infotrends.in.InfoTrendsIn.dao.UsersRepository;
import com.infotrends.in.InfoTrendsIn.data.Users;
import com.infotrends.in.InfoTrendsIn.exceptions.UserExceptions;
import com.infotrends.in.InfoTrendsIn.exceptions.config.ErrorsMappings;
import com.infotrends.in.InfoTrendsIn.model.UsersResponseModel;
import com.infotrends.in.InfoTrendsIn.resources.UserResources;
import com.infotrends.in.InfoTrendsIn.service.UsersService;

@ExtendWith(MockitoExtension.class)
class TestUsersService {

    @Mock
    UsersRepository usersRepository;
	
    @InjectMocks
	UsersService usersSvc;
    

    @Mock
	UsersService usersSvcMocked;
    
    @InjectMocks
	UserResources userResources;

	@Test
	void should_Return_The_User_Details_For_The_Get_Users_Method() {
		//Arrange
		String userId = "C0ID00001";		
		Users user = createUserDetails(userId);
		
		//Mock
		Mockito.when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

		// Act
		Optional<Users> usersResp = usersSvc.findById(userId);
		
		//Assert
		Mockito.verify(usersRepository).findById(userId);
		assertEquals(usersResp.isPresent(), true);
	}
	
	@Test
	void should_Throw_User_Not_Found_Excedption_For_The_Get_Users_Details_Api() {
		String userId = "C0ID00001";		
		Mockito.when(usersSvcMocked.findById(userId)).thenReturn(Optional.empty());
		UserExceptions.UserNotFoudException userException =assertThrows(UserExceptions.UserNotFoudException.class, () -> {
			userResources.getUserById(userId);
		});
		assertNotNull(userException);
		assertThat(userException.getMessage(), containsString(ErrorsMappings.USER_NOT_FOUND_MESSAGE));
	}

	private Users createUserDetails(String userId) {
		Users user = new Users();
		user.setId(userId);
		user.setFullname("One");
		user.setUsername("one@one.com");
		user.setDeletedFlag("N");
		user.setIsAdmin("N");
		return user;
	}

}
