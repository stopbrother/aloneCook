package com.aloneCook.modules.account;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.aloneCook.infra.config.AppProperties;
import com.aloneCook.infra.email.EmailMessage;
import com.aloneCook.infra.email.EmailService;
import com.aloneCook.modules.account.form.JoinForm;
import com.aloneCook.modules.account.form.Profile;
import com.aloneCook.modules.follow.Follow;
import com.aloneCook.modules.follow.FollowRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	private final UserRepository userRepository;
	private final FollowRepository followRepository;
	private final AppProperties appProperties;
	private final TemplateEngine templateEngine;
	private final EmailService emailService;

	
	public Account saveNewUser(@Valid JoinForm joinForm) {
		Account account = Account.builder()
				.nickname(joinForm.getNickname())
				.email(joinForm.getEmail())
				.password(passwordEncoder.encode(joinForm.getPassword()))
				.active(true)
				.build();
		
		 return userRepository.save(account);
		/*		
		joinForm.setPassword(passwordEncoder.encode(joinForm.getPassword()));
		UserDTO user = modelMapper.map(joinForm, UserDTO.class);
		//user.createEmailCheckToken();
		return userRepository.save(user); */
	}
	
	public void login(Account account) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				new UserAccount(account),
				account.getPassword(),
				List.of(new SimpleGrantedAuthority("USER")));
		SecurityContextHolder.getContext().setAuthentication(token);
	}

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { //DB정보와 일치하는지
		Account account = userRepository.findByEmail(email);
		if (account == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserAccount(account);
	}

	public void updateProfile(Account account, Profile profile) {
		modelMapper.map(profile, account);
		userRepository.save(account);
	}
	public void updateNickname(Account account, String nickname) {
		account.setNickname(nickname);
		userRepository.save(account);
		login(account);
	}



	public void updatePassword(Account account, String newPassword) {
		account.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(account);
	}
	public void resetPassword(Account account, String resetPassword) {
		account.setPassword(passwordEncoder.encode(resetPassword));
		account.setEmailToken(null); //토큰만료
		userRepository.save(account);
	}


	public Account getAccount(String nickname) {
		Account account = userRepository.findByNickname(nickname);
		if (account == null) {
			throw new IllegalArgumentException("해당하는 사용자가 없습니다.");
		}
		return account;
	}

	public boolean isCurrentPasswordValid(Account account, String currentPassword) {
		Account user = userRepository.findByEmail(account.getEmail());
	
		return passwordEncoder.matches(currentPassword, user.getPassword());
	}

	public void resetSendEmail(Account account) {
		String token = UUID.randomUUID().toString();
		account.setEmailToken(token);
		userRepository.save(account);

		Context context = new Context();
		context.setVariable("link", "/reset-password?token=" + account.getEmailToken() 
				+ "&email=" + account.getEmail());
		context.setVariable("nickname", account.getNickname());
		context.setVariable("linkName", "비밀번호 재설정");
		context.setVariable("message", "비밀번호 초기화");
		context.setVariable("host", appProperties.getHost());
		
		String message = templateEngine.process("mail/simple-link", context);
		
		EmailMessage emailMessage = EmailMessage.builder()
				.to(account.getEmail())
				.title("나혼자요리 비밀번호 초기화")
				.message(message)
				.build();
		emailService.sendEmail(emailMessage);
	}

	public boolean isValidToken(String token, Account account) {
		
		return account != null && token.equals(account.getEmailToken());
	}

	public void deleteAt(Account account) {
		account.activeOff();
		userRepository.save(account);
	}





	

	

/*
	public void addFollow(Account byNickname, Account account) {
		if (!followRepository.existsByToUserAndFromUser(byNickname, account)) {
			Follow follow = new Follow();
			follow.setToUser(byNickname);
			follow.setFromUser(account);
			//follow.setFollowed(true);
			//account.addFollowing(follow);
			//byNickname.addFollower(account);
			followRepository.save(follow);
		}		
	}
*/











	

}
