package melfood.framework.abn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.Crypt;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import melfood.framework.Ctx;
import melfood.framework.system.BeanHelper;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

/**
 * This is a client-facing service class. All public methods will be exposed to the client.<br>
 * Their return values and parameters will be passed to the client or taken from the client, respectively.<br>
 * This will be a singleton instance, shared between all requests.
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL, String, Exception).<br>
 * LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level. For info on these levels, look for tomcat/log4j documentation
 */
public class ABNLookup {
	
	private static final Logger logger = LoggerFactory.getLogger(ABNLookup.class);
	
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level; recommend changing this to FATAL or ERROR before deploying. For info on these levels, look for tomcat/log4j documentation
	 */
	String Name = "";
	ABNDetails abnDetails;

	// String gEntityName = "";
	// String gFirstName = "";
	// String gSurname = "";

	public ABNLookup() {
	}

	public static void main(String[] args) {
		final String abn = "72144073147";// 50212805852, 073901032
		ABNLookup lookup = new ABNLookup();
		try {
			lookup.getEntityNamebyABN(abn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ABNDetails getACNByCreditworks(String ACN) {
		abnDetails = new ABNDetails();
		abnDetails.setCheckAgainst("ASIC");
		abnDetails.setACN(ACN);
		abnDetails.setEntityName("ACN Not Found");

		try {
			String User = Ctx.xmlConfig.getString("lookup-url/creditWorks/User");
			String Secret = Ctx.xmlConfig.getString("lookup-url/creditWorks/Secret");
			String Action = Ctx.xmlConfig.getString("lookup-url/creditWorks/Action");

			SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd"); 
			String plainText = Secret + Action + dt.format(new Date());
			String Salt = Ctx.xmlConfig.getString("lookup-url/creditWorks/Salt");

			String Hash = Crypt.crypt(plainText, Salt);
			String AuthText = User + ":" + Hash;

			URL url = new URL(Ctx.xmlConfig.getString("lookup-url/creditWorks/URL") + "/" + Action + "?ACN=" + ACN);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("X-Authorization", AuthText);

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String apiOutput = br.readLine();
			System.out.println(apiOutput);
			conn.disconnect();

			JSONObject obj = new JSONObject(apiOutput);
			String CompanyName = obj.getString("CompanyName");
			abnDetails.setEntityName(CompanyName);
			abnDetails.setACN(ACN);
			String ABN = obj.getString("ABN");
			abnDetails.setABN(ABN);
			// 4 letters APTY default to Private
			// String CompanyType = obj.getString("CompanyType");
			// abnDetails.setEntityTypeCode(CompanyType);
			abnDetails.setEntityTypeCode("PRV");
			// 4 letters REGD --> Registered
			String CompanyStatusCode = obj.getString("CompanyStatusCode");
			abnDetails.setEntityStatus(CompanyStatusCode);
			// Registered
			String CompanyStatus = obj.getString("CompanyStatus");
			// state currently is blank
			String State = obj.getString("State");
			// sub
			String Locality = obj.getString("Locality");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (!StringUtils.equalsIgnoreCase(abnDetails.getEntityName(), "ACN Not Found")) {
					RpcCmmLookupCountService lookupCountService = (RpcCmmLookupCountService) BeanHelper.getBean("lookupCountService");
					lookupCountService.increaseLookupCount("ACN", ACN);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return abnDetails;

	}

	public ABNDetails getEntityNamebyABN(String ABN) throws Exception {
		try {
			abnDetails = new ABNDetails();
			abnDetails.setCheckAgainst("ABR");
			abnDetails.setABN(ABN);
			abnDetails.setEntityName("ABN Not Found");

			Builder parser = new Builder();

			String url = Ctx.xmlConfig.getString("lookup-url/abn") + ABN;

			logger.info("Lookup request URL : " + url);
			Document doc = parser.build(url);

			// save xml file to local folder just for testing
//			PrintWriter out = new PrintWriter(UssCtx.config.getEnvConfig("data-directory/aemo-inbox/dir") + "ABN_" + ABN + ".xml");
//			out.write(doc.toXML());
//			out.flush();
//			out.close();
			// save xml file to local folder

			Element RootElement = doc.getRootElement();
			Name = "";
			listChildren(RootElement, 5);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (!StringUtils.equalsIgnoreCase(abnDetails.getEntityName(), "ABN Not Found")) {
					RpcCmmLookupCountService lookupCountService = (RpcCmmLookupCountService) BeanHelper.getBean("lookupCountService");
					lookupCountService.increaseLookupCount("ABN", ABN);
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}

		return abnDetails;
	}

	public ABNDetails getEntityNamebyACN(String ACN) throws Exception {

		try {
			abnDetails = new ABNDetails();
			abnDetails.setCheckAgainst("ABR");
			abnDetails.setACN(ACN);
			abnDetails.setEntityName("ACN Not Found");

			Builder parser = new Builder();

			String url = Ctx.xmlConfig.getString("lookup-url/acn") + ACN;

			Document doc = parser.build(url);

			// save xml file to local folder just for testing
//			PrintWriter out = new PrintWriter(UssCtx.config.getEnvConfig("data-directory/aemo-inbox/dir") + "ACN_" + ACN + ".xml");
//			out.write(doc.toXML());
//			out.flush();
//			out.close();
			// save xml file to local folder

			Element RootElement = doc.getRootElement();
			Name = "";
			listChildren(RootElement, 5);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (!StringUtils.equalsIgnoreCase(abnDetails.getEntityName(), "ACN Not Found")) {
					RpcCmmLookupCountService lookupCountService = (RpcCmmLookupCountService) BeanHelper.getBean("lookupCountService");
					lookupCountService.increaseLookupCount("ACN", ACN);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return abnDetails;
	}

	public void listChildren(Element current, int depth) {

		// ASICNumber
		if (current.getQualifiedName().equals("ASICNumber")) {
			abnDetails.setASICNumber(current.getValue());
		}

		// organisationName with mainName
		if (current.getQualifiedName().equals("mainName")) {
			Name = current.getQualifiedName();
		}
		if (Name.equals("mainName")) if (current.getQualifiedName().equals("organisationName")) {
			abnDetails.setEntityName(current.getValue());
			Name = "";
		}

		// organisationName with mainTradingName
		if (abnDetails.getEntityName().contains("Not Found") && current.getQualifiedName().equals("mainTradingName")) {
			Name = current.getQualifiedName();
		}
		if (Name.equals("mainTradingName")) if (current.getQualifiedName().equals("organisationName")) {
			abnDetails.setEntityName(current.getValue());
			Name = "";
		}

		// Sole Trader, entity name is legal name
		if (abnDetails.getEntityName().contains("Not Found") && current.getQualifiedName().equals("legalName")) {
			Name = current.getQualifiedName();
		}
		if (Name.equals("legalName")) {
			if (current.getQualifiedName().equals("givenName")) {
				abnDetails.setEntityName(current.getValue());
			}

			if (current.getQualifiedName().equals("otherGivenName")) {
				if (null != current.getValue() && current.getValue().trim().length() > 0) abnDetails.setEntityName(abnDetails.getEntityName() + " " + current.getValue());
			}

			// Put FamilyName In Front
			if (current.getQualifiedName().equals("familyName")) {
				// abnDetails.setEntityName(current.getValue());
				abnDetails.setEntityName(current.getValue() + ", " + abnDetails.getEntityName());
			}

			// if (current.getQualifiedName().equals("givenName")) {
			// abnDetails.setEntityName(current.getValue());
			// }
			// if (current.getQualifiedName().equals("familyName")) {
			// abnDetails.setEntityName(abnDetails.getEntityName()+" "+
			// current.getValue());
			// }
		}

		// entityStatusCode
		if (current.getQualifiedName().equals("entityStatus")) {
			Name = current.getQualifiedName();
		}
		if (Name.equals("entityStatus")) if (current.getQualifiedName().equals("entityStatusCode")) {
			abnDetails.setEntityStatus(current.getValue());
			Name = "";
		}

		// entityTypeCode & Description
		if (current.getQualifiedName().equals("entityType")) {
			Name = current.getQualifiedName();
		}
		if (Name.equals("entityType")) {
			if (current.getQualifiedName().equals("entityTypeCode")) {
				abnDetails.setEntityTypeCode(current.getValue());
			}
			if (current.getQualifiedName().equals("entityDescription")) {
				abnDetails.setEntityDescription(current.getValue());
			}
		}

		// stateCode & postcode
		if (current.getQualifiedName().equals("mainBusinessPhysicalAddress")) {
			Name = current.getQualifiedName();
		}
		if (Name.equals("mainBusinessPhysicalAddress")) {
			if (current.getQualifiedName().equals("stateCode")) {
				abnDetails.setStateCode(current.getValue());
			}
			if (current.getQualifiedName().equals("postcode")) {
				abnDetails.setPostcode(current.getValue());
			}
		}

		// For Sole Trader, should check if the legal name match the customer's
		// name
		if (current.getQualifiedName().equals("legalName")) {
			Name = current.getQualifiedName();
		}
		if (Name.equals("legalName")) {
			if (current.getQualifiedName().equals("givenName")) {
				abnDetails.setFirstName(current.getValue());
			}
			if (current.getQualifiedName().equals("familyName")) {
				abnDetails.setSurname(current.getValue());
			}
		}

		Elements children = current.getChildElements();
		for (int i = 0; i < children.size(); i++) {
			listChildren(children.get(i), depth + 1);
		}
	}

}
