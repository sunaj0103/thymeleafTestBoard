package com.example.springboot.tlboard.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TlBoardVo {
	
	private Integer board_idx;	
	private String writer;
	private String subject;
	private String content;
	private Integer hit_cnt;
	private String del_yn;
	private LocalDateTime date_created;
	private LocalDateTime date_modified;

}
