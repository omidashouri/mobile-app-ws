package ir.omidashouri.mobileappws.utilities;

public class OnlineRegistration {




/*    public void init(){
        registrationForm = new OnLineRegistrationNewForm();
        btmSubmitStatus = false;
        timerAutoStart = false;
        registrationForm.getTblContact().setDescription("3");
        educationLevelList = new TblParameterDAO().findByProperty("paramName","educationLevel");
        Collections.sort(educationLevelList, Comparator.comparing(TblParameter::getParamValue));
        //    bundle = ResourceBundle.getBundle("jsfres",FacesContext.getCurrentInstance().getViewRoot().getLocale());
        Object periodId = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodId");
        this.setPeriodName("sazm modri santi");

        String periodNameTemp = null;
        if(null!=periodId)
            periodNameTemp = new PeriodDAOImpl().findById(Long.valueOf(periodId.toString())).getName();
        if(null!=periodNameTemp)
            this.setPeriodName(periodNameTemp.trim());
    }*/



/*    public void update(){
        TblPerson tblPerson;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String msgTitle,msgDetail,familiarityTemp,familiarityStatus;
        try {

            familiarityStatus = new RegisterDispatchAction().getFamiliarityStatusTitle(this.getRegistrationForm().getTblContact().getDescription());

            familiarityTemp = new StringBuilder().append("#[").append(this.getPeriodName()).append(":")
                    .append(familiarityStatus).append("]").toString();
            registrationForm.getTblContact().setDescription(familiarityTemp);
            tblPerson =  onLineRegistrationNewDispatcher.addToDataBase(registrationForm.getTblContact());

            this.setUserName(tblPerson.getUsername());
            this.setPassword(tblPerson.getUsername().subSequence(0, 6).toString());
            this.timerAutoStart = true;
            requestContext.execute("PF('dlgConfirm').show()");
            requestContext.update("formRegister:growlId");

        } catch (Exception e) {

            LOGGER.error(e.getMessage(),e);
            msgTitle = JsfUtils.getMessageResourceString("JSFResources",
                    "edu.registration.form.validator.fatal.title.message", null, FacesContext.getCurrentInstance().getViewRoot().getLocale());
            msgDetail = JsfUtils.getMessageResourceString("JSFResources",
                    "edu.registration.form.reject.insert.message", null, FacesContext.getCurrentInstance().getViewRoot().getLocale());
            facesContext.addMessage("",new FacesMessage(FacesMessage.SEVERITY_ERROR,msgTitle,msgDetail));
            requestContext.update("formRegister");
        }

    }*/



/*    public class OnLineRegistrationNewDispatcher implements Serializable {
        public TblPerson addToDataBase(TblContact tblContact) throws Exception {
            TblPerson tblPerson ;
            try {

                tblPerson = new PersonDAOImpl().findPersonByNationCode(tblContact.getNationCode());

                if (tblPerson == null) {
                    tblPerson = new TblPerson();
                    StringBuffer nationCode = new StringBuffer(tblContact.getNationCode());
                    String password = nationCode.subSequence(0, 6).toString();

                    tblPerson.setNationCode(tblContact.getNationCode());
                    tblPerson.setUsername(tblContact.getNationCode());

                    tblPerson.setPassword(MD5.MD5_Creator(password));

                    tblPerson.setFirstName(tblContact.getFirstName());
                    tblPerson.setLastName(tblContact.getLastName());
                    tblPerson.setEmail(tblContact.getEmail());

                    tblPerson.setActivityStatus("1");
                    tblPerson.setAddress(tblContact.getAddress());
                    tblPerson.setTblContact(tblContact);

                    TblLanguage persianLanguage = new TblLanguageDAO().findById(1l);
                    tblPerson.setTblLanguage(persianLanguage);

                    TblCompany tblCompany = new TblCompanyDAO().findById(4l);
                    tblPerson.setTblCompany(tblCompany);
                    tblContact.setTblCompany(tblCompany);

                    tblPerson.setSelectedSkin("Graphite");

                    tblContact.setAllowMail("1");
                    tblContact.setAllowEmail("1");
                    tblContact.setAllowPhone("1");
                }
                new OnLineRegistrationDelegate().personAndContactUpdate(tblContact,tblPerson);
            }catch (Exception exception){
                throw exception;
            }
            return tblPerson;
        }*/



/*    public void personAndContactUpdate(TblContact tblContact, TblPerson tblPerson) throws Exception{
        Session session = getSession();
        try {
            session.beginTransaction();
            new TblContactDAO().save(tblContact);
            new TblPersonDAO().save(tblPerson);
            session.getTransaction().commit();
        }catch (Exception exception){
            session.getTransaction().rollback();
            session.close();
            throw exception;
        }
    }*/






}
