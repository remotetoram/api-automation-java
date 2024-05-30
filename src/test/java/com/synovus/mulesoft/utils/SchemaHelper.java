package com.synovus.mulesoft.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import jakarta.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayOutputStream;

@Slf4j
@Component
public class SchemaHelper {
	public  FileInputStream getSchemaFilePath(String fileName) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream("src/test/resources/schema/" + fileName);
		} catch (FileNotFoundException e) {
			log.info("Schema file not found: " + fileName + "-" + e.getMessage());
		}
		return fileInputStream;
	}

	public  FileInputStream getXmlSchemaFilePath(String fileName) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream("src/test/resources/schema/xsd/" + fileName);
		} catch (FileNotFoundException e) {
			log.info("Schema file not found: " + fileName + "-" + e.getMessage());
		}
		return fileInputStream;
	}

	public String parseAndModifySOAPMessage(Response response) throws Exception {
		String soapResponse = response.getBody().asString();
		SOAPMessage soapMessage = null;
		try {
			// Use SOAPMessageFactory to create the SOAP message
			MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			soapMessage = messageFactory.createMessage(null, new ByteArrayInputStream(soapResponse.getBytes()));
		} catch (SOAPException e) {
			// Handle SOAPException
			log.error("Error creating SOAP message: " + e.getMessage());
			throw e;
		}
		SOAPBody soapBody = null;
		try {
			SOAPPart part = soapMessage.getSOAPPart();
			SOAPEnvelope soapEnvelope = part.getEnvelope();
			soapBody = soapEnvelope.getBody();
		} catch (SOAPException e) {
			log.error("Error extracting SOAP body: " + e.getMessage());
			throw e;
		}
		//log.info("Extracted SOAP body: " + soapBody.toString());
		try {
			// Get the content of the SOAP body as XML
			Document doc = soapBody.extractContentAsDocument();
			NodeList someNodes = doc.getElementsByTagName("someElement");
			if (someNodes.getLength() > 0) {
				someNodes.item(0).setTextContent("New Value");
			}
			Source modifiedSource = new DOMSource(doc);
			// Transform the modified content to a String
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			transformer.transform(modifiedSource, new StreamResult(byteArrayOutputStream));
			String modifiedContent = byteArrayOutputStream.toString();
			//log.info("modifiedContent: " + modifiedContent);
			return modifiedContent;
		} catch (SOAPException | TransformerException e) {
			log.error("Error processing SOAP message: " + e.getMessage());
			throw e;
		}
	}

}
