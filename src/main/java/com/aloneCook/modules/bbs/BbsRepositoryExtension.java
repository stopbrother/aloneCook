package com.aloneCook.modules.bbs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface BbsRepositoryExtension {

	Page<Bbs> findAll(Pageable pageable);
	
	Page<Bbs> findByKeyword(String keyword, Pageable pageable);
	
	Page<Bbs> findAllCategory(Category category, Pageable pageable);
}
