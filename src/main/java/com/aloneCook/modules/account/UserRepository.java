package com.aloneCook.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<Account, Long> {
	
	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);

	Account findByEmail(String email);

	Account findByNickname(String nickname);

	@EntityGraph(attributePaths = {"followings", "followers"})
	Account findAccountWithFollowingsAndFollowersBynickname(String nickname);

	Account findAccountWithFollowingsBynickname(String nickname);



	

	

	

}
