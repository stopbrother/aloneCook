package com.aloneCook.modules.account.history;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.recipe.Recipe;

@Transactional(readOnly = true)
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
		
	List<UserHistory> findByAccountOrderByTimeStampDesc(Account account);

	UserHistory findByAccountAndRecipe(Account account, Recipe recipe);	
	
}
