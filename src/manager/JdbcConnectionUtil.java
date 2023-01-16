package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//싱글톤(Singleton) 적용
// - 싱글톤이란 자바 프로그램이 동작할 때 최초 한번만 메모리를 할당하고 할당된 메모리에서만 사용하는 방식을 말한다.
//   즉, 고정된 메모리영역을 사용하여 메모리 낭비를 줄이고 공통된 객체를 사용할 때 매번 객체를 새로 생성하지 않는 방식을 말한다.
public class JdbcConnectionUtil {
	//자기자신과 동일하게 참조할 수 있는 변수를 만든다 (=instance)
	//전역 객체변수로 사용하기 위해 static 객체변수로 생성함.
	private static JdbcConnectionUtil instance;
	//DB 접속정보
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "jspuser";
	private String password = "1234";
	
	
	
	//생성자를 private로 만들어 접근을 막는다.
	private JdbcConnectionUtil() {
			
	}
	//getInstance 메소드를 통해 한번만 생성된 객체를 가져온다.
	public static JdbcConnectionUtil getInstance() {
		// 동기화(synchronized)
		//	- 위와 같이 단일 생성자를 생성하는 과정에서는 메모리상의 문제가 없지만, 예를들어 멀티쓰레드 환경에서는
		//	- 1,2,3개의 instance를 동시에 생성한다고 했을 때, 어떤 특정 시점에서 instance가 반환이 되지 않은 상태라,
		//	- instance값이 null이 아닌 시점이 올 수가 있다. 그렇다면 에러가 발생되므로, synchronized로 *동기화 처리*를 통해 해결할 수 있다.
		//    ex) 만약 1번이 null값이 아닌 상태에서 2번이 생성하려고 할때, 동기화 블럭안에서 2번이 해당 블럭을 실행하지 못하게 대기처리한다.
		//    	    해당 블럭은 반드시 하나의 스레드로 동작하게 만드는 개념.
		synchronized(JdbcConnectionUtil.class) {
			//DriverClass를 검색함.
			try {
				Class.forName("oracle.jdbc.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			if(instance == null) {
				instance = new JdbcConnectionUtil(); //최초 한번만 new 연산자를 통해, 메모리에 할당한다.
			}
		}
		return instance;
	}
	//public Connection을 반환해주는 getConnection()를 정의해서 드라이버매니저를 반환.
	//예외 처리는 커넥션을 호출하는 측에서 해라. throws SQLException
	public Connection getConnection() throws SQLException {
		//위에서 만든 DB접속에 필요한 3가지 요소로 커넥션에 접근해라.
		return DriverManager.getConnection(url, user, password);
	}
	
	
	
	
}
