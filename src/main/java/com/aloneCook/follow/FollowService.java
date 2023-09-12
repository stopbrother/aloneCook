package com.aloneCook.follow;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.user.Account;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
	
	private final FollowRepository followRepository;

	public void addFollow(Account nickname, Account account) {
		if (!followRepository.existsByToUserAndFromUser(nickname, account)
				&& !nickname.equals(account)) {
			Follow follow = new Follow();
			follow.setToUser(nickname);
			follow.setFromUser(account);
			//account.addFollowing(follow);
			followRepository.save(follow);
		}
	}
	
	public void unFollow(Account byNickname, Account account) {
		Follow follow = followRepository.findByFromUserAndToUser(account, byNickname);
		followRepository.delete(follow);
	}



	

}
