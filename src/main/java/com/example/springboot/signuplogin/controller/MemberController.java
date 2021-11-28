package com.example.springboot.signuplogin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springboot.signuplogin.service.MemberService;
import com.example.springboot.signuplogin.vo.MembersVo;
import com.example.springboot.tlboard.controller.TlBoardController;

@Controller
public class MemberController {
	
	private static final Logger log = LoggerFactory.getLogger(TlBoardController.class);
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/index")
	public String index(){
		return "index";
	}
	
	// 회원가입 페이지
    @GetMapping("/user/signup")
    public String dispSignup() {
        return "user/signup";
    }

    @GetMapping("/user/signupError")
    public String signupError() {
        return "user/signupError";
    }
    
    // 회원가입 처리
    @PostMapping("/user/signup")
    public String execSignup(MembersVo membersVo, Model model) {
    	try {
    		Map<String, String> valResult = memberService.validate(membersVo);
    		log.debug("msg -> "+valResult.get("msg"));
    		if(valResult.containsKey("msg")) {
    			log.error("Error at execSignup");
    			model.addAttribute("msg", valResult.get("msg"));
    			return "user/signupError";
    			
    		} else {
    			int memCnt = memberService.signup(membersVo);
                
                if(memCnt > 0) {
                	return "user/signupCompl";
    			} else {
    				log.error("Error at execSignup");
    				model.addAttribute("msg", "회원가입 처리 에러");
    				return "error";
    			}
    		}
            
		} catch (Exception e) {
			log.error("Error at execSignup", e);
			model.addAttribute("msg", "회원가입 처리 에러");
			return "error";
		}
    	
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
        return "user/login";
    }
    
    // 로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "user/loginSuccess";
    }
    
    // 로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "user/logout";
    }

    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "user/denied";
    }

    // 내 정보 페이지
    @GetMapping("/user/info")
    public String dispMyInfo() {
        return "user/myinfo";
    }

    // 어드민 페이지
    @GetMapping("/user/admin")
    public String dispAdmin() {
        return "user/admin";
    }

}
