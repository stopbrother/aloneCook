package com.aloneCook.bbs;

import javax.persistence.EnumType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;

public class BbsRepositoryExtensionImpl extends QuerydslRepositorySupport implements BbsRepositoryExtension {

	public BbsRepositoryExtensionImpl() {
		super(Bbs.class);
	}
	
	@Override
	public Page<Bbs> findAll(Pageable pageable) {
		QBbs bbs = QBbs.bbs;
		JPQLQuery<Bbs> result = from(bbs);
		JPQLQuery<Bbs> pageableQuery = getQuerydsl().applyPagination(pageable, result);
		QueryResults<Bbs> fetchResults = pageableQuery.fetchResults();
		return new PageImpl<Bbs>(fetchResults.getResults(), pageable, fetchResults.getTotal());
	}
	
	@Override
	public Page<Bbs> findByKeyword(String keyword, Pageable pageable) {
		QBbs bbs = QBbs.bbs;
		JPQLQuery<Bbs> query = from(bbs).where(bbs.title.containsIgnoreCase(keyword));
		JPQLQuery<Bbs> pageableQuery = getQuerydsl().applyPagination(pageable, query);
		QueryResults<Bbs> fetchResults = pageableQuery.fetchResults();
		return new PageImpl<Bbs>(fetchResults.getResults(), pageable, fetchResults.getTotal());
	}
	
	@Override
	public Page<Bbs> findByCategory(Category category, Pageable pageable) {
		QBbs bbs = QBbs.bbs;		
		JPQLQuery<Bbs> query = from(bbs).where(bbs.category.eq(category));
		JPQLQuery<Bbs> pageableQuery = getQuerydsl().applyPagination(pageable, query);
		QueryResults<Bbs> fetchResults = pageableQuery.fetchResults();
		return new PageImpl<Bbs>(fetchResults.getResults(), pageable, fetchResults.getTotal());
	}
	
}
