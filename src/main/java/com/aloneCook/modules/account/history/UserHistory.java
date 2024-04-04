package com.aloneCook.modules.account.history;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.recipe.Recipe;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
public class UserHistory {

	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private Account account;
	
	@ManyToOne	
	private Recipe recipe;
	
	private LocalDateTime timeStamp;
}
