package com.etouch.docusign.users;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HtppResponseHelper {
	
	public static final String token = "bearer [your security token here]";
	public static final String createUserUrl = "https://demo.docusign.net/restapi/v2/accounts/[account id here]/users";
	public static final String newUserCreationExcelFile = "C:\\eSignature\\docusign\\docusign_new_user_creation.xls";  // file path
	public static final String deleteUseExcelFile = "C:\\eSignature\\docusign\\docusign_delete_user.xls"; //excel file path
	
	
	public static String getResponseBody(InputStream is) {
		BufferedReader br = null;
		StringBuilder body = null;
		String line = "";
		try {
			br = new BufferedReader(new InputStreamReader(is));
			body = new StringBuilder();
			while ((line = br.readLine()) != null)
				body.append(line);
			return body.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
