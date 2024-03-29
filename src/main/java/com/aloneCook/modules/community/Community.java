package com.aloneCook.community;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.aloneCook.bbs.Bbs;
import com.aloneCook.recipe.Recipe;
import com.aloneCook.user.Account;

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
