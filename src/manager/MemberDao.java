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
	//메인함수에 있었던 개념들을 Dao class에 따로 범용적으로 정의한 것,
	
public class MemberDao {
	//context,web.xml에서 생성된 DataSource 객체를 사용하기 위한 객체 선언
	private DataSource ds;
	//MemberDao를 참조할 수 있는 instance 생성
	private static MemberDao instance;
	//MemberDao 생성자
	private MemberDao() {
		try {
			Context ctx = new InitialContext();
			//Context정보를 JNDI경로에서 jdbc/TestDB의 이름을 가진 정보를 찾아와서 ds(DataSource)에 참조하도록 함
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/TestDB"); //JNDI경로, ds = DBCP기능을 가진 객체
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	//MemberDao를 반환해주는 getInstance메서드 정의
	public static MemberDao getInstance() {
		//동기화처리
		synchronized(MemberDao.class) {
			if(instance ==null ) {
				instance = new MemberDao(); //MemberDao 클래스 내부에서 MemberDao를 생성하는 것이기 때문에 private를 활용가능
			}
		}
		return instance;
	}
	
	//C(insert) examjdbc02_Ex04 Class에 insert쿼리 확인.
	public int insertMember(MemberVo vo) {
		Connection conn = null;
		//쿼리들을 날리기 위해 필요한 클래스들을 정리
		PreparedStatement pstmt = null;
		
		//insert, delete, update 반환하는 값이 ResultSet rs가 아니라,
		//숫자로 반환된다. ex) 1행이 삭제,수정,삽입 되었습니다.
		int result = 0;
//		ResultSet rs = null;

		try {
			//Instance객체의 참조를 가져와서, getConnection을 해준다.
			conn = ds.getConnection();
			System.out.println("접속성공");
			//StringBuffer : 문자열을 관리하기위해 쓰임.
			//StringBuffer대신 String을 써도 되는데 굳이 StringBuffer를 쓰는 이유는 메모리 절약이 목적.
			//String은 어미에 데이터를 추가한 뒤, 새로운 데이터를 만들어 내지만, StringBuffer는 바로 뒤에 이어붙어준다.
			StringBuffer query = new StringBuffer();
			//append : 지속적으로 문자를 어미에 추가해줌.
			query.append("insert into \"MEMBER\" "); 
			query.append("(\"NUM\", \"MEMBERID\", \"MEMBERPW\", \"NICKNAME\", \"REGDATE\") "); 
//			query.append("values (\"MEMBER_SEQ\".nextval, 'tester2', '1234', 'nick2', sysdate)"); 
//			물음표는 사용자에게 입력받은값, 나머지 NUM이랑 Regdate부분은 자동으로 들어가는 부분
			query.append("values (\"MEMBER_SEQ\".nextval, ?, ?, ?, sysdate)");
			
			//쿼리가 제대로 만들어졌는지 확인 = 쿼리가 갖고있는 문자열을 보여달라
			System.out.println(query.toString());
			
			

			pstmt = conn.prepareStatement(query.toString());
//			수정전
//			물음표 부분의 바인딩 변수에 ""값들을 넣는다.
//			pstmt.setString(1, "tester7");
//			pstmt.setString(2, "1234");
//			pstmt.setString(3, "testnick4");
			
//			수정후
			pstmt.setString(1, vo.getMemberId());
			pstmt.setString(2, vo.getMemberPw());
			pstmt.setString(3, vo.getNickName());
			
			//executeQuery가 아니라 executeUpdate로 써야됨! 
			//Why? insert, update, delete는 반환하는 값이 rs가 아니라 Result(숫자형)으로 반환되기 때문이다.
			result = pstmt.executeUpdate();
			System.out.println(result + "행이 삽입되었습니다.");

		}catch(SQLException e) {
			e.printStackTrace();
		//접속이 되면(conn이 null이 아니면, conn을 닫아라.
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
		//부분조회
			//examjdbc02_Ex03 Class에 select쿼리 확인.
			//public MemberVo를 반환해주는 selectMember라고 함수를 정의해줌.
			//이때, sM는 회원번호(int num)를 받아서 해당번호에 맞는 멤버정보를 돌려주도록 합니다.
	public MemberVo selectMember(int num) {
		Connection conn = null;
		//쿼리들을 날리기 위해 필요한 클래스들을 정리
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//B_01 : Member 하나를 result로 가지게 된다.		
		MemberVo result = null;
		try {
			//ds객체의 참조를 가져와서, getConnection을 해준다.
			conn = ds.getConnection();
			System.out.println("접속성공");
			//쿼리문 작성, "MEMBER" 테이블의 NUM을 통해 원하는 테이블 정보를 가져온다.
			//(?는 바인딩 변수, 사용자가 전달한 값이 이 부분에 셋팅된다.)
			pstmt = conn.prepareStatement("select * from \"MEMBER\" where \"NUM\"=?");

			
			//pstmt.setInt(1(=첫번째 index니까 1번), num = 사용자가 호출할때 전달한 번호로 바인딩 변수 셋팅
			pstmt.setInt(1, num);

			
			//쿼리를 실제로 전송하는 함수(pstmt.executeQuery();)를 rs에 담는다.
			rs=pstmt.executeQuery();
			//if(String이 아닌 Int값이라 굳이 while 필요 x)로 
			//rs에 담겨진 테이블의 정보들을 결과값만큼(rs값 만큼) 한줄씩(next) 가져온다.
			
//B_02 : 검색이 되었다면, result 값에 검색된 결과를 담는다.
			if(rs.next()) {
				//가져온 값들을 활용해서 생성자,MemberVo vo 객체를 생성할때, 아래 정보들을 가져올 수 있다. 
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
		//접속이 되면(conn이 null이 아니면, conn을 닫아라.
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
//B_03 : 출력이 아닌 반환해준다.		
		return result;
	}
		
		//전체조회
			//examjdbc02_Ex02 Class에 R쿼리 확인.
//A_01 : selectMemberAll을 호출하게 되면,
	public List<MemberVo> selectMemberAll(){
		Connection conn = null;
		//쿼리들을 날리기 위해 필요한 클래스들을 정리
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//A_02 : List를 만들어서,
		List<MemberVo> result = new ArrayList<>();
		try {
			//ds객체의 참조를 가져와서, getConnection을 해준다.
			conn = ds.getConnection();
			System.out.println("접속성공");
			//쿼리문 작성, "MEMBER" 테이블의 모든 정보가 쿼리가 정의된 순서대로 온다.
//A_03 : DB의 전체조회 목록을
			pstmt = conn.prepareStatement("select * from \"MEMBER\"");
			//쿼리를 실제로 전송하는 함수(pstmt.executeQuery();)를 rs에 담는다.
//A_04 : 가져온 다음에
			rs=pstmt.executeQuery();
			//while로 rs에 담겨진 테이블의 정보들을 결과값만큼(rs값 만큼) 한줄씩(next) 가져온다.
			while(rs.next()) {
				//가져온 값들을 활용해서 생성자,MemberVo vo 객체를 생성할때, 아래 정보들을 가져올 수 있다. 
				MemberVo vo = new MemberVo(
//A_05 : 하나하나 객체로 생성을 해서,
						rs.getInt(1), //NUM
						rs.getString(2), //MEMBERID
						rs.getString("MEMBERPW"), //3
						rs.getString(4)//NICKNAME
						);
						vo.setRegdate(rs.getDate("REGDATE")); //5
//A_06 : list에 담고,						
						result.add(vo);

			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		//접속이 되면(conn이 null이 아니면, conn을 닫아라.
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
//A_07 : 출력이 아닌, 반환해준다.
		return result;

		
	}
	
	//U(update)
	//examjdbc02_Ex05 Class에 update쿼리 확인.
	public int updateMember(MemberVo vo) {

		// TODO Auto-generated method stub
		Connection conn = null;
		//쿼리들을 날리기 위해 필요한 클래스들을 정리
		PreparedStatement pstmt = null;
		
		//insert, delete, update 반환하는 값이 ResultSet rs가 아니라,
		//숫자로 반환된다. ex) 1행이 삭제,수정,삽입 되었습니다.
		int result = 0;
		
		try {
			//ds객체의 참조를 가져와서, getConnection을 해준다.
			conn = ds.getConnection();
			System.out.println("접속성공");
			//StringBuffer : 문자열을 관리하기위해 쓰임.
			//StringBuffer대신 String을 써도 되는데 굳이 StringBuffer를 쓰는 이유는 메모리 절약이 목적.
			//String은 어미에 데이터를 추가한 뒤, 새로운 데이터를 만들어 내지만, StringBuffer는 바로 뒤에 이어붙어준다.
			StringBuffer query = new StringBuffer();
			//append : 지속적으로 문자를 어미에 추가해줌.
			//MEMBER 테이블을 수정한다.
			query.append("update \"MEMBER\" ");
			//수정할수 있는 건 비밀번호와 닉네임만 수정가능
			query.append("set \"MEMBERPW\"=?, \"NICKNAME\"=? "); 
//			query.append("values (\"MEMBER_SEQ\".nextval, 'tester2', '1234', 'nick2', sysdate)"); 
//			물음표는 사용자에게 입력받은값, 나머지 NUM이랑 Regdate부분은 자동으로 들어가는 부분
			//NUM을 찾아서 위의 MEMBERPW와 NICKNAME을 수정하자.
			query.append("where \"NUM\"=?");
			
			//쿼리가 제대로 만들어졌는지 확인 = 쿼리가 갖고있는 문자열을 보여달라
			System.out.println(query.toString());
			
			

			pstmt = conn.prepareStatement(query.toString());
			//물음표 부분의 바인딩 변수에 ""값들을 넣는다.
			pstmt.setString(1, vo.getMemberPw()); //첫번째 바인딩 값 : 바꿀 비밀번호
			pstmt.setString(2, vo.getNickName()); //두번째 바인딩 값 : 바꿀 닉네임
			pstmt.setInt(3, vo.getNum()); // 세번째 바인딩 값 : 몇번 바꿀꺼냐? 6번
			
			//executeQuery가 아니라 executeUpdate로 써야됨! 
			//Why? insert, update, delete는 반환하는 값이 rs가 아니라 Result(숫자형)으로 반환되기 때문이다.
			result = pstmt.executeUpdate();
//			System.out.println(result + "행이 수정되었습니다.");

		}catch(SQLException e) {
			e.printStackTrace();
		//접속이 되면(conn이 null이 아니면, conn을 닫아라.
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
		//0일땐 수정된 것이 없다, 1일땐 수정이 1개 됬다. 성공 실패를 반환자료를 통해 알 수 있다.
		return result;
	}
	
	
	//D(delete)
	//examjdbc02_Ex06 Class에 update쿼리 확인.
	public int deleteMember(int num) {
		Connection conn = null;
		//쿼리들을 날리기 위해 필요한 클래스들을 정리
		PreparedStatement pstmt = null;
		
		//insert, delete, update 반환하는 값이 rs(ResultSet)가 아니라,
		//숫자로 반환된다. ex) 1행이 삭제,수정,삽입 되었습니다.
		int result = 0;
//		ResultSet rs = null;

		try {
			//Instance객체의 참조를 가져와서, getConnection을 해준다.
			conn = ds.getConnection();
			System.out.println("접속성공");
			//StringBuffer : 문자열을 관리하기위해 쓰임.
			//StringBuffer대신 String을 써도 되는데 굳이 StringBuffer를 쓰는 이유는 메모리 절약이 목적.
			//String은 어미에 데이터를 추가한 뒤, 새로운 데이터를 만들어 내지만, StringBuffer는 바로 뒤에 이어붙어준다.
			StringBuffer query = new StringBuffer();
			//append : 지속적으로 문자를 어미에 추가해줌.
			//MEMBER 테이블을 수정한다.
			query.append("delete from \"MEMBER\" ");
			//수정할수 있는 건 비밀번호와 닉네임만 수정가능
			query.append("where \"NUM\"=?");
			
			//쿼리가 제대로 만들어졌는지 확인 = 쿼리가 갖고있는 문자열을 보여달라
			System.out.println(query.toString());
			
			

			pstmt = conn.prepareStatement(query.toString());
			//물음표 부분의 바인딩 변수에 ""값들을 넣는다.
			pstmt.setInt(1, num); // 첫번째 바인딩 값(NUM)에 num번 정보를 삭제한다.
			
			//executeQuery가 아니라 executeUpdate로 써야됨! 
			//Why? insert, update, delete는 반환하는 값이 rs가 아니라 result(숫자형)으로 반환되기 때문이다.
			result = pstmt.executeUpdate();
			//삭제된 행이 몇개인지 반환해줌.
			System.out.println(result + "행이 삭제 되었습니다.");

		}catch(SQLException e) {
			e.printStackTrace();
		//접속이 되면(conn이 null이 아니면, conn을 닫아라.
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
