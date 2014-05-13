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
import com.cmc.store.mcoe.pojo.Branch;
import com.cmc.store.mcoe.pojo.Message;

@Path("/MessageService")
public class MessageService {
	
	// URL :  http://localhost:8080/StoreServices/store/MessageService/addMessage
	
	/**
	 * add message details in the Database
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/addMessage")
	@Produces({"application/json"})
	public Response addMessage(@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			String dealMessage = request.getParameter("dealMessage");
			Message message = new Message();
			message.setDealMessage(dealMessage);
			
			jsonObject = serviceAdapter.addMessage(message);
			
		}catch (Exception e) {
			System.out.println("\n \n exception from MessageService---addMessage()");
			e.printStackTrace();
		}finally{
			serviceAdapter = null;
		}
		//System.out.println("from MessageService ---addMessage()--- jsonObjectOne  :" +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * update message details in the Database
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/updateMessage")
	@Produces({"application/json"})
	public Response updateMessage(@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			int messageId = Integer.parseInt(request.getParameter("messageId"));
			String dealMessage = request.getParameter("dealMessage");
			Message message = new Message();
			message.setMessageId(messageId);
			message.setDealMessage(dealMessage);
			
			jsonObject = serviceAdapter.updateMessage(message);
			
		}catch (Exception e) {
			System.out.println("\n \n exception from MessageService---updateMessage()");
			e.printStackTrace();
		}finally{
			serviceAdapter = null;
		}
		//System.out.println("from MessageService ---updateMessage()--- jsonObjectOne  :" +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * delete message details in the Database
	 * @param request
	 * @param response
	 * @return Response
	 */
	@POST
	@Path("/deleteMessage")
	@Produces({"application/json"})
	public Response deleteMessage(@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		
		JSONObject jsonObject = null ;
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			int messageId = Integer.parseInt(request.getParameter("messageId"));
			Message message = new Message();
			message.setMessageId(messageId);
			
			jsonObject = serviceAdapter.deleteMessage(message);
			
		}catch (Exception e) {
			System.out.println("\n \n exception from MessageService---deleteMessage()");
			e.printStackTrace();
		}finally{
			serviceAdapter = null;
		}
		//System.out.println("from MessageService ---deleteMessage()--- jsonObjectOne  :" +jsonObject);
		return Response.ok(jsonObject.toString()).build();
	}

	/**
	 * getting message details
	 * @param request
	 * @param response
	 * @return Response
	 */
	@GET
	@Path("/getMessage")
	@Produces({ "application/json"})
	public Response getMessage(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		String messageDetails = null;
		String messageId = request.getParameter("messageId");
		String query =  "select * from Message where messageId="+messageId;
		ArrayList<Message> list = null;
		ServiceAdapter serviceAdapter = new ServiceAdapter();
		if (messageId != null) {
			list = (ArrayList<Message>) serviceAdapter.getMessages(query);
			messageDetails = framingMessageResponseToJson(list);
		}
		serviceAdapter = null;
		return Response.ok(messageDetails).build();
	}

	/**
	 * getting message details
	 * @param request
	 * @param response
	 * @return Response
	 */
	@GET
	@Path("/getMessages")
	@Produces({ "application/json"})
	public Response getMessages(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		String messageDetails = null;
		String query =  "select * from Message";
		ArrayList<Message> list = null;
		
		ServiceAdapter serviceAdapter = new ServiceAdapter();
		list = (ArrayList<Message>) serviceAdapter.getMessages(query);
		messageDetails = framingMessageResponseToJson(list);

		serviceAdapter = null;
		return Response.ok(messageDetails).build();
	}

	/**
	 * changing the response data to specified JSON format
	 * @param ArrayList<Message>
	 * @return String
	 */
	private String framingMessageResponseToJson(ArrayList<Message> messages) {

		StringBuffer sb = new StringBuffer();
		try {
			JSONObject jsonObject = new JSONObject();
			if (messages != null && messages.size() > 0) {
				int iSize = messages.size();
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < iSize; i++) {
					JSONObject item =new JSONObject();
					item.put("messageId", messages.get(i).getMessageId());
					item.put("dealMessage", messages.get(i).getDealMessage());

					Branch branch = messages.get(i).getBranch();
					JSONObject branchItem = new JSONObject();
					branchItem.put("branchId", branch.getBranchId());	
					item.put("branch", branchItem);

					jsonArray.put(item);
				}
				jsonObject.put("MessageDetails", jsonArray);
				sb.append(jsonObject.toString());
			}

		} catch (Exception ee) {
			System.out.println("---- In framingMessageResponseToJson Exception ----" + ee.getMessage());
		}
		return sb.toString();
	}
}
