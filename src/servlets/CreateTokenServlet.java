package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.security.MD5Encoder;

import com.google.gson.Gson;

import bean.AppParams;
import bean.Response;
import bean.Status;
import bean.Token;
import config.Config;
import sun.security.provider.MD5;

public class CreateTokenServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("GET");
		Response<String> response = new Response<String>("", Status.STATUS_METHOD_NOT_SUPPORT,
				Status.getMessageWithStatus(Status.STATUS_METHOD_NOT_SUPPORT));
		Gson gson = new Gson();
		String replyString = gson.toJson(response);
		resp.getWriter().write(replyString);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doPost");
		Gson gson = new Gson();
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		AppParams appParams = gson.fromJson(sb.toString(), AppParams.class);
		System.out.println(sb.toString());
		if (appParams != null) {
			String appIdString = appParams.getAppId();
			String appSecretString = appParams.getAppSecret();
			System.out.println(appIdString);
			System.out.println(appSecretString);
			if (appIdString != null && appIdString.equals(Config.APP_ID) && appSecretString != null
					&& appSecretString.equals(Config.APP_SECRETE)) {
				long currentTime = System.currentTimeMillis();//ºÁÃë
				String tokenString = Config.APP_ID + currentTime;
				System.out.println(tokenString);
				try {
					long expireTime = (currentTime + 3600*1000);
					String sql = "insert into token (appid,token,expiretime) values(" + Config.APP_ID + ","
							+ tokenString + "," + String.valueOf(expireTime) + ")";
					System.out.println("sql:" + sql);
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection(Config.MYSQL_URL, Config.MYSQL_USER,
							Config.MYSQL_PASSWORD);
					Statement statement = conn.createStatement();
					int success = statement.executeUpdate(sql);
					if (success > 0) {
						Token token = new Token();
						token.setToken(tokenString);
						token.setExpireTime(String.valueOf(expireTime) );
						Response<Token> response = new Response<Token>(token, Status.STATUS_OK,
								Status.getMessageWithStatus(Status.STATUS_OK));
						String replyString = gson.toJson(response);
						resp.getWriter().write(replyString);
					} else {
						Response<String> response = new Response<String>("", Status.STATUS_DATABASE_ERROR,
								Status.getMessageWithStatus(Status.STATUS_DATABASE_ERROR));
						String replyString = gson.toJson(response);
						resp.getWriter().write(replyString);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					e.printStackTrace();
				}

			} else {
				Response<String> response = new Response<String>("", Status.STATUS_PARAM_DATA_IS_ELLEAGL,
						Status.getMessageWithStatus(Status.STATUS_PARAM_DATA_IS_ELLEAGL));
				String replyString = gson.toJson(response);
				resp.getWriter().write(replyString);
			}
		}

	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy");
	}

}
