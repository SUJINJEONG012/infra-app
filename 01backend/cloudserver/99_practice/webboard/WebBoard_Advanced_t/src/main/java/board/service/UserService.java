package board.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import board.dto.UserDto;
import board.entity.User;
import board.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;	
	private final PasswordEncoder passwordEncoder;
	
	
	public void registerUser(UserDto userDto) {
	 
		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(encodedPassword);
		//서비스에선 엔티티를 만들어준다.
		User user= userDto.toEntity();	
		// 레파지토리를 저장한다. 
		userRepository.save(user);
	}
	
	
	// UserDto로 변환하여 검색 (필요 시 사용)
	public Optional<UserDto> searchUser(String uid) {
		return userRepository.findById(uid)
				// dto로 변환해서 줌.
				.map(UserDto::from);
	}

}
