package com.movisol.questionwizard.dataio;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.movisol.questionwizard.entities.Literals;

public class LiteralsXmlParserDom {
	InputStream xmlFile;

	public LiteralsXmlParserDom(InputStream xmlFile) {

		this.xmlFile = xmlFile;

	}

	public List<Literals> parse() {

		// Instantiates the DOM factory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		List<Literals> test = new ArrayList<Literals>();

		// Creates a new DOM parser
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			// Reads the whole xml file
			Document dom = builder.parse(xmlFile);

			// Retrieves the root element
			Element root = dom.getDocumentElement();

			//Gets all the <literal> tags
			NodeList nlLiteral = root.getElementsByTagName("literal");
			
			for(int j = 0; j < nlLiteral.getLength(); j++)
			{
				Node nLiteral = nlLiteral.item(j);
				NamedNodeMap literalAttr = nLiteral.getAttributes();
				Literals literal = new Literals();
				literal.setKey(literalAttr.getNamedItem("key").getNodeValue());

				//Extracts the CDATA value
				NodeList nlTemp = nLiteral.getChildNodes();
				Node nValue = (Node)nlTemp.item(0);				
				if(nValue != null && nValue.getNodeValue() != null)
					literal.setValue(nValue.getNodeValue());
				else
					literal.setValue("");
				test.add(literal);
			}
			

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return test;
	}
}
