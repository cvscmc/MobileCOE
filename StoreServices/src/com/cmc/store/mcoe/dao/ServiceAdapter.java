package com.cmc.store.mcoe.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.cmc.store.mcoe.pojo.Address;
import com.cmc.store.mcoe.pojo.Application;
import com.cmc.store.mcoe.pojo.Branch;
import com.cmc.store.mcoe.pojo.Login;
import com.cmc.store.mcoe.pojo.Message;
import com.cmc.store.mcoe.pojo.Store;

public class ServiceAdapter {

	// SQL 2005 code change
	// private final String URL =
	// "jdbc:sqlserver://localhost:1433;databaseName=LBSPlatform;user=sa;password=chakra123";
	// private String driverName =
	// "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	// My SQL code change
	private final String URL = "jdbc:mysql://localhost:3306/LBSPlatform";
	private String driverName = "com.mysql.jdbc.Driver";

	private static Connection connection = null;

	public ServiceAdapter() {
	}

	/**
	 * to get Database Connection
	 * 
	 * @return Connection
	 */
	private Connection getConnection() {
		try {
			Class.forName(driverName);
			// connection = DriverManager.getConnection(URL); sridharxyz123$ /
			// Root@1234
			// My SQL code change
			connection = DriverManager.getConnection(URL, "root", "Root@1234");
		} catch (SQLException sqlee) {
			sqlee.printStackTrace();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return connection;
	}

	/**
	 * to check given login credentials are valid or not, for request type
	 * GET/POST
	 * 
	 * @param userName
	 * @param password
	 * @return JSONObject
	 */
	public JSONObject checkLogin(String userName, String password) {

		String query = "select * from Login where userName ='" + userName
				+ "'and password='" + password + "'";
		ResultSet rs = null;
		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				PreparedStatement ps;
				try {
					ps = connection.prepareStatement(query);
					if (ps != null) {
						rs = ps.executeQuery();
						if (rs != null && rs.next()) {
							String role = rs.getString("role");
							if (role != null && role != "") {
								// i.e login authentication success
								jsonObject.put("LOGINSTATUS", role);
							} else {
								jsonObject.put("LOGINSTATUS", true);
							}
						} else {
							// i.e login authentication fail
							jsonObject.put("LOGINSTATUS", false);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---checkLogin()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * add Application details in the database
	 * 
	 * @param Application
	 *            object
	 * @return JSONObject
	 */
	public JSONObject addApplication(Application application) {

		String query = "insert into Application (appName, theme, headerLabel, footerLabel, logo) values ('"
				+ application.getAppName()
				+ "','"
				+ application.getTheme()
				+ "','"
				+ application.getHeaderLabel()
				+ "','"
				+ application.getFooterLabel()
				+ "','"
				+ application.getLogo()
				+ "')";
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---addApplication()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * update Application details in the database
	 * 
	 * @param Application
	 *            object
	 * @return JSONObject
	 */
	public JSONObject updateApplication(Application application) {

		String query = "update Application set appName='"
				+ application.getAppName() + "', theme='"
				+ application.getTheme() + "', headerLabel='"
				+ application.getHeaderLabel() + "', footerLabel='"
				+ application.getFooterLabel() + "', logo='"
				+ application.getLogo() + "' where appId="
				+ application.getAppId();
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---updateApplication()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * delete Application details from the database
	 * 
	 * @param Application
	 *            object
	 * @return JSONObject
	 */
	public JSONObject deleteApplication(Application application) {

		String query = "delete from Application where appId="
				+ application.getAppId();
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---deleteApplication()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * to get Application details from database
	 * 
	 * @param query
	 * @return List<Application>
	 */
	public List<Application> getApplications(String query) {

		List<Application> list = new ArrayList<Application>();
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			ResultSet rs = null;
			if (connection != null) {
				PreparedStatement ps;
				try {
					ps = connection.prepareStatement(query);
					if (ps != null) {
						rs = ps.executeQuery();

						if (rs != null) {
							while (rs.next()) {
								Application application = new Application();
								application.setAppId(rs.getInt("appId"));
								application.setAppName(rs.getString("appName"));
								application.setTheme(rs.getString("theme"));
								application.setHeaderLabel(rs
										.getString("headerLabel"));
								application.setFooterLabel(rs
										.getString("footerLabel"));
								application.setLogo(rs.getString("logo"));
								list.add(application);
							}
						} else {
							System.out.println("resultset object is null");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception eee) {
					eee.printStackTrace();
				}
			} else {
				System.out
						.println(" --from Database---getApplications()---- Connection not available ");
			}
		} catch (Exception e) {
			System.out.println("Exception from Database---getApplications()"
					+ e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * add User details in the database
	 * 
	 * @param User
	 *            object
	 * @return JSONObject
	 */
	public JSONObject addUser(Login user) {

		String query = "insert into Login (userName, password, role) values ('"
				+ user.getUserName() + "','" + user.getPassword() + "','"
				+ user.getRole() + "')";
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---addUser()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * update User details in the database
	 * 
	 * @param User
	 *            object
	 * @return JSONObject
	 */
	public JSONObject updateUser(Login user) {

		String query = "update Login set userName='" + user.getUserName()
				+ "', password='" + user.getPassword() + "', role='"
				+ user.getRole() + "' where userId=" + user.getUserId();
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---updateUser()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * delete User details from the database
	 * 
	 * @param User
	 *            object
	 * @return JSONObject
	 */
	public JSONObject deleteUser(Login user) {

		String query = "delete from Login where userId=" + user.getUserId();
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---deleteUser()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * to get user details from database
	 * 
	 * @param query
	 * @return List<Login>
	 */
	public List<Login> getUsers(String query) {

		List<Login> list = new ArrayList<Login>();
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			ResultSet rs = null;
			if (connection != null) {
				PreparedStatement ps;
				try {
					ps = connection.prepareStatement(query);
					if (ps != null) {
						rs = ps.executeQuery();

						if (rs != null) {
							while (rs.next()) {
								Login user = new Login();
								user.setUserId(rs.getInt("userId"));
								user.setUserName(rs.getString("userName"));
								user.setPassword(rs.getString("password"));
								String role = rs.getString("role");
								if (role == null) {
									role = "-";
								}
								user.setRole(role);
								list.add(user);
							}
						} else {
							System.out.println("resultset object is null");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception eee) {
					eee.printStackTrace();
				}
			} else {
				System.out
						.println(" --from Database---getUsers()---- Connection not available ");
			}
		} catch (Exception e) {
			System.out.println("Exception from Database---getUsers()"
					+ e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * add Store details in the database
	 * 
	 * @param Store
	 *            object
	 * @return JSONObject
	 */
	public JSONObject addStore(Store store) {

		String query = "insert into Stores (storeType, storeName, storeManager, appId) values ('"
				+ store.getStoreType()
				+ "','"
				+ store.getStoreName()
				+ "','"
				+ store.getStoreManager() + "','" + store.getAppId() + "')";
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---addStore()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * update Store details in the database
	 * 
	 * @param Store
	 *            object
	 * @return JSONObject
	 */
	public JSONObject updateStore(Store store) {

		String query = "update Stores set storeType='" + store.getStoreType()
				+ "', storeName='" + store.getStoreName() + "', storeManager='"
				+ store.getStoreManager() + "' where storeId="
				+ store.getStoreId();
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---updateStore()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * delete Store details from the database
	 * 
	 * @param Store
	 *            object
	 * @return JSONObject
	 */
	public JSONObject deleteStore(Store store) {

		String query = "delete from Stores where storeId=" + store.getStoreId();
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---deleteStore()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * to get store details from database
	 * 
	 * @param query
	 * @return List<Store>
	 */
	public List<Store> getStores(String query) {

		List<Store> list = new ArrayList<Store>();
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			ResultSet rs = null;
			if (connection != null) {
				PreparedStatement ps;
				try {
					ps = connection.prepareStatement(query);
					if (ps != null) {
						rs = ps.executeQuery();

						if (rs != null) {
							while (rs.next()) {
								Store store = new Store();
								store.setStoreId(rs.getInt("storeId"));
								store.setStoreName(rs.getString("storeName"));
								store.setStoreType(rs.getString("storeType"));
								store.setStoreManager(rs
										.getString("storeManager"));
								store.setAppId(rs.getInt("appId"));
								list.add(store);
							}
						} else {
							System.out.println("resultset object is null");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception eee) {
					eee.printStackTrace();
				}
			} else {
				System.out
						.println(" --from Database---getStores()---- Connection not available ");
			}
		} catch (Exception e) {
			System.out.println("Exception from Database---getStores()"
					+ e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * add Address details in the database
	 * 
	 * @param Address
	 *            object
	 * @return JSONObject
	 */
	public JSONObject addAddress(Address address) {

		String query = "insert into Address (address1, address2, area, city, state, landMark, pincode, "
				+ "phone, mobile1, mobile2, latitude, longitude) values ('"
				+ address.getAddress1()
				+ "','"
				+ address.getAddress2()
				+ "','"
				+ address.getArea()
				+ "','"
				+ address.getCity()
				+ "','"
				+ address.getState()
				+ "','"
				+ address.getLandMark()
				+ "','"
				+ address.getPincode()
				+ "','"
				+ address.getPhone()
				+ "','"
				+ address.getMobile1()
				+ "','"
				+ address.getMobile2()
				+ "',"
				+ address.getLatitude() + "," + address.getLongitude() + ")";
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---addAddress()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * update Address details in the database
	 * 
	 * @param Address
	 *            object
	 * @return JSONObject
	 */
	public JSONObject updateAddress(Address address) {

		String query = "update Address set address1='" + address.getAddress1()
				+ "', address2='" + address.getAddress2() + "', area='"
				+ address.getArea() + "', city='" + address.getCity()
				+ "', state='" + address.getState() + "', landMark='"
				+ address.getLandMark() + "', pincode='" + address.getPincode()
				+ "', phone='" + address.getPhone() + "', mobile1='"
				+ address.getMobile1() + "', mobile2='" + address.getMobile2()
				+ "', latitude=" + address.getLatitude() + ", longitude="
				+ address.getLongitude() + " where addressId="
				+ address.getAddressId();
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---updateAddress()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * delete Address details from the database
	 * 
	 * @param Address
	 *            object
	 * @return JSONObject
	 */
	public JSONObject deleteAddress(Address address) {

		String query = "delete from Address where addressId="
				+ address.getAddressId();
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---deleteAddress()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * to get Address details from database
	 * 
	 * @param query
	 * @return List<Address>
	 */
	public List<Address> getAddresses(String query) {

		List<Address> list = new ArrayList<Address>();
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			ResultSet rs = null;
			if (connection != null) {
				PreparedStatement ps;
				try {
					ps = connection.prepareStatement(query);
					if (ps != null) {
						rs = ps.executeQuery();

						if (rs != null) {
							while (rs.next()) {
								Address address = new Address();
								address.setAddressId(rs.getInt("addressId"));
								address.setAddress1(rs.getString("address1"));
								address.setAddress2(rs.getString("address2"));
								address.setArea(rs.getString("area"));
								address.setCity(rs.getString("city"));
								address.setState(rs.getString("state"));
								address.setPincode(rs.getString("pincode"));
								address.setLandMark(rs.getString("landMark"));
								address.setPhone(rs.getString("phone"));
								address.setMobile1(rs.getString("mobile1"));
								address.setMobile2(rs.getString("mobile2"));
								address.setLatitude(rs.getFloat("latitude"));
								address.setLongitude(rs.getFloat("longitude"));
								list.add(address);
							}
						} else {
							System.out.println("resultset object is null");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception eee) {
					eee.printStackTrace();
				}
			} else {
				System.out
						.println(" --from Database---getAddresses()---- Connection not available ");
			}
		} catch (Exception e) {
			System.out.println("Exception from Database---getAddresses()"
					+ e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * add Branch details in the database
	 * 
	 * @param Branch
	 *            object
	 * @return JSONObject
	 */
	public JSONObject addBranch(Branch branch) {

		String query = "insert into Branch (branchName, storeId, addressId) values ('"
				+ branch.getBranchName()
				+ "','"
				+ branch.getStore().getStoreId()
				+ "','"
				+ branch.getAddress().getAddressId() + "')";
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---addBranch()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * update Branch details in the database
	 * 
	 * @param Branch
	 *            object
	 * @return JSONObject
	 */
	public JSONObject updateBranch(Branch branch) {

		String query = "update Branch set branchName='"
				+ branch.getBranchName() + "', storeId='"
				+ branch.getStore().getStoreId() + "', addressId='"
				+ branch.getAddress().getAddressId() + "' where branchId="
				+ branch.getBranchId();
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---updateBranch()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * delete Branch details from the database
	 * 
	 * @param Branch
	 *            object
	 * @return JSONObject
	 */
	public JSONObject deleteBranch(Branch branch) {

		String query = "delete from Branch where branchId="
				+ branch.getBranchId();
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---deleteBranch()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * to get Branch details from database
	 * 
	 * @param query
	 * @return List<Branch>
	 */
	public List<Branch> getBranches(String query) {

		List<Branch> list = new ArrayList<Branch>();
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			ResultSet rs = null;
			if (connection != null) {
				PreparedStatement ps;
				try {
					ps = connection.prepareStatement(query);
					if (ps != null) {
						rs = ps.executeQuery();

						if (rs != null) {
							while (rs.next()) {
								Branch branch = new Branch();
								branch.setBranchId(rs.getInt("branchId"));
								branch.setBranchName(rs.getString("branchName"));
								Store store = new Store();
								store.setStoreId(rs.getInt("storeId"));
								branch.setStore(store);

								Address address = new Address();
								address.setAddressId(rs.getInt("addressId"));
								branch.setAddress(address);
								list.add(branch);
							}
						} else {
							System.out.println("resultset object is null");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception eee) {
					eee.printStackTrace();
				}
			} else {
				System.out
						.println(" --from Database---getBranches()---- Connection not available ");
			}
		} catch (Exception e) {
			System.out.println("Exception from Database---getBranches()"
					+ e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * add Message details in the database
	 * 
	 * @param Message
	 *            object
	 * @return JSONObject
	 */
	public JSONObject addMessage(Message message) {

		String query = "insert into Message (dealMessage, branchId) values ('"
				+ message.getDealMessage() + "','"
				+ message.getBranch().getBranchId() + "')";
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---addMessage()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * update Message details in the database
	 * 
	 * @param Message
	 *            object
	 * @return JSONObject
	 */
	public JSONObject updateMessage(Message message) {

		String query = "update Message set dealMessage='"
				+ message.getDealMessage() + "', branchId='"
				+ message.getBranch().getBranchId() + "' where messageId="
				+ message.getMessageId();
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---updateMessage()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * delete Message details from the database
	 * 
	 * @param Message
	 *            object
	 * @return JSONObject
	 */
	public JSONObject deleteMessage(Message message) {

		String query = "delete from Message where messageId="
				+ message.getMessageId();
		Statement stmt;
		boolean status = false;

		ServiceAdapter serviceAdapter = null;
		JSONObject jsonObject = new JSONObject();
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			if (connection != null) {
				try {
					stmt = connection.createStatement();
					int success = stmt.executeUpdate(query);

					if (success > 0) {
						status = true;
					}
					jsonObject.put("STATUS", status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("from Database ---deleteBranch()--- jsonObject  :"
		// +jsonObject);
		return jsonObject;
	}

	/**
	 * to get Message details from database
	 * 
	 * @param query
	 * @return List<Message>
	 */
	public List<Message> getMessages(String query) {

		List<Message> list = new ArrayList<Message>();
		ServiceAdapter serviceAdapter = null;
		try {
			serviceAdapter = new ServiceAdapter();
			serviceAdapter.getConnection();
			ResultSet rs = null;
			if (connection != null) {
				PreparedStatement ps;
				try {
					ps = connection.prepareStatement(query);
					if (ps != null) {
						rs = ps.executeQuery();

						if (rs != null) {
							while (rs.next()) {
								Message message = new Message();
								message.setMessageId(rs.getInt("messageId"));
								message.setDealMessage(rs
										.getString("dealMessage"));
								Branch branch = new Branch();
								branch.setBranchId(rs.getInt("branchId"));
								message.setBranch(branch);
								list.add(message);
							}
						} else {
							System.out.println("resultset object is null");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception eee) {
					eee.printStackTrace();
				}
			} else {
				System.out
						.println("from Database---getMessages()---Connection not available");
			}
		} catch (Exception e) {
			System.out.println("Exception from Database---getMessages()"
					+ e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

}
