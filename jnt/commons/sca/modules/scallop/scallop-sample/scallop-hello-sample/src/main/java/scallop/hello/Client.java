package scallop.hello;

import java.rmi.Naming;

public class Client {
	public static void main(String[] args) {
		try {
			Helloworld service = (Helloworld) Naming
					.lookup("rmi://localhost:8095/HelloRMIService");
			while(true){
				try {
					System.out.println(service.sayHello("songkun"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				Thread.currentThread().sleep(1000L);
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
