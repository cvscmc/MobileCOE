package com.cmc.store.mcoe.services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cmc.store.mcoe.dao.ServiceAdapter;
import com.cmc.store.mcoe.pojo.Login;

@Path("/LoginService")
public class LoginService {

	// URL :
	// http://107.170.81.234:8080/StoreServices/login/LoginService/doLogin?userName=test&password=test

	/**
	 * to check given login credential's are valid or not for request type GET
	 * 
	 * @param request
	 * @param response
	 * @return Response
	 */
	@GET
	@Path("/doLogin")
	@Produces({ "application/json" })
	public Response checkLoginDetails(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {

		JSONObject jsonObject = null;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			if (userName != "" && userName != null && password != ""
					&& password != null) {
				jsonObject = null;
				jsonObject = serviceAdapter.checkLogin(userName, password);

			} else {
				jsonObject = null;
				jsonObject = new JSONObject();
				jsonObject.put("LOGINSTATUS", false);
			}
		} catch (JSONException e) {
			System.out
					.println("\n \n exception from LoginService---checkLoginDetails()");
			e.printStackTrace();
		} finally {
			serviceAdapter = null;
		}
		// System.out.println("from LoginService ---checkLoginDetails()--- jsonObjectOne  :"
		// +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * add user details in the Database
	 * 
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/addUser")
	@Produces({ "application/json" })
	@Consumes("application/x-www-form-urlencoded")
	public Response addUser(MultivaluedMap<String, String> formParams) {

		String userName = formParams.get("userName").get(0);
		String password = formParams.get("password").get(0);
		String role = formParams.get("role").get(0);

		JSONObject jsonObject = null;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			Login user = new Login();
			user.setUserName(userName);
			user.setPassword(password);
			user.setRole(role);

			jsonObject = serviceAdapter.addUser(user);

		} catch (Exception e) {
			System.out.println("\n \n exception from LoginService---addUser()");
			e.printStackTrace();
		} finally {
			serviceAdapter = null;
		}
		// System.out.println("from LoginService ---addUser()--- jsonObjectOne  :"
		// +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * update user details in the Database
	 * 
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/updateUser")
	@Produces({ "application/json" })
	@Consumes("application/x-www-form-urlencoded")
	public Response updateUser(MultivaluedMap<String, String> formParams) {

		int userId = Integer.parseInt(formParams.get("userId").get(0));
		String userName = formParams.get("userName").get(0);
		String password = formParams.get("password").get(0);
		String role = formParams.get("role").get(0);

		JSONObject jsonObject = null;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			Login user = new Login();
			user.setUserId(userId);
			user.setUserName(userName);
			user.setPassword(password);
			user.setRole(role);

			jsonObject = serviceAdapter.updateUser(user);

		} catch (Exception e) {
			System.out
					.println("\n \n exception from LoginService---updateUser()");
			e.printStackTrace();
		} finally {
			serviceAdapter = null;
		}
		// System.out.println("from LoginService ---updateUser()--- jsonObjectOne  :"
		// +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * delete user details in the Database
	 * 
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/deleteUser")
	@Produces({ "application/json" })
	@Consumes("application/x-www-form-urlencoded")
	public Response deleteUser(MultivaluedMap<String, String> formParams) {

		JSONObject jsonObject = null;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			int userId = Integer.parseInt(formParams.get("userId").get(0));
			Login user = new Login();
			user.setUserId(userId);

			jsonObject = serviceAdapter.deleteUser(user);

		} catch (Exception e) {
			System.out
					.println("\n \n exception from LoginService---deleteUser()");
			e.printStackTrace();
		} finally {
			serviceAdapter = null;
		}
		// System.out.println("from LoginService ---deleteUser()--- jsonObjectOne  :"
		// +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * getting user details
	 * 
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/getUser")
	@Produces({ "application/json" })
	@Consumes("application/x-www-form-urlencoded")
	public Response getUser(MultivaluedMap<String, String> formParams) {
		String userDetails = null;
		String userId = formParams.get("userId").get(0);
		String query = "select * from Login where userId=" + userId;
		ArrayList<Login> list = null;
		ServiceAdapter serviceAdapter = new ServiceAdapter();
		if (userId != null) {
			list = (ArrayList<Login>) serviceAdapter.getUsers(query);
			userDetails = framingUserResponseToJson(list);
		}
		serviceAdapter = null;
		return Response.ok(userDetails).build();
	}

	/**
	 * getting user details
	 * 
	 * @param request
	 * @param response
	 * @return Response
	 */
	@GET
	@Path("/getUsers")
	@Produces({ "application/json" })
	public Response getUsers(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		String userDetails = null;
		String query = "select * from Login";
		ArrayList<Login> list = null;

		ServiceAdapter serviceAdapter = new ServiceAdapter();
		list = (ArrayList<Login>) serviceAdapter.getUsers(query);
		userDetails = framingUserResponseToJson(list);

		serviceAdapter = null;
		return Response.ok(userDetails).build();
	}

	/**
	 * changing the response data to specified JSON format
	 * 
	 * @param ArrayList
	 *            <Login>
	 * @return String
	 */
	private String framingUserResponseToJson(ArrayList<Login> users) {

		StringBuffer sb = new StringBuffer();
		try {
			JSONObject jsonObject = new JSONObject();
			if (users != null && users.size() > 0) {
				int iSize = users.size();
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < iSize; i++) {
					JSONObject item = new JSONObject();
					item.put("userId", users.get(i).getUserId());
					item.put("userName", users.get(i).getUserName());
					item.put("password", users.get(i).getPassword());
					item.put("role", users.get(i).getRole());
					jsonArray.put(item);
				}
				jsonObject.put("UserDetails", jsonArray);
				sb.append(jsonObject.toString());
			}

		} catch (Exception ee) {
			System.out
					.println("---- In framingUserResponseToJson Exception ----"
							+ ee.getMessage());
		}
		return sb.toString();
	}

}
