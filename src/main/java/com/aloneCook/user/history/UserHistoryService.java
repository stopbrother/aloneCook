package com.aloneCook.user.history;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.recipe.Recipe;
import com.aloneCook.user.Account;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserHistoryService {
	
	private final UserHistoryRepository userHistoryRepository; 
	
	public void saveUserHistory(Account account, Recipe recipe) {
		UserHistory existingHistory = userHistoryRepository.findByAccountAndRecipe(account, recipe);
		if (existingHistory != null) {
			removeUserHistory(account, recipe);
		}
		UserHistory userHistory = new UserHistory();
		userHistory.setAccount(account);
		userHistory.setRecipe(recipe);
		userHistory.setTimeStamp(LocalDateTime.now());
		
		userHistoryRepository.save(userHistory);
	}

	public void removeUserHistory(Account account, Recipe recipe) {
		UserHistory userHistory = userHistoryRepository.findByAccountAndRecipe(account, recipe);
		userHistoryRepository.delete(userHistory);
	}
	
}
