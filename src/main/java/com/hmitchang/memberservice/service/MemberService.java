package com.hmitchang.memberservice.service;

import java.util.Collection;

import com.hmitchang.memberservice.model.Member;

public interface MemberService {
	
	public Member getMember(long id);
	public long saveMember(Member member);
	public void deleteMember(long id);
	public void updateMember(long id, Member member);
	public Collection<Member> getAllMembers();

}
