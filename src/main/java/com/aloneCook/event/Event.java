package com.aloneCook.event;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.aloneCook.recipe.Recipe;
import com.aloneCook.user.Account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Event {

	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private Recipe recipe;
	
	@ManyToOne
	private Account account;
}
