package com.aloneCook.modules.bbs;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloneCook.modules.account.Account;
import com.aloneCook.modules.community.Community;
import com.aloneCook.modules.community.CommunityRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BbsService {
	
	private final CommunityRepository communityRepository;
	private final BbsRepository bbsRepository;
	private final ModelMapper modelMapper;
	private final ApplicationEventPublisher eventPublisher;
	
	private void checkIfManager(Account account, Bbs bbs) {
		if (!bbs.isManagedBy(account)) {
			throw new AccessDeniedException("해당 기능을 사용할 수 없습니다.");
		}
	}
	
	public Bbs createNewBbs(Bbs bbs, Account account) {
		Bbs newBbs = bbsRepository.save(bbs);
		newBbs.addManager(account);
		newBbs.setWriter(account);
		newBbs.setCreatedDateTime(LocalDateTime.now());
		return newBbs;
	}

	public Bbs getBbs(Long id) {	
		return bbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
	}

	public Bbs getBbsToEdit(Account account, Long id) {
		Bbs bbs = this.getBbs(id);
		checkIfManager(account, bbs);
		return bbs;
	}


	public void editBbs(Bbs bbs, @Valid BbsForm bbsForm) {
		modelMapper.map(bbsForm, bbs);
		
	}

	public void remove(Bbs bbs) {
		bbsRepository.delete(bbs);
	}

	public Community createComment(Community community, Bbs bbs, Account account) {
		community.setBbs(bbs);
		community.setAccount(account);
		community.setCreateDateTime(LocalDateTime.now());
		bbs.addComment(community);
		
		return communityRepository.save(community);
	}

	public void remove(Community community, Bbs bbs) {		
		bbs.subtractComment(community);
		communityRepository.delete(community);
	}

	public void getBbsCnt(Long id, Account account) {
		Bbs bbs = this.getBbs(id);
		bbs.viewCnt(account);
	}
	
	
}
