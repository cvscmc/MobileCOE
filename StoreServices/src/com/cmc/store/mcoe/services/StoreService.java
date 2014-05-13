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
import org.json.JSONObject;

import com.cmc.store.mcoe.dao.ServiceAdapter;
import com.cmc.store.mcoe.pojo.Application;
import com.cmc.store.mcoe.pojo.Store;

@Path("/StoreService")
public class StoreService {
	
	// URL : http://localhost:8080/StoreServices/store/StoreService/addStore
	
	/**
	 * add store details in the Database
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/addStore")
	@Produces({"application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public Response addStore(MultivaluedMap<String, String> formParams){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			String storeName = formParams.get("storeName").get(0);
			String storeType = formParams.get("storeType").get(0);
			String storeManager = formParams.get("storeManager").get(0);
			int appId = Integer.parseInt(formParams.get("appId").get(0));

			Store store = new Store();
			store.setStoreName(storeName);
			store.setStoreType(storeType);
			store.setStoreManager(storeManager);
			store.setAppId(appId);
			
			jsonObject = serviceAdapter.addStore(store);
			
		}catch (Exception e) {
			System.out.println("\n \n exception from StoreService---addStore()");
			e.printStackTrace();
		}finally{
			serviceAdapter = null;
		}
		//System.out.println("from StoreService ---addStore()--- jsonObjectOne  :" +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * update store details in the Database
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/updateStore")
	@Produces({"application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public Response updateStore(MultivaluedMap<String, String> formParams){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			int storeId = Integer.parseInt(formParams.get("storeId").get(0));
			String storeName = formParams.get("storeName").get(0);
			String storeType = formParams.get("storeType").get(0);
			String storeManager = formParams.get("storeManager").get(0);
			
			Store store = new Store();
			store.setStoreId(storeId);
			store.setStoreName(storeName);
			store.setStoreType(storeType);
			store.setStoreManager(storeManager);
			
			jsonObject = serviceAdapter.updateStore(store);
			
		}catch (Exception e) {
			System.out.println("\n \n exception from StoreService---updateStore()");
			e.printStackTrace();
		}finally{
			serviceAdapter = null;
		}
		//System.out.println("from StoreService ---updateStore()--- jsonObjectOne  :" +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * delete store details in the Database
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/deleteStore")
	@Produces({"application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public Response deleteStore(MultivaluedMap<String, String> formParams){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			int storeId = Integer.parseInt(formParams.get("storeId").get(0));
			Store store = new Store();
			store.setStoreId(storeId);
			
			jsonObject = serviceAdapter.deleteStore(store);
			
		}catch (Exception e) {
			System.out.println("\n \n exception from StoreService---deleteStore()");
			e.printStackTrace();
		}finally{
			serviceAdapter = null;
		}
		//System.out.println("from StoreService ---deleteStore()--- jsonObjectOne  :" +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * getting store details
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/getStore")
	@Produces({ "application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public Response getStore(MultivaluedMap<String, String> formParams){

		String storeDetails = null;
		String storeId = formParams.get("storeId").get(0);
		String query =  "select * from Stores where storeId="+storeId;
		ArrayList<Store> list = null;
		ServiceAdapter serviceAdapter = new ServiceAdapter();
		if (storeId != null) {
			list = (ArrayList<Store>) serviceAdapter.getStores(query);
			storeDetails = framingStoreResponseToJson(list);
		}
		serviceAdapter = null;
		return Response.ok(storeDetails).build();
	}

	/**
	 * getting store details
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/getAppStores")
	@Produces({ "application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public Response getAppStores(MultivaluedMap<String, String> formParams){

		String storeDetails = null;
		String appId = formParams.get("appId").get(0);
		String query =  "select * from Stores where appId="+appId;
		ArrayList<Store> list = null;
		ServiceAdapter serviceAdapter = new ServiceAdapter();
		if (appId != null) {
			list = (ArrayList<Store>) serviceAdapter.getStores(query);
			storeDetails = framingStoreResponseToJson(list);
		}
		serviceAdapter = null;
		return Response.ok(storeDetails).build();
	}

	/**
	 * getting store details
	 * @param request
	 * @param response
	 * @return Response
	 */
	@GET
	@Path("/getStores")
	@Produces({ "application/json"})
	public Response getStores(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		String storeDetails = null;
		String query =  "select * from Stores";
		ArrayList<Store> list = null;
		
		ServiceAdapter serviceAdapter = new ServiceAdapter();
		list = (ArrayList<Store>) serviceAdapter.getStores(query);
		storeDetails = framingStoreResponseToJson(list);

		serviceAdapter = null;
		return Response.ok(storeDetails).build();
	}

	/**
	 * changing the response data to specified JSON format
	 * @param ArrayList<Store>
	 * @return String
	 */
	private String framingStoreResponseToJson(ArrayList<Store> stores) {

		StringBuffer sb = new StringBuffer();
		try {
			JSONObject jsonObject = new JSONObject();
			if (stores != null && stores.size() > 0) {
				int iSize = stores.size();
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < iSize; i++) {
					JSONObject item =new JSONObject();
					item.put("storeId", stores.get(i).getStoreId());
					item.put("storeType", stores.get(i).getStoreType());
					item.put("storeName", stores.get(i).getStoreName());
					item.put("storeManager", stores.get(i).getStoreManager());
					item.put("appId", stores.get(i).getAppId());
					jsonArray.put(item);
				}
				jsonObject.put("StoreDetails", jsonArray);
				sb.append(jsonObject.toString());
			}

		} catch (Exception ee) {
			System.out.println("---- In framingStoreResponseToJson Exception ----" + ee.getMessage());
		}
		return sb.toString();
	}

	/**
	 * add Application details in the Database
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/addApplication")
	@Produces({"application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public Response addApplication(MultivaluedMap<String, String> formParams){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			String appName = formParams.get("appName").get(0);
			String theme = formParams.get("theme").get(0);
			String headerLabel = formParams.get("headerLabel").get(0);
			String footerLabel = formParams.get("footerLabel").get(0);
			String logo = formParams.get("logo").get(0);

			Application application = new Application();
			application.setAppName(appName);
			application.setTheme(theme);
			application.setHeaderLabel(headerLabel);
			application.setFooterLabel(footerLabel);
			application.setLogo(logo);
			
			jsonObject = serviceAdapter.addApplication(application);
			
		}catch (Exception e) {
			System.out.println("\n \n exception from StoreService---addApplication()");
			e.printStackTrace();
		}finally{
			serviceAdapter = null;
		}
		//System.out.println("from StoreService ---addApplication()--- jsonObjectOne  :" +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * update Application details in the Database
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/updateApplication")
	@Produces({"application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public Response updateApplication(MultivaluedMap<String, String> formParams){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			int appId = Integer.parseInt(formParams.get("appId").get(0));
			String appName = formParams.get("appName").get(0);
			String theme = formParams.get("theme").get(0);
			String headerLabel = formParams.get("headerLabel").get(0);
			String footerLabel = formParams.get("footerLabel").get(0);
			String logo = formParams.get("logo").get(0);
			
			Application application = new Application();
			application.setAppId(appId);
			application.setAppName(appName);
			application.setTheme(theme);
			application.setHeaderLabel(headerLabel);
			application.setFooterLabel(footerLabel);
			application.setLogo(logo);
			
			jsonObject = serviceAdapter.updateApplication(application);
			
		}catch (Exception e) {
			System.out.println("\n \n exception from StoreService---updateApplication()");
			e.printStackTrace();
		}finally{
			serviceAdapter = null;
		}
		//System.out.println("from StoreService ---updateApplication()--- jsonObjectOne  :" +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * delete Application details in the Database
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/deleteApplication")
	@Produces({"application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public Response deleteApplication(MultivaluedMap<String, String> formParams){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			int appId = Integer.parseInt(formParams.get("appId").get(0));
			Application application = new Application();
			application.setAppId(appId);
			
			jsonObject = serviceAdapter.deleteApplication(application);
			
		}catch (Exception e) {
			System.out.println("\n \n exception from StoreService---deleteApplication()");
			e.printStackTrace();
		}finally{
			serviceAdapter = null;
		}
		//System.out.println("from StoreService ---deleteApplication()--- jsonObjectOne  :" +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * getting Application details
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/getApplication")
	@Produces({ "application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public Response getApplication(MultivaluedMap<String, String> formParams){

		String appDetails = null;
		String appId = formParams.get("appId").get(0);
		String query =  "select * from Application where appId="+appId;
		ArrayList<Application> list = null;
		ServiceAdapter serviceAdapter = new ServiceAdapter();
		if (appId != null) {
			list = (ArrayList<Application>) serviceAdapter.getApplications(query);
			appDetails = framingApplicationResponseToJson(list);
		}
		serviceAdapter = null;
		return Response.ok(appDetails).build();
	}

	/**
	 * getting application details
	 * @param request
	 * @param response
	 * @return Response
	 */
	@GET
	@Path("/getApplications")
	@Produces({ "application/json"})
	public Response getApplications(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		String appDetails = null;
		String query =  "select * from Application";
		ArrayList<Application> list = null;
		
		ServiceAdapter serviceAdapter = new ServiceAdapter();
		list = (ArrayList<Application>) serviceAdapter.getApplications(query);
		appDetails = framingApplicationResponseToJson(list);

		serviceAdapter = null;
		return Response.ok(appDetails).build();
	}

	/**
	 * changing the response data to specified JSON format
	 * @param ArrayList<Application>
	 * @return String
	 */
	private String framingApplicationResponseToJson(ArrayList<Application> applications) {

		StringBuffer sb = new StringBuffer();
		try {
			JSONObject jsonObject = new JSONObject();
			if (applications != null && applications.size() > 0) {
				int iSize = applications.size();
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < iSize; i++) {
					JSONObject item =new JSONObject();
					item.put("appId", applications.get(i).getAppId());
					item.put("appName", applications.get(i).getAppName());
					item.put("theme", applications.get(i).getTheme());
					item.put("headerLabel", applications.get(i).getHeaderLabel());
					item.put("footerLabel", applications.get(i).getFooterLabel());
					item.put("logo", applications.get(i).getLogo());
					jsonArray.put(item);
				}
				jsonObject.put("AppDetails", jsonArray);
				sb.append(jsonObject.toString());
			}

		} catch (Exception ee) {
			System.out.println("---- In framingApplicationResponseToJson Exception ----" + ee.getMessage());
		}
		return sb.toString();
	}
}
