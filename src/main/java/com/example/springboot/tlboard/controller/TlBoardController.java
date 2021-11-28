package com.example.springboot.tlboard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springboot.tlboard.service.TlBoardService;
import com.example.springboot.tlboard.vo.TlBoardVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/tlBoard")
public class TlBoardController {
	
	private static final Logger log = LoggerFactory.getLogger(TlBoardController.class);
	
	@Autowired
	TlBoardService tlBoardService;
	
	@GetMapping("/tlBoardList")
	public String tlBoardList(@RequestParam(value="page", defaultValue = "1") int pageNum, HttpServletRequest req, Model model) {
		try {
			List<TlBoardVo> tlBoardList = new ArrayList<TlBoardVo>();
			
			int count = 0;
			//int pageNum = 1;
			
			String searchOption = req.getParameter("searchOption");
			String keyword = req.getParameter("keyword");		
			log.debug("pageNum: " + pageNum);
			log.debug("searchOption: " + searchOption);
			log.debug("keyword: " + keyword);
			
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> pageMap = new HashMap<String, Object>();
			
			if(searchOption != null && !searchOption.equals("")){
				map.put("searchOption", searchOption);
				map.put("keyword", keyword);
				
				count = tlBoardService.getTlBoardSearchCount(map);
				pageMap = tlBoardService.calcPageNum(pageNum, count);
				map.putAll(pageMap);
				tlBoardList = tlBoardService.getTlBoardSearchList(map);
				
				model.addAttribute("searchOption", searchOption);
				model.addAttribute("keyword", keyword);
				
			}else{
				count = tlBoardService.getTlBoardCount();
				pageMap = tlBoardService.calcPageNum(pageNum, count);
				map.putAll(pageMap);
				tlBoardList = tlBoardService.getTlBoardList(map);
			}
			
			//Integer[] pageList = tlBoardService.getPageNumList(pageNum,count);
			
			model.addAttribute("tlBoardList", tlBoardList);
			model.addAttribute("count", count);
			model.addAttribute("pageList", pageMap.get("pageList"));
			model.addAttribute("prev", pageMap.get("prev"));
			model.addAttribute("next", pageMap.get("next"));
			model.addAttribute("endPage", pageMap.get("endPage"));
			model.addAttribute("totalPageNum", pageMap.get("totalPageNum"));
			
			return "tlboard/tlBoardList";
			
		} catch (Exception e) {
			log.error("Error at tlBoardListk", e);
			model.addAttribute("msg", "게시판 리스트 불러오기 실패");
			return "tlboard/tlBoardError";
		}
		
	}
	
	@GetMapping("/tlBoardWrite")
	public String tlBoardWrite() {
		return "tlboard/tlBoardWrite";
	}
	
	@PostMapping("/tlBoardSave")
	public String tlBoardSave(TlBoardVo tlBoardVo, Model model) {
		try {
			//tlBoardVo.setDel_yn("N");
			//tlBoardVo.setHit_cnt(0);
			int boardIdx = tlBoardService.insertTlBoard(tlBoardVo);
			return "tlboard/tlBoardWriteSave";
			
		} catch (Exception e) {
			log.error("Error at tlBoardSave", e);
			model.addAttribute("msg", "게시판 입력 에러");
			return "tlboard/tlBoardError";
		}
		
	}

	@GetMapping("/tlBoardWritSave")
	public String tlBoardWritSave() {
		return "tlboard/tlBoardWriteSave";
	}
	
	@GetMapping("/tlBoardDetail")
	public String tlBoardDetail(@RequestParam(value="idx") int board_idx, Model model) {
		try {
			TlBoardVo tlBoardDtl = tlBoardService.getTlBoardDetail(board_idx);
			model.addAttribute("tlBoardDtl", tlBoardDtl);
			return "tlboard/tlBoardDetail";
			
		} catch (Exception e) {
			log.error("Error at tlBoardDetail", e);
			model.addAttribute("msg", "게시판 조회 에러");
			return "tlboard/tlBoardError";
		}
		
	}
	
	@GetMapping("/tlBoardEdit")
	public String tlBoardEdit(@RequestParam(value="idx") int board_idx, Model model) {
		try {
			TlBoardVo tlBoardEdit = tlBoardService.getTlBoardDetail(board_idx);
			model.addAttribute("tlBoardEdit", tlBoardEdit);
			return "tlboard/tlBoardEdit";
			
		} catch (Exception e) {
			log.error("Error at tlBoardEdit", e);
			model.addAttribute("msg", "게시판 조회 에러");
			return "tlboard/tlBoardError";
		}
		
	}
	
	@PostMapping("/tlBoardEditSave")
	public String tlBoardEditSave(TlBoardVo tlBoardVo, Model model) {
		try {
			int edit = tlBoardService.updateTlBoard(tlBoardVo);
			if(edit > 0) {
				model.addAttribute("tlBoardVo", tlBoardVo);
				return "tlboard/tlBoardEditSave";
			} else {
				log.error("Error at tlBoardEditSave");
				model.addAttribute("msg", "게시판 수정 에러");
				return "tlboard/tlBoardError";
			}
			
		} catch (Exception e) {
			log.error("Error at tlBoardEditSave", e);
			model.addAttribute("msg", "게시판 수정 에러");
			return "tlboard/tlBoardError";
		}
		
	}
	
	@GetMapping("/tlBoardDel")
	public String tlBoardDel(@RequestParam(value="idx") int board_idx, Model model) {
		try {
			int del = tlBoardService.tlBoardDel(board_idx);
			if(del > 0) {
				return "tlboard/tlBoardDel";
			} else {
				log.error("Error at tlBoardDel");
				model.addAttribute("msg", "게시판 삭제 에러");
				return "tlboard/tlBoardError";
			}
			
		} catch (Exception e) {
			log.error("Error at tlBoardDel", e);
			model.addAttribute("msg", "게시판 삭제 에러");
			return "tlboard/tlBoardError";
		}
		
	}

	@GetMapping("/tlBoardError")
	public String error(){
		return "tlboard/tlBoardError";
	}

}
