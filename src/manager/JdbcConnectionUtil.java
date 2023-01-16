package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//�̱���(Singleton) ����
// - �̱����̶� �ڹ� ���α׷��� ������ �� ���� �ѹ��� �޸𸮸� �Ҵ��ϰ� �Ҵ�� �޸𸮿����� ����ϴ� ����� ���Ѵ�.
//   ��, ������ �޸𸮿����� ����Ͽ� �޸� ���� ���̰� ����� ��ü�� ����� �� �Ź� ��ü�� ���� �������� �ʴ� ����� ���Ѵ�.
public class JdbcConnectionUtil {
	//�ڱ��ڽŰ� �����ϰ� ������ �� �ִ� ������ ����� (=instance)
	//���� ��ü������ ����ϱ� ���� static ��ü������ ������.
	private static JdbcConnectionUtil instance;
	//DB ��������
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "jspuser";
	private String password = "1234";
	
	
	
	//�����ڸ� private�� ����� ������ ���´�.
	private JdbcConnectionUtil() {
			
	}
	//getInstance �޼ҵ带 ���� �ѹ��� ������ ��ü�� �����´�.
	public static JdbcConnectionUtil getInstance() {
		// ����ȭ(synchronized)
		//	- ���� ���� ���� �����ڸ� �����ϴ� ���������� �޸𸮻��� ������ ������, ������� ��Ƽ������ ȯ�濡����
		//	- 1,2,3���� instance�� ���ÿ� �����Ѵٰ� ���� ��, � Ư�� �������� instance�� ��ȯ�� ���� ���� ���¶�,
		//	- instance���� null�� �ƴ� ������ �� ���� �ִ�. �׷��ٸ� ������ �߻��ǹǷ�, synchronized�� *����ȭ ó��*�� ���� �ذ��� �� �ִ�.
		//    ex) ���� 1���� null���� �ƴ� ���¿��� 2���� �����Ϸ��� �Ҷ�, ����ȭ ���ȿ��� 2���� �ش� ���� �������� ���ϰ� ���ó���Ѵ�.
		//    	    �ش� ���� �ݵ�� �ϳ��� ������� �����ϰ� ����� ����.
		synchronized(JdbcConnectionUtil.class) {
			//DriverClass�� �˻���.
			try {
				Class.forName("oracle.jdbc.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			if(instance == null) {
				instance = new JdbcConnectionUtil(); //���� �ѹ��� new �����ڸ� ����, �޸𸮿� �Ҵ��Ѵ�.
			}
		}
		return instance;
	}
	//public Connection�� ��ȯ���ִ� getConnection()�� �����ؼ� ����̹��Ŵ����� ��ȯ.
	//���� ó���� Ŀ�ؼ��� ȣ���ϴ� ������ �ض�. throws SQLException
	public Connection getConnection() throws SQLException {
		//������ ���� DB���ӿ� �ʿ��� 3���� ��ҷ� Ŀ�ؼǿ� �����ض�.
		return DriverManager.getConnection(url, user, password);
	}
	
	
	
	
}
