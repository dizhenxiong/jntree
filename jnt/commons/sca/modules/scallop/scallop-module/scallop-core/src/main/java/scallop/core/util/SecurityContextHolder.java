package scallop.core.util;

public class SecurityContextHolder {
	private static final ThreadLocal<String> securityContext = new ThreadLocal<String>();

	public static void setSecurityContext(String securityInfo) {
		securityContext.set(securityInfo);
	}

	public static String getSecurityContext() {
		return (String) securityContext.get();
	}

	public static void clearSecurityContext() {
		securityContext.remove();
	}

}
