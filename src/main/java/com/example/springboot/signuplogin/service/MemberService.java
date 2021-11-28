package com.example.springboot.signuplogin.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.signuplogin.mapper.MemberMapper;
import com.example.springboot.signuplogin.vo.MembersVo;
import com.example.springboot.tlboard.controller.TlBoardController;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	private static final Logger log = LoggerFactory.getLogger(TlBoardController.class);

	public Map<String, String> validate(MembersVo memVo) {
		Map<String, String> map = new HashMap<String, String>();
		log.debug(memVo.getMem_userid());
		log.debug(memVo.getMem_username());
		log.debug(memVo.getMem_password());
		log.debug(memVo.getMem_email());
		
        if(memVo.getMem_userid() == null || memVo.getMem_userid().trim().isEmpty()) {
            map.put("msg", "회원 아이디를 입력하세요.");
        } else if(memVo.getMem_username() == null || memVo.getMem_username().trim().isEmpty()) {
        	map.put("msg", "회원 이름을 입력하세요.");
        } else if (!Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\\\W)(?=\\\\S+$).{4,20}", memVo.getMem_password())) {
        	map.put("msg", "비밀번호는 영문 대/소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 4자 ~ 20자의 비밀번호여야 합니다.");
        } else if(!Pattern.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+$", memVo.getMem_email())){
        	map.put("msg", "이메일 형식에 맞지 않습니다.");
        } 
		
		return map;
	}
	
	@Transactional
	public int signup(MembersVo membersVo) {
		// password encode
		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
		membersVo.setMem_password(pwdEncoder.encode(membersVo.getMem_password()));
		int complCnt = memberMapper.insertSignup(membersVo);
		return complCnt;
	}
	
	@Transactional
	public MembersVo confirmUser(String mem_userid) {
		MembersVo membersVo = memberMapper.confirmUser(mem_userid);
		return membersVo;
	}

}
