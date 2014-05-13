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


@Path("/AddressService")
public class AddressService {
	
	// URL :  http://localhost:8080/StoreServices/store/AddressService/addAddress
	
	/**
	 * add Address details in the Database
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/addAddress")
	@Produces({"application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public Response addAddress(MultivaluedMap<String, String> formParams){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			String address1 = formParams.get("address1").get(0);
			String address2 = formParams.get("address2").get(0);
			String area = formParams.get("area").get(0);
			String city = formParams.get("city").get(0);
			String state = formParams.get("state").get(0);
			String pincode = formParams.get("pincode").get(0);
			String landMark = formParams.get("landMark").get(0);
			String phone = formParams.get("phone").get(0);
			String mobile1 = formParams.get("mobile1").get(0);
			String mobile2 = formParams.get("mobile2").get(0);
			String latitude = formParams.get("latitude").get(0);
			String longitude = formParams.get("longitude").get(0);

			Address address = new Address();
			address.setAddress1(address1);
			address.setAddress2(address2);
			address.setArea(area);
			address.setCity(city);
			address.setState(state);
			address.setLandMark(landMark);
			address.setPincode(pincode);
			address.setPhone(phone);
			address.setMobile1(mobile1);
			address.setMobile2(mobile2);
			address.setLatitude(Float.parseFloat(latitude));
			address.setLongitude(Float.parseFloat(longitude));
			
			jsonObject = serviceAdapter.addAddress(address);
			
		}catch (Exception e) {
			System.out.println("\n \n exception from AddressService---addAddress()");
			e.printStackTrace();
		}finally{
			serviceAdapter = null;
		}
		//System.out.println("from AddressService ---addAddress()--- jsonObjectOne  :" +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * update Address details in the Database
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/updateAddress")
	@Produces({"application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public Response updateAddress(MultivaluedMap<String, String> formParams){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			int addressId = Integer.parseInt(formParams.get("addressId").get(0));
			String address1 = formParams.get("address1").get(0);
			String address2 = formParams.get("address2").get(0);
			String area = formParams.get("area").get(0);
			String city = formParams.get("city").get(0);
			String state = formParams.get("state").get(0);
			String pincode = formParams.get("pincode").get(0);
			String landMark = formParams.get("landMark").get(0);
			String phone = formParams.get("phone").get(0);
			String mobile1 = formParams.get("mobile1").get(0);
			String mobile2 = formParams.get("mobile2").get(0);
			String latitude = formParams.get("latitude").get(0);
			String longitude = formParams.get("longitude").get(0);

			Address address = new Address();
			address.setAddressId(addressId);
			address.setAddress1(address1);
			address.setAddress2(address2);
			address.setArea(area);
			address.setCity(city);
			address.setState(state);
			address.setLandMark(landMark);
			address.setPincode(pincode);
			address.setPhone(phone);
			address.setMobile1(mobile1);
			address.setMobile2(mobile2);
			address.setLatitude(Float.parseFloat(latitude));
			address.setLongitude(Float.parseFloat(longitude));
			
			jsonObject = serviceAdapter.updateAddress(address);
			
		}catch (Exception e) {
			System.out.println("\n \n exception from AddressService---updateAddress()");
			e.printStackTrace();
		}finally{
			serviceAdapter = null;
		}
		//System.out.println("from AddressService ---updateAddress()--- jsonObjectOne  :" +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * delete Address details from the Database
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/deleteAddress")
	@Produces({"application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public Response deleteAddress(MultivaluedMap<String, String> formParams){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			int addressId = Integer.parseInt(formParams.get("addressId").get(0));
			Address address = new Address();
			address.setAddressId(addressId);
			
			jsonObject = serviceAdapter.deleteAddress(address);
			
		}catch (Exception e) {
			System.out.println("\n \n exception from AddressService---deleteAddress()");
			e.printStackTrace();
		}finally{
			serviceAdapter = null;
		}
		//System.out.println("from AddressService ---deleteAddress()--- jsonObjectOne  :" +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * getting address details
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/getAddress")
	@Produces({ "application/json"})
	@Consumes("application/x-www-form-urlencoded")
	public Response getAddress(MultivaluedMap<String, String> formParams){

		String addressDetails = null;
		String addressId = formParams.get("addressId").get(0);
		String query =  "select * from Address where addressId="+addressId;
		ArrayList<Address> list = null;
		ServiceAdapter serviceAdapter = new ServiceAdapter();
		if (addressId != null) {
			list = (ArrayList<Address>) serviceAdapter.getAddresses(query);
			addressDetails = framingAddressResponseToJson(list);
		}
		serviceAdapter = null;
		return Response.ok(addressDetails).build();
	}

	/**
	 * getting address details
	 * @param request
	 * @param response
	 * @return Response
	 */
	@GET
	@Path("/getAddresses")
	@Produces({ "application/json"})
	public Response getAddresses(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		String addressDetails = null;
		String query =  "select * from Address";
		ArrayList<Address> list = null;
		
		ServiceAdapter serviceAdapter = new ServiceAdapter();
		list = (ArrayList<Address>) serviceAdapter.getAddresses(query);
		addressDetails = framingAddressResponseToJson(list);

		serviceAdapter = null;
		return Response.ok(addressDetails).build();
	}

	/**
	 * changing the response data to specified JSON format
	 * @param ArrayList<Address>
	 * @return String
	 */
	private String framingAddressResponseToJson(ArrayList<Address> addressList) {

		StringBuffer sb = new StringBuffer();
		try {
			JSONObject jsonObject = new JSONObject();
			if (addressList != null && addressList.size() > 0) {
				int iSize = addressList.size();
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < iSize; i++) {
					JSONObject item =new JSONObject();
					item.put("addressId", addressList.get(i).getAddressId());
					item.put("address1", addressList.get(i).getAddress1());
					item.put("address2", addressList.get(i).getAddress2());
					item.put("area", addressList.get(i).getArea());
					item.put("city", addressList.get(i).getCity());
					item.put("state", addressList.get(i).getState());
					item.put("pincode", addressList.get(i).getPincode());
					item.put("landMark", addressList.get(i).getLandMark());
					item.put("phone", addressList.get(i).getPhone());
					item.put("mobile1", addressList.get(i).getMobile1());
					item.put("mobile2", addressList.get(i).getMobile2());
					item.put("latitude", addressList.get(i).getLatitude());
					item.put("longitude", addressList.get(i).getLongitude());
					jsonArray.put(item);
				}
				jsonObject.put("AddressDetails", jsonArray);
				sb.append(jsonObject.toString());
			}

		} catch (Exception ee) {
			System.out.println("---- In framingAddressResponseToJson Exception ----" + ee.getMessage());
		}
		return sb.toString();
	}
}
