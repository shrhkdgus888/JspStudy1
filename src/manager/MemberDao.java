package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



//Member Database Access object
	//�����Լ��� �־��� ������� Dao class�� ���� ���������� ������ ��,
	
public class MemberDao {
	//context,web.xml���� ������ DataSource ��ü�� ����ϱ� ���� ��ü ����
	private DataSource ds;
	//MemberDao�� ������ �� �ִ� instance ����
	private static MemberDao instance;
	//MemberDao ������
	private MemberDao() {
		try {
			Context ctx = new InitialContext();
			//Context������ JNDI��ο��� jdbc/TestDB�� �̸��� ���� ������ ã�ƿͼ� ds(DataSource)�� �����ϵ��� ��
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/TestDB"); //JNDI���, ds = DBCP����� ���� ��ü
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	//MemberDao�� ��ȯ���ִ� getInstance�޼��� ����
	public static MemberDao getInstance() {
		//����ȭó��
		synchronized(MemberDao.class) {
			if(instance ==null ) {
				instance = new MemberDao(); //MemberDao Ŭ���� ���ο��� MemberDao�� �����ϴ� ���̱� ������ private�� Ȱ�밡��
			}
		}
		return instance;
	}
	
	//C(insert) examjdbc02_Ex04 Class�� insert���� Ȯ��.
	public int insertMember(MemberVo vo) {
		Connection conn = null;
		//�������� ������ ���� �ʿ��� Ŭ�������� ����
		PreparedStatement pstmt = null;
		
		//insert, delete, update ��ȯ�ϴ� ���� ResultSet rs�� �ƴ϶�,
		//���ڷ� ��ȯ�ȴ�. ex) 1���� ����,����,���� �Ǿ����ϴ�.
		int result = 0;
//		ResultSet rs = null;

		try {
			//Instance��ü�� ������ �����ͼ�, getConnection�� ���ش�.
			conn = ds.getConnection();
			System.out.println("���Ӽ���");
			//StringBuffer : ���ڿ��� �����ϱ����� ����.
			//StringBuffer��� String�� �ᵵ �Ǵµ� ���� StringBuffer�� ���� ������ �޸� ������ ����.
			//String�� ��̿� �����͸� �߰��� ��, ���ο� �����͸� ����� ������, StringBuffer�� �ٷ� �ڿ� �̾�پ��ش�.
			StringBuffer query = new StringBuffer();
			//append : ���������� ���ڸ� ��̿� �߰�����.
			query.append("insert into \"MEMBER\" "); 
			query.append("(\"NUM\", \"MEMBERID\", \"MEMBERPW\", \"NICKNAME\", \"REGDATE\") "); 
//			query.append("values (\"MEMBER_SEQ\".nextval, 'tester2', '1234', 'nick2', sysdate)"); 
//			����ǥ�� ����ڿ��� �Է¹�����, ������ NUM�̶� Regdate�κ��� �ڵ����� ���� �κ�
			query.append("values (\"MEMBER_SEQ\".nextval, ?, ?, ?, sysdate)");
			
			//������ ����� ����������� Ȯ�� = ������ �����ִ� ���ڿ��� �����޶�
			System.out.println(query.toString());
			
			

			pstmt = conn.prepareStatement(query.toString());
//			������
//			����ǥ �κ��� ���ε� ������ ""������ �ִ´�.
//			pstmt.setString(1, "tester7");
//			pstmt.setString(2, "1234");
//			pstmt.setString(3, "testnick4");
			
//			������
			pstmt.setString(1, vo.getMemberId());
			pstmt.setString(2, vo.getMemberPw());
			pstmt.setString(3, vo.getNickName());
			
			//executeQuery�� �ƴ϶� executeUpdate�� ��ߵ�! 
			//Why? insert, update, delete�� ��ȯ�ϴ� ���� rs�� �ƴ϶� Result(������)���� ��ȯ�Ǳ� �����̴�.
			result = pstmt.executeUpdate();
			System.out.println(result + "���� ���ԵǾ����ϴ�.");

		}catch(SQLException e) {
			e.printStackTrace();
		//������ �Ǹ�(conn�� null�� �ƴϸ�, conn�� �ݾƶ�.
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return result; 
	}
	
	//R(Read)
		//�κ���ȸ
			//examjdbc02_Ex03 Class�� select���� Ȯ��.
			//public MemberVo�� ��ȯ���ִ� selectMember��� �Լ��� ��������.
			//�̶�, sM�� ȸ����ȣ(int num)�� �޾Ƽ� �ش��ȣ�� �´� ��������� �����ֵ��� �մϴ�.
	public MemberVo selectMember(int num) {
		Connection conn = null;
		//�������� ������ ���� �ʿ��� Ŭ�������� ����
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//B_01 : Member �ϳ��� result�� ������ �ȴ�.		
		MemberVo result = null;
		try {
			//ds��ü�� ������ �����ͼ�, getConnection�� ���ش�.
			conn = ds.getConnection();
			System.out.println("���Ӽ���");
			//������ �ۼ�, "MEMBER" ���̺��� NUM�� ���� ���ϴ� ���̺� ������ �����´�.
			//(?�� ���ε� ����, ����ڰ� ������ ���� �� �κп� ���õȴ�.)
			pstmt = conn.prepareStatement("select * from \"MEMBER\" where \"NUM\"=?");

			
			//pstmt.setInt(1(=ù��° index�ϱ� 1��), num = ����ڰ� ȣ���Ҷ� ������ ��ȣ�� ���ε� ���� ����
			pstmt.setInt(1, num);

			
			//������ ������ �����ϴ� �Լ�(pstmt.executeQuery();)�� rs�� ��´�.
			rs=pstmt.executeQuery();
			//if(String�� �ƴ� Int���̶� ���� while �ʿ� x)�� 
			//rs�� ����� ���̺��� �������� �������ŭ(rs�� ��ŭ) ���پ�(next) �����´�.
			
//B_02 : �˻��� �Ǿ��ٸ�, result ���� �˻��� ����� ��´�.
			if(rs.next()) {
				//������ ������ Ȱ���ؼ� ������,MemberVo vo ��ü�� �����Ҷ�, �Ʒ� �������� ������ �� �ִ�. 
				result = new MemberVo(
						rs.getInt(1), //"NUM"
						rs.getString("MEMBERID"), //2
						rs.getString("MEMBERPW"), //3
						rs.getString(4)//"NICKNAME"
						);
						result.setRegdate(rs.getDate("REGDATE")); //5
						
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		//������ �Ǹ�(conn�� null�� �ƴϸ�, conn�� �ݾƶ�.
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		
		
		}
//B_03 : ����� �ƴ� ��ȯ���ش�.		
		return result;
	}
		
		//��ü��ȸ
			//examjdbc02_Ex02 Class�� R���� Ȯ��.
//A_01 : selectMemberAll�� ȣ���ϰ� �Ǹ�,
	public List<MemberVo> selectMemberAll(){
		Connection conn = null;
		//�������� ������ ���� �ʿ��� Ŭ�������� ����
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//A_02 : List�� ����,
		List<MemberVo> result = new ArrayList<>();
		try {
			//ds��ü�� ������ �����ͼ�, getConnection�� ���ش�.
			conn = ds.getConnection();
			System.out.println("���Ӽ���");
			//������ �ۼ�, "MEMBER" ���̺��� ��� ������ ������ ���ǵ� ������� �´�.
//A_03 : DB�� ��ü��ȸ �����
			pstmt = conn.prepareStatement("select * from \"MEMBER\"");
			//������ ������ �����ϴ� �Լ�(pstmt.executeQuery();)�� rs�� ��´�.
//A_04 : ������ ������
			rs=pstmt.executeQuery();
			//while�� rs�� ����� ���̺��� �������� �������ŭ(rs�� ��ŭ) ���پ�(next) �����´�.
			while(rs.next()) {
				//������ ������ Ȱ���ؼ� ������,MemberVo vo ��ü�� �����Ҷ�, �Ʒ� �������� ������ �� �ִ�. 
				MemberVo vo = new MemberVo(
//A_05 : �ϳ��ϳ� ��ü�� ������ �ؼ�,
						rs.getInt(1), //NUM
						rs.getString(2), //MEMBERID
						rs.getString("MEMBERPW"), //3
						rs.getString(4)//NICKNAME
						);
						vo.setRegdate(rs.getDate("REGDATE")); //5
//A_06 : list�� ���,						
						result.add(vo);

			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		//������ �Ǹ�(conn�� null�� �ƴϸ�, conn�� �ݾƶ�.
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
//A_07 : ����� �ƴ�, ��ȯ���ش�.
		return result;

		
	}
	
	//U(update)
	//examjdbc02_Ex05 Class�� update���� Ȯ��.
	public int updateMember(MemberVo vo) {

		// TODO Auto-generated method stub
		Connection conn = null;
		//�������� ������ ���� �ʿ��� Ŭ�������� ����
		PreparedStatement pstmt = null;
		
		//insert, delete, update ��ȯ�ϴ� ���� ResultSet rs�� �ƴ϶�,
		//���ڷ� ��ȯ�ȴ�. ex) 1���� ����,����,���� �Ǿ����ϴ�.
		int result = 0;
		
		try {
			//ds��ü�� ������ �����ͼ�, getConnection�� ���ش�.
			conn = ds.getConnection();
			System.out.println("���Ӽ���");
			//StringBuffer : ���ڿ��� �����ϱ����� ����.
			//StringBuffer��� String�� �ᵵ �Ǵµ� ���� StringBuffer�� ���� ������ �޸� ������ ����.
			//String�� ��̿� �����͸� �߰��� ��, ���ο� �����͸� ����� ������, StringBuffer�� �ٷ� �ڿ� �̾�پ��ش�.
			StringBuffer query = new StringBuffer();
			//append : ���������� ���ڸ� ��̿� �߰�����.
			//MEMBER ���̺��� �����Ѵ�.
			query.append("update \"MEMBER\" ");
			//�����Ҽ� �ִ� �� ��й�ȣ�� �г��Ӹ� ��������
			query.append("set \"MEMBERPW\"=?, \"NICKNAME\"=? "); 
//			query.append("values (\"MEMBER_SEQ\".nextval, 'tester2', '1234', 'nick2', sysdate)"); 
//			����ǥ�� ����ڿ��� �Է¹�����, ������ NUM�̶� Regdate�κ��� �ڵ����� ���� �κ�
			//NUM�� ã�Ƽ� ���� MEMBERPW�� NICKNAME�� ��������.
			query.append("where \"NUM\"=?");
			
			//������ ����� ����������� Ȯ�� = ������ �����ִ� ���ڿ��� �����޶�
			System.out.println(query.toString());
			
			

			pstmt = conn.prepareStatement(query.toString());
			//����ǥ �κ��� ���ε� ������ ""������ �ִ´�.
			pstmt.setString(1, vo.getMemberPw()); //ù��° ���ε� �� : �ٲ� ��й�ȣ
			pstmt.setString(2, vo.getNickName()); //�ι�° ���ε� �� : �ٲ� �г���
			pstmt.setInt(3, vo.getNum()); // ����° ���ε� �� : ��� �ٲܲ���? 6��
			
			//executeQuery�� �ƴ϶� executeUpdate�� ��ߵ�! 
			//Why? insert, update, delete�� ��ȯ�ϴ� ���� rs�� �ƴ϶� Result(������)���� ��ȯ�Ǳ� �����̴�.
			result = pstmt.executeUpdate();
//			System.out.println(result + "���� �����Ǿ����ϴ�.");

		}catch(SQLException e) {
			e.printStackTrace();
		//������ �Ǹ�(conn�� null�� �ƴϸ�, conn�� �ݾƶ�.
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		//0�϶� ������ ���� ����, 1�϶� ������ 1�� ���. ���� ���и� ��ȯ�ڷḦ ���� �� �� �ִ�.
		return result;
	}
	
	
	//D(delete)
	//examjdbc02_Ex06 Class�� update���� Ȯ��.
	public int deleteMember(int num) {
		Connection conn = null;
		//�������� ������ ���� �ʿ��� Ŭ�������� ����
		PreparedStatement pstmt = null;
		
		//insert, delete, update ��ȯ�ϴ� ���� rs(ResultSet)�� �ƴ϶�,
		//���ڷ� ��ȯ�ȴ�. ex) 1���� ����,����,���� �Ǿ����ϴ�.
		int result = 0;
//		ResultSet rs = null;

		try {
			//Instance��ü�� ������ �����ͼ�, getConnection�� ���ش�.
			conn = ds.getConnection();
			System.out.println("���Ӽ���");
			//StringBuffer : ���ڿ��� �����ϱ����� ����.
			//StringBuffer��� String�� �ᵵ �Ǵµ� ���� StringBuffer�� ���� ������ �޸� ������ ����.
			//String�� ��̿� �����͸� �߰��� ��, ���ο� �����͸� ����� ������, StringBuffer�� �ٷ� �ڿ� �̾�پ��ش�.
			StringBuffer query = new StringBuffer();
			//append : ���������� ���ڸ� ��̿� �߰�����.
			//MEMBER ���̺��� �����Ѵ�.
			query.append("delete from \"MEMBER\" ");
			//�����Ҽ� �ִ� �� ��й�ȣ�� �г��Ӹ� ��������
			query.append("where \"NUM\"=?");
			
			//������ ����� ����������� Ȯ�� = ������ �����ִ� ���ڿ��� �����޶�
			System.out.println(query.toString());
			
			

			pstmt = conn.prepareStatement(query.toString());
			//����ǥ �κ��� ���ε� ������ ""������ �ִ´�.
			pstmt.setInt(1, num); // ù��° ���ε� ��(NUM)�� num�� ������ �����Ѵ�.
			
			//executeQuery�� �ƴ϶� executeUpdate�� ��ߵ�! 
			//Why? insert, update, delete�� ��ȯ�ϴ� ���� rs�� �ƴ϶� result(������)���� ��ȯ�Ǳ� �����̴�.
			result = pstmt.executeUpdate();
			//������ ���� ����� ��ȯ����.
			System.out.println(result + "���� ���� �Ǿ����ϴ�.");

		}catch(SQLException e) {
			e.printStackTrace();
		//������ �Ǹ�(conn�� null�� �ƴϸ�, conn�� �ݾƶ�.
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		return result;
	}

	
	
		
		
	
}
