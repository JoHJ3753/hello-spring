package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import java.util.*;
/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemoryMemberRepository implements MemberRepository {

    //메모리에 저장을 하기 위해 변수선언
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //23line
    }

    @Override
    public List<Member> findAll() {
        //자바에서 반환할 때 리스트를 많이 씀(루프 돌리기 편한 이유 등)
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) //35line
                .findAny(); //36line
    }

    public void clearStore() {
        store.clear();
    }
}
