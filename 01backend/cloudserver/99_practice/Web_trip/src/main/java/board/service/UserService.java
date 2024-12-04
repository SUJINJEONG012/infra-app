package board.service;

import org.springframework.stereotype.Service;

import board.dto.UserDto;
import board.entity.User;
import board.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;

	public void registerUser(UserDto userDto) {
	 
		//서비스에선 엔티티를 만들어준다.
		User user= userDto.toEntity();
		
		// 레파지토리를 저장한다. 
		userRepository.save(user);
		
	}

}
