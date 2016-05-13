package com.hmitchang.memberservice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hmitchang.memberservice.model.Member;
import com.hmitchang.memberservice.service.MemberService;



@RestController
public class MemberController {

	@Autowired
    MemberService memberService;

	//------------ ADD ---------------
    @RequestMapping(value = "/members", method = RequestMethod.POST)
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
    	memberService.saveMember(member);
        return new ResponseEntity<Member>(member,HttpStatus.CREATED);
    }
    
  //------------ GET ALL ---------------
    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public ResponseEntity<Collection<Member>> getAllMembers() {
        return new ResponseEntity<Collection<Member>>(memberService.getAllMembers(),HttpStatus.OK);
    }
    
  //------------ GET ---------------
    @RequestMapping(value = "/members/{id}", method = RequestMethod.GET)
    public ResponseEntity<Member> getMember(@PathVariable("id")long id) {
    	Member member = memberService.getMember(id);
    	if(member != null)
    		return new ResponseEntity<Member>(member,HttpStatus.OK);
    	
    	return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
    }
    
  //------------ UPDATE ---------------
    @RequestMapping(value = "/members/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Member> updateMember(@PathVariable("id") long id, @RequestBody Member member) {
    	Member currentMember = memberService.getMember(id);
    	if(currentMember != null){
    		member.setId(id);
    		memberService.updateMember(id, member);
    		return new ResponseEntity<Member>(member,HttpStatus.OK);
    	}
    	return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
    }
    
  //------------ DELETE ---------------
    @RequestMapping(value = "/members/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Member> deleteMember(@PathVariable("id") long id) {
    	Member member = memberService.getMember(id);
    	if(member != null){
    		memberService.deleteMember(id);
    		return new ResponseEntity<Member>(HttpStatus.OK);
    	}
    	
        return new ResponseEntity<Member>(HttpStatus.NOT_FOUND);
    }
}
