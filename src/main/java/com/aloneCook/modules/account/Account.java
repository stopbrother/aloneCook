package com.aloneCook.modules.account;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.aloneCook.modules.follow.Follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {

	@Id @GeneratedValue
	private Long id;
	
	@Column(unique = true)
	private String nickname;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	private LocalDateTime joinedAt; //인증이 된 사용자 가입.

	private String intro;
	
	private String url;
	
	@Lob @Basic(fetch = FetchType.EAGER)
	private String profileImg;
	
	private String emailToken;
	
	private boolean active; //활성여부
	
	private LocalDateTime deletedAt; //회원탈퇴일

	/*
	@OneToMany(mappedBy = "fromUser")
	private List<Follow> followers;
	
	@OneToMany(mappedBy = "toUser")
	private List<Follow> following;
*/
	
	
	
	
/*
	@OneToMany(mappedBy = "fromUser")
	private List<Follow> follower = new ArrayList<>();


	@OneToMany(mappedBy = "toUser")
	private List<Follow> following = new ArrayList<>();



	public boolean isFollowed(UserAccount userAccount) {
		Account account = userAccount.getAccount();
		for(Follow f : this.following) {
			if (f.getToUser().equals(account)) {
				return true;
			}
		}
		return false;
	}



	public void addFollowing(Follow follow) {
		this.following.add(follow);
	}


*/





	
}
