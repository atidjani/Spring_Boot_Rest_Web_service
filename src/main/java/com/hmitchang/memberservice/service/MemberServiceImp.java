package com.hmitchang.memberservice.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.hmitchang.memberservice.model.Member;

@Service("userService")
public class MemberServiceImp implements MemberService {
	
	private AtomicLong counter = new AtomicLong(0);
	private HashMap<Long,Member> members = new HashMap<Long,Member>(); 

	@Override
	public Member getMember(long id) {
		return members.get(id);
	}

	@Override
	public long saveMember(Member member) {
		long id = counter.incrementAndGet();
		member.setId(id);
		members.put(id, member);
		return id;
	}

	@Override
	public void deleteMember(long id) {
		members.remove(id);
	}

	@Override
	public void updateMember(long id, Member member) {
			members.put(id, member);
	}

	@Override
	public Collection<Member> getAllMembers() {
		return members.values();
	}
}
