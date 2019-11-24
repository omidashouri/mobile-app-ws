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



//58325b09966b8b4c6ffb8cc5c34985c0


/*    crm.tbl_person (
    id,
    first_name,
    last_name,
    tel,
    address,
    email,
    website,
    username,
    password,
    limitation_number,
    signature,
    lastlogindate,
    company_id,
    contact_id,
    selected_skin,
    selected_language,
    email_process_type,
    personal_code,
    activity_status,
    kind,
    organization_position_id,
    owner_id,
    organization_class_id,
    noe_estekhdam,
    pwdp,
    signature_img,
    commerce_additional_info
) VALUES (
    100160,
            'اميد',
            'عاشوري',
            '22010790',
            'modiri sanat',
            'omidashouri@gmail.com',
    NULL,
    '90**',
            '58325b09966b8b4c6ffb8cc5c34985c0',
    NULL,
    NULL,
    NULL,
    4,
            100160,
            'EnterpriseBlue',
            1,
            'STOP',
            '90**',
            '1',
            'سازمان',
    NULL,
    NULL,
    2,
            'قراردادي',
            '90**',
    NULL,
    NULL
    );
    */


/*    tbl_contact (
            id,
            account_id,
            salutation_id,
            first_name,
            middle_name,
            last_name,
            job_title,
            business_phone,
            home_phone,
            mobile_phone,
            fax_number,
            pager_number,
            email,
            currency_id,
            department,
            role,
            manager_id,
            manager_phone,
            assistant_id,
            assistant_phone,
            gender,
            marital_status,
            partner_name,
            birthdate,
            anniversary,
            description,
            parent_id,
            country_id,
            state_id,
            city_id,
            address_phone,
            address,
            company_id,
            organization_id,
            lead_source_id,
            campaign_id,
            team_id,
            sync_out_look,
            preferred_contact_method,
            allow_email,
            allow_bulk_email,
            allow_phone,
            allow_fax,
            allow_mail,
            address_type_id,
            user_creator_id,
            from_city,
            certificate_no,
            nation_code,
            father_name,
            birth_city_id,
            access_type,
            image,
            lfirst_name,
            llast_name,
            religion_id,
            military_service_id,
            edu_level_id,
            field_name,
            bank_name,
            branch_name,
            bank_account_number,
            bank_accoount_type,
            entrance_date,
            contract_type_id,
            university,
            insurance_kind_id,
            insurance_box_id,
            gender1,
            marital1,
            military1,
            edit_temp_row_flag,
            create_temp_row_flag,
            verified,
            postal_code,
            edit_date,
            create_date,
            user_editor_id,
            convert_date_who,
            convert_uniq,
            lfather_name,
            lfrom_city,
            lfrom_city_id
            ) VALUES (
            100160,
            1,
            56,
            'اميد',
            'پسوند',
            'عاشوري',
            'كارشناس اداري وپشتيباني 1',
            '22010790',
            '22010790',
            '09126607350',
            '22010790',
            '02122010',
            'omidashouri@gmail.com',
            5,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
            'm',
            'm',
    NULL,
            '13__/__/__',
    NULL,
            'Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]Tozohat - با سلام جهت هر گونه سوال مي توانيد با داخلي 324 تماس بگيريد #[بازاريابي ديجيتال نام نوبت هجده:متوسط]'
            ,
    NULL,
            33,
            33,
            41,
            '02122010',
            'مديريت صنعتي',
            4,
            100810,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
            100160,
            'محل صدور',
            '5205',
            '0075175266',
            'غلام',
            101888,
            'public',
    NULL,
            'OMID',
            'ASHOURI',
    NULL,
    NULL,
            33,
            'مهندسي كامپيوترنرم افزار',
            'نامشخص',
            'نامشخص',
            '0',
    NULL,
            '1390/07/01',
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
    NULL,
            '1393/08/10',
    NULL,
            1,
    NULL,
    NULL,
            'Gholamr555r',
            'shiraz',
            45
            ); */

}
