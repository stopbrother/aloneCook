package com.aloneCook.modules.follow;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.modules.account.Account;

@Transactional(readOnly = true)
public interface FollowRepositoryExtension {

	List<Follow> findByAccount(Account account, String nickname);
}
