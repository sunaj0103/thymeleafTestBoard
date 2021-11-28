package com.example.springboot.tlboard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.tlboard.controller.TlBoardController;
import com.example.springboot.tlboard.mapper.TlBoardMapper;
import com.example.springboot.tlboard.vo.TlBoardVo;

@Service
@Transactional
public class TlBoardService {
	
	@Autowired
	private TlBoardMapper tlBoardMapper;
	
	private static final Logger log = LoggerFactory.getLogger(TlBoardController.class);
	
	private static final int ONE_PAGE_COUNT = 5;   // 표시할 페이지 수 
    private static final int PAGE_POST_COUNT = 3;   // 한 페이지당 글 수 
	
    @Transactional
	public int getTlBoardCount() {
    	int count = tlBoardMapper.getTlBoardCount();
		return count;
	}
    
    @Transactional
	public int getTlBoardSearchCount(Map<String, Object> map) {
    	int count = tlBoardMapper.getTlBoardSearchCount(map);
		return count;
	}
    
    public Map<String, Object> calcPageNum(int pageNum, int count) {
    	Map<String, Object> map = new HashMap<String, Object>();
        boolean prev;
        boolean next;
        Integer[] pageList = new Integer[ONE_PAGE_COUNT];
        
        int endPage = (int) (Math.ceil(pageNum / (double) ONE_PAGE_COUNT) * ONE_PAGE_COUNT);
    	 
        int startPage = (endPage - ONE_PAGE_COUNT) + 1;
        if(startPage <= 0) startPage = 1;
        
        int tempEndPage = (int) (Math.ceil(count / (double) PAGE_POST_COUNT));
        if (endPage > tempEndPage) {
            endPage = tempEndPage;
        }
 
        prev = startPage == 1 ? false : true;
        next = endPage * PAGE_POST_COUNT < count ? true : false;
        
        for (int num = startPage, i = 0; num <= endPage ; num++, i++) {
    		pageList[i] = num;
		}
        
        int pageStart = (pageNum-1) * PAGE_POST_COUNT;
        int perPageNum = PAGE_POST_COUNT;
        
        map.put("startPage", startPage);
        map.put("endPage", endPage);
        map.put("prev", prev);
        map.put("next", next);
        map.put("pageList", pageList);
        map.put("pageStart", pageStart);
        map.put("perPageNum", perPageNum);
        map.put("totalPageNum", tempEndPage);
        
       /* for (String key : map.keySet()) {
        	String value = Objects.toString(map.get(key));
        	log.debug(key+" : "+value);
		}*/

		return map;
    }
    
    /*
    public Integer[] getPageNumList(int pageNum, int count) {
    	Integer[] pageList = new Integer[ONE_PAGE_COUNT];
    	
    	// 총 페이지 수
    	Integer totalLastPageNum = (int) Math.ceil(count / (double)PAGE_POST_COUNT);
    	System.out.println(totalLastPageNum);
    	// 시작 페이지
    	Integer startPageNum = (pageNum <= 3) ? 1 : pageNum - (int) Math.ceil(ONE_PAGE_COUNT/2);
    			//(pageNum % PAGE_POST_COUNT) == 0 
    			//? (pageNum - PAGE_POST_COUNT + 1) : (pageNum / PAGE_POST_COUNT * PAGE_POST_COUNT +1);
    	System.out.println(startPageNum);
    	// 마지막 페이지
    	Integer lastPageNum = (totalLastPageNum > pageNum + ONE_PAGE_COUNT)
                ? pageNum + ONE_PAGE_COUNT
                : totalLastPageNum;
    			//startPageNum + ONE_PAGE_COUNT - 1;
    	System.out.println(lastPageNum);
    	for (int num = startPageNum, i = 0; num <= lastPageNum ; num++, i++) {
    		pageList[i] = num;
		}
    	
		return pageList;
    }
    */
    
	@Transactional
	public int insertTlBoard(TlBoardVo tlBoardVo) {
		tlBoardMapper.insertTlBoard(tlBoardVo);
		return tlBoardVo.getBoard_idx();
	}
	
	@Transactional
	public List<TlBoardVo> getTlBoardList(Map<String, Object> map) {
		List<TlBoardVo> tlBoardList = tlBoardMapper.selectTlBoardList(map);
		return tlBoardList;
	}
	
	@Transactional
	public List<TlBoardVo> getTlBoardSearchList(Map<String, Object> map) {
		List<TlBoardVo> tlBoardList = tlBoardMapper.getTlBoardSearchList(map);
		return tlBoardList;
	}

	@Transactional
	public TlBoardVo getTlBoardDetail(int board_idx) {
		TlBoardVo tlBoardDtl = tlBoardMapper.getTlBoardDetail(board_idx);
		tlBoardMapper.addHitCount(board_idx);
		return tlBoardDtl;
	}

	@Transactional
	public int updateTlBoard(TlBoardVo tlBoardVo) {
		int edit = tlBoardMapper.updateTlBoard(tlBoardVo);
		return edit;
	}

	@Transactional
	public int tlBoardDel(int board_idx) {
		int del = tlBoardMapper.tlBoardDel(board_idx);
		return del;
	}

}
