package scallop.user;

import java.rmi.Naming;


public class Client {
	public static void main(String[] args) {
		try {
			UserService service = (UserService) Naming
					.lookup("rmi://localhost:8099/UserRmiService");
			System.out.println(service.getUser());
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
		
	}
}
