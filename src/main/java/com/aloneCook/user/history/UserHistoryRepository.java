package com.aloneCook.user.history;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.user.Account;

@Transactional(readOnly = true)
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

	List<UserHistory> findByAccountOrderByTimeStampDesc(Account account);
}
