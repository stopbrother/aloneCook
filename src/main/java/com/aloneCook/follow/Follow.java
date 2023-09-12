package com.aloneCook.follow;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.aloneCook.user.Account;
import com.aloneCook.user.UserAccount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Follow {
	
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private Account toUser;
		
	@ManyToOne
	private Account fromUser;
	

	
}
