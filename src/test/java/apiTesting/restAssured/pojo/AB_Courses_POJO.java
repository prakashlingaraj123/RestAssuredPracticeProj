package apiTesting.restAssured.pojo;

import java.util.List;

public class AB_Courses_POJO {
	private List<AC_WebAutomation> webAutomation;
	private List<AC_Api> api;
	private List<AC_Mobile> mobile;
	public List<AC_WebAutomation> getWebAutomation() {
		return webAutomation;
	}
	public void setWebAutomation(List<AC_WebAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}
	public List<AC_Api> getApi() {
		return api;
	}
	public void setApi(List<AC_Api> api) {
		this.api = api;
	}
	public List<AC_Mobile> getMobile() {
		return mobile;
	}
	public void setMobile(List<AC_Mobile> mobile) {
		this.mobile = mobile;
	}

}