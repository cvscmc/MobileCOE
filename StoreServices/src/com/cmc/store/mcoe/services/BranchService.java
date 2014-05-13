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
import com.cmc.store.mcoe.pojo.Address;
import com.cmc.store.mcoe.pojo.Branch;
import com.cmc.store.mcoe.pojo.Store;

@Path("/BranchService")
public class BranchService {

	// URL : http://localhost:8080/StoreServices/store/BranchService/addBranch

	/**
	 * add branch details in the Database
	 * 
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/addBranch")
	@Produces({ "application/json" })
	@Consumes("application/x-www-form-urlencoded")
	public Response addBranch(MultivaluedMap<String, String> formParams) {

		JSONObject jsonObject = null;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			String branchName = formParams.get("branchName").get(0);
			String storeId = formParams.get("storeId").get(0);
			String addressId = formParams.get("addressId").get(0);
			Branch branch = new Branch();
			branch.setBranchName(branchName);

			Store store = new Store();
			store.setStoreId(Integer.parseInt(storeId));
			branch.setStore(store);

			Address address = new Address();
			address.setAddressId(Integer.parseInt(addressId));
			branch.setAddress(address);

			jsonObject = serviceAdapter.addBranch(branch);

		} catch (Exception e) {
			System.out
					.println("\n \n exception from BranchService---addBranch()");
			e.printStackTrace();
		} finally {
			serviceAdapter = null;
		}
		// System.out.println("from BranchService ---addBranch()--- jsonObjectOne  :"
		// +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * update branch details in the Database
	 * 
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/updateBranch")
	@Produces({ "application/json" })
	@Consumes("application/x-www-form-urlencoded")
	public Response updateBranch(MultivaluedMap<String, String> formParams) {

		JSONObject jsonObject = null;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			int branchId = Integer.parseInt(formParams.get("branchId").get(0));
			String branchName = formParams.get("branchName").get(0);
			String storeId = formParams.get("storeId").get(0);
			String addressId = formParams.get("addressId").get(0);
			Branch branch = new Branch();
			branch.setBranchId(branchId);
			branch.setBranchName(branchName);

			Store store = new Store();
			store.setStoreId(Integer.parseInt(storeId));
			branch.setStore(store);

			Address address = new Address();
			address.setAddressId(Integer.parseInt(addressId));
			branch.setAddress(address);

			jsonObject = serviceAdapter.updateBranch(branch);

		} catch (Exception e) {
			System.out
					.println("\n \n exception from BranchService---updateBranch()");
			e.printStackTrace();
		} finally {
			serviceAdapter = null;
		}
		// System.out.println("from BranchService ---updateBranch()--- jsonObjectOne  :"
		// +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * delete branch details in the Database
	 * 
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/deleteBranch")
	@Produces({ "application/json" })
	@Consumes("application/x-www-form-urlencoded")
	public Response deleteBranch(MultivaluedMap<String, String> formParams) {

		JSONObject jsonObject = null;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			int branchId = Integer.parseInt(formParams.get("branchId").get(0));
			Branch branch = new Branch();
			branch.setBranchId(branchId);

			jsonObject = serviceAdapter.deleteBranch(branch);

		} catch (Exception e) {
			System.out
					.println("\n \n exception from BranchService---deleteBranch()");
			e.printStackTrace();
		} finally {
			serviceAdapter = null;
		}
		// System.out.println("from BranchService ---deleteBranch()--- jsonObjectOne  :"
		// +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * getting branch details
	 * 
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/getBranch")
	@Produces({ "application/json" })
	@Consumes("application/x-www-form-urlencoded")
	public Response getBranch(MultivaluedMap<String, String> formParams) {

		String branchDetails = null;
		String branchId = formParams.get("branchId").get(0);
		String query = "select * from Branch where branchId=" + branchId;
		ArrayList<Branch> list = null;
		ServiceAdapter serviceAdapter = new ServiceAdapter();
		if (branchId != null) {
			list = (ArrayList<Branch>) serviceAdapter.getBranches(query);
			branchDetails = framingBranchResponseToJson(list);
		}
		serviceAdapter = null;
		return Response.ok(branchDetails).build();
	}

	/**
	 * getting branch details
	 * 
	 * @param request
	 * @param response
	 * @return Response
	 */
	@GET
	@Path("/getBranches")
	@Produces({ "application/json" })
	public Response getBranches(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		String branchDetails = null;
		String query = "select * from Branch";
		ArrayList<Branch> list = null;

		ServiceAdapter serviceAdapter = new ServiceAdapter();
		list = (ArrayList<Branch>) serviceAdapter.getBranches(query);
		branchDetails = framingBranchResponseToJson(list);

		serviceAdapter = null;
		return Response.ok(branchDetails).build();
	}

	/**
	 * changing the response data to specified JSON format
	 * 
	 * @param ArrayList
	 *            <Branch>
	 * @return String
	 */
	private String framingBranchResponseToJson(ArrayList<Branch> branches) {

		StringBuffer sb = new StringBuffer();
		try {
			JSONObject jsonObject = new JSONObject();
			if (branches != null && branches.size() > 0) {
				int iSize = branches.size();
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < iSize; i++) {
					JSONObject item = new JSONObject();
					item.put("branchId", branches.get(i).getBranchId());
					item.put("branchName", branches.get(i).getBranchName());

					Store store = branches.get(i).getStore();
					JSONObject storeItem = new JSONObject();
					storeItem.put("storeId", store.getStoreId());
					item.put("store", storeItem);

					Address address = branches.get(i).getAddress();
					JSONObject addressItem = new JSONObject();
					addressItem.put("addressId", address.getAddressId());
					item.put("address", addressItem);
					jsonArray.put(item);
				}
				jsonObject.put("BranchDetails", jsonArray);
				sb.append(jsonObject.toString());
			}

		} catch (Exception ee) {
			System.out
					.println("---- In framingBranchResponseToJson Exception ----"
							+ ee.getMessage());
		}
		return sb.toString();
	}
}
