package com.aloneCook.modules.recipe;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.springframework.web.multipart.MultipartFile;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.account.UserAccount;
import com.aloneCook.modules.community.Community;
import com.aloneCook.modules.image.Image;
import com.aloneCook.modules.like.Likes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")

public class Recipe {

	@Id @GeneratedValue
	private Long id;
	
	@ManyToMany
	private Set<Account> manager = new HashSet<>();
	
	@ManyToOne
	private Account writer;	
	
	@Column(unique = true)
	private String path;
	
	@Lob @Basic(fetch = FetchType.EAGER)
	private String foodImg;
	
	private String title;
	
	private String foodIntro;
	
	@Enumerated(EnumType.STRING)
	private Level level;
	
	@Lob @Basic(fetch = FetchType.EAGER)
	private String ingredients;
	
	@Lob @Basic(fetch = FetchType.EAGER)
	private String steps;
	
	private String videoUrl;
		
	private LocalDateTime createdDateTime;
	
	private LocalDateTime publishedDateTime;
	
	private boolean published;
	
	private boolean drafted;
	
	private Long viewCount = 0L; //초기값을 0으로 설정
	
	private Long likeCount = 0L;
	
	private Long commentCount = 0L;
	
	@OneToMany(mappedBy = "recipe")
	//@OrderBy("likedTime")
	private List<Likes> likes = new ArrayList<>();
	
	@OrderBy("id")
	@OneToMany(mappedBy = "recipe")	
	private List<Community> comments = new ArrayList<>();
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<Image> images = new ArrayList<>();
	
	
	public void viewCnt(Account account) {
		if(!this.isManage(account)) {
			this.viewCount++;
		}
	}
	
	
	
	public void likeAdd(Likes likes) {		
		this.likes.add(likes);
		this.likeCountAdd();
		likes.setRecipe(this);
	}
	
	private void likeCountAdd() {
		if (this.likeCount == null) {
			this.likeCount = 0L;
		}
		this.likeCount++;
	}



	public void removeLike(Likes like) {
		this.likes.remove(like);
		this.likeCount--;
		like.setRecipe(null);
	}
	
	public boolean isLiker(UserAccount userAccount) {
		Account account = userAccount.getAccount();
		for (Likes e : this.likes) {
			if (e.getAccount().equals(account)) {
				return true;
			}
		}
		return false;
	}


	
	/*
	public boolean isLiker(UserAccount userAccount) {
		return this.likes.contains(userAccount.getUserDTO());
	}
	
	public void addLike(UserDTO userDTO) {
		this.getLikes().add(userDTO);
		this.likeCount++;
	}
	
	public void removeLike(UserDTO userDTO) {
		this.getLikes().remove(userDTO);
		this.likeCount--;
	}
	*/
	
	
	public String getEncodedPath() {
		return URLEncoder.encode(this.path, StandardCharsets.UTF_8);
	}
	
	public void addManager(Account account) {
		this.manager.add(account);
	}

	public boolean isManage(Account account) {
		return this.getManager().contains(account);
	}
/*
	public void publish() {
		this.published = true;
		this.createdDateTime = LocalDateTime.now();
	}
*/


	public void addComment(Community community) {
		this.comments.add(community);
		this.commentCount++;
		community.setRecipe(this);
	}

	public void removeComment(Community community) {
		this.comments.remove(community);
		this.commentCount--;
		community.setRecipe(null);
	}

	




	







	
	
	
	
	
/*
	public void addManager(UserDTO userDTO) {
		this.manager.add(userDTO);
	}
*/	
	
}
