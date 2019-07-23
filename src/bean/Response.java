package bean;

public class Response<D> {
	private D data;
	private int status;
	private String message;
	
	public Response(D data, int status, String message) {
		super();
		this.data = data;
		this.status = status;
		this.message = message;
	}
	public D getData() {
		return data;
	}
	public void setData(D data) {
		this.data = data;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
