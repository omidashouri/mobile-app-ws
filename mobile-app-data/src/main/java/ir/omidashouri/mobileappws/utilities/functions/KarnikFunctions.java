package ir.omidashouri.mobileappws.utilities.functions;


/*import ir.org.karnik.crm.business.authenticate.PersonDAOImpl;
import ir.org.karnik.crm.business.setting.ParameterDAOImpl;
import ir.org.karnik.crm.dataLayer.authenticate.TblCapability;
import ir.org.karnik.crm.dataLayer.authenticate.TblPerson;
import ir.org.karnik.crm.dataLayer.setting.TblParameter;
import ir.org.karnik.crm.dataLayer.setting.TblParameterCaption;
import ir.org.karnik.crm.presentation.cartable.message.MessageManager;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.security.Security;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;*/

public class KarnikFunctions {
   /* private static final Logger LOGGER = Logger.getLogger(KarnikFunctions.class);
    private TblParameter parameter;
    private ParameterDAOImpl impl ;
    
    //property for send email
    String host = "mail.imi.ir";
	String from = "edusupport@imi.ir";
	String username = "edusupport@imi.ir";
	String password = "123456789";
	
	String subject ="Username And Password";	
    
	public static String generateString(Random rng, String characters, int length)
	{
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}	
	 public Long getCreateCode(String className) {
		   long output = 1000;
		   impl = new ParameterDAOImpl();
		   String parameterName = className ;
	       parameter = impl.findByParamNameNew(parameterName);
	       
	       if(parameter == null )
	       {
	    	   parameter = new TblParameter();
	    	   parameter.setParamName(parameterName);
	    	   parameter.setParamValue(output+ "");
	    	   impl.parameterSave(parameter);
//			   try {
//				throw new Exception(
//					"%%%%% The Parameter That Defined Does Not Exist. %%%%%");
//			    } catch (Exception e) {
//LOGGER.error(//			    	e.getMessage(),//			    	e);
//			    	return null;	
//			    }
	    	   
	       }else
	       {
	    	   output = Long.parseLong(parameter.getParamValue()) + 1;
	    	   parameter.setParamValue(String.valueOf(output));
		       impl.parameterUpdate(parameter);
	       }
	       return new Long(output);
	   }
	public static float Round(float floatNumber, int roundDigitNumber) {
		  float p = (float)Math.pow(10,roundDigitNumber);
		  floatNumber = floatNumber * p;
		  float tmp = Math.round(floatNumber);
		  return (float)tmp/p;
		  }
	
	public boolean IsThisANumber(String str){
		boolean result=true;
		int charecter;
		for(int i=0;i<str.length();i++){
			charecter=str.charAt(i);
			if(!(charecter>=48 && charecter<=57))
				return false;
		}
		
		return result;
	}
	
	public void getParamValueFromParamCaptionByLanguageId(TblParameter[] tblParameters,Long languageId) {
		for (int i = 0; i < tblParameters.length; i++) {
			if (tblParameters[i]!=null && tblParameters[i].getTblParameterCaptions()!=null) {
				TblParameterCaption[] tblParameterCaptions = tblParameters[i]
						.getTblParameterCaptions().toArray(
								new TblParameterCaption[tblParameters[i]
										.getTblParameterCaptions().size()]);;
				for (int j = 0; j < tblParameterCaptions.length; j++) {
					if (tblParameterCaptions[j].getTblLanguage().getId().equals(languageId)) {
						tblParameters[i]
								.setParamValue(tblParameterCaptions[j]
										.getCaption());
						break;
					}
				}
			}
		}
		
	}
	
	public String[] createReportDateForProjectTaskHeader(String StartDate , String endDate){
		
		String[] StarDate = StartDate.split("/");
		String[] enDate = endDate.split("/");
		ArrayList<String> arrayList = new ArrayList<String>();
		
		int counter = Integer.parseInt(StarDate[1]);
		int year = Integer.parseInt(StarDate[0]);
		int endOfMonth = 12;
		
		while(year <= Integer.parseInt(enDate[0]))
		{
			if(year == Integer.parseInt(enDate[0]))
				endOfMonth = Integer.parseInt(enDate[1]);
				
			for (int i = counter; i <= endOfMonth  ; i++) {
				if (i >= 10) {
					if(i == 12)
						arrayList.add(year+ "/" + i + "/29");
					else
						arrayList.add(year+ "/" + i + "/30");
				}
				else
					arrayList.add(year+ "/0" + i + "/30");
			}
			counter = 1;
			year ++ ;
		}
		StarDate = (String[]) arrayList.toArray(new String[arrayList.size()]);
		return StarDate;
		
	}
	public String criteriaParser(Map criteria){
		String result="";
		
		Set set1=criteria.keySet();
		Iterator iterator=set1.iterator();
		
		String operator="";
		while(iterator.hasNext()){
			Object object=iterator.next();
			String key=object.toString();
			if (key.equalsIgnoreCase("fieldName")){
				String temp=fixedCriteria(criteria);
				if(!temp.equals(""))
					return (" ("+temp+") ");
				else
					return "";
			}
			if (key.equalsIgnoreCase("operator")){
				operator = criteria.get(key).toString();
				continue;
			}
			if(key.equalsIgnoreCase("criteria")){
				List<?> tt;
				tt=(List)criteria.get(key);
				if(operator.equalsIgnoreCase("not")){
					if(tt.size()>0){
						result+=" not( ";
						for(int i=0;i<tt.size();i++){
							criteria=(Map)tt.get(i);
							String temp=criteriaParser(criteria);
							result+=temp;
							if (i==tt.size()-1 || temp.equals(""))
								;
							else
								result+=" and ";
						}
						result+=") ";
					}
				}else{
					if(tt.size()>0){
						result+=" (";
						for(int i=0;i<tt.size();i++){
							criteria=(Map)tt.get(i);
							String temp=criteriaParser(criteria);
							result+=temp;
							if (i==tt.size()-1 || temp.equals(""))
								;
							else
								result+= operator + " ";
						}
						result+=") ";
					}
				}
			}
			
		}
		if(result.length()<5)
			return "";
		return result;
	}
	public int CountDaysBetweenToDate(String StartDate1,String EndDate1)
	{
		
		String[] startDa1=StartDate1.toString().split("/");
		String[] endDa1=EndDate1.toString().split("/");
		int[] startDate={0,0,0};
		int[] endDate={0,0,0};
		for(int i=0;i<3;i++)
		{
			startDate[i]=(new Long(startDa1[i])).intValue();
		}
		for(int i=0;i<3;i++)
		{
			endDate[i]=(new Long(endDa1[i])).intValue();
		}
		int dCount=0;
		
		dCount=endDate[2]-startDate[2]+(endDate[0]-startDate[0])*365+(endDate[1]-startDate[1])*30;
		
		for(int i=0;i<=6-startDate[1];i++)
		{
			dCount=dCount+1;
		}
		for(int i=0;i<=6-endDate[1];i++)
		{
			dCount=dCount-1;
		}
		return dCount;
	}
	
	String fixedCriteria(Map fixc){
		String string="";
		Set set1=fixc.keySet();
		Iterator iterator=set1.iterator();
		String operator="";
		String fieldName="";
		boolean operatorFind=false;
		boolean betweenFlag=false;
		boolean betparam=false;
		while(iterator.hasNext()){
			Object object=iterator.next();
			String key=object.toString();
			if(fixc.get(key)==null)
				return "";
			String value=fixc.get(key).toString();
			
			/////// fieldName \\\\\\\\\
			if(key.equalsIgnoreCase("fieldName")){
				string+="-";
				string+=value;
				string+="-";
				fieldName="-";
				fieldName+=value;
				fieldName+="-";
			}
			
			////// operator \\\\\\\\\
			else if(key.equalsIgnoreCase("operator")){
				operator=value;
				if(value.equalsIgnoreCase("iContains")){
					string+=" LIKE '%";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("iStartsWith")){
					string+=" LIKE '";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("iEndsWith")){
					string+=" LIKE '%";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("iNotContains")){
					string+=" NOT LIKE '%";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("iNotStartsWith")){
					string+=" NOT LIKE '";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("iNotEndsWith")){
					string+=" NOT LIKE '%";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("equalsIgnoreCase") || value.equalsIgnoreCase("iEquals")|| value.equalsIgnoreCase("equals")){
					string+=" ='";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("notEqual") || value.equalsIgnoreCase("iNotEqual")){
					string+=" <>'";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("lessThan")){
					string+=" <'";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("greaterThan")){
					string+=" >'";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("lessOrEqual")){
					string+=" <='";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("greaterOrEqual")){
					string+=" >='";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("isNull")){
					string+=" is null ";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("notNull")){
					string+=" is not null ";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("equalsIgnoreCaseField")){
					string+=" = ";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("notEqualField")){
					string+=" <> ";
					operatorFind=true;
				}
				else if(value.equalsIgnoreCase("between")){
					string=string.substring(0, string.length()-fieldName.length());
					operatorFind=true;
				}
			}
			
			//////// value \\\\\\\\\\
			else if(key.equalsIgnoreCase("value")){
				if(operator.equalsIgnoreCase("iContains")){
					string+=value;
					string+="%'";
				}
				else if(operator.equalsIgnoreCase("iStartsWith")){
					string+=value;
					string+="%'";
				}
				else if(operator.equalsIgnoreCase("iEndsWith")){
					string+=value;
					string+="'";
				}
				else if(operator.equalsIgnoreCase("iNotContains")){
					string+=value;
					string+="%'";
				}
				else if(operator.equalsIgnoreCase("iNotStartsWith")){
					string+=value;
					string+="%'";
				}
				else if(operator.equalsIgnoreCase("iNotEndsWith")){
					string+=value;
					string+="'";
				}
				else if(operator.equalsIgnoreCase("equalsIgnoreCase")|| operator.equalsIgnoreCase("iEquals") || operator.equalsIgnoreCase("equals")){
					string+=value;
					string+="'";
				}
				else if(operator.equalsIgnoreCase("notEqual") || operator.equalsIgnoreCase("iNotEqual")){
					string+=value;
					string+="'";
				}
				else if(operator.equalsIgnoreCase("lessThan")){
					string+=value;
					string+="'";
				}
				else if(operator.equalsIgnoreCase("greaterThan")){
					string+=value;
					string+="'";
				}
				else if(operator.equalsIgnoreCase("lessOrEqual")){
					string+=value;
					string+="'";
				}
				else if(operator.equalsIgnoreCase("greaterOrEqual")){
					string+=value;
					string+="'";
				}
				else if(operator.equalsIgnoreCase("equalsIgnoreCaseField")){
					string+="-";
						string+=value;
						string+="-";
					//string+="'";
				}
				else if(operator.equalsIgnoreCase("notEqualField")){
					string+="-";
						string+=value;
						string+="-";
					//string+="'";
				}
				else if(operator.equalsIgnoreCase("between")){
					;
				}
				
			}
			else if(operator.equalsIgnoreCase("between") && key.equalsIgnoreCase("start")){
				if(value!=null){
					betparam=true;
					string+=fieldName+">="+" '"+value+"'";
					betweenFlag=true;
				}
				//string+=" and ";
			}
			else if(operator.equalsIgnoreCase("between") && key.equalsIgnoreCase("end")){
				if(value!=null){
					betparam=true;
					if(betweenFlag)
						string+=" and "+fieldName;
					
					string+=" <= "+"'"+value+"'";;
					string+=" ";
				}
			}
			else
				;//string+=value+" ";
		}
		if((operatorFind==false) || (operator.equalsIgnoreCase("between") && betparam==false)){
			string="";
		}
				
		return string;
	}
	public static boolean getSerialVersionUID(Long serialVersionUID)
	{
		int a 	= serialVersionUID.intValue() * -1;
		a 		= a -10;
		a 		= a /20;
		a 		= (a *4)-20;
		if(a == 0)
			return true;
		else
			return false;
			
	}
	
	public static boolean capabilityCheck(Long userId,String action) {
		if(action!=null && action.equals("")){
			return true;
		}
		PersonDAOImpl personDAOImpl = new PersonDAOImpl();    
		TblCapability tblCapability = personDAOImpl.personCanDo(userId, action);
		if (tblCapability!=null && tblCapability.getId() != null)
			return true;
		else
			return false;
	}
	
	protected static String makeCommaSeparatedStringFromArray(TblPerson[] persons){
		String personIds="";
		if (persons!=null){
			for(TblPerson person:persons){
				if (!"".equals(personIds)){
					personIds+=",";
				}
				personIds+=person.getId();
			}
		}
		return personIds;
	}


	public void SendSMS(String phoneNumber, String smsText, Long companyId , Long userId){
		MessageManager messageManager = new MessageManager();
		String[] phoneNumbers = new String[1];
		if (!phoneNumber.startsWith("+"))
				phoneNumber = "+98" + phoneNumber.substring(1);
		phoneNumbers[0] = phoneNumber;
		try {
			messageManager.sendSMS(phoneNumbers, smsText , companyId,userId);
		} catch (Exception e) {
		      LOGGER.error(e.getMessage());
		   }

	}
	
	public void sendEmail(String user,String pass,String email,String mailText,HttpServletRequest request)throws MessagingException{
    	
    	String result = "";
			
													
		String text="";
		if(mailText!=null || !mailText.equalsIgnoreCase(""))
			text=mailText;
		else
			text="Username: "+user+'\n'
					+"Password: "+pass;
		

		Security
				.addProvider(Security.getProvider("SunJSSE"));

		// Get system properties
		Properties props = new Properties();
		

		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "25");

		// Get session
		JAuthenticate authenticator = new JAuthenticate(username, password);

		// Define message
		try {
			Transport trans = null;
			
					javax.mail.Session session = javax.mail.Session.getInstance(props, authenticator);
					session.setDebug(true);
					MimeMessage message = new MimeMessage(session);

					message.setFrom(new InternetAddress(from));
					message.setSubject(subject, "utf-8");
					
					message.addRecipient(Message.RecipientType.TO,
							new InternetAddress(email));
					// Define message

					MimeBodyPart textPart = new MimeBodyPart();
					textPart.setContent(text, "text/html;charset=utf-8");

					Multipart mp = new MimeMultipart();
					mp.addBodyPart(textPart);

					message.setContent(mp);
					// Send message
					
					trans = session.getTransport("smtp");
					trans.send(message);
					trans.close();
					session = null;
					trans.close();
					
			
			
			result = "success";
		} catch (MessagingException e) {
			result = "fail";
			throw e;
		}

	
    }

public void sendEmail(String email,String subjectMail,String mailText,HttpServletRequest request)throws MessagingException{
    	
    	String result = "";
		
													
		String text="";
		if(subjectMail != null)
			subject = subjectMail;
		if(mailText!=null || !mailText.equalsIgnoreCase(""))
			text=mailText;
		else
			text="";
		

		Security
				.addProvider(Security.getProvider("SunJSSE"));

		// Get system properties
		Properties props = new Properties();
		

		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "25");

		// Get session
		JAuthenticate authenticator = new JAuthenticate(username, password);

		// Define message
		try {
			Transport trans = null;
			
					javax.mail.Session session = javax.mail.Session.getInstance(props, authenticator);
					session.setDebug(true);
					MimeMessage message = new MimeMessage(session);

					message.setFrom(new InternetAddress(from));
					message.setSubject(subject, "utf-8");
					
					message.addRecipient(Message.RecipientType.TO,
							new InternetAddress(email));
					// Define message

					MimeBodyPart textPart = new MimeBodyPart();
					textPart.setContent(text, "text/html;charset=utf-8");

					Multipart mp = new MimeMultipart();
					mp.addBodyPart(textPart);

					message.setContent(mp);
					// Send message
					
					trans = session.getTransport("smtp");
					trans.send(message);
					trans.close();
					session = null;
					trans.close();
					
			
			
			result = "success";
		} catch (MessagingException e) {
			result = "fail";
			throw e;
		}

	
    }
	public void sendEmail(String sender,String receiver,String subject,String mailText,
			HttpServletRequest request,String username,String password)throws MessagingException{
    	
		String host = KarnikSettings.mailHost;
													
		if(mailText==null || mailText.equalsIgnoreCase(" "))
			throw new MessagingException();
		
		Security
				.addProvider(Security.getProvider("SunJSSE"));

		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "25");

		JAuthenticate authenticator = new JAuthenticate(username, password);

		try {
			Transport trans = null;
			
			javax.mail.Session session = javax.mail.Session.getInstance(props, authenticator);
			session.setDebug(true);
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(sender));
			message.setSubject(subject, "utf-8");
			
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(receiver));
			// Define message

			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(mailText, "text/html;charset=utf-8");

			Multipart mp = new MimeMultipart();
			mp.addBodyPart(textPart);

			message.setContent(mp);
			trans = session.getTransport("smtp");
			trans.send(message);
			trans.close();
		} catch (MessagingException e) {
			throw e;
		}

    }

	public String makeDateByYearAndMonthAndDay(String Year,String Month,String Day){
	String result = "";
	result+=Year;
	result+="/";
	result+=Month;
	result+="/";
	result+=Day;
	return result;
}

public static boolean checkIfNumber(String in) {
    try {
        Long.parseLong(in);
    } catch (NumberFormatException ex) {
        return false;
    }

   return true;
}

public static boolean checkMobileNumber(String in) {
    try {
    	Double.parseDouble(in);
        if(in.length() != 11 && in.charAt(0) != '0')
        	return false;
    } catch (NumberFormatException ex) {
        return false;
    }

   return true;
}


	public String convertDigit2String(Long digit){
		String[] a = new String[]{"صفر" , "يك", "دو", "سه" , "چهار" ,"پنج" , "شش" ,"هفت" , "هشت" ,"نه"};
		String[] b = new String[]{"ده" , "يازده","دوازده" , "سيزده", "چهارده" , "پانزده" ,"شانزده" , "هفده" ,"هجده" , "نوزده"};
		String[] c = new String[]{" "," ","بيست" , "سي", "چهل", "پنجاه" , "شصت" ,"هفتاد" , "هشتاد" ,"نود"};
		String[] d = new String[]{" ","صد" , "دويست", "سيصد", "چهارصد" , "پانصد" ,"ششصد" , "هفتصد" ,"هشتصد","نهصد"};
		String[] e = new String[]{"","هزار" , "ميليون", "ميليارد"};
		
		String res = "";
		
		if (digit<10)
			res = a[new Integer(digit.toString())];
		else if (digit<20){
			Long dd = digit-10;
			res = b[new Integer(dd.toString())]; 
		} else if (digit <100) {
			Long dd = digit / 10;
			Long yk = digit - (dd*10);
			res += c[new Integer(dd.toString())];
			if (yk!=0){
				res += " و ";
				res += a[new Integer(yk.toString())];
			}
		} else if (digit <1000){
			Long sd = digit / 100;
			Long rem = (digit - (sd * 100));
			
			res = d[new Integer(sd.toString())];
			if (rem != 0){
				res += " و ";
				res += convertDigit2String(rem);
			}
		} else {
			Long[] parts = new Long[5];
			int i=0;
			while (digit!=0){
				parts[i] = digit % 1000;
				digit = digit / 1000;
				i ++;
			}
			for (int j=i-1 ; j >=0 ; j--){
				if (parts[j]>0){
					if (j!=i-1)
						res += " و ";
					res += convertDigit2String(parts[j]) + "  "
							+ e[j];
				}
					
			}
			
		}
		
		
		return res;
	}
	
	public static Object cloner(Object instance, int depth){
		
		if(depth==0)
			return null;
		Class myclass = instance.getClass();
		Object object=null;
		try {
			
			object=myclass.newInstance();
			Method[] methods = myclass.getMethods();
			
			for(int i=0;i<methods.length;i++){
				String name=methods[i].getName();
				if(name.startsWith("get")){
					String methodName="set"+name.substring(3, name.length());
					int index=-1;
					for(int k=0;k<methods.length;k++){
						if(methods[k].getName().equals(methodName)){
							index=k;
							break;
						}
					}
					Object getTemp=methods[i].invoke(instance);
					if(getTemp!=null && getTemp.getClass().getName().startsWith("java")){
						if(index>=0)
							methods[index].invoke(object, methods[i].invoke(instance));
					}else if(getTemp!=null){
						//cloner(getTemp, depth-1);
						if(index>=0){
							methods[index].invoke(object,cloner(getTemp,depth-1));
						}
					}
				}
		}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}

		
		return object;
	}
	
	public static void renameFile(String originalFileName,
			String renamedFileName) {

		File originalFile = new File(originalFileName);
		*//**
		 * Check if file exists
		 *//*
		boolean fileExists = originalFile.exists();

		*//**
		 * Check if it is a directory
		 *//*
		boolean isDirectory = originalFile.isDirectory();

		*//**
		 * If file does not exist, return
		 *//*
		if (!fileExists) {

			return;
		}
		*//**
		 * If file is a directory, return
		 *//*
		if (isDirectory) {
			return;
		}

		File renamedFile = new File(renamedFileName);

		boolean renamed = originalFile.renameTo(renamedFile);
	}
	
	//write by abolfazl mohiti
	//format of orgImage is : c:\\image\\mkyong_jpg.jpg
	public static void resizeImage(String orgImage,int IMG_WIDTH, int IMG_HEIGHT){
				try
				{
				BufferedImage originalImage = ImageIO.read(new File(orgImage));
				int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		 
				BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
				

				Graphics2D g = resizedImage.createGraphics();
				g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
				g.dispose();
				String ext = orgImage.substring(orgImage.length()-3, orgImage.length());
				ImageIO.write(resizedImage, ext, new File(orgImage));
				
				}catch(Exception ex)				
{
LOGGER.error(ex.getMessage(),ex);
//					throw ex;
				}

	    }
	
	*//** @author Ashouri.Omid	 *//*
	public Map<String, Object> removeNullFromSessionMap(
			HttpServletRequest request, String criteriaName) throws Exception {
		try {

			Map<String, Object> criteriaMap = new HashMap<String, Object>();
			criteriaMap = (Map<String, Object>) request.getSession().getAttribute(criteriaName);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Set<String> set = criteriaMap.keySet();
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				String key = iterator.next();
				if (criteriaMap.get(key) != null || criteriaMap.get(key) == "") {
					Object value = criteriaMap.get(key);
					paramMap.put(key, value);
				}
			}
			return paramMap;
		} catch (Exception e) {
			throw e;
		}


	}

	public static String convertMinuteToHour(String min)
	{
		String hour = "000:00";
		try {
			int timeInMIn = new Integer(min).intValue();
			int  hh = timeInMIn / 60;
			int  mm = timeInMIn - (hh * 60);
			hour =  (hh<10?"00" + hh:(hh<100?"0" + hh:hh)) + ":" + (mm<10?"0" + mm:mm);
		} catch (NumberFormatException e) {
		      LOGGER.error(e.getMessage());
		   }


		
		return hour;
	}
	
	public static void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}
	
	public static File[] getDirectoryFolders(File folder) {
		File[] subDirs = folder.listFiles(new FileFilter() {  
	        public boolean accept(File pathname) {  
	            return pathname.isDirectory();  
	        }  
	    });  
		return subDirs;
	}
	
	public static File[] getDirectoryFiles(File folder) {
		File[] subFiles = folder.listFiles(new FileFilter() {  
	        public boolean accept(File pathname) {  
	            return pathname.isFile();  
	        }  
	    });  
		return subFiles;
	}
	
	public static File[] getDirectoryFoldersAndFiles(File folder) {
		File[] subDirs = folder.listFiles();  
		return subDirs;
	}
	
	*//** This method compare the input value to predefined regex pattern
	 * @author Omid Ashouri
	 * @param patternType  : could be "date" or "time" or "digit" or "character"
	 * @param inputValue : is the value you want the method to check it with the patternType
	 * @return boolean
	 *//*
	public static boolean regexMatcher(String patternType,String inputValue){
		String regex = null;

		if(patternType.equalsIgnoreCase("date")){
			regex = "13\\d{2}/[0-1]\\d{1}/[0-3]\\d{1}";
		} else if (patternType.equalsIgnoreCase("time")) {
			regex = "[0-2]\\d{1}:[0-5]\\d{1}";
		} else if (patternType.equalsIgnoreCase("digit")) {
			regex = "\\d+";
		} else if (patternType.equalsIgnoreCase("character")) {
			regex = "\\S+";
		}
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(inputValue);
		return matcher.matches();
	}

    *//** This method change the first character to Capital letter
     * @author Omid Ashouri
     * @param inputString  : should be "String"
     * @return String
     *//*
    public static String capitalizeFirstChar(String inputString) {
        inputString = inputString.substring(0, 1).toUpperCase() + inputString.substring(1);
        return inputString;
    }*/
}
