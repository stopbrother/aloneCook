package com.aloneCook.modules.follow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.modules.account.Account;

@Transactional(readOnly = true)
public interface FollowRepository extends JpaRepository<Follow, Long> {

	boolean existsByToUserAndFromUser(Account byNickname, Account account);
	
	Follow findByFromUserAndToUser(Account byNickname, Account account);		

	long countByToUser(Account byNickname);

	long countByFromUser(Account byNickname);

	List<Follow> findByToUser(Account account);
	
	List<Follow> findByFromUser(Account account);

	List<Follow> findAllByToUserOrderByFollowedTimeDesc(Account account);
	
	List<Follow> findAllByFromUserOrderByFollowedTimeDesc(Account account);


}
