package com.aloneCook.bbs;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aloneCook.user.CurrentUser;
import com.aloneCook.community.CommentForm;
import com.aloneCook.community.Community;
import com.aloneCook.community.CommunityRepository;
import com.aloneCook.user.Account;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BbsController {
	
	private final BbsService bbsService;
	private final ModelMapper modelMapper;
	private final BbsRepository bbsRepository;
	private final CommunityRepository communityRepository;

	@GetMapping("/bbs-write")
	public String bbsWrite(@CurrentUser Account account, Model model) {
		model.addAttribute(account);
		model.addAttribute(new BbsForm());
		return "bbs/write";
	}
	
	@PostMapping("/new-bbs")
	public String newBbsSubmit(@CurrentUser Account account, @Valid BbsForm bbsForm, Errors errors) {
		if (errors.hasErrors()) {
			return "bbs/write";
		}
		Bbs newBbs = bbsService.createNewBbs(modelMapper.map(bbsForm, Bbs.class), account);
		return "redirect:/bbs/" + newBbs.getId();
	}		
	
	@GetMapping("/bbs/{id}")
	public String bbsView(@CurrentUser Account account, @PathVariable Long id, Model model) {
		Bbs bbs = bbsService.getBbs(id);
		bbsService.getBbsCnt(id, account);
		model.addAttribute(account);
		model.addAttribute(bbs);
		model.addAttribute(new CommentForm());
		
		List<Community> community = communityRepository.findByBbsOrderByCreateDateTimeDesc(bbs);
		
		model.addAttribute("community", community);
		return "bbs/view";
	}
	
	@GetMapping("/bbs-list/{category}")
	public String bbsList(@CurrentUser Account account, @PathVariable Category category, Model model,
						@PageableDefault(size = 10, sort = "createdDateTime", direction = Sort.Direction.DESC)
						Pageable pageable) {
		if(account != null) {
			model.addAttribute(account);
		}
		Page<Bbs> bbsPageAll = bbsRepository.findAll(pageable);
		Page<Bbs> bbsCategory = bbsRepository.findByCategory(category, pageable);
		
		model.addAttribute("category", category.name());
		model.addAttribute("bbsPageAll", bbsPageAll);
		model.addAttribute("bbsCategory", bbsCategory);
		model.addAttribute("sortProperty", pageable.getSort().toString()
						.contains("createdDateTime")? "createdDateTime" : "viewCount");
		
		return "bbs/list";
	}	
	
	@GetMapping("/bbs/{id}/edit")
	public String bbsEditView(@CurrentUser Account account, @PathVariable("id") Long id, Model model) {
		Bbs bbs = bbsService.getBbsToEdit(account, id);
		
		model.addAttribute(account);
		model.addAttribute(bbs);
		model.addAttribute(modelMapper.map(bbs, BbsForm.class));
		return "bbs/edit";
	}
	
	@PostMapping("/bbs/{id}/edit")
	public String bbsEditSubmit(@CurrentUser Account account, @PathVariable("id") Long id,
								@Valid BbsForm bbsForm, Errors errors, Model model, RedirectAttributes attributes) {
		Bbs bbs = bbsService.getBbsToEdit(account, id);
		
		if (errors.hasErrors()) {
			model.addAttribute(account);
			model.addAttribute(bbs);
			return "bbs/edit";
		}
		
		bbsService.editBbs(bbs, bbsForm);
		attributes.addFlashAttribute("message", "게시물을 수정했습니다.");
		return "redirect:/bbs/" + bbs.getId();
	}
	
	@GetMapping("/bbs/{id}/remove")
	public String bbsRemove(@CurrentUser Account account, @PathVariable Long id) {
		Bbs bbs = bbsService.getBbsToEdit(account, id);
		bbsService.remove(bbs);
		return "redirect:/bbs-list";
	}
	
	@GetMapping("/bbs/search")
	public String searchBbs(@CurrentUser Account account, String keyword, Model model,
						@PageableDefault(size = 10, sort = "createdDateTime", direction = Sort.Direction.DESC)
						Pageable pageable) {
		if(account != null) {
			model.addAttribute(account);
		}
		Page<Bbs> bbsPage = bbsRepository.findByKeyword(keyword, pageable);
		model.addAttribute("bbsPage", bbsPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sortProperty", pageable.getSort().toString().contains("createdDateTime") ? "createdDateTime" : "id");
		
		return "bbs/search";
	}
	
	
	@PostMapping("/bbs/{id}/comment/add")
	@ResponseBody
	public ResponseEntity addComment(@CurrentUser Account account, @PathVariable Long id,
									@RequestBody CommentForm commentForm) {
		Bbs bbs = bbsService.getBbs(id);
		bbsService.createComment(modelMapper.map(commentForm, Community.class), bbs, account);	
		return ResponseEntity.ok().build();
	}
	@DeleteMapping("/bbs/{id}/comment/{id}")
	@ResponseBody
	public ResponseEntity removeComment(@CurrentUser Account account, @PathVariable Long id,
									@PathVariable("id") Community community) {
		Bbs bbs = bbsService.getBbs(id);
		bbsService.remove(community, bbs);
		return ResponseEntity.ok().build();
	}
}
