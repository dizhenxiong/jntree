package scallop.user;


import org.oasisopen.sca.annotation.Reference;


public class UserServiceRemoteImpl implements UserService{
	
	private UserService userService;

	
	
	 public UserService getVctlUserService() {
		return userService;
	}


	@Reference
	public void setUserService(UserService userService) {
		this.userService = userService;
	}



	public String getUser() {
		 return userService.getUser();
	 }


}
