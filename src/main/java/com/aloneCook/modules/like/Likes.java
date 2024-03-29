package com.aloneCook.like;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

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
public class Likes {

	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private Recipe recipe;
	
	@ManyToOne
	private Account account;
	
	private boolean liked;
	
	//private LocalDateTime likedTime;
	
	
}
