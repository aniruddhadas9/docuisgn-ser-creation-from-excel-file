package com.etouch.docusign.users;

import java.util.List;


import org.json.JSONObject;
import org.w3c.dom.Document;

import com.etouch.docusign.users.NewUserXml;

public class ExcelServices {
	
	public JSONObject createNewUsersInDocuisgn() {
		JSONObject obj = null;
		
		ExcelToXml excelToXml = new ExcelToXml();
		DocusignUser docusignUser = new DocusignUser();
		
		try {

			List<NewUserXml> newUserXmlList  = excelToXml.createNewUserFromExcel();

			for (NewUserXml newUserXml : newUserXmlList) {
				Document doc = excelToXml.createNewUserXml(newUserXml);
				String xml = excelToXml.documentToString(doc);
				System.out.println("-----------------------------------------------------");
				System.out.println(xml);
				obj = docusignUser.createUser(xml);
				System.out.println("-----------------------------------------------------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;

	}
	
	public JSONObject deleteUsersInDocuisgn() {
		JSONObject returnValue = null;
		
		ExcelToXml excelToXml = new ExcelToXml();
		DocusignUser docusignUser = new DocusignUser();
		try {

			List<NewUserXml> newUserXmlList  = excelToXml.createDeleteUserJson();

			for (NewUserXml newUserXml : newUserXmlList) {
				String xml = "{\"users\":[{\"userId\": \""+newUserXml.getUserId()+"\", \"userName\":\""+newUserXml.getUserName()+"\"}]}";
				System.out.println("-----------------------------------------------------");
				System.out.println(xml);
				returnValue = docusignUser.deleteUser(xml);
				System.out.println("-----------------------------------------------------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;

	}
	

	public static void main(String args[]) {
		ExcelServices excelServices = new ExcelServices();
		JSONObject newUser = excelServices.createNewUsersInDocuisgn();
		JSONObject deleteUser = excelServices.deleteUsersInDocuisgn();

	}

}
