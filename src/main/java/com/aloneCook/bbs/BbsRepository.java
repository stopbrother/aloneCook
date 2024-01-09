package com.aloneCook.bbs;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.aloneCook.bbs.Category;
import com.aloneCook.bbs.Bbs;

@Transactional(readOnly = true)
public interface BbsRepository extends JpaRepository<Bbs, Long>, BbsRepositoryExtension {

	

	
}
