package com.aloneCook.modules.image;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.aloneCook.modules.recipe.Recipe;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Image {

	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne	
	private Recipe recipe;
	
	private String fileName;
	
	
}
