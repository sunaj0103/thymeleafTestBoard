package com.example.springboot.tlboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.springboot.tlboard.vo.TlBoardVo;

@Repository
@Mapper
public interface TlBoardMapper {
	
	public void insertTlBoard(TlBoardVo tlBoardVo);

	public List<TlBoardVo> selectTlBoardList(Map<String, Object> map);

	public List<TlBoardVo> getTlBoardSearchList(Map<String, Object> map);

	public int getTlBoardCount();

	public int getTlBoardSearchCount(Map<String, Object> map);

	public TlBoardVo getTlBoardDetail(int board_idx);

	public int updateTlBoard(TlBoardVo tlBoardVo);

	public void addHitCount(int board_idx);

	public int tlBoardDel(int board_idx);

}
