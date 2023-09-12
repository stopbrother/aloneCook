package com.aloneCook.follow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.user.Account;

@Transactional(readOnly = true)
public interface FollowRepository extends JpaRepository<Follow, Long> {

	boolean existsByToUserAndFromUser(Account byNickname, Account account);
	
	Follow findByFromUserAndToUser(Account byNickname, Account account);		
	
	List<Follow> findAllByToUser(Account account);

	List<Follow> findAllByFromUser(Account account);

	long countByToUser(Account byNickname);

	long countByFromUser(Account byNickname);

	
	
	
	

}
