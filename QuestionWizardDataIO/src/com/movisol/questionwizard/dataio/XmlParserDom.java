package com.movisol.questionwizard.dataio;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.movisol.questionwizard.entities.Choice;
import com.movisol.questionwizard.entities.ChoiceButtonImageQuestion;
import com.movisol.questionwizard.entities.ChoiceButtonSoundQuestion;
import com.movisol.questionwizard.entities.ChoiceImageQuestion;
import com.movisol.questionwizard.entities.ChoiceImageScrollQuestion;
import com.movisol.questionwizard.entities.ChoicePickerQuestion;
import com.movisol.questionwizard.entities.ChoiceQuestion;
import com.movisol.questionwizard.entities.DateQuestion;
import com.movisol.questionwizard.entities.Option;
import com.movisol.questionwizard.entities.Options;
import com.movisol.questionwizard.entities.PreQuestionTip;
import com.movisol.questionwizard.entities.Question;
import com.movisol.questionwizard.entities.Range;
import com.movisol.questionwizard.entities.TextQuestion;
import com.movisol.tools.HelperUtils;

public class XmlParserDom {
	InputStream xmlFile;

	public XmlParserDom(InputStream xmlFile) {

		this.xmlFile = xmlFile;

	}

	public List<Question> parse(boolean isMetric) {

		// Instantiates the DOM factory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		List<Question> test = new ArrayList<Question>();

		// Creates a new DOM parser
		DocumentBuilder builder;
		try 
		{
			builder = factory.newDocumentBuilder();
			// Reads the whole xml file
			Document dom = builder.parse(xmlFile);

			// Retrieves the root element
			Element root = dom.getDocumentElement();

			// Retrieves all the <question> tags
			NodeList nlPreguntas = root.getElementsByTagName("question");

			// Iterates over all the question
			for (int i = 0; i < nlPreguntas.getLength(); i++) 
			{

				// Gets the current question
				Node actPreg = nlPreguntas.item(i);

				// Gets the question attributes
				NamedNodeMap pregAttr = actPreg.getAttributes();

				String type = pregAttr.getNamedItem("type").getNodeValue();

				
				
				// Gets the questionTip from the current question
				NodeList questionTipList = ((Element) actPreg).getElementsByTagName("questionTip");

				try 
				{
					
					/*********************************/
					/************CHOICE**************/
					/*******************************/
					if (type.equalsIgnoreCase("choice")) 
					{
						ChoiceQuestion choiceQuestion = null;

						choiceQuestion = new ChoiceQuestion();
						
						//Fills the choiceQuestion with the parameters from the xml
						choiceQuestion.setTitle(pregAttr.getNamedItem("title").getNodeValue());
						choiceQuestion.setCategory(pregAttr.getNamedItem("category").getNodeValue());
						choiceQuestion.setMandatory(Boolean.parseBoolean(pregAttr.getNamedItem("mandatory").getNodeValue().toString()));
						choiceQuestion.setKey(pregAttr.getNamedItem("key").getNodeValue());
						if(pregAttr.getNamedItem("answerTime") != null)
							choiceQuestion.setAnswerTime(Integer.parseInt(pregAttr.getNamedItem("answerTime").getNodeValue()));
						if(pregAttr.getNamedItem("filter") != null)
							choiceQuestion.setFilter(pregAttr.getNamedItem("filter").getNodeValue());
						if(pregAttr.getNamedItem("background") != null)
						{
							StringTokenizer st = new StringTokenizer(pregAttr.getNamedItem("background").getNodeValue(), ".");
							try {
								choiceQuestion.setBackground(st.nextToken().toLowerCase());
							} catch (NoSuchElementException e) {
								choiceQuestion.setBackground(pregAttr.getNamedItem("background").getNodeValue().toLowerCase());
							}
						}
					
						
						//Checks if the current question has a tip
						if(questionTipList != null && questionTipList.getLength() > 0)
						{
							Node questionTipNode = questionTipList.item(0);
							
							String questionTip = "";
							for(int j = 0; j < questionTipNode.getChildNodes().getLength(); j++)
							{
								Node cdataNode = questionTipNode.getChildNodes().item(j); 
								
								//Extracts the CDATA info
								if(cdataNode instanceof CDATASection)
								{
									questionTip = cdataNode.getNodeValue();	
									break;
								}
							}
							//Sets the questionTip for the current question								
							choiceQuestion.setQuestionTip(questionTip);
						}

						
						
						// Gets the options list from the current question
						NodeList tmpList = ((Element) actPreg).getElementsByTagName("choice");

						List<Choice> listOpt = new ArrayList<Choice>();
						for (int tmp = 0; tmp < tmpList.getLength(); tmp++) 
						{

							NamedNodeMap choiceAttr = tmpList.item(tmp)
									.getAttributes();
							Choice choice = new Choice();
							choice.setTitle(choiceAttr.getNamedItem("title")
									.getNodeValue().toString());
							choice.setValue(choiceAttr.getNamedItem("value")
									.getNodeValue().toString());
							
							if(choiceAttr.getNamedItem("nextQuestionKey") != null)
							{
								choice.setNextQuestionKey(choiceAttr.getNamedItem("nextQuestionKey").getNodeValue().toString());
							}
						
							NodeList choiceNodeList = ((Element)tmpList.item(tmp)).getElementsByTagName("tip");
							//Checks if the current choice has a tip
							if(choiceNodeList != null && choiceNodeList.getLength() > 0)
							{
								Node tipNode = choiceNodeList.item(0);
								
								String tip = "";
								for(int j = 0; j < tipNode.getChildNodes().getLength(); j++)
								{
									Node cdataNode = tipNode.getChildNodes().item(j); 
									
									//Extracts the CDATA info
									if(cdataNode instanceof CDATASection)
									{
										tip = cdataNode.getNodeValue();	
										break;
									}
								}
								//Sets the choiceTip for the current choice								
								choice.setTip(tip);
							}
							
							listOpt.add(choice);
						
						}

						// Sets the retrieved options list to the variable
						// choiceQuestion
						choiceQuestion.setChoices(listOpt);
						
						// Gets the prequestion tip from the current question
						NodeList tmpPQList = ((Element) actPreg).getElementsByTagName("preQuestionTip");
						if(tmpPQList.getLength() > 0)
						{
							Node preQtipNode = tmpPQList.item(0);
							// Gets the question attributes
							NamedNodeMap preTipAttr = preQtipNode.getAttributes();
							PreQuestionTip preQuestionTip = new PreQuestionTip();
							preQuestionTip.setTitle(HelperUtils.getNodeText(preQtipNode));
							if(preTipAttr.getNamedItem("time") != null)
								preQuestionTip.setTime(Integer.valueOf(preTipAttr.getNamedItem("time").getNodeValue()));
							
							choiceQuestion.setPreQuestionTip(preQuestionTip);
						}
						
						//Sets the question in our test
						test.add(choiceQuestion);
					}
					
					/*********************************/
					/*******CHOICEBUTTOMIMAGE********/
					/*******************************/
					if (type.equalsIgnoreCase("choicebuttonimage")) 
					{
						ChoiceButtonImageQuestion choiceButtonImageQuestion = new ChoiceButtonImageQuestion();
					
						//Fills the choiceQuestion with the parameters from the xml
						choiceButtonImageQuestion.setTitle(pregAttr.getNamedItem("title").getNodeValue());
						choiceButtonImageQuestion.setCategory(pregAttr.getNamedItem("category").getNodeValue());
						choiceButtonImageQuestion.setMandatory(Boolean.parseBoolean(pregAttr.getNamedItem("mandatory").getNodeValue().toString()));
						choiceButtonImageQuestion.setKey(pregAttr.getNamedItem("key").getNodeValue());
						if(pregAttr.getNamedItem("answerTime") != null)
							choiceButtonImageQuestion.setAnswerTime(Integer.parseInt(pregAttr.getNamedItem("answerTime").getNodeValue()));
						if(pregAttr.getNamedItem("filter") != null)
							choiceButtonImageQuestion.setFilter(pregAttr.getNamedItem("filter").getNodeValue());
						if(pregAttr.getNamedItem("background") != null)
						{
							StringTokenizer st = new StringTokenizer(pregAttr.getNamedItem("background").getNodeValue(), ".");
							try {
								choiceButtonImageQuestion.setBackground(st.nextToken().toLowerCase());
							} catch (NoSuchElementException e) {
								choiceButtonImageQuestion.setBackground(pregAttr.getNamedItem("background").getNodeValue().toLowerCase());
							}
						}
					
						
						//Checks if the current question has a tip
						if(questionTipList != null && questionTipList.getLength() > 0)
						{
							Node questionTipNode = questionTipList.item(0);
							
							String questionTip = "";
							for(int j = 0; j < questionTipNode.getChildNodes().getLength(); j++)
							{
								Node cdataNode = questionTipNode.getChildNodes().item(j); 
								
								//Extracts the CDATA info
								if(cdataNode instanceof CDATASection)
								{
									questionTip = cdataNode.getNodeValue();	
									break;
								}
							}
							//Sets the questionTip for the current question								
							choiceButtonImageQuestion.setQuestionTip(questionTip);
						}

						
						
						// Gets the options list from the current question
						NodeList tmpList = ((Element) actPreg).getElementsByTagName("choice");

						List<Choice> listOpt = new ArrayList<Choice>();
						for (int tmp = 0; tmp < tmpList.getLength(); tmp++) 
						{

							NamedNodeMap choiceAttr = tmpList.item(tmp)
									.getAttributes();
							Choice choice = new Choice();
							choice.setTitle(choiceAttr.getNamedItem("title").getNodeValue().toString());
							choice.setValue(choiceAttr.getNamedItem("value").getNodeValue().toString());
							
							if(choiceAttr.getNamedItem("image") != null)
							{
								StringTokenizer st = new StringTokenizer(choiceAttr.getNamedItem("image").getNodeValue(), ".");
								try {
									choice.setImage(st.nextToken().toLowerCase());
								} catch (NoSuchElementException e) {
									choice.setImage(choiceAttr.getNamedItem("image").getNodeValue().toString().toLowerCase());
								}
							}
							
							if(choiceAttr.getNamedItem("nextQuestionKey") != null)
							{
								choice.setNextQuestionKey(choiceAttr.getNamedItem("nextQuestionKey").getNodeValue().toString());
							}
						
							NodeList choiceNodeList = ((Element)tmpList.item(tmp)).getElementsByTagName("tip");
							//Checks if the current choice has a tip
							if(choiceNodeList != null && choiceNodeList.getLength() > 0)
							{
								Node tipNode = choiceNodeList.item(0);
								
								String tip = "";
								for(int j = 0; j < tipNode.getChildNodes().getLength(); j++)
								{
									Node cdataNode = tipNode.getChildNodes().item(j); 
									
									//Extracts the CDATA info
									if(cdataNode instanceof CDATASection)
									{
										tip = cdataNode.getNodeValue();	
										break;
									}
								}
								//Sets the choiceTip for the current choice								
								choice.setTip(tip);
							}
							
							listOpt.add(choice);
						
						}

						// Sets the retrieved options list to the variable
						// choiceQuestion
						choiceButtonImageQuestion.setChoices(listOpt);
						
						// Gets the prequestion tip from the current question
						NodeList tmpPQList = ((Element) actPreg).getElementsByTagName("preQuestionTip");
						if(tmpPQList.getLength() > 0)
						{
							Node preQtipNode = tmpPQList.item(0);
							// Gets the question attributes
							NamedNodeMap preTipAttr = preQtipNode.getAttributes();
							PreQuestionTip preQuestionTip = new PreQuestionTip();
							preQuestionTip.setTitle(HelperUtils.getNodeText(preQtipNode));
							if(preTipAttr.getNamedItem("time") != null)
								preQuestionTip.setTime(Integer.valueOf(preTipAttr.getNamedItem("time").getNodeValue()));
							choiceButtonImageQuestion.setPreQuestionTip(preQuestionTip);
						}
						
						//Sets the question in our test
						test.add(choiceButtonImageQuestion);
					}
		//Added by Girish for ChoiceButtonSound Type Questions			
					/*********************************/
					/**********CHOICEBUTTONSOUND***********/
					/*******************************/
					if (type.equalsIgnoreCase("choicebuttonsound")) 
					{
						ChoiceButtonSoundQuestion choiceButtonSoundQuestion = new ChoiceButtonSoundQuestion();
					
						//Fills the choiceQuestion with the parameters from the xml
						choiceButtonSoundQuestion.setTitle(pregAttr.getNamedItem("title").getNodeValue());
						choiceButtonSoundQuestion.setCategory(pregAttr.getNamedItem("category").getNodeValue());
						choiceButtonSoundQuestion.setMandatory(Boolean.parseBoolean(pregAttr.getNamedItem("mandatory").getNodeValue().toString()));
						choiceButtonSoundQuestion.setKey(pregAttr.getNamedItem("key").getNodeValue());
						if(pregAttr.getNamedItem("answerTime") != null)
							choiceButtonSoundQuestion.setAnswerTime(Integer.parseInt(pregAttr.getNamedItem("answerTime").getNodeValue()));
						if(pregAttr.getNamedItem("filter") != null)
							choiceButtonSoundQuestion.setFilter(pregAttr.getNamedItem("filter").getNodeValue());
						if(pregAttr.getNamedItem("background") != null)
						{
							StringTokenizer st = new StringTokenizer(pregAttr.getNamedItem("background").getNodeValue(), ".");
							try {
								choiceButtonSoundQuestion.setBackground(st.nextToken().toLowerCase());
							} catch (NoSuchElementException e) {
								choiceButtonSoundQuestion.setBackground(pregAttr.getNamedItem("background").getNodeValue().toLowerCase());
							}
						}
						if(pregAttr.getNamedItem("sound") != null)
						{
							StringTokenizer st = new StringTokenizer(pregAttr.getNamedItem("sound").getNodeValue(), ".");
							try {
								choiceButtonSoundQuestion.setSound(st.nextToken().toLowerCase());
							} catch (NoSuchElementException e) {
								choiceButtonSoundQuestion.setSound(pregAttr.getNamedItem("sound").getNodeValue().toLowerCase());
							}
						}
					
						
						//Checks if the current question has a tip
						if(questionTipList != null && questionTipList.getLength() > 0)
						{
							Node questionTipNode = questionTipList.item(0);
							
							String questionTip = "";
							for(int j = 0; j < questionTipNode.getChildNodes().getLength(); j++)
							{
								Node cdataNode = questionTipNode.getChildNodes().item(j); 
								
								//Extracts the CDATA info
								if(cdataNode instanceof CDATASection)
								{
									questionTip = cdataNode.getNodeValue();	
									break;
								}
							}
							//Sets the questionTip for the current question								
							choiceButtonSoundQuestion.setQuestionTip(questionTip);
						}

						
						
						// Gets the options list from the current question
						NodeList tmpList = ((Element) actPreg).getElementsByTagName("choice");

						List<Choice> listOpt = new ArrayList<Choice>();
						for (int tmp = 0; tmp < tmpList.getLength(); tmp++) 
						{

							NamedNodeMap choiceAttr = tmpList.item(tmp)
									.getAttributes();
							Choice choice = new Choice();
							if(choiceAttr.getNamedItem("title") != null)
								choice.setTitle(choiceAttr.getNamedItem("title").getNodeValue().toString());
							if(choiceAttr.getNamedItem("value") != null)
								choice.setValue(choiceAttr.getNamedItem("value").getNodeValue().toString());
							if(choiceAttr.getNamedItem("image") != null)
							{
								StringTokenizer st = new StringTokenizer(choiceAttr.getNamedItem("image").getNodeValue(), ".");
								try {
									choice.setImage(st.nextToken().toLowerCase());
								} catch (NoSuchElementException e) {
									choice.setImage(choiceAttr.getNamedItem("image").getNodeValue().toLowerCase());
								}
							}

							
							if(choiceAttr.getNamedItem("nextQuestionKey") != null)
							{
								choice.setNextQuestionKey(choiceAttr.getNamedItem("nextQuestionKey").getNodeValue().toString());
							}
						
							NodeList choiceNodeList = ((Element)tmpList.item(tmp)).getElementsByTagName("tip");
							//Checks if the current choice has a tip
							if(choiceNodeList != null && choiceNodeList.getLength() > 0)
							{
								Node tipNode = choiceNodeList.item(0);
								
								String tip = "";
								for(int j = 0; j < tipNode.getChildNodes().getLength(); j++)
								{
									Node cdataNode = tipNode.getChildNodes().item(j); 
									
									//Extracts the CDATA info
									if(cdataNode instanceof CDATASection)
									{
										tip = cdataNode.getNodeValue();	
										break;
									}
								}
								//Sets the choiceTip for the current choice								
								choice.setTip(tip);
							}
							
							listOpt.add(choice);
						
						}

						// Sets the retrieved options list to the variable
						// choiceQuestion
						choiceButtonSoundQuestion.setChoices(listOpt);
						
						// Gets the prequestion tip from the current question
						NodeList tmpPQList = ((Element) actPreg).getElementsByTagName("preQuestionTip");
						if(tmpPQList.getLength() > 0)
						{
							Node preQtipNode = tmpPQList.item(0);
							// Gets the question attributes
							NamedNodeMap preTipAttr = preQtipNode.getAttributes();
							PreQuestionTip preQuestionTip = new PreQuestionTip();
							preQuestionTip.setTitle(HelperUtils.getNodeText(preQtipNode));
							if(preTipAttr.getNamedItem("time") != null)
								preQuestionTip.setTime(Integer.valueOf(preTipAttr.getNamedItem("time").getNodeValue()));
							choiceButtonSoundQuestion.setPreQuestionTip(preQuestionTip);
						}
						
						//Sets the question in our test
						test.add(choiceButtonSoundQuestion);
					}
	//	END of	Added by Girish for ChoiceButtonSound Type Questions			
				
					/*********************************/
					/**********CHOICEIMAGE***********/
					/*******************************/
					if (type.equalsIgnoreCase("choiceimage")) 
					{
						ChoiceImageQuestion choiceImageQuestion = new ChoiceImageQuestion();
					
						//Fills the choiceQuestion with the parameters from the xml
						choiceImageQuestion.setTitle(pregAttr.getNamedItem("title").getNodeValue());
						choiceImageQuestion.setCategory(pregAttr.getNamedItem("category").getNodeValue());
						choiceImageQuestion.setMandatory(Boolean.parseBoolean(pregAttr.getNamedItem("mandatory").getNodeValue().toString()));
						choiceImageQuestion.setKey(pregAttr.getNamedItem("key").getNodeValue());
						if(pregAttr.getNamedItem("answerTime") != null)
							choiceImageQuestion.setAnswerTime(Integer.parseInt(pregAttr.getNamedItem("answerTime").getNodeValue()));
						if(pregAttr.getNamedItem("filter") != null)
							choiceImageQuestion.setFilter(pregAttr.getNamedItem("filter").getNodeValue());
						if(pregAttr.getNamedItem("background") != null)
						{
							StringTokenizer st = new StringTokenizer(pregAttr.getNamedItem("background").getNodeValue(), ".");
							try {
								choiceImageQuestion.setBackground(st.nextToken().toLowerCase());
							} catch (NoSuchElementException e) {
								choiceImageQuestion.setBackground(pregAttr.getNamedItem("background").getNodeValue().toLowerCase());
							}
						}
						if(pregAttr.getNamedItem("image") != null)
						{
							StringTokenizer st = new StringTokenizer(pregAttr.getNamedItem("image").getNodeValue(), ".");
							try {
								choiceImageQuestion.setImage(st.nextToken().toLowerCase());
							} catch (NoSuchElementException e) {
								choiceImageQuestion.setImage(pregAttr.getNamedItem("image").getNodeValue().toLowerCase());
							}
						}
					
						
						//Checks if the current question has a tip
						if(questionTipList != null && questionTipList.getLength() > 0)
						{
							Node questionTipNode = questionTipList.item(0);
							
							String questionTip = "";
							for(int j = 0; j < questionTipNode.getChildNodes().getLength(); j++)
							{
								Node cdataNode = questionTipNode.getChildNodes().item(j); 
								
								//Extracts the CDATA info
								if(cdataNode instanceof CDATASection)
								{
									questionTip = cdataNode.getNodeValue();	
									break;
								}
							}
							//Sets the questionTip for the current question								
							choiceImageQuestion.setQuestionTip(questionTip);
						}

						
						
						// Gets the options list from the current question
						NodeList tmpList = ((Element) actPreg).getElementsByTagName("choice");

						List<Choice> listOpt = new ArrayList<Choice>();
						for (int tmp = 0; tmp < tmpList.getLength(); tmp++) 
						{

							NamedNodeMap choiceAttr = tmpList.item(tmp)
									.getAttributes();
							Choice choice = new Choice();
							if(choiceAttr.getNamedItem("title") != null)
								choice.setTitle(choiceAttr.getNamedItem("title").getNodeValue().toString());
							if(choiceAttr.getNamedItem("value") != null)
								choice.setValue(choiceAttr.getNamedItem("value").getNodeValue().toString());
							if(choiceAttr.getNamedItem("image") != null)
							{
								StringTokenizer st = new StringTokenizer(choiceAttr.getNamedItem("image").getNodeValue(), ".");
								try {
									choice.setImage(st.nextToken().toLowerCase());
								} catch (NoSuchElementException e) {
									choice.setImage(choiceAttr.getNamedItem("image").getNodeValue().toLowerCase());
								}
							}

							
							if(choiceAttr.getNamedItem("nextQuestionKey") != null)
							{
								choice.setNextQuestionKey(choiceAttr.getNamedItem("nextQuestionKey").getNodeValue().toString());
							}
						
							NodeList choiceNodeList = ((Element)tmpList.item(tmp)).getElementsByTagName("tip");
							//Checks if the current choice has a tip
							if(choiceNodeList != null && choiceNodeList.getLength() > 0)
							{
								Node tipNode = choiceNodeList.item(0);
								
								String tip = "";
								for(int j = 0; j < tipNode.getChildNodes().getLength(); j++)
								{
									Node cdataNode = tipNode.getChildNodes().item(j); 
									
									//Extracts the CDATA info
									if(cdataNode instanceof CDATASection)
									{
										tip = cdataNode.getNodeValue();	
										break;
									}
								}
								//Sets the choiceTip for the current choice								
								choice.setTip(tip);
							}
							
							listOpt.add(choice);
						
						}

						// Sets the retrieved options list to the variable
						// choiceQuestion
						choiceImageQuestion.setChoices(listOpt);
						
						// Gets the prequestion tip from the current question
						NodeList tmpPQList = ((Element) actPreg).getElementsByTagName("preQuestionTip");
						if(tmpPQList.getLength() > 0)
						{
							Node preQtipNode = tmpPQList.item(0);
							// Gets the question attributes
							NamedNodeMap preTipAttr = preQtipNode.getAttributes();
							PreQuestionTip preQuestionTip = new PreQuestionTip();
							preQuestionTip.setTitle(HelperUtils.getNodeText(preQtipNode));
							if(preTipAttr.getNamedItem("time") != null)
								preQuestionTip.setTime(Integer.valueOf(preTipAttr.getNamedItem("time").getNodeValue()));
							choiceImageQuestion.setPreQuestionTip(preQuestionTip);
						}
						
						//Sets the question in our test
						test.add(choiceImageQuestion);
					}
					/*********************************/
					/*******CHOICEIMAGESCROLL********/
					/*******************************/
					if (type.equalsIgnoreCase("choiceimagescroll")) 
					{
						ChoiceImageScrollQuestion choiceImageScrollQuestion = new ChoiceImageScrollQuestion();
					
						//Fills the choiceQuestion with the parameters from the xml
						choiceImageScrollQuestion.setTitle(pregAttr.getNamedItem("title").getNodeValue());
						choiceImageScrollQuestion.setCategory(pregAttr.getNamedItem("category").getNodeValue());
						choiceImageScrollQuestion.setMandatory(Boolean.parseBoolean(pregAttr.getNamedItem("mandatory").getNodeValue().toString()));
						choiceImageScrollQuestion.setKey(pregAttr.getNamedItem("key").getNodeValue());
						if(pregAttr.getNamedItem("filter") != null)
							choiceImageScrollQuestion.setFilter(pregAttr.getNamedItem("filter").getNodeValue());
						if(pregAttr.getNamedItem("background") != null)
						{
							StringTokenizer st = new StringTokenizer(pregAttr.getNamedItem("background").getNodeValue(), ".");
							try {
								choiceImageScrollQuestion.setBackground(st.nextToken().toLowerCase());
							} catch (NoSuchElementException e) {
								choiceImageScrollQuestion.setBackground(pregAttr.getNamedItem("background").getNodeValue().toLowerCase());
							}
						}
						if(pregAttr.getNamedItem("image") != null)
						{
							StringTokenizer st = new StringTokenizer(pregAttr.getNamedItem("image").getNodeValue(), ".");
							try {
								choiceImageScrollQuestion.setImage(st.nextToken().toLowerCase());
							} catch (NoSuchElementException e) {
								choiceImageScrollQuestion.setImage(pregAttr.getNamedItem("image").getNodeValue().toLowerCase());
							}
						}
					
						
						//Checks if the current question has a tip
						if(questionTipList != null && questionTipList.getLength() > 0)
						{
							Node questionTipNode = questionTipList.item(0);
							
							String questionTip = "";
							for(int j = 0; j < questionTipNode.getChildNodes().getLength(); j++)
							{
								Node cdataNode = questionTipNode.getChildNodes().item(j); 
								
								//Extracts the CDATA info
								if(cdataNode instanceof CDATASection)
								{
									questionTip = cdataNode.getNodeValue();	
									break;
								}
							}
							//Sets the questionTip for the current question								
							choiceImageScrollQuestion.setQuestionTip(questionTip);
						}

						
						
						// Gets the options list from the current question
						NodeList tmpList = ((Element) actPreg).getElementsByTagName("choice");

						List<Choice> listOpt = new ArrayList<Choice>();
						for (int tmp = 0; tmp < tmpList.getLength(); tmp++) 
						{

							NamedNodeMap choiceAttr = tmpList.item(tmp)
									.getAttributes();
							Choice choice = new Choice();
							choice.setTitle(choiceAttr.getNamedItem("title").getNodeValue().toString());
							choice.setValue(choiceAttr.getNamedItem("value").getNodeValue().toString());

							
							if(choiceAttr.getNamedItem("nextQuestionKey") != null)
							{
								choice.setNextQuestionKey(choiceAttr.getNamedItem("nextQuestionKey").getNodeValue().toString());
							}
						
							NodeList choiceNodeList = ((Element)tmpList.item(tmp)).getElementsByTagName("tip");
							//Checks if the current choice has a tip
							if(choiceNodeList != null && choiceNodeList.getLength() > 0)
							{
								Node tipNode = choiceNodeList.item(0);
								
								String tip = "";
								for(int j = 0; j < tipNode.getChildNodes().getLength(); j++)
								{
									Node cdataNode = tipNode.getChildNodes().item(j); 
									
									//Extracts the CDATA info
									if(cdataNode instanceof CDATASection)
									{
										tip = cdataNode.getNodeValue();	
										break;
									}
								}
								//Sets the choiceTip for the current choice								
								choice.setTip(tip);
							}
							
							listOpt.add(choice);
						
						}

						// Sets the retrieved options list to the variable
						// choiceQuestion
						choiceImageScrollQuestion.setChoices(listOpt);
						
						// Gets the prequestion tip from the current question
						NodeList tmpPQList = ((Element) actPreg).getElementsByTagName("preQuestionTip");
						if(tmpPQList.getLength() > 0)
						{
							Node preQtipNode = tmpPQList.item(0);
							// Gets the question attributes
							NamedNodeMap preTipAttr = preQtipNode.getAttributes();
							PreQuestionTip preQuestionTip = new PreQuestionTip();
							preQuestionTip.setTitle(HelperUtils.getNodeText(preQtipNode));
							if(preTipAttr.getNamedItem("time") != null)
								preQuestionTip.setTime(Integer.valueOf(preTipAttr.getNamedItem("time").getNodeValue()));
							choiceImageScrollQuestion.setPreQuestionTip(preQuestionTip);
						}

						
						//Sets the question in our test
						test.add(choiceImageScrollQuestion);
					}
					
					/*********************************/
					/************TEXT**************/
					/*******************************/
					if (type.equalsIgnoreCase("text")) 
					{
						TextQuestion textQuestion = null;

						textQuestion = new TextQuestion();
						
						//Fills the choiceQuestion with the parameters from the xml
						textQuestion.setTitle(pregAttr.getNamedItem("title").getNodeValue());
						textQuestion.setCategory(pregAttr.getNamedItem("category").getNodeValue());
						textQuestion.setMandatory(Boolean.parseBoolean(pregAttr.getNamedItem("mandatory").getNodeValue().toString()));
						textQuestion.setKey(pregAttr.getNamedItem("key").getNodeValue());
						if(pregAttr.getNamedItem("answerTime") != null)
							textQuestion.setAnswerTime(Integer.parseInt(pregAttr.getNamedItem("answerTime").getNodeValue()));
						if(pregAttr.getNamedItem("filter") != null)
							textQuestion.setFilter(pregAttr.getNamedItem("filter").getNodeValue());
						if(pregAttr.getNamedItem("background") != null)
						{
							StringTokenizer st = new StringTokenizer(pregAttr.getNamedItem("background").getNodeValue(), ".");
							try {
								textQuestion.setBackground(st.nextToken().toLowerCase());
							} catch (NoSuchElementException e) {
								textQuestion.setBackground(pregAttr.getNamedItem("background").getNodeValue().toLowerCase());
							}
						}
						
						//Checks if the current question has a tip
						if(questionTipList != null && questionTipList.getLength() > 0)
						{
							Node questionTipNode = questionTipList.item(0);
							
							String questionTip = "";
							for(int j = 0; j < questionTipNode.getChildNodes().getLength(); j++)
							{
								Node cdataNode = questionTipNode.getChildNodes().item(j); 
								
								//Extracts the CDATA info
								if(cdataNode instanceof CDATASection)
								{
									questionTip = cdataNode.getNodeValue();	
									break;
								}
							}
							//Sets the questionTip for the current question								
							textQuestion.setQuestionTip(questionTip);
						}
						
						// Gets the prequestion tip from the current question
						NodeList tmpPQList = ((Element) actPreg).getElementsByTagName("preQuestionTip");
						if(tmpPQList.getLength() > 0)
						{
							Node preQtipNode = tmpPQList.item(0);
							// Gets the question attributes
							NamedNodeMap preTipAttr = preQtipNode.getAttributes();
							PreQuestionTip preQuestionTip = new PreQuestionTip();
							preQuestionTip.setTitle(HelperUtils.getNodeText(preQtipNode));
							if(preTipAttr.getNamedItem("time") != null)
								preQuestionTip.setTime(Integer.valueOf(preTipAttr.getNamedItem("time").getNodeValue()));
							textQuestion.setPreQuestionTip(preQuestionTip);
						}
						
						//Sets the question in our test
						test.add(textQuestion);
					}					
					
					/*********************************/
					/*************DATE***************/
					/*******************************/
					if (type.equalsIgnoreCase("date")) 
					{
						DateQuestion dateQuestion = null;

						dateQuestion = new DateQuestion();
						
						//Fills the choiceQuestion with the parameters from the xml
						dateQuestion.setTitle(pregAttr.getNamedItem("title").getNodeValue());
						dateQuestion.setCategory(pregAttr.getNamedItem("category").getNodeValue());
						dateQuestion.setMandatory(Boolean.parseBoolean(pregAttr.getNamedItem("mandatory").getNodeValue().toString()));
						dateQuestion.setKey(pregAttr.getNamedItem("key").getNodeValue());
						if(pregAttr.getNamedItem("filter") != null)
							dateQuestion.setFilter(pregAttr.getNamedItem("filter").getNodeValue());
						
						if(pregAttr.getNamedItem("background") != null)
						{
							StringTokenizer st = new StringTokenizer(pregAttr.getNamedItem("background").getNodeValue(), ".");
							try {
								dateQuestion.setBackground(st.nextToken().toLowerCase());
							} catch (NoSuchElementException e) {
								dateQuestion.setBackground(pregAttr.getNamedItem("background").getNodeValue().toLowerCase());
							}
						}
					
						
						//Checks if the current question has a tip
						if(questionTipList != null && questionTipList.getLength() > 0)
						{
							Node questionTipNode = questionTipList.item(0);
							
							String questionTip = "";
							for(int j = 0; j < questionTipNode.getChildNodes().getLength(); j++)
							{
								Node cdataNode = questionTipNode.getChildNodes().item(j); 
								
								//Extracts the CDATA info
								if(cdataNode instanceof CDATASection)
								{
									questionTip = cdataNode.getNodeValue();	
									break;
								}
							}
							//Sets the questionTip for the current question								
							dateQuestion.setQuestionTip(questionTip);
						}
						
						// Gets the prequestion tip from the current question
						NodeList tmpPQList = ((Element) actPreg).getElementsByTagName("preQuestionTip");
						if(tmpPQList.getLength() > 0)
						{
							Node preQtipNode = tmpPQList.item(0);
							// Gets the question attributes
							NamedNodeMap preTipAttr = preQtipNode.getAttributes();
							PreQuestionTip preQuestionTip = new PreQuestionTip();
							preQuestionTip.setTitle(HelperUtils.getNodeText(preQtipNode));
							if(preTipAttr.getNamedItem("time") != null)
								preQuestionTip.setTime(Integer.valueOf(preTipAttr.getNamedItem("time").getNodeValue()));
							dateQuestion.setPreQuestionTip(preQuestionTip);
						}
						
						//Sets the question in our test
						test.add(dateQuestion);
					}


					/*********************************/
					/**********CHOICEPICKER**********/
					/*******************************/
					if (type.equalsIgnoreCase("choicepicker")) 
					{
						ChoicePickerQuestion choicePickerQuestion = null;	
						choicePickerQuestion = new ChoicePickerQuestion();
						//Fills the choiceQuestion with the parameters from the xml
						choicePickerQuestion.setTitle(pregAttr.getNamedItem("title").getNodeValue());
						choicePickerQuestion.setCategory(pregAttr.getNamedItem("category").getNodeValue());
						choicePickerQuestion.setMandatory(Boolean.parseBoolean(pregAttr.getNamedItem("mandatory").getNodeValue().toString()));
						choicePickerQuestion.setKey(pregAttr.getNamedItem("key").getNodeValue());
						if(pregAttr.getNamedItem("filter") != null)
							choicePickerQuestion.setFilter(pregAttr.getNamedItem("filter").getNodeValue());

						if(pregAttr.getNamedItem("background") != null)
						{
							StringTokenizer st = new StringTokenizer(pregAttr.getNamedItem("background").getNodeValue(), ".");
							try {
								choicePickerQuestion.setBackground(st.nextToken().toLowerCase());
							} catch (NoSuchElementException e) {
								choicePickerQuestion.setBackground(pregAttr.getNamedItem("background").getNodeValue().toLowerCase());
							}
						}
						
						
						//Checks if the current question has a tip
						if(questionTipList != null && questionTipList.getLength() > 0)
						{
							Node questionTipNode = questionTipList.item(0);
							
							String questionTip = "";
							for(int j = 0; j < questionTipNode.getChildNodes().getLength(); j++)
							{
								Node cdataNode = questionTipNode.getChildNodes().item(j); 
								
								//Extracts the CDATA info
								if(cdataNode instanceof CDATASection)
								{
									questionTip = cdataNode.getNodeValue();	
									break;
								}
							}
							//Sets the questionTip for the current question								
							choicePickerQuestion.setQuestionTip(questionTip);
						}
							
						
						

						// Gets the picker list from the current question
						NodeList tmpList = ((Element) actPreg).getElementsByTagName("options");
						List<Options> listPicker = new ArrayList<Options>();

						for (int tmp = 0; tmp < tmpList.getLength(); tmp++) 
						{

							NamedNodeMap choiceAttr = tmpList.item(tmp).getAttributes();
							
							Options options = new Options();
							if(choiceAttr.getNamedItem("title") != null)
								options.setTitle(choiceAttr.getNamedItem("title").getNodeValue().toString());
							if(choiceAttr.getNamedItem("width") != null)
								options.setWidth(Integer.parseInt(choiceAttr.getNamedItem("width").getNodeValue().toString()));
							if(choiceAttr.getNamedItem("style") != null)
								options.setStyle(choiceAttr.getNamedItem("style").getNodeValue().toString());
							if(choiceAttr.getNamedItem("defaultValue") != null)
								options.setDefaultValue(choiceAttr.getNamedItem("defaultValue").getNodeValue().toString());
							
							
							NodeList tmpOption = ((Element) tmpList.item(0)).getElementsByTagName("option");							
							
							if(tmpOption != null && tmpOption.getLength() > 0)
							{
								List<Option> optionList = new ArrayList<Option>();
								
								for(int j = 0; j < tmpOption.getLength(); j++)
								{
									NamedNodeMap pickerAttr = tmpOption.item(j).getAttributes();
									
									Option option = new Option();
									
									if(pickerAttr.getNamedItem("title") != null)
										option.setTitle(pickerAttr.getNamedItem("title").getNodeValue().toString());
									if(pickerAttr.getNamedItem("value") != null)
										option.setValue(Integer.parseInt(pickerAttr.getNamedItem("value").getNodeValue().toString()));
	
									optionList.add(option);
								}
								options.setOption(optionList);
							}
							
							NodeList tmpRange = ((Element) tmpList.item(tmp)).getElementsByTagName("range");							
							
							if(tmpRange != null && tmpRange.getLength() > 0)
							{
								NamedNodeMap pickerAttr = tmpRange.item(0).getAttributes();
								
								Range range = new Range();
								if(pickerAttr.getNamedItem("from") != null)
									range.setFrom(Integer.parseInt(pickerAttr.getNamedItem("from").getNodeValue().toString()));
								if(pickerAttr.getNamedItem("to") != null)
									range.setTo(Integer.parseInt(pickerAttr.getNamedItem("to").getNodeValue().toString()));
								if(pickerAttr.getNamedItem("interval") != null)
									range.setInterval(Integer.parseInt(pickerAttr.getNamedItem("interval").getNodeValue().toString()));
								if(pickerAttr.getNamedItem("decimaldigits") != null)
									range.setDecimalDigits(Integer.parseInt(pickerAttr.getNamedItem("decimaldigits").getNodeValue().toString()));
	
								options.setRange(range);
							}
						
							listPicker.add(options);

						}

						// Sets the retrieved options list to the variable
						// choiceQuestion
						choicePickerQuestion.setOptions(listPicker);
						
						// Gets the prequestion tip from the current question
						NodeList tmpPQList = ((Element) actPreg).getElementsByTagName("preQuestionTip");
						if(tmpPQList.getLength() > 0)
						{
							Node preQtipNode = tmpPQList.item(0);
							// Gets the question attributes
							NamedNodeMap preTipAttr = preQtipNode.getAttributes();
							PreQuestionTip preQuestionTip = new PreQuestionTip();
							preQuestionTip.setTitle(HelperUtils.getNodeText(preQtipNode));
							if(preTipAttr.getNamedItem("time") != null)
								preQuestionTip.setTime(Integer.valueOf(preTipAttr.getNamedItem("time").getNodeValue()));
							choicePickerQuestion.setPreQuestionTip(preQuestionTip);
						}
						
						if(pregAttr.getNamedItem("filter") != null)
						{
							if(isMetric && pregAttr.getNamedItem("filter").getNodeValue().toString().equals("usesMetricSystem=YES"))
								test.add(choicePickerQuestion);
							if(!isMetric && pregAttr.getNamedItem("filter").getNodeValue().toString().equals("usesMetricSystem=NO"))
								test.add(choicePickerQuestion);
						}
						else
							test.add(choicePickerQuestion);
					}
					
					
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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
