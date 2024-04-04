package com.aloneCook.modules.bbs;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.modules.bbs.Bbs;
import com.aloneCook.modules.bbs.Category;

@Transactional(readOnly = true)
public interface BbsRepository extends JpaRepository<Bbs, Long>, BbsRepositoryExtension {

	

	
}
