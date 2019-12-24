package ir.omidashouri.mobileappws.utilities.functions;

/*import ir.org.karnik.crm.business.cartable.MessageDAOImpl;
import ir.org.karnik.crm.helper.KarnikFunctions;
import ir.org.karnik.mainParts.helper.sms.magfa.webservice.SendSMSFromFarapayamakWebService;
import org.apache.log4j.Logger;
import ir.org.karnik.crm.business.account.CompanyDAOImpl;
import ir.org.karnik.crm.business.account.ContactDAOImpl;
import ir.org.karnik.crm.business.authenticate.PersonDAOImpl;
import ir.org.karnik.crm.business.setting.caption.ElementCaptionDAOImpl;
import ir.org.karnik.crm.dataLayer.account.TblCompany;
import ir.org.karnik.crm.dataLayer.account.TblContact;
import ir.org.karnik.crm.dataLayer.authenticate.TblPerson;
import ir.org.karnik.crm.dataLayer.setting.language.TblLanguage;
import ir.org.karnik.crm.helper.BaseDispatcherAction;
import ir.org.karnik.crm.helper.CaptionFactory;
import ir.org.karnik.crm.helper.MD5;
import ir.org.karnik.crm.presentation.cartable.message.MessageManager;
import nl.captcha.Captcha;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;*/

/**
 * 
 * @author
 * @struts.action name="userForm" path="/maintainForgetPasswordDispatcher"
 *                parameter="action"
 * 
 * @struts.action-forward name="resetPassword" path="/pages/message.jsp"
 *                        redirect="false"
 * 
 * 
 */

public class MaintainForgetPasswordDispatcher {//extends BaseDispatcherAction {
/*    private static final Logger LOGGER = Logger.getLogger(MaintainForgetPasswordDispatcher.class);
	public static String COMPANY_HOST;
	public static String COMPANY_FROM;
	public static String COMPANY_FROM_USERNAME;
	public static String COMPANY_FROM_PASSWORD;
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return null;
	}

	public ActionForward resetPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserForm userForm = (UserForm) form;
		String password = "";
		String message = "";
		String mail="";
		String p1 , mg ;
		String smsSenderProvider;
		boolean sendThroughFarapayamak=false;

		boolean sms = false  , email = false;
		TblContact tblContact = null;
		TblPerson tblPerson = null;
		Long userId = new Long(1);
		TblCompany tblCompany = new TblCompany();
		tblCompany.setId(new Long(4));
		TblLanguage tblLanguage = new TblLanguage();
		Captcha captcha = (Captcha) request.getSession().getAttribute(
				Captcha.NAME);
		request.setCharacterEncoding("UTF-8");// Do This So We Can Capture
												// Non-Latin Chars
		String answer = userForm.getValidation();// request.getParameter("answer");
		if (!captcha.isCorrect(answer)) {

			userForm.setMessage("captchaError");
			userForm.setValidation("false");
			message  = CaptionFactory.getCaption("Please Enter The Captcha Word Correctly!",
					 1, request.getSession());
			request.setAttribute("messageShow", message);
			return mapping.findForward("resetPassword");
		}

		try {

			ContactDAOImpl contactDAOImpl = new ContactDAOImpl();
			PersonDAOImpl personDAOImpl = new PersonDAOImpl();
			tblContact = contactDAOImpl.findContactByNationCode(userForm
					.getNationCode());
			mail = userForm.getEmail();
			smsSenderProvider = userForm.getSmsSenderProvider();

			if(smsSenderProvider !=null && "farapayamak".equalsIgnoreCase(smsSenderProvider)){
				sendThroughFarapayamak=true;
			}


			tblLanguage.setId(new Long(1));

			if (tblContact != null && tblContact.getEmail()!=null && tblContact.getEmail().equalsIgnoreCase(mail)) {
				tblPerson = personDAOImpl.findPersonByNationCode(userForm
						.getNationCode());
				if (tblPerson != null) {

//					StringBuffer nationCode = new StringBuffer(userForm.getNationCode());
//					password = nationCode.subSequence(0, 6).toString();

					for (int p = 0; p < 6; p++) {
						password += (int) (Math.random() * 10);
					}

					tblPerson.setPassword(MD5.MD5_Creator(password));
//					tblPerson.setUsername(userForm.getNationCode());
					if(tblPerson.getNationCode() == null)
						tblPerson.setNationCode(userForm.getNationCode());
					personDAOImpl.personUpdate(tblPerson);
					// userForm.setMessage(CaptionFactory.getCaption("Your UserName and PassWord Has Been Sent To Your Email And Your Mobile.",
					// tblLanguage.getId(), request.getSession()));
					userForm.setMessage("resetPassword");


				    try {
				    	MessageManager messageManager = new MessageManager();
				    	if(tblContact.getMobilePhone() != null)
				    		{
				    		try{
				    		String[] smsRecipients = new String[1];
				    		smsRecipients[0] = tblContact.getMobilePhone();

				    		String messageText = setSMSText(tblPerson.getUsername(), password, tblCompany.getId(), tblLanguage.getId());

								if(sendThroughFarapayamak) {

										// Send from farapayamak web service
									String messageContext;
									String[] recipientNumbers =new String[1];
									String[] recIdsArrays;
									messageContext = messageText;
									recipientNumbers[0] = smsRecipients[0];

									recIdsArrays = new SendSMSFromFarapayamakWebService().sendSimpleSMS(recipientNumbers, messageContext);
									new MessageDAOImpl().saveFrapayamakSMS(recipientNumbers, recIdsArrays, messageContext, userId, null);

								}else {
									messageManager.sendSMS(smsRecipients, messageText, tblCompany.getId(), userId);
								}
								sms = true;
				    			}catch(Exception ex)
{
				    				sms = false;
				    			}

				    		}

				    	if(tblContact.getEmail() != null)
				    	{
				    		try
				    		{
						    String[] to = new String[1];
						    to[0] = tblPerson.getEmail();
*//*						    String messageSubject = CaptionFactory.getCaption("Reset User Name And Password",
									 tblLanguage.getId(), request.getSession());*//*
						    String messageContent = setEmailText(tblPerson.getUsername(), password, tblCompany.getId(), tblLanguage.getId());
//						    setInfo();
							KarnikFunctions karnikFunctions =new KarnikFunctions();
							karnikFunctions.sendEmail(null,null,mail,messageContent,null);
//					    	messageManager.sendEmail(to, null, null, messageSubject, messageContent, tblLanguage.getId(), COMPANY_HOST, COMPANY_FROM, COMPANY_FROM_USERNAME, COMPANY_FROM_PASSWORD, request);
					    	email = true;
				    		}catch (Exception ex)
{
				    			email = false;
				    		}

				    	}

					} catch (Exception e) {
					      LOGGER.error(e.getMessage());
					   }


				    p1 = "";
					mg ="<table border=0 width='90%' bgcolor='#FFFFAA' cellPadding='0' cellSpacing='0' ><tr><td  class='normalTitr' align='center'>"+p1;
					mg = mg + "</td></tr><tr><td  align='right' class='normalTitr' align='right'>";
					p1 = CaptionFactory.getCaption("Dear User",
							 tblLanguage.getId(), request.getSession());
//					mg = mg + p1 + "<br>";
*//*					p1 = CaptionFactory.getCaption("Your Password Was Successfully Reset To First Six Number Of Your Nation Code",
							 tblLanguage.getId(), request.getSession());*//*
					mg = mg + p1 + "</TD></tr>";
					if(email)
					{
							mg = mg  + "<tr><td  align='right' class='normalTitr'>";
							p1 = CaptionFactory.getCaption("New Password Send To Your Email:",
								tblLanguage.getId(), request.getSession());
							mg = mg + p1;
							p1 = " "+ tblContact.getEmail();
							mg = mg + p1 + "</td></tr>";
					}
					if(sms)
					{

						mg = mg  + "<tr><td align='right' class='normalTitr' border='0'>";
*//*						if(email)
							p1 = CaptionFactory.getCaption("And SMS For You",
									tblLanguage.getId(), request.getSession());
						else*//*
						p1 = CaptionFactory.getCaption("New Password SMS For You",
									tblLanguage.getId(), request.getSession());
						mg = mg + p1+"<br/>";
						p1=tblContact.getMobilePhone();
						mg = mg  + p1+"</td></tr>";
					}
					mg = mg + "<tr><td class='internerRegErrorRow' align='right'>";
					p1 = CaptionFactory.getCaption("If you have any problem please call 22043005 internal number 375",
							tblLanguage.getId(), request.getSession());
					mg = mg + p1 + "</td></tr>";
					mg = mg  +  "</table>";
				    message = mg;
				} else {
					// userForm.setMessage(CaptionFactory.getCaption("Your Contact Information Exist In System! Please Call This Number",
					// tblLanguage.getId(), request.getSession()));
					message = CaptionFactory.getCaption("Error In Your Information. Please Contact With System Adminsitrator"
							,tblLanguage.getId(), request.getSession());
					userForm.setMessage("callOrganization");
				}
			} else {
//				userForm.setMessage("NotRegistered");
				message =  CaptionFactory.getCaption("You Are Not A Member Of Website.Please Register In Website",1, request.getSession());
			}

			request.setAttribute("messageShow", message);
			return mapping.findForward("resetPassword");

		} catch (Exception e) {

			userForm.setMessage(CaptionFactory.getCaption(
					"Some Problems Occured. Please Try Later",
					tblLanguage.getId(), request.getSession()));
			return mapping.findForward("resetPassword");
		}


	}


	private String setEmailText(String userName,String password , Long companyId , Long languageId){
		String messageText = "";
		try
		{
		TblCompany tblCompany = new TblCompany();
		CompanyDAOImpl companyDAOImpl = new CompanyDAOImpl();
		tblCompany = companyDAOImpl.findByPrimaryKey(companyId);
		ElementCaptionDAOImpl  captionDAOImpl = new ElementCaptionDAOImpl();
		String s1 = captionDAOImpl.findCaption("PasswordShoma", languageId);
		String s2 = captionDAOImpl.findCaption("BaMovaghiatResetShod", languageId);
		String s3 = captionDAOImpl.findCaption("NameKarbariVaKalemeOboorShoma", languageId);
		String s4= captionDAOImpl.findCaption("JahatEstefadehAzSystemEbaratandAz", languageId);
		String s5= captionDAOImpl.findCaption("User Name", languageId);
		String s6= captionDAOImpl.findCaption("Password", languageId);
		String s7= captionDAOImpl.findCaption("VorodBeSystem", languageId);
		String s8= captionDAOImpl.findCaption("BaTashakkor", languageId);
		String s9= captionDAOImpl.findCaption("BaSalam", languageId);

	*/
//		messageText = "<!--[if !mso]><style>v\\:* {behavior:url(#default#VML);}o\\:* " +
//		"{behavior:url(#default#VML);}w\\:* {behavior:url(#default#VML);}.shape {behavior:url(#default#VML);}" +
//		" </style><![endif]--><!--[if gte mso 9]><xml><w:WordDocument><w:View>Normal</w:View><w:Zoom>0</w:Zoom><w:PunctuationKerning/><w:ValidateAgainstSchemas/> " +
//		"  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid> " +
//		"  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>" +
//		"  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>" +
//		" <w:Compatibility>" +
//		"   <w:BreakWrappedTables/>" +
//		"   <w:SnapToGridInCell/>" +
//		"   <w:WrapTextWithPunct/>" +
//		"   <w:UseAsianBreakRules/>" +
//		"   <w:DontGrowAutofit/>" +
//		"  </w:Compatibility>" +
//		"  <w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel>" +
//		" </w:WordDocument>" +
//		"</xml><![endif]--><!--[if gte mso 9]><xml>" +
//		" <w:LatentStyles DefLockedState='false' LatentStyleCount='156'>" +
//		" </w:LatentStyles>" +
//		"</xml><![endif]--><!--[if gte mso 10]>" +
//		"<style>" +
//		" /* Style Definitions */" +
//		" table.MsoNormalTable" +
//		"	{mso-style-name:'Table Normal';" +
//		"   mso-tstyle-rowband-size:0;" +
//		"	mso-tstyle-colband-size:0;" +
//		"	mso-style-noshow:yes;" +
//		"	mso-style-parent:'';" +
//		"	mso-padding-alt:0in 5.4pt 0in 5.4pt;" +
//		"	mso-para-margin:0in;" +
//		"	mso-para-margin-bottom:.0001pt;" +
//		"	mso-pagination:widow-orphan;" +
//		"	font-size:10.0pt;" +
//		"	font-family:'Times New Roman';" +
//		"	mso-ansi-language:#0400;" +
//		"	mso-fareast-language:#0400;" +
//		"	mso-bidi-language:#0400;}" +
//		"</style>" +
//		"<![endif]-->" +
//		"" +
//		"<p class='MsoNormal' dir='RTL'><b><span dir='LTR' style='font-family:Tahoma'><font size='1'>"+tblCompany.getNameLo()+"</font><img src='http://erp.imi.ir/ERP/images/b3.gif' /></span></b><b><span style='font-size:9.0pt;font-family:Tahoma' lang='AR-SA'></span></b><b><span style='font-family:Tahoma' lang='AR-SA'></span></b></p>" +
//		"" +
//		"<p class='MsoNormal' dir='RTL'><b><span style='font-family:Tahoma' lang='AR-SA'>&nbsp;</span></b></p>" +
//		"" +
//		"<p class='MsoNormal' dir='RTL'><b><span style='font-family:Tahoma' lang='AR-SA'>" + s9+
//		"</span></b><span dir='LTR' style='font-family:Tahoma'><br />" +
//		"</span><span style='font-family:Tahoma' lang='AR-SA'>" +s1 +
//		"<b> "+ tblCompany.getNameLo() +" </b>" +s2+
//		" </span><span dir='LTR'></span><span dir='LTR'></span><span dir='LTR' style='font-family:Tahoma'><span dir='LTR'></span><span dir='LTR'></span>.<br />" +
//		"</span><span style='font-family:Tahoma' lang='AR-SA'>" +s3+s4+
//		"</span><span dir='LTR'></span><span dir='LTR'></span><span dir='LTR' style='font-family:Tahoma'><span dir='LTR'></span><span dir='LTR'></span>:<br />" +
//		"<span style='font-family:Tahoma' lang='AR-SA'></span><span dir='LTR'></span><span dir='LTR'></span><span dir='LTR' style='font-family:Tahoma'><span dir='LTR'></span><span dir='LTR'></span><br><br />" +
//		"<br /></span>" +
//		"</span><br><span style='font-family:Tahoma' lang='AR-SA'>"+s5+"</span><span dir='LTR'></span><span dir='LTR'></span><span dir='LTR' style='font-family:Tahoma'><span dir='LTR'></span><span dir='LTR'>:</span><br> " + userName + "<br />" +
//		"</span><span style='font-family:Tahoma' lang='AR-SA'>"+s6+"</span><span dir='LTR'></span><span dir='LTR'></span><span dir='LTR' style='font-family:Tahoma'><span dir='LTR'></span><span dir='LTR'>:</span><br>" + password + "<br />" +
//		"<br />" +
//		"</span><span style='font-family:Tahoma' lang='AR-SA'>"+s7+"</span><span dir='LTR' style='font-family:Tahoma'><br />" +
//		"http://erp.imi.ir<br />" +
//		"<br />" +
//		"</span><span style='font-family:Tahoma' lang='AR-SA'>"+s8+"</span><span dir='LTR' style='font-family:Tahoma'><br />" +
//		"</span><span style='font-family:Tahoma' lang='AR-SA'>"+tblCompany.getNameLo()+"</span><span dir='LTR'><br />" +
//		"</span><b><span dir='LTR' style='font-size:10.0pt;font-family:Tahoma'>ERP_SUPPORT@IMI.IR</span></b></p>" +
//		"" +
//		"<p class='MsoNormal' dir='RTL'><span dir='LTR' style='mso-bidi-language:FA'>&nbsp;</span></p>";
	/*	}catch(Exception ex)		{
		      LOGGER.error(ex.getMessage());
		   }

		return messageText;

	}*/

	/*private String setSMSText(String userName,String password, Long companyId,
			Long languageId){
		String messageText = " ";
		String temp = "";
		try
		{
		ElementCaptionDAOImpl  captionDAOImpl = new ElementCaptionDAOImpl();
		TblCompany tblCompany = new TblCompany();
		CompanyDAOImpl companyDAOImpl = new CompanyDAOImpl();
		tblCompany = companyDAOImpl.findByPrimaryKey(companyId);


		messageText+= tblCompany.getNameLo()+ "\n";
		temp = captionDAOImpl.findCaption("BaSalamPasswordShomaBaMovaghaiatResetShod", languageId);
		messageText+=temp+"\n";
		temp = captionDAOImpl.findCaption("User Name", languageId);
		messageText+=temp+userName+"\n";
		temp = captionDAOImpl.findCaption("Password", languageId);
		messageText+=temp+password+"\n";
		temp = captionDAOImpl.findCaption("Website", languageId);

		messageText+=temp + ": erp.imi.ir";
		}catch(Exception ex)
{
			LOGGER.error(ex.getMessage(),ex);
		}

		return messageText;

	}

	private boolean captchaValidation(String userEntered, String captchaName) {
		if (captchaName.equals("captcha0")
				&& userEntered.equalsIgnoreCase("z8fsq"))
			return true;
		if (captchaName.equals("captcha1")
				&& userEntered.equalsIgnoreCase("pk25f"))
			return true;
		if (captchaName.equals("captcha2")
				&& userEntered.equalsIgnoreCase("k36j2"))
			return true;
		if (captchaName.equals("captcha3")
				&& userEntered.equalsIgnoreCase("27y8c"))
			return true;
		if (captchaName.equals("captcha4")
				&& userEntered.equalsIgnoreCase("psw2a"))
			return true;
		if (captchaName.equals("captcha5")
				&& userEntered.equalsIgnoreCase("8e2bh"))
			return true;
		if (captchaName.equals("captcha6")
				&& userEntered.equalsIgnoreCase("2ejrw"))
			return true;
		if (captchaName.equals("captcha7")
				&& userEntered.equalsIgnoreCase("pcrgt"))
			return true;
		if (captchaName.equals("captcha8")
				&& userEntered.equalsIgnoreCase("27jcq"))
			return true;
		if (captchaName.equals("captcha9")
				&& userEntered.equalsIgnoreCase("twa72"))
			return true;
		if (captchaName.equals("captcha10")
				&& userEntered.equalsIgnoreCase("t67ph"))
			return true;
		return false;
	}

	 public void setInfo() {
	        try {

	            InputStream inputFile = getClass().getResourceAsStream("/ir/org/karnik/mainParts/config/imi.properties");
	            ResourceBundle resourceBundle = new PropertyResourceBundle(inputFile);
	            COMPANY_HOST = resourceBundle.getString("COMPANY_HOST");
	            COMPANY_FROM = resourceBundle.getString("COMPANY_FROM");
	            COMPANY_FROM_USERNAME =resourceBundle.getString("COMPANY_FROM_USERNAME");
	            COMPANY_FROM_PASSWORD = resourceBundle.getString("COMPANY_FROM_PASSWORD");




	        } catch (IOException e) {
	              LOGGER.error(e.getMessage());
	           }


	    }*/
}
