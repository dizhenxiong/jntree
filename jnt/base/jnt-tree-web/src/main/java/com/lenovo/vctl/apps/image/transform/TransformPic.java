package com.lenovo.vctl.apps.image.transform;

import java.io.Serializable;

public class TransformPic implements Serializable{
	public String pic;
	public String srcUrl;
	public long userId;
	
	public TransformPic(String pic, String srcUrl, long userId) {
		super();
		this.pic = pic;
		this.srcUrl = srcUrl;
		this.userId = userId;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	
	
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getSrcUrl() {
		return srcUrl;
	}

	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}

	@Override
	public String toString() {
		return "TransformPic [pic=" + pic + ", srcUrl=" + srcUrl + ", userId="
				+ userId + "]";
	}





	
}
