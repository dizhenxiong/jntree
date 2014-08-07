package scallop.user;

import org.oasisopen.sca.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import scallop.hello.Helloworld;


@Service(UserService.class)
@Component("UserService")
public class UserServiceImpl implements UserService {
	

    private Helloworld helloWorldService;

	
	public Helloworld getHelloWorldService() {
		return helloWorldService;
	}


	public void setHelloWorldService(Helloworld helloWorldService) {
		this.helloWorldService = helloWorldService;
	}


	public String getUser(){
		try {
			return helloWorldService.sayHello("songkun");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}
}
