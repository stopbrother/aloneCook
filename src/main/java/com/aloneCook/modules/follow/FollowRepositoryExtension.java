package com.aloneCook.follow;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.user.Account;

@Transactional(readOnly = true)
public interface FollowRepositoryExtension {

	List<Follow> findByAccount(Account account, String nickname);
}
