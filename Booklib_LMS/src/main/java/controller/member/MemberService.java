package controller.member;

import model.Member;

import java.util.List;

public interface MemberService {
    boolean addMember(Member member);
    boolean updateMember(Member member);
    boolean deleteMember(String id);
    Member searchMember(String id);
    List<Member> getAll();
}
