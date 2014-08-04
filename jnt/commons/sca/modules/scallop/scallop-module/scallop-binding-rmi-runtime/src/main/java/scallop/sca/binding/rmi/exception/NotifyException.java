package scallop.sca.binding.rmi.exception;

public class NotifyException extends Exception {
	
	String message;
	
	public NotifyException(String message){
		super(message);
		this.message = message;
	}
}
