package com.aloneCook.modules.community;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.bbs.Bbs;
import com.aloneCook.modules.recipe.Recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Community {

	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private Account account;
	
	@ManyToOne
	private Recipe recipe;
	
	@ManyToOne
	private Bbs bbs;
	
	private String comment;
	
	private LocalDateTime createDateTime;
}
