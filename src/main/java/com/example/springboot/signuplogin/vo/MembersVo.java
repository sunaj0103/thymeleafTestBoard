package com.example.springboot.signuplogin.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.springboot.signuplogin.domain.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembersVo {
	
	private String mem_userid;
	private String mem_password;
	private String mem_email;
	private String mem_username;
	private String mem_gender;
	private LocalDateTime mem_regtime;
	private LocalDateTime mem_logtime;
	private LocalDateTime mem_pass_change;
	private String mem_withdrawal_yn;
	private LocalDateTime date_created;
	private LocalDateTime date_modified;
	
	
}
