package com.aloneCook.modules.community;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.modules.bbs.Bbs;
import com.aloneCook.modules.recipe.Recipe;

@Transactional(readOnly = true)
public interface CommunityRepository extends JpaRepository<Community, Long> {

		

	Community findByComment(String comment);

	List<Community> findByRecipeOrderByCreateDateTimeDesc(Recipe recipe);

	List<Community> findByBbsOrderByCreateDateTimeDesc(Bbs bbs);

	long countByBbs(Bbs bbs);
}
