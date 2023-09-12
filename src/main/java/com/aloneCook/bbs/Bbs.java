package com.aloneCook.bbs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.aloneCook.user.UserAccount;
import com.aloneCook.community.Community;
import com.aloneCook.user.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
//@Builder @AllArgsConstructor @NoArgsConstructor
public class Bbs {

	@Id @GeneratedValue
	private Long id;
	
	@ManyToMany
	private Set<Account> managers = new HashSet<>();
	
	@ManyToOne
	private Account writer;
	
	private String title;
	
	@Lob @Basic(fetch = FetchType.EAGER)
	private String content;
	
	@Lob @Basic(fetch = FetchType.EAGER)
	private String image;
	
	private LocalDateTime createdDateTime;
	
	private int viewCount;
	
	@OneToMany(mappedBy = "bbs")
	private List<Community> comments = new ArrayList<>();
	
	
	public void addManager(Account account) {
		this.managers.add(account);
	}

	public boolean isManagedBy(Account account) {		
		return this.getManagers().contains(account);
	}
	
	public boolean isManager(UserAccount userAccount) {
		return this.managers.contains(userAccount.getAccount());
	}

	public void addComment(Community community) {
		this.comments.add(community);
		community.setBbs(this);
	}

	public void subtractComment(Community community) {
		this.comments.remove(community);
		community.setBbs(null);
	}

	public void viewCnt(Account account) {
		if(!this.isManagedBy(account)) {
			this.viewCount++;
		}		
	}


	
}
