package bean;

public class Status {
	public static final int STATUS_METHOD_NOT_SUPPORT = -1001;
	public static final int STATUS_PARAM_DATA_IS_ELLEAGL = -1002;
	public static final int STATUS_DATABASE_ERROR = -1003;
	public static final int STATUS_OK = 0;
	public static String getMessageWithStatus(int status) {
		String message = "";
		switch (status) {
		case STATUS_OK:
			message = "sucess";
			break;
			
		case STATUS_METHOD_NOT_SUPPORT:
			message = "method not support";
			break;
		case STATUS_PARAM_DATA_IS_ELLEAGL:
			message = "STATUS_PARAM_DATA_IS_ELLEAGL";
			break;
		case STATUS_DATABASE_ERROR:
			message = "STATUS_DATABASE_ERROR";
			break;
		default:
			break;
		}
		
		return message;
	}
}
