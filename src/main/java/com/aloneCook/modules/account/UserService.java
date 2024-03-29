package com.aloneCook.user;

import java.util.List;
import java.util.Optional;

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
import org.thymeleaf.context.Context;

import com.aloneCook.follow.Follow;
import com.aloneCook.follow.FollowRepository;
import com.aloneCook.user.form.JoinForm;
import com.aloneCook.user.form.Profile;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	private final UserRepository userRepository;
	private final FollowRepository followRepository;

	
	public Account saveNewUser(@Valid JoinForm joinForm) {
		Account account = Account.builder()
				.nickname(joinForm.getNickname())
				.email(joinForm.getEmail())
				.password(passwordEncoder.encode(joinForm.getPassword()))
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
