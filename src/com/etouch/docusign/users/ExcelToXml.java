package com.etouch.docusign.users;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.etouch.docusign.users.NewUserXml;

public class ExcelToXml {
	
	
	public List<NewUserXml> createNewUserFromExcel() {
		List<NewUserXml> newUserXmlList = new ArrayList<NewUserXml>();
		
		try {
			FileInputStream file = new FileInputStream(new File(HtppResponseHelper.newUserCreationExcelFile));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			
			while (rowIterator.hasNext()) {
				
				NewUserXml newUserXml = new NewUserXml();
				Row row = rowIterator.next();
				System.out.println("row.getRowNum(): "+row.getRowNum());
				if(row.getRowNum()==0){
					continue;
				}
				newUserXml.setEmail(convertCellToString(row.getCell(0)));
				newUserXml.setEnableConnectForUser(convertCellToString(row.getCell(1)));
				newUserXml.setFirstName(convertCellToString(row.getCell(2)));
				newUserXml.setLastName(convertCellToString(row.getCell(3)));
				newUserXml.setPassword(convertCellToString(row.getCell(4)));
				newUserXml.setForgottenPasswordQuestion1(convertCellToString(row.getCell(5)));
				newUserXml.setForgottenPasswordAnswer1(convertCellToString(row.getCell(6)));
				newUserXml.setSendActivationOnInvalidLogin(convertCellToString(row.getCell(7)));
				newUserXml.setTitle(convertCellToString(row.getCell(8)));
				newUserXml.setUserName(convertCellToString(row.getCell(9)));
				newUserXml.setGroup(convertCellToString(row.getCell(10)));
				
				newUserXmlList.add(newUserXml);
			}
			file.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return newUserXmlList;
	}
	
	
	public Document createNewUserXml(NewUserXml newUserXml) {
		DOMSource source = null;
		Document doc = null;
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			doc = docBuilder.newDocument();
			
			//IntegratorKey element
			/*Element integratorKey = doc.createElement("IntegratorKey");
			integratorKey.appendChild(doc.createTextNode(HtppResponseHelper.integratorKey));
			doc.appendChild(integratorKey);*/


			
			Element newUsersDefinition = doc.createElement("newUsersDefinition");
			doc.appendChild(newUsersDefinition);

			// newUsers elements
			Element newUsers = doc.createElement("newUsers");
			newUsersDefinition.appendChild(newUsers);

			// userInformation elements
			Element userInformation = doc.createElement("userInformation");
			//userInformation.appendChild(doc.createTextNode("yong"));
			newUsers.appendChild(userInformation);
			
			/* setting of paramaters starts here*/

			Element email = doc.createElement("email");
			email.appendChild(doc.createTextNode(newUserXml.getEmail()));
			userInformation.appendChild(email);

			Element enableConnectForUser = doc.createElement("enableConnectForUser");
			enableConnectForUser.appendChild(doc.createTextNode(newUserXml.getEnableConnectForUser()));
			userInformation.appendChild(enableConnectForUser);

			Element firstName = doc.createElement("firstName");
			firstName.appendChild(doc.createTextNode(newUserXml.getFirstName()));
			userInformation.appendChild(firstName);
			
			Element lastName = doc.createElement("lastName");
			lastName.appendChild(doc.createTextNode(newUserXml.getLastName()));
			userInformation.appendChild(lastName);
			
			Element password = doc.createElement("password");
			password.appendChild(doc.createTextNode(newUserXml.getPassword()));
			userInformation.appendChild(password);
			
			Element forgottenPasswordInfo = doc.createElement("forgottenPasswordInfo");
			
			Element forgottenPasswordQuestion1 = doc.createElement("forgottenPasswordQuestion1");
			forgottenPasswordQuestion1.appendChild(doc.createTextNode(newUserXml.getForgottenPasswordQuestion1()));
			forgottenPasswordInfo.appendChild(forgottenPasswordQuestion1);
			Element forgottenPasswordAnswer1 = doc.createElement("forgottenPasswordAnswer1");
			forgottenPasswordAnswer1.appendChild(doc.createTextNode(newUserXml.getForgottenPasswordQuestion1()));
			forgottenPasswordInfo.appendChild(forgottenPasswordAnswer1);
			userInformation.appendChild(forgottenPasswordInfo);
			
			Element title = doc.createElement("title");
			title.appendChild(doc.createTextNode(newUserXml.getTitle()));
			userInformation.appendChild(title);
			
			Element userName = doc.createElement("userName");
			userName.appendChild(doc.createTextNode(newUserXml.getUserName()));
			userInformation.appendChild(userName);
			
			Element sendActivationOnInvalidLogin = doc.createElement("sendActivationOnInvalidLogin");
			sendActivationOnInvalidLogin.appendChild(doc.createTextNode(newUserXml.getSendActivationOnInvalidLogin()));
			userInformation.appendChild(sendActivationOnInvalidLogin);
			
			Element groupList = doc.createElement("groupList");
			Element group = doc.createElement("group");
			Element groupId = doc.createElement("groupId");
			groupId.appendChild(doc.createTextNode(newUserXml.getGroup()));
			group.appendChild(groupId);
			groupList.appendChild(group);
			userInformation.appendChild(groupList);
			
			/* setting of paramaters ends here*/

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\aniruddh\\anifile.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		
		//System.out.println(documentToString(doc));
		
		return doc;
		
	}
	
	public List<NewUserXml> createDeleteUserJson() {
		
		List<NewUserXml> newUserXmlList = new ArrayList<NewUserXml>();
		
		try {
			FileInputStream file = new FileInputStream(new File(HtppResponseHelper.deleteUseExcelFile));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			
			while (rowIterator.hasNext()) {
				
				NewUserXml newUserXml = new NewUserXml();
				Row row = rowIterator.next();
				System.out.println("row.getRowNum(): "+row.getRowNum());
				if(row.getRowNum()==0){
					continue;
				}
				newUserXml.setUserId(convertCellToString(row.getCell(0)));
				newUserXml.setUserName(convertCellToString(row.getCell(1)));
				
				newUserXmlList.add(newUserXml);
			}
			file.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return newUserXmlList;
	}
	
	
	public String convertCellToString(Cell cell) {
		DecimalFormat df = new DecimalFormat("###");
		String returnValue = "";
        switch(cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
            	returnValue += cell.getBooleanCellValue();
                System.out.print(cell.getBooleanCellValue() + "\t\t");
                break;
            case Cell.CELL_TYPE_NUMERIC:
                System.out.print(cell.getNumericCellValue() + "\t\t");
                returnValue += df.format(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                System.out.print(cell.getStringCellValue() + "\t\t");
                returnValue += cell.getStringCellValue();
                break;
        }
        return returnValue;
	}
	
	public String documentToString(Document doc) {
	    try {
	        StringWriter sw = new StringWriter();
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

	        transformer.transform(new DOMSource(doc), new StreamResult(sw));
	        return sw.toString();
	    } catch (Exception ex) {
	        throw new RuntimeException("Error converting to String", ex);
	    }
	}

}
