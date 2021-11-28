package com.example.springboot.signuplogin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.springboot.signuplogin.vo.MembersVo;

@Repository
@Mapper
public interface MemberMapper {

	public int insertSignup(MembersVo membersVo);

	public MembersVo confirmUser(String mem_userid);

}
