// YOU ARE ALLOWED TO MODIFY THIS FILE

import org.json.simple.JSONObject;
import org.json.simple.parser.*;


public class RequestProcessor implements IRequestProcessor
{
	/*
		This is dependency injection. Everything the class and this method needs to do their job is
		passed to it. This allows you to perfectly test every aspect of your class by writing mock
		objects that implement these interfaces such that you can test every possible path through
		your code.
	*/
	public String processRequest(String json,
								 IAuthentication authentication,
								 IShipMate shipMate,
								 IDatabase database)
	{
		String response = "";
		try
		{
			Object obj = new JSONParser().parse(json);
			RequestClass requestClass = new RequestClass();
			JSONObject responseObj = new JSONObject();
			JSONObject ob= (JSONObject) obj;
			Address address= new Address();
			Integer drugCount;
			Integer quantity;
			requestClass.apikey=ob.get("apikey")!=null?(String) ob.get("apikey"):"";
			requestClass.username =ob.get("username")!=null? (String) ob.get("username"):"";
			requestClass.action =ob.get("action")!=null?(String) ob.get("action"):"";
			requestClass.drug =ob.get("drug")!=null? (String) ob.get("drug"):"";
			String estimatedDate;

			if(authentication.authenticate(requestClass.apikey))
			{
				responseObj.put("status","200");

				if(requestClass.action.toLowerCase()=="query" || requestClass.action.toLowerCase().equals("query"))
				{
					if(authentication.authorize(requestClass.username,RequestAction.QUERY))
					{

						if(database.isDrugExist(requestClass.drug))
						{
							drugCount=database.drugCount(requestClass.drug);
							responseObj.put("status",200);
							responseObj.put("count",drugCount);
						}
						else
						{
							responseObj.put("status",500);
							responseObj.put("error","Unknown Drug");
						}

					}
					else
					{
						responseObj.put("status",500);
						responseObj.put("error","Not Authorized");
					}

				}
				else if(requestClass.action.toLowerCase()=="ship" || requestClass.action.toLowerCase().equals("ship"))
				{
					JSONObject addressObj= (JSONObject) ob.get("address");
					quantity=(int)(long)ob.get("quantity");
					address.customer= addressObj.get("customer")!=null?(String) addressObj.get("customer"):"";
					address.street=addressObj.get("street")!=null?(String) addressObj.get("street"):"";
					address.city= addressObj.get("city")!=null?(String) addressObj.get("city"):"";
					address.province=addressObj.get("province")!=null?(String) addressObj.get("province"):"";
					address.country=addressObj.get("country")!=null? (String) addressObj.get("country"):"";
					address.postalCode=addressObj.get("postalCode")!=null?(String) addressObj.get("postalCode"):"";



					if(authentication.authorize(requestClass.username,RequestAction.SHIP))
					{


						if(database.isDrugExist(requestClass.drug))
						{
							if(database.claimDrug(requestClass.drug,quantity))
							{
								if(shipMate.isKnownAddress(address))
								{
									estimatedDate=shipMate.shipToAddress(address,quantity,requestClass.drug);
									responseObj.put("status","200");
									responseObj.put("estimatedDeliveryDate",estimatedDate);

								}
								else
								{
									responseObj.put("status",500);
									responseObj.put("error","Unknown Address");
								}
							}
							else
							{
								responseObj.put("status",500);
								responseObj.put("error","Insufficient stock");
							}

						}
						else
						{
							responseObj.put("status",500);
							responseObj.put("error","Unknown Drug");
						}
					}
					else
					{
						responseObj.put("status",500);
						responseObj.put("error","Not Authorized");
					}
				}

			}
			else
			{
				responseObj.put("status",500);
				responseObj.put("error","Authentication Failure");
			}

            // convert response object into String

			response= responseObj!=null? responseObj.toJSONString():"";
		}

		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return response;
	}

	/*
		Insert all of your instantiation of mock objects and RequestProcessor(s)
		here. Then insert calls to all of your unit tests for the RequestProcessor
		class.  These tests should send different combinations of JSON strings
		to your class with mock objects such that you test all paths through the
		API.  Write one test function per "path" you are testing.  For example,
		to test authentication you would write two unit tests: authenticateSuccess()
		that passes JSON with a known API key that should be authenticated by your
		mock security object and tests for the correct JSON response from processRequest(),
		and authenticateFailure() that passes JSON with a bad API key that should fail to
		be authenticated by your mock security object and tests for the correct JSON
		response from processRequest().

		The runUnitTests() method will be called by Main.java. It must run your unit tests.
		All of your unit tests should System.out.println() one line indicating pass or
		failure with the following format:
		PASS - <Name of test>
		FAIL - <Name of test>
	*/

	public void authenticateSuccess( IAuthentication authentication,
									 IShipMate shipMate,
									 IDatabase database)
{
	// testCase to check Known API response

	String jsonString = "{  \n" +
			"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jtrue\",\n" +
			"   \"username\":\"rhawkeyQUERY\",\n" +
			"   \"action\":\"QUERY\",\n" +
			"   \"drug\":\"Azithromycin\"\n" +
			"}\n";
	String expectedOutput="{\"count\":20,\"status\":200}";
	String response = processRequest(jsonString,authentication,shipMate,database);

	if(response.equals(expectedOutput))
	{
		System.out.println("PASS - authenticateSuccess");
	}
	else
	{
		System.out.println("FAIL - authenticateSuccess");
	}

}
	public void authenticateFailure1( IAuthentication authentication,
									 IShipMate shipMate,
									 IDatabase database)
	{

		// testCase to check response of unKnown API request

		String jsonString = "{  \n" +
			"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jFALSE\",\n" +
			"   \"username\":\"rhawkeyQUERY\",\n" +
			"   \"action\":\"QUERY\",\n" +
			"   \"drug\":\"Azithromycin\"\n" +
			"}\n";
		String expectedOutput="{\"error\":\"Authentication Failure\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);
		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - authenticateFailure1");
		}
		else
		{
			System.out.println("FAIL - authenticateFailure1");
		}

	}
	public void authenticateFailure2( IAuthentication authentication,
									 IShipMate shipMate,
									 IDatabase database)
	{

		// testCase to check response of empty API key request

		String jsonString = "{  \n" +
				"   \"apikey\":\"        \",\n" +
				"   \"username\":\"rhawkeyQUERY\",\n" +
				"   \"action\":\"QUERY\",\n" +
				"   \"drug\":\"Azithromycin\"\n" +
				"}\n";
		String expectedOutput="{\"error\":\"Authentication Failure\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);
		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - authenticateFailure2");
		}
		else
		{
			System.out.println("FAIL - authenticateFailure2");
		}

	}

	public void authorizeQuerySuccess( IAuthentication authentication,
									   IShipMate shipMate,
									   IDatabase database)
	{
		// testCase to check response of known Username for Query request

		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"rhawkeyQUERY\",\n" +
				"   \"action\":\"QUERY\",\n" +
				"   \"drug\":\"Azithromycin\"\n" +
				"}\n";
		String expectedOutput="{\"count\":20,\"status\":200}";
		String response = processRequest(jsonString,authentication,shipMate,database);

		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - authorizeQuerySuccess");
		}
		else
		{
			System.out.println("FAIL - authorizeQuerySuccess");
		}

	}

	public void authorizeQueryFailure1( IAuthentication authentication,
									   IShipMate shipMate,
									   IDatabase database)
	{
		// testCase to check response authorization with unknown Username which does not contain "query" word  for Query request
		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"rhawkey\",\n" +
				"   \"action\":\"QUERY\",\n" +
				"   \"drug\":\"Azithromycin\"\n" +
				"}\n";
		String expectedOutput="{\"error\":\"Not Authorized\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);

		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - authorizeQueryFailure1");
		}
		else
		{
			System.out.println("FAIL - authorizeQueryFailure1");
		}

	}
	public void authorizeQueryFailure2( IAuthentication authentication,
									   IShipMate shipMate,
									   IDatabase database)
	{
		// testCase to check response of empty Username  request

		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"    \",\n" +
				"   \"action\":\"QUERY\",\n" +
				"   \"drug\":\"Azithromycin\"\n" +
				"}\n";
		String expectedOutput="{\"error\":\"Not Authorized\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);

		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - authorizeQueryFailure2");
		}
		else
		{
			System.out.println("FAIL - authorizeQueryFailure2");
		}

	}

	public void authorizeShipSuccess( IAuthentication authentication,
									  IShipMate shipMate,
									  IDatabase database)
	{
		// testCase to check authorization with known Username for SHIP request
		String jsonString = "{\n" +
				"  \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"  \"username\":\"rhawkeySHIP\",\n" +
				"  \"action\":\"SHIP\",\n" +
				"  \"drug\":\"Azithromycin\",\n" +
				"  \"quantity\":5,\n" +
				"  \"address\":{\n" +
				"    \"customer\":\"Rob Hawkey\",\n" +
				"    \"street\":\"123 Street\",\n" +
				"    \"city\":\"Halifax\",\n" +
				"    \"province\":\"Nova Scotia\",\n" +
				"    \"country\":\"Canada\",\n" +
				"    \"postalCode\":\"H0H0H0\"\n" +
				"  }\n" +
				"}";
		String expectedOutput="{\"estimatedDeliveryDate\":\"30-06-2019\",\"status\":\"200\"}";
		String response = processRequest(jsonString,authentication,shipMate,database);
		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - authorizeShipSuccess");
		}
		else
		{
			System.out.println("FAIL - authorizeShipSuccess");
		}

	}
	public void authorizeShipFailure1( IAuthentication authentication,
									  IShipMate shipMate,
									  IDatabase database)
	{
		// testCase to check authorization with unknown Username(blank string in username) for SHIP request

		String jsonString = "{\n" +
				"  \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"  \"username\":\"           \",\n" +
				"  \"action\":\"SHIP\",\n" +
				"  \"drug\":\"Azithromycin\",\n" +
				"  \"quantity\":5,\n" +
				"  \"address\":{\n" +
				"    \"customer\":\"Rob Hawkey\",\n" +
				"    \"street\":\"123 Street\",\n" +
				"    \"city\":\"Halifax\",\n" +
				"    \"province\":\"Nova Scotia\",\n" +
				"    \"country\":\"Canada\",\n" +
				"    \"postalCode\":\"H0H0H0\"\n" +
				"  }\n" +
				"}";
		String expectedOutput="{\"error\":\"Not Authorized\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);

		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - authorizeShipFailure1");
		}
		else
		{
			System.out.println("FAIL - authorizeShipFailure1");
		}


	}
	public void authorizeShipFailure2( IAuthentication authentication,
									  IShipMate shipMate,
									  IDatabase database)
	{
		// testCase to check authorization with unknown Username(invalid string) for SHIP request

		String jsonString = "{\n" +
				"  \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"  \"username\":\"bhumiPatel         \",\n" +
				"  \"action\":\"SHIP\",\n" +
				"  \"drug\":\"Azithromycin\",\n" +
				"  \"quantity\":5,\n" +
				"  \"address\":{\n" +
				"    \"customer\":\"Rob Hawkey\",\n" +
				"    \"street\":\"123 Street\",\n" +
				"    \"city\":\"Halifax\",\n" +
				"    \"province\":\"Nova Scotia\",\n" +
				"    \"country\":\"Canada\",\n" +
				"    \"postalCode\":\"H0H0H0\"\n" +
				"  }\n" +
				"}";
		String expectedOutput="{\"error\":\"Not Authorized\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);

		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - authorizeShipFailure2");
		}
		else
		{
			System.out.println("FAIL - authorizeShipFailure2");
		}


	}

	public void knownDrugSuccess( IAuthentication authentication,
								  IShipMate shipMate,
								  IDatabase database)
	{

		//TEST case to check DrugCount for Known drug.
		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"rhawkeyQUERY\",\n" +
				"   \"action\":\"QUERY\",\n" +
				"   \"drug\":\"Azithromycin\"\n" +
				"}\n";

		String expectedOutput="{\"count\":15,\"status\":200}";
		String response = processRequest(jsonString,authentication,shipMate,database);

		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - knownDrugSuccess");
		}
		else
		{
			System.out.println("FAIL - knownDrugSuccess");
		}

	}

	public void knownDrugFailure( IAuthentication authentication,
								  IShipMate shipMate,
								  IDatabase database)
	{
		//TEST case to check DrugCount for unKnown drug.
		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"rhawkeyQUERY\",\n" +
				"   \"action\":\"QUERY\",\n" +
				"   \"drug\":\"Azithromycinecd\"\n" +
				"}\n";

		String expectedOutput="{\"error\":\"Unknown Drug\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);

		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - knownDrugFailure");
		}
		else
		{
			System.out.println("FAIL - knownDrugFailure");
		}

	}

	public void knownAddressSuccess( IAuthentication authentication,
								  IShipMate shipMate,
								  IDatabase database)
	{
		// testCase to check response for known address
		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"rhawkeySHIP\",\n" +
				"   \"action\":\"SHIP\",\n" +
				"   \"drug\":\"Simvastatin\",\n" +
				"   \"quantity\":10,\n" +
				"   \"address\":{  \n" +
				"      \"customer\":\"Rob Hawkey\",\n" +
				"      \"street\":\"123 Street\",\n" +
				"      \"city\":\"Halifax\",\n" +
				"      \"province\":\"Nova Scotia\",\n" +
				"      \"country\":\"Canada\",\n" +
				"      \"postalCode\":\"H0H0H0\"\n" +
				"   }\n" +
				"}\n";

		String expectedOutput="{\"estimatedDeliveryDate\":\"30-06-2019\",\"status\":\"200\"}";
		String response = processRequest(jsonString,authentication,shipMate,database);


		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - knownAddressSuccess");
		}
		else
		{
			System.out.println("FAIL - knownAddressSuccess");
		}

	}
	public void knownAddressFailureCustomer( IAuthentication authentication,
									 IShipMate shipMate,
									 IDatabase database)
	{
		// testCase to check response for unknown customer name in Address
		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"rhawkeySHIP\",\n" +
				"   \"action\":\"SHIP\",\n" +
				"   \"drug\":\"Simvastatin\",\n" +
				"   \"quantity\":5,\n" +
				"   \"address\":{  \n" +
				"      \"customer\":\"   \",\n" +
				"      \"street\":\"123 Street\",\n" +
				"      \"city\":\"Halifax\",\n" +
				"      \"province\":\"Nova Scotia\",\n" +
				"      \"country\":\"Canada\",\n" +
				"      \"postalCode\":\"H0H0H0\"\n" +
				"   }\n" +
				"}\n";

		String expectedOutput="{\"error\":\"Unknown Address\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);
		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - knownAddressFailureCustomer");
		}
		else
		{
			System.out.println("FAIL - knownAddressFailureCustomer");
		}

	}
	public void knownAddressFailureStreet( IAuthentication authentication,
											 IShipMate shipMate,
											 IDatabase database)
	{
		// testCase to check response for unknown street name in Address
		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"rhawkeySHIP\",\n" +
				"   \"action\":\"SHIP\",\n" +
				"   \"drug\":\"Lisinopril\",\n" +
				"   \"quantity\":5,\n" +
				"   \"address\":{  \n" +
				"      \"customer\":\" Rob Hawkey  \",\n" +
				"      \"street\":\"  \",\n" +
				"      \"city\":\"Halifax\",\n" +
				"      \"province\":\"Nova Scotia\",\n" +
				"      \"country\":\"Canada\",\n" +
				"      \"postalCode\":\"H0H0H0\"\n" +
				"   }\n" +
				"}\n";

		String expectedOutput="{\"error\":\"Unknown Address\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);
		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - knownAddressFailureStreet");
		}
		else
		{
			System.out.println("FAIL - knownAddressFailureStreet");
		}

	}
	public void knownAddressFailureCity( IAuthentication authentication,
										   IShipMate shipMate,
										   IDatabase database)
	{
		// testCase to check response for unknown city name in Address
		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"rhawkeySHIP\",\n" +
				"   \"action\":\"SHIP\",\n" +
				"   \"drug\":\"Lisinopril\",\n" +
				"   \"quantity\":5,\n" +
				"   \"address\":{  \n" +
				"      \"customer\":\" Rob Hawkey  \",\n" +
				"      \"street\":\"123 Street  \",\n" +
				"      \"city\":\" \",\n" +
				"      \"province\":\"Nova Scotia\",\n" +
				"      \"country\":\"Canada\",\n" +
				"      \"postalCode\":\"H0H0H0\"\n" +
				"   }\n" +
				"}\n";

		String expectedOutput="{\"error\":\"Unknown Address\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);
		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - knownAddressFailureCity");
		}
		else
		{
			System.out.println("FAIL - knownAddressFailureCity");
		}

	}
	public void knownAddressFailureProvince( IAuthentication authentication,
										 IShipMate shipMate,
										 IDatabase database)
	{
		// testCase to check response for unknown province name in Address
		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"rhawkeySHIP\",\n" +
				"   \"action\":\"SHIP\",\n" +
				"   \"drug\":\"Lisinopril\",\n" +
				"   \"quantity\":5,\n" +
				"   \"address\":{  \n" +
				"      \"customer\":\" Rob Hawkey  \",\n" +
				"      \"street\":\"123 Street  \",\n" +
				"      \"city\":\" Halifax\",\n" +
				"      \"province\":\"    \",\n" +
				"      \"country\":\"Canada\",\n" +
				"      \"postalCode\":\"H0H0H0\"\n" +
				"   }\n" +
				"}\n";

		String expectedOutput="{\"error\":\"Unknown Address\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);
		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - knownAddressFailureProvince");
		}
		else
		{
			System.out.println("FAIL - knownAddressFailureProvince");
		}

	}

	public void knownAddressFailureCountry( IAuthentication authentication,
											 IShipMate shipMate,
											 IDatabase database)
	{
		// testCase to check response for unknown country name in Address
		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"rhawkeySHIP\",\n" +
				"   \"action\":\"SHIP\",\n" +
				"   \"drug\":\"Lisinopril\",\n" +
				"   \"quantity\":5,\n" +
				"   \"address\":{  \n" +
				"      \"customer\":\" Rob Hawkey  \",\n" +
				"      \"street\":\"123 Street  \",\n" +
				"      \"city\":\" Halifax\",\n" +
				"      \"province\":\"Nova Scotia    \",\n" +
				"      \"country\":\"      \",\n" +
				"      \"postalCode\":\"H0H0H0\"\n" +
				"   }\n" +
				"}\n";

		String expectedOutput="{\"error\":\"Unknown Address\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);
		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - knownAddressFailureCountry");
		}
		else
		{
			System.out.println("FAIL - knownAddressFailureCountry");
		}

	}
	public void knownAddressFailurePostalCode( IAuthentication authentication,
											IShipMate shipMate,
											IDatabase database)
	{
		// testCase to check response for unknown postalCode name in Address
		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"rhawkeySHIP\",\n" +
				"   \"action\":\"SHIP\",\n" +
				"   \"drug\":\"Lisinopril\",\n" +
				"   \"quantity\":5,\n" +
				"   \"address\":{  \n" +
				"      \"customer\":\" Rob Hawkey  \",\n" +
				"      \"street\":\"123 Street  \",\n" +
				"      \"city\":\" Halifax\",\n" +
				"      \"province\":\"Nova Scotia    \",\n" +
				"      \"country\":\"canada\",\n" +
				"      \"postalCode\":\"   \"\n" +
				"   }\n" +
				"}\n";

		String expectedOutput="{\"error\":\"Unknown Address\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);
		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - knownAddressFailurePostalCode");
		}
		else
		{
			System.out.println("FAIL - knownAddressFailurePostalCode");
		}

	}

	public void sufficientStockSuccess( IAuthentication authentication,
										IShipMate shipMate,
										IDatabase database)
	{
		//test Case to check response of sufficient Stock
		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"rhawkeySHIP\",\n" +
				"   \"action\":\"SHIP\",\n" +
				"   \"drug\":\"Lisinopril\",\n" +
				"   \"quantity\":5,\n" +
				"   \"address\":{  \n" +
				"      \"customer\":\" Rob Hawkey  \",\n" +
				"      \"street\":\"123 Street  \",\n" +
				"      \"city\":\" Halifax\",\n" +
				"      \"province\":\"Nova Scotia    \",\n" +
				"      \"country\":\"canada\",\n" +
				"      \"postalCode\":\"B3j2g9\"\n" +
				"   }\n" +
				"}\n";

		String expectedOutput="{\"estimatedDeliveryDate\":\"30-06-2019\",\"status\":\"200\"}";
		String response = processRequest(jsonString,authentication,shipMate,database);

		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - sufficientStockSuccess");
		}
		else
		{
			System.out.println("FAIL - sufficientStockSuccess");
		}

	}

	public void sufficientStockFailure( IAuthentication authentication,
										IShipMate shipMate,
										IDatabase database)
	{
		//test Case to check response of Insufficient Stock
		String jsonString = "{  \n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22jTRUE\",\n" +
				"   \"username\":\"rhawkeySHIP\",\n" +
				"   \"action\":\"SHIP\",\n" +
				"   \"drug\":\"Hydrochlorothiazide\",\n" +
				"   \"quantity\":25,\n" +
				"   \"address\":{  \n" +
				"      \"customer\":\" Rob Hawkey  \",\n" +
				"      \"street\":\"123 Street  \",\n" +
				"      \"city\":\" Halifax\",\n" +
				"      \"province\":\"Nova Scotia    \",\n" +
				"      \"country\":\"canada\",\n" +
				"      \"postalCode\":\"B3j2g9\"\n" +
				"   }\n" +
				"}\n";

		String expectedOutput="{\"error\":\"Insufficient stock\",\"status\":500}";
		String response = processRequest(jsonString,authentication,shipMate,database);

		if(response.equals(expectedOutput))
		{
			System.out.println("PASS - sufficientStockFailure");
		}
		else
		{
			System.out.println("FAIL - sufficientStockFailure");
		}

	}






	static public void runUnitTests()
	{

		RequestProcessor requestProcessor = new RequestProcessor();
		IAuthentication authentication= new IAuthenticationImplementation();
		IShipMate shipMate= new IshipMateInterfaceImpl();
		IDatabase database= new IDatabaseImplementation();

        // test case for valid API(contain TRUE)key
		requestProcessor.authenticateSuccess(authentication,shipMate,database);

		// test case for unknown API(contain FALSE) key
		requestProcessor.authenticateFailure1(authentication, shipMate,database);

		//test case for empty API key
		requestProcessor.authenticateFailure2(authentication,shipMate,database);

		//test case for valid username (contain QUERY)
		requestProcessor.authorizeQuerySuccess(authentication, shipMate,database);

		//test case for unknown username (without QUERY word in username)
		requestProcessor.authorizeQueryFailure1(authentication, shipMate,database);

		//test case for empty username
		requestProcessor.authorizeQueryFailure2(authentication, shipMate,database);

		//test case for valid username (contain SHIP)
		requestProcessor.authorizeShipSuccess(authentication, shipMate,database);

		// test case for unknown username(empty string)
		requestProcessor.authorizeShipFailure1(authentication, shipMate,database);

		// test case for unknown username(Without Ship word in username)
		requestProcessor.authorizeShipFailure2(authentication, shipMate,database);

		// test case for known drug request
		requestProcessor.knownDrugSuccess(authentication, shipMate,database);

		// test case for unknown drug request
		requestProcessor.knownDrugFailure(authentication, shipMate,database);

		// test case for valid known address request
		requestProcessor.knownAddressSuccess(authentication, shipMate,database);

		// test case for unknown address(customer string is empty)
		requestProcessor.knownAddressFailureCustomer(authentication,shipMate,database);

		// test case for unknown address(Street  string is empty)
		requestProcessor.knownAddressFailureStreet(authentication,shipMate,database);

		// test case for unknown address(city string is empty)
		requestProcessor.knownAddressFailureCity(authentication,shipMate,database);

		// test case for unknown address(province string is empty)
		requestProcessor.knownAddressFailureProvince(authentication,shipMate,database);

		// test case for unknown address(Country string is empty)
		requestProcessor.knownAddressFailureCountry(authentication,shipMate,database);

		// test case for unknown address(postalCode string is empty)
		requestProcessor.knownAddressFailurePostalCode(authentication,shipMate,database);

		// test case for sufficient stock of valid drug request
		requestProcessor.sufficientStockSuccess(authentication,shipMate,database);

		// test case for Insufficient stock of valid drug request
		requestProcessor.sufficientStockFailure(authentication,shipMate,database);


	}
}
