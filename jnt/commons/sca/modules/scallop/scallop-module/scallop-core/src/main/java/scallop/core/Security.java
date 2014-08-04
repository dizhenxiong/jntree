package scallop.core;

import java.io.Serializable;

public class Security implements Serializable {

	private static final long serialVersionUID = 2708741147297452667L;
	private Long id;
	private String name;

	private String secretKey;


	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
