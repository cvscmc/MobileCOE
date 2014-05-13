package com.cmc.store.mcoe.services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cmc.store.mcoe.dao.ServiceAdapter;
import com.cmc.store.mcoe.pojo.Store;

@Path("/DealsService")
public class DealsService {
	
	// URL :  http://localhost:8080/StoreServices/store/DealsService/addDeal
	
	/**
	 * add store details in the Database
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/addDeal")
	@Produces({"application/json"})
	public Response addDeal(@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			String storeName = request.getParameter("storeName");
			String storeType = request.getParameter("storeType");
			String storeManager = request.getParameter("storeManager");
			Store store = new Store();
			store.setStoreName(storeName);
			store.setStoreType(storeType);
			store.setStoreManager(storeManager);
			
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
	@Path("/updateDeal")
	@Produces({"application/json"})
	public Response updateDeal(@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			int storeId = Integer.parseInt(request.getParameter("storeId"));
			String storeName = request.getParameter("storeName");
			String storeType = request.getParameter("storeType");
			String storeManager = request.getParameter("storeManager");
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
	@Path("/deleteDeal")
	@Produces({"application/json"})
	public Response deleteDeal(@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			int storeId = Integer.parseInt(request.getParameter("storeId"));
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
	@GET
	@Path("/getDeal")
	@Produces({ "application/json"})
	public Response getDeal(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		String storeDetails = null;
		String storeId = request.getParameter("storeId");
		String query =  "select * from Stores where storeId="+storeId;
		ArrayList<Store> list = null;
		ServiceAdapter serviceAdapter = new ServiceAdapter();
		if (storeId != null) {
			list = (ArrayList<Store>) serviceAdapter.getStores(query);
			storeDetails = framingDealResponseToJson(list);
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
	@Path("/getDeals")
	@Produces({ "application/json"})
	public Response getDeals(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		String storeDetails = null;
		String query =  "select * from Stores";
		ArrayList<Store> list = null;
		
		ServiceAdapter serviceAdapter = new ServiceAdapter();
		list = (ArrayList<Store>) serviceAdapter.getStores(query);
		storeDetails = framingDealResponseToJson(list);

		serviceAdapter = null;
		return Response.ok(storeDetails).build();
	}

	/**
	 * changing the response data to specified JSON format
	 * @param ArrayList<Store>
	 * @return String
	 */
	private String framingDealResponseToJson(ArrayList<Store> stores) {

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
					jsonArray.put(item);
				}
				jsonObject.put("StoreDetails", jsonArray);
				sb.append(jsonObject.toString());
			}

		} catch (Exception ee) {
			System.out.println("---- In framingDealResponseToJson Exception ----" + ee.getMessage());
		}
		return sb.toString();
	}
}
