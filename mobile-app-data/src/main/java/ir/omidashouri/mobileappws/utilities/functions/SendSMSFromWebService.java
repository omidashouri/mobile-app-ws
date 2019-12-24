package ir.omidashouri.mobileappws.utilities.functions;

/*import org.apache.log4j.Logger;
import ir.org.karnik.crm.business.cartable.MessageDAOImpl;
import ir.org.karnik.crm.business.cartable.MessageRecieverDAOImpl;
import ir.org.karnik.crm.dataLayer.account.TblContact;
import ir.org.karnik.crm.dataLayer.authenticate.TblPerson;
import ir.org.karnik.crm.dataLayer.cartable.TblMessage;
import ir.org.karnik.crm.dataLayer.cartable.TblMessageReciever;
import ir.org.karnik.crm.helper.DateConvertor;
import ir.org.karnik.mainParts.business.SmsSettingDAOImpl;
import ir.org.karnik.mainParts.dataLayer.sms.TblSmsSetting;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;


import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.net.URL;*/

/**
 * User: Magfa Date: Apr 20, 2009 Time: 5:48:13 PM this sample shows how to send
 * smses using webservice. remember to fill the USER_NAME & PASSWORD fields with
 * yours before starting. if you have to pass a proxy to reach the Internet, add
 * these lines to the VMParameters: -Dhttp.proxyHost=[your_proxy_IP]
 * -Dhttp.proxyPort=[your_proxy_port]
 */
public class SendSMSFromWebService {
    /*private static final Logger LOGGER = Logger.getLogger(SendSMSFromWebService.class);

	private int parameterCount; // specifies how many requests are to be made

	private static final String END_POINT_URL = "http://sms.magfa.com/services/urn:SOAPSmsQueue";

	String URN = "urn:SOAPSmsQueue";
	String ENQUEUE_METHOD_CALL = "enqueue";
	String USER_NAME;
	String PASSWORD;
	String SENDER_NUMBER;
	String RECIPIENT_NUMBER;
	String DOMAIN;
	public void sendSMS() {

		DOMAIN = "magfa";
		USER_NAME = "modiriat_sanati"; // fill this with your username
		PASSWORD = "urx48k3d"; // fill this with your password
		SENDER_NUMBER = "9830008693"; // fill this with a number from your
										// accounts's number range
		RECIPIENT_NUMBER = "989369865820"; // fill this with the destination
											// number
		URN = "urn:SOAPSmsQueue";
		ENQUEUE_METHOD_CALL = "enqueue";

		try {
			// creating a service object
			Service service = new Service();
			// creating a call object from the service and setting it's basic
			// properties
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(END_POINT_URL));
			call.setOperationName(new QName(URN, ENQUEUE_METHOD_CALL));
			call.setUsername(USER_NAME);
			call.setPassword(PASSWORD);
			call.setReturnType(XMLType.SOAP_ARRAY);
			call.setTimeout(10 * 60 * 1000);
			// defining the parameters the service accepts
			call.addParameter("domain", XMLType.SOAP_STRING, ParameterMode.IN);
			call.addParameter("messages", XMLType.SOAP_ARRAY, ParameterMode.IN);
			call.addParameter("recipientNumbers", XMLType.SOAP_ARRAY,
					ParameterMode.IN);
			call.addParameter("senderNumbers", XMLType.SOAP_ARRAY,
					ParameterMode.IN);
			call
					.addParameter("encodings", XMLType.SOAP_ARRAY,
							ParameterMode.IN);
			call.addParameter("udhs", XMLType.SOAP_ARRAY, ParameterMode.IN);
			call.addParameter("messageClasses", XMLType.SOAP_ARRAY,
					ParameterMode.IN);
			call.addParameter("priorities", XMLType.SOAP_ARRAY,
					ParameterMode.IN);
			call.addParameter("checkingMessageIds", XMLType.SOAP_ARRAY,
					ParameterMode.IN);

			String domain;
			String[] messages;
			String[] recipientNumbers;
			String[] senderNumbers;
			int[] encodings;
			String[] udhs;
			Integer[] mClass;
			Integer[] priorities;
			long[] checkingMessageIds;

			domain = DOMAIN;
			messages = new String[parameterCount];
			recipientNumbers = new String[parameterCount];
			senderNumbers = new String[parameterCount];
			encodings = new int[parameterCount];
			udhs = null;
			mClass = null;
			priorities = null;
			checkingMessageIds = new long[parameterCount];

			for (int i = 0; i < parameterCount; i++) {
				recipientNumbers[i] = RECIPIENT_NUMBER;
				senderNumbers[i] = SENDER_NUMBER;
				checkingMessageIds[i] = i + 10L;
				messages[i] = "Enqueue test message #" + i;
				encodings[i] = -1;
			}
			// preparing the inputs for calling the service
			Object[] params = { domain, messages, recipientNumbers,
					senderNumbers, encodings, udhs, mClass, priorities,
					checkingMessageIds };
			// preparing the object array to be filled as output values
			Object[] returnArray = null;

			try {
				double startTime = System.currentTimeMillis();
				// making the request
				returnArray = (Object[]) call.invoke(params);
                LOGGER.info("request response-time="
                        + (System.currentTimeMillis() - startTime) / 1000
                        + " sec.s");
			} catch (Exception exception) {
				LOGGER.error(exception.getMessage(),exception);
			}

			// print out the results
			if (returnArray != null) {
				for (int i = 0; i < returnArray.length; i++) {
					final long returnValue = (Long) returnArray[i];
					if (returnValue < ErrorCodes.WEB_SENDER_NUMBER_ARRAY_IS_NULL
							.getCode())
                        LOGGER.error("caught error: "
                                + returnValue
                                + ", "
                                + ErrorCodes
                                .getDescriptionForCode((int) returnValue));
					else
                        LOGGER.info("returnArray index " + i + " = "
                            + returnValue);
				}
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
		}


	}

	public Object[] sendSMS(TblPerson[] persons, String senderNumber,
			String messageText ,Long userId) {

		Object[] returnArray = null;
		String[] smsRecipients = new String[persons.length];
		for (int i = 0; i < persons.length; i++) {
			smsRecipients[i] = persons[i].getTblContact()
			.getMobilePhone();
		}
		try {
			returnArray = sendSMSAndSaveMessage(persons, senderNumber, messageText, userId);
					
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}

		return returnArray;
	}

	public Object[] sendSMS(String mobilePhone, String senderNumber,
			String messageText) {

		SmsSettingDAOImpl smsSettingDAOImpl = new SmsSettingDAOImpl();
		TblSmsSetting tblSmsSetting = new TblSmsSetting();
		Object[] returnArray = null;
		URN = "urn:SOAPSmsQueue";
		ENQUEUE_METHOD_CALL = "enqueue";

		try {
			// creating a service object
			tblSmsSetting = smsSettingDAOImpl.findBySenderNumber(senderNumber);
			DOMAIN = tblSmsSetting.getDomain();
			USER_NAME = tblSmsSetting.getUserName(); // fill this with your
														// username
			PASSWORD = tblSmsSetting.getPassword(); // fill this with your
													// password
			SENDER_NUMBER = tblSmsSetting.getSenderNumber(); // fill this
																// with a number
																// from your
																// accounts's
																// number range
			// RECIPIENT_NUMBER = "989369865820"; //fill this with the
			// destination number
			Service service = new Service();
			// creating a call object from the service and setting it's basic
			// properties
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(END_POINT_URL));
			call.setOperationName(new QName(URN, ENQUEUE_METHOD_CALL));
			call.setUsername(USER_NAME);
			call.setPassword(PASSWORD);
			call.setReturnType(XMLType.SOAP_ARRAY);
			call.setTimeout(10 * 60 * 1000);
			// defining the parameters the service accepts
			call.addParameter("domain", XMLType.SOAP_STRING, ParameterMode.IN);
			call.addParameter("messages", XMLType.SOAP_ARRAY, ParameterMode.IN);
			call.addParameter("recipientNumbers", XMLType.SOAP_ARRAY,
					ParameterMode.IN);
			call.addParameter("senderNumbers", XMLType.SOAP_ARRAY,
					ParameterMode.IN);
			call
					.addParameter("encodings", XMLType.SOAP_ARRAY,
							ParameterMode.IN);
			call.addParameter("udhs", XMLType.SOAP_ARRAY, ParameterMode.IN);
			call.addParameter("messageClasses", XMLType.SOAP_ARRAY,
					ParameterMode.IN);
			call.addParameter("priorities", XMLType.SOAP_ARRAY,
					ParameterMode.IN);
			call.addParameter("checkingMessageIds", XMLType.SOAP_ARRAY,
					ParameterMode.IN);

			String domain;
			String[] messages;
			String[] recipientNumbers;
			String[] senderNumbers;
			int[] encodings;
			String[] udhs;
			Integer[] mClass;
			Integer[] priorities;
			long[] checkingMessageIds;

			domain = DOMAIN;
			messages = new String[1];
			recipientNumbers = new String[1];
			senderNumbers = new String[1];
			encodings = new int[1];
			udhs = null;
			mClass = null;
			priorities = null;
			checkingMessageIds = new long[1];

			recipientNumbers[0] = mobilePhone;
			senderNumbers[0] = SENDER_NUMBER;
			checkingMessageIds[0] = 0 + 10L;
			messages[0] = messageText;
			encodings[0] = -1;
			// preparing the inputs for calling the service
			Object[] params = { domain, messages, recipientNumbers,
					senderNumbers, encodings, udhs, mClass, priorities,
					checkingMessageIds };
			// preparing the object array to be filled as output values

			try {
				double startTime = System.currentTimeMillis();
				// making the request
				returnArray = (Object[]) call.invoke(params);
                LOGGER.info("request response-time="
                        + (System.currentTimeMillis() - startTime) / 1000
                        + " sec.s");
			} catch (Exception exception) {
				LOGGER.error(exception.getMessage(),exception);
				returnArray = null;
			}

			// print out the results
			if (returnArray != null) {
				for (int i = 0; i < returnArray.length; i++) {
					final long returnValue = (Long) returnArray[i];
					if (returnValue < ErrorCodes.WEB_SENDER_NUMBER_ARRAY_IS_NULL
							.getCode())
                        LOGGER.error("caught error: "
                                + returnValue
                                + ", "
                                + ErrorCodes
                                .getDescriptionForCode((int) returnValue));
					else
                        LOGGER.info("returnArray index " + i + " = "
                            + returnValue);
				}
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
		}

		return returnArray;
	}

	public Object[] sendSMS(TblContact[] contacts, String senderNumber,
			String messageText ,Long userId) {

		Object[] returnArray = null;
		String[] smsRecipients = new String[contacts.length];
		for (int i = 0; i < contacts.length; i++) {
			smsRecipients[i] = contacts[i].getMobilePhone();
		}
		try {
			returnArray = sendSMS(smsRecipients, senderNumber, messageText,userId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}

		return returnArray;
	}

	public Object[] getStatus(String senderNumber, Long[] messageIds) {

		SmsSettingDAOImpl smsSettingDAOImpl = new SmsSettingDAOImpl();
		TblSmsSetting tblSmsSetting = new TblSmsSetting();
		Object[] returnValues = null;
		URN = "urn:SOAPSmsQueue";
		ENQUEUE_METHOD_CALL = "getMessageStatus";
		try {
			tblSmsSetting = smsSettingDAOImpl.findBySenderNumber(senderNumber);
			DOMAIN = tblSmsSetting.getDomain();
			USER_NAME = tblSmsSetting.getUserName(); // fill this with your
														// username
			PASSWORD = tblSmsSetting.getPassword(); // fill this with your
													// password
			SENDER_NUMBER = tblSmsSetting.getSenderNumber(); // fill this
																// with a number
																// from your
																// accounts's
																// number range
			// creating a service object
			Service service = new Service();
			// making a call out of service
			Call call = (Call) service.createCall();
			// set the basic call properties:
			call.setTargetEndpointAddress(new URL(END_POINT_URL));
			call.setOperationName(new QName(URN, ENQUEUE_METHOD_CALL));
			call.setUsername(USER_NAME);
			call.setPassword(PASSWORD);
			call.setReturnType(XMLType.SOAP_ARRAY);
			call.setTimeout(10 * 60 * 1000);

			// defining the service's input types
			call.addParameter("messageIds", XMLType.SOAP_ARRAY,
					ParameterMode.IN);

			// this is the messageIDs which their staus will be returned
			// fill it with desired messageIDs
			// the length and value of the array is optional
			// long[] messageIds = new long[4];
			// messageIds[0] = 1055812L;
			// messageIds[1] = 4008L;
			// messageIds[2] = 4010L;
			// messageIds[3] = 6000L;

			// preparing the input values to the service
			Object[] params = { messageIds };

			try {
				double startTime = System.currentTimeMillis();

				// requesting the service
				returnValues = (Object[]) call.invoke(params);
                LOGGER.info("Request Response Time= "
                        + (System.currentTimeMillis() - startTime) / 1000
                        + "sec's");
			} catch (Exception exception) {
				LOGGER.error(exception.getMessage(),exception);
			}

			// print out the results
			if (returnValues != null) {
				for (int iterator = 0; iterator < returnValues.length; iterator++) {
                    LOGGER.info("status for message "
                            + messageIds[iterator] + " = "
                            + returnValues[iterator]);
				}
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
		}

		return returnValues;
	}

	public Long getStatus(String senderNumber, Long messageId) {
		SmsSettingDAOImpl smsSettingDAOImpl = new SmsSettingDAOImpl();
		TblSmsSetting tblSmsSetting = new TblSmsSetting();
		Long returnValue = null;
		try {
			tblSmsSetting = smsSettingDAOImpl.findBySenderNumber(senderNumber);
			DOMAIN = tblSmsSetting.getDomain();
			USER_NAME = tblSmsSetting.getUserName(); // fill this with your
														// username
			PASSWORD = tblSmsSetting.getPassword(); // fill this with your
													// password
			SENDER_NUMBER = tblSmsSetting.getSenderNumber(); // fill this
																// with a number
																// from your
																// accounts's
																// number range
			// creating a service object
			Service service = new Service();
			// making a call out of service
			Call call = (Call) service.createCall();
			// setting the basic properties of call
			call.setTargetEndpointAddress(new URL(END_POINT_URL));
			call.setOperationName(new QName(URN, ENQUEUE_METHOD_CALL));
			call.setUsername(USER_NAME);
			call.setPassword(PASSWORD);
			call.setReturnType(XMLType.SOAP_INT);
			call.setTimeout(10 * 60 * 1000);

			// defining the input type of the service
			call.addParameter("messageId", XMLType.SOAP_LONG, ParameterMode.IN);

			// prepare the input values for the service
			// Object[] params = {messageId.toString()};
			Object[] params = { messageId };

			try {
				double startTime = System.currentTimeMillis();

				// requesting the service
				// returnValue = (Long) call.invoke(params);

				call.invoke(params);
                LOGGER.info("Request Response Time= "
                        + (System.currentTimeMillis() - startTime) / 1000
                        + "sec's");
			} catch (Exception exception) {
				LOGGER.error(exception.getMessage(),exception);
			}

			// println out the result:
            LOGGER.info("messageID: " + messageId + ", Status= "
                    + returnValue);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
		}

		return returnValue;
	}

	public Object[] getRealStatus(String senderNumber, Long[] messageIds) {
		SmsSettingDAOImpl smsSettingDAOImpl = new SmsSettingDAOImpl();
		TblSmsSetting tblSmsSetting = new TblSmsSetting();
		Object[] returnValues = null;
		URN = "urn:SOAPSmsQueue";
		ENQUEUE_METHOD_CALL = "getRealMessageStatuses";
		try {
			tblSmsSetting = smsSettingDAOImpl.findBySenderNumber(senderNumber);
			DOMAIN = tblSmsSetting.getDomain();
			USER_NAME = tblSmsSetting.getUserName(); // fill this with your
														// username
			PASSWORD = tblSmsSetting.getPassword(); // fill this with your
													// password
			SENDER_NUMBER = tblSmsSetting.getSenderNumber(); // fill this
																// with a number
																// from your
																// accounts's
																// number range
			// creating a service object
			Service service = new Service();
			// creating a call from service
			Call call = (Call) service.createCall();
			// set the basic properties for call
			call.setTargetEndpointAddress(new URL(END_POINT_URL));
			call.setOperationName(new QName(URN, ENQUEUE_METHOD_CALL));
			call.setUsername(USER_NAME);
			call.setPassword(PASSWORD);
			call.setReturnType(XMLType.SOAP_ARRAY);
			call.setTimeout(10 * 60 * 1000);
			// defining the input data types for the service
			call.addParameter("messageIds", XMLType.SOAP_ARRAY,
					ParameterMode.IN);

			// this is the messageIDs which their staus will be returned
			// fill it with desired messageIDs
			// the length and value of the array is optional
			// long[] messageIds = new long[5];
			// messageIds[0] = 1001L;
			// messageIds[1] = 11027L;
			// messageIds[2] = 11028L;
			// messageIds[3] = 3004L;
			// messageIds[4] = 4005L;

			// prepare the input values for the service
			Object[] params = { messageIds };

			try {
				double startTime = System.currentTimeMillis();

				// requesting the service:
				returnValues = (Object[]) call.invoke(params);

			} catch (Exception exception) {
				LOGGER.error(exception.getMessage(),exception);
			}

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(),ex);
		}

		return returnValues;
	}
	//main Send SMS than Used in MessageManager And  SendSMSDispatchAction
	public Object[] sendSMS(String[] smsRecipients, String senderNumber,
			String messageText ,Long userId)throws Exception {
		
//		int sendCount = (smsRecipients.length /90)+1;
		int i= 0;
		Object[] result = null;
		String[] smsRecipients5 = null;
		String errorNumbers = null;
		try {
			
//			for (int i = 0; i < smsRecipients.length; i) {
//				
//				int start = i * 5;
//				int end = ((i+1) * 5)>smsRecipients.length?smsRecipients.length:((i+1) * 5);
//				
//				smsRecipients5 = Arrays.copyOfRange(smsRecipients, start, end);
//				result = sendSMSLessThan5(smsRecipients5, senderNumber, messageText);
//			}
			while(i < smsRecipients.length)
			{
				
				try
				{
				smsRecipients5 = new String[1];
				
				smsRecipients5[0] =smsRecipients[i];
				result = sendSMSLessThan5(smsRecipients5, senderNumber, messageText);
				try{
					if(result != null && result[0] != null)
					createMessage(messageText, userId, smsRecipients5, null, result[0].toString() , "SENT" , senderNumber);
					else
						createMessage(messageText, userId, smsRecipients5, null, null , "FAIL",senderNumber);
				}catch(Exception e1)				
{
					LOGGER.error(e1.getMessage(),e1);
					createMessage(messageText, userId, smsRecipients5, null, null , "FAIL",senderNumber);
				}

				}catch(Exception ex)				
{
					LOGGER.error(ex.getMessage(),ex);
				}

				i++;
			}
		} catch (Exception e) {
			errorNumbers +=smsRecipients5.toString();
		}

		if(errorNumbers!=null && errorNumbers.length()>0)
		{
			result = new String[1];
			result[0] = "Message Sned Susscceded But There Is an error in this Nubmers:" + errorNumbers;
		}
		return result;
	}	
	
	
	public Object[] sendSMSAndSaveMessage(TblPerson[] persons, String senderNumber,
			String messageText,Long userId)throws Exception {
		
//		int sendCount = (smsRecipients.length /90)+1;
		int i= 0;
		Object[] result = null;
		String[] smsRecipients5 = null;
		String errorNumbers = null;
		try {
			
//			for (int i = 0; i < smsRecipients.length; i) {
//				
//				int start = i * 5;
//				int end = ((i+1) * 5)>smsRecipients.length?smsRecipients.length:((i+1) * 5);
//				
//				smsRecipients5 = Arrays.copyOfRange(smsRecipients, start, end);
//				result = sendSMSLessThan5(smsRecipients5, senderNumber, messageText);
//			}
			while(i < persons.length)
			{
				
				try
				{
				smsRecipients5 = new String[1];
				
				smsRecipients5[0] =persons[i].getTblContact().getMobilePhone();
				result = sendSMSLessThan5(smsRecipients5, senderNumber, messageText);
				try{
					createMessage(messageText, userId, smsRecipients5, persons[i], result[0].toString() , "SENT",senderNumber);
				}catch(Exception e1)				
{
					LOGGER.error(e1.getMessage(),e1);
					createMessage(messageText, userId, smsRecipients5, persons[i], null , "FAIL",senderNumber);

				}

					
				
				}catch(Exception ex)				
{
					LOGGER.error(ex.getMessage(),ex);
					createMessage(messageText, userId, smsRecipients5, persons[i], result[0].toString() , "FAIL",senderNumber);

				}

				i++;
			}
		} catch (Exception e) {
			errorNumbers +=smsRecipients5.toString();
		}

		if(errorNumbers!=null && errorNumbers.length()>0)
		{
			result = new String[1];
			result[0] = "Message Sned Susscceded But There Is an error in this Nubmers:" + errorNumbers;
		}
		return result;
	}	
	
	public void createMessage(String messageText ,Long userId ,String[] smsRecipients5,TblPerson person,String sendId ,String status,String senderNumber)
	{
		try{
			TblMessage tblMessage = new TblMessage();
			tblMessage.setMessageText(messageText);
			tblMessage.setSendSms("y");
			DateConvertor convertor = new DateConvertor();
			tblMessage.setSendDate(convertor.todayDate());
			tblMessage.setSendTime(convertor.getThisTime());
			tblMessage.setSenderEmail(senderNumber);
			TblPerson tblPerson = new TblPerson();
			tblPerson.setId(userId);
		
			tblMessage.setTblPerson(tblPerson);
			tblMessage.setToSms(smsRecipients5[0]);
			tblMessage.setStatus(status);
			MessageDAOImpl messageDAOImpl = new MessageDAOImpl();
			messageDAOImpl.messageSave(tblMessage);
			TblMessageReciever tblMessageReciever = new TblMessageReciever();
			tblMessageReciever.setTblMessage(tblMessage);
			tblMessageReciever.setReceiveNumber(smsRecipients5[0]);
			if(status != null)
			tblMessageReciever.setStatus(status);
			tblMessageReciever.setTblPerson(tblPerson);
			tblMessageReciever.setMessageType("s");
			if(sendId != null)
				tblMessageReciever.setSendId(sendId);
			if(person != null && person.getId() != null)
			{
			
			tblMessageReciever.setSectionId(person.getId());
			
			tblMessageReciever.setSectionName("tblPerson");
			}
			
			MessageRecieverDAOImpl impl = new MessageRecieverDAOImpl();
			impl.messageRecieverSave(tblMessageReciever);
			
		}catch(Exception e1)		{
		      LOGGER.error(e1.getMessage());
		   }

	}
	
	public Object[] sendSMSLessThan5(String[] smsRecipients, String senderNumber,
			String messageText)throws Exception {

		SmsSettingDAOImpl smsSettingDAOImpl = new SmsSettingDAOImpl();
		TblSmsSetting tblSmsSetting = new TblSmsSetting();
		Object[] returnArray = null;
		int tryNumber = 3;

		URN = "urn:SOAPSmsQueue";
		ENQUEUE_METHOD_CALL = "enqueue";
		
		try {
			// creating a service object
			
			tryNumber = 3;
			while(tryNumber > 0)
			{
						tblSmsSetting = smsSettingDAOImpl.findBySenderNumber(senderNumber);
						DOMAIN = tblSmsSetting.getDomain();
						USER_NAME = tblSmsSetting.getUserName(); // fill this with your
																	// username
						PASSWORD = tblSmsSetting.getPassword(); // fill this with your
																// password
						SENDER_NUMBER = tblSmsSetting.getSenderNumber(); // fill this
																			// with a number
																			// from your
																			// accounts's
																			// number range
						// RECIPIENT_NUMBER = "989369865820"; //fill this with the
						// destination number
						Service service = new Service();
						// creating a call object from the service and setting it's basic
						// properties
						Call call = (Call) service.createCall();
						call.setTargetEndpointAddress(new URL(END_POINT_URL));
						call.setOperationName(new QName(URN, ENQUEUE_METHOD_CALL));
						call.setUsername(USER_NAME);
						call.setPassword(PASSWORD);
						call.setReturnType(XMLType.SOAP_ARRAY);
						call.setTimeout(10 * 60 * 1000);
						// defining the parameters the service accepts
						call.addParameter("domain", XMLType.SOAP_STRING, ParameterMode.IN);
						call.addParameter("messages", XMLType.SOAP_ARRAY, ParameterMode.IN);
						call.addParameter("recipientNumbers", XMLType.SOAP_ARRAY,
								ParameterMode.IN);
						call.addParameter("senderNumbers", XMLType.SOAP_ARRAY,
								ParameterMode.IN);
						call
								.addParameter("encodings", XMLType.SOAP_ARRAY,
										ParameterMode.IN);
						call.addParameter("udhs", XMLType.SOAP_ARRAY, ParameterMode.IN);
						call.addParameter("messageClasses", XMLType.SOAP_ARRAY,
								ParameterMode.IN);
						call.addParameter("priorities", XMLType.SOAP_ARRAY,
								ParameterMode.IN);
						call.addParameter("checkingMessageIds", XMLType.SOAP_ARRAY,
								ParameterMode.IN);
			
						String domain;
						String[] messages;
						String[] recipientNumbers;
						String[] senderNumbers;
						int[] encodings;
						String[] udhs;
						Integer[] mClass;
						Integer[] priorities;
						long[] checkingMessageIds;
			
						domain = DOMAIN;
						messages = new String[smsRecipients.length];
						recipientNumbers = new String[smsRecipients.length];
						senderNumbers = new String[smsRecipients.length];
						encodings = new int[smsRecipients.length];
						udhs = null;
						mClass = null;
						priorities = null;
						checkingMessageIds = new long[smsRecipients.length];
			
						for (int i = 0; i < smsRecipients.length; i++) {
							recipientNumbers[i] = smsRecipients[i];
							senderNumbers[i] = SENDER_NUMBER;
							checkingMessageIds[i] = i + 10L;
							messages[i] = messageText.replaceAll("\n\b", "\n");
							encodings[i] = -1;
						}
						// preparing the inputs for calling the service
						Object[] params = { domain, messages, recipientNumbers,
								senderNumbers, encodings, udhs, mClass, priorities,
								checkingMessageIds };
						// preparing the object array to be filled as output values
			
						try {
			//				double startTime = System.currentTimeMillis();
							// making the request
							returnArray = (Object[]) call.invoke(params);
						}catch (org.apache.axis.AxisFault ex) {
							LOGGER.error(ex.getMessage(),ex);
							returnArray = null;
							tryNumber = tryNumber - 1;
                            LOGGER.info("Send sms retry number :"+tryNumber);
							continue;
						}
 catch (Exception exception) {
							LOGGER.error(exception.getMessage(),exception);
							returnArray = null;
							throw new Exception(exception);
						}

						// print out the results
						if (returnArray != null) {
							for (int i = 0; i < returnArray.length; i++) {
								final long returnValue = (Long) returnArray[i];
								if (returnValue < ErrorCodes.WEB_SENDER_NUMBER_ARRAY_IS_NULL
										.getCode())
								{
                                    LOGGER.error("caught error: "
                                            + returnValue
                                            + ", "
                                            + ErrorCodes
                                            .getDescriptionForCode((int) returnValue));
									throw new Exception(ErrorCodes
											.getDescriptionForCode((int) returnValue));
								}
							}
						}		
			break;			
			}
		} catch (Exception ex) {
            LOGGER.error(ex.getMessage(),ex);
			throw new Exception(ex);
		}

		return returnArray;
	}*/

}
