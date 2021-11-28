package com.example.springboot.signuplogin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springboot.signuplogin.domain.Role;
import com.example.springboot.signuplogin.vo.MembersVo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private MemberService memberService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("=========loadUserByUsername=========="+username);
		MembersVo membersVo = memberService.confirmUser(username);
		
		System.out.println("===========>"+membersVo.getMem_userid());
		
		List<GrantedAuthority> authorities = new ArrayList<>();

		if (membersVo == null || membersVo.getMem_withdrawal_yn().equals("Y")) { 
			throw new UsernameNotFoundException("User details not found with this username: " + username); 
		} else {
			if (("admin").equals(username)) {
	            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
	        } else {
	            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
	        }

		}

        return new User(membersVo.getMem_userid(), membersVo.getMem_password(), authorities);
        
	}
	
}
