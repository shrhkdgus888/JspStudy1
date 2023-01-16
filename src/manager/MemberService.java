package manager;

import java.sql.SQLException;
import java.util.List;

public class MemberService {
	//MemberService�� ������ �� �ִ� instance ����
	private static MemberService instance;
	private MemberDao memberDao;
	//MemberService�� �����ڷ� memberDao�� �㵵��.
	private MemberService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	//MemberService�� ��ȯ���ִ� getInstance�޼��� ����
	public static MemberService getInstance() {
		synchronized(MemberService.class){
			if(instance == null) {
				instance = new MemberService(MemberDao.getInstance());
			}
		}
		return instance;
	}
	
	
	
	//1. ��ϱ�� / ������ ���� ����(DaoŬ������ ����� �ٸ���.)
		//- Dao�� �׳� ������ ����,��Ϲ�ȯ.
		//- ���񽺴� ����ڰ� ��û�Ѱ��� �����ΰ��� ���� �װͿ� ���� ó���ϴ� ��.
	//����ڰ� ���񽺷� ��û�� �ߴٸ� ��� �Ҳ���?
	public boolean regist(MemberVo vo) 
	{
		

		//int ret ������ Dao�� insert�� ���ε�, 
		//Dao�� insertMember �޼���� int���� ��ȯ�ϸ�, ������ �����ϴ� ������ �Ѵ�.
		//���� ���� ������ �����ߴٸ�, 1���� ������ �����Ͽ��ٰ� �ܼ�â�� ����� ��Ÿ���µ�,
		//��, ret == 1�� ���� ���ۿ� �����Ͽ� return�� �Ѵٴ� ���̰�(=�ٽ� ����ȭ������)
		int ret = memberDao.insertMember(vo);
			if(ret == 1){
			return true;
			//������ �����Ͽ��ٸ�, catch(SQLException e) {e.printStackTrace();}�� ���� ������ �߰� �ȴ�.
			}
			return false;
	}
	
	//2. ��ȸ���
	public MemberVo read(int num) {
		return memberDao.selectMember(num);
	}
	
	//3. ��ü��ȸ���
	public List<MemberVo> listAll(){
		return memberDao.selectMemberAll();
	}
	//4. ����
		//�˻��ߴ� �������(searchMember)�� ���� �н�����(getMemberPw)�� ���޹��� vo�� �ִ� �н�����(vo.getMemberPw())��
		//�����ϴٸ� �����ض�. ���������ʴٸ� �������� ����. ������ ���� ���θ� �˷��ֱ� ���� �ڷ��ȯ���� void�� �ƴ� boolean����!
	public boolean edit(MemberVo vo, String memberPwOld) {
		//MemberDao�� Update �޼��� �� ������Ʈ�� �����ϸ� result���� 1�� �ǹǷ�, 
		//�ܼ��� 1(������Ʈ ����)�� -1(������Ʈ ����)�� �����ϱ� ���� ������ -1�� �����س��°�.
		int result = -1;
		//modify.jsp ���� ����
		//1. vo, memberPwOld �� �޾Ƽ� (public void edit(MemberVo vo, String memberPwOld)) 
		//2. memberDao��(memberDao.)
		//3. �ش� ��ȣ�� ������ �ͼ�(selectMember(vo.getNum());)
		//4. searchMember �˻��� ��� ��ü(�����������)�� �־��ش�.
		MemberVo searchMember = memberDao.selectMember(vo.getNum());
		//5. searchMember(�����������)���� ������ getMemberPw ������ ���� ���޹��� memberPwOld�� ���ٸ�,
		if(searchMember.getMemberPw().equals(memberPwOld)) {
		//6. (memberPwNew(���ο� ��й�ȣ)=vo)�� ������Ʈ(����)�� �ض�.
			result = memberDao.updateMember(vo); //result���� -1�ε�, updateMember �޼��忡�� ������Ʈ�� �����ߴٸ� 1�� ����.
		//7.���� �ʴٸ� ��������. 
		}
		//return�Ҷ�, result�� 1(������Ʈ ����)�� ���ٸ� true, ���� �ʴٸ�(������Ʈ ����) false
		return(result == 1) ? true : false;
	}
	//5. Ż��
	public boolean remove(int num) {
		int ret = memberDao.deleteMember(num);
		//���׿����� ���
		//ret ���� 1�϶�,(=1���� �����Ǿ��� ��) true, �ƴϸ� false
		return (ret == 1) ? true : false;
	}
	
}
