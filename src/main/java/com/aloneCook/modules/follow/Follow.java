package com.aloneCook.modules.follow;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.account.UserAccount;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@AllArgsConstructor @NoArgsConstructor
public class Follow {
	
	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	private Account toUser;
		
	@ManyToOne
	private Account fromUser;
	
	private LocalDateTime followedTime;
	
}
