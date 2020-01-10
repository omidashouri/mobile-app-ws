# mobile-app-ws
Spring RESTful Web Services, Java, Spring Boot, Spring MVC and JPA

---
1.Normalize tables:
    Users: 
        id, user_id, first_name, last_name, email, password
    Addresses: 
        id, address_id, users_id, country, city, street_address
    Posts:
        id, post_id, users_id, title, text, date

2.Entity Relationships:
    @OneToOne
    @OneToMany
    @ManyToOne
    @ManyToMany
    
3.The Addresses Table:
    Users @OneToMany
    Addresses @ManyToOne
    
4.Entity Mapping @OneToOne:
    User:
        @Id
        @GeneratedValue
        private Long id;

        @OneToOne(mappedBy="userDetail",cascade="CascadeType.ALL)
        private Address address;
    
    Address:
        @Id
        @GeneratedValue
        private Long id;
        
        @OneToOne
        @JoinColumn(name="users_id")
        private User userDetail;
        
4.Entity Mapping @OneToMany and @ManyToOne:
    User:
        @Id
        @GeneratedValue
        private Long id;

        @OneToMany(mappedBy="userDetails",cascade="CascadeType.ALL)
        private List<Address> addresses;
    
    Address:
        @Id
        @GeneratedValue
        private Long id;
        
        @ManyToOne
        @JoinColumn(name="users_id")
        private User userDetails;
        
        
    
    
-----------------------
{
	"email":"omidashouri3@gmail.com",
	"password":"123",
	"firstName":"omid",
	"lastName":"ashouri",
	"addresses":[
					{
					"city":"vancover",
					"country":"canada",
					"streetName":"",
					"postalCode":"123456",
					"type":"billing"
					},{
					"city":"vancover",
					"country":"canada",
					"streetName":"",
					"postalCode":"123456",
					"type":"billing"	
					}
				]
}

-----------------------

Spring Hateoas 1.0:

    changes:
        ResourceSupport -is now> RepresentationModel
        Resource -is now> EntityModel
        Resources -is now> CollectionModel
        PagedResources -is now> PagedModel
        
    RepresentationModel:
        EntityModel -|> RepresentationModel
        CollectionModel -|> RepresentationModel
        PagedModel -|> CollectionModel

-----------------------

CONTROLLER:  public AddressRest getUserAddress



POST: http://localhost:8080/v1/users

{
        "firstName":"omid3",
        "lastName":"ashouri3",
        "email":"omidashouri3@gmail.com",
        "password":"123",
        "addresses":[
					{
					"city":"vancover",
					"country":"canada",
					"streetName":"",
					"postalCode":"123456",
					"type":"billing"
					},{
					"city":"vancover1",
					"country":"canada1",
					"streetName":"",
					"postalCode":"56789",
					"type":"billing1"
					}
				]
}

GET: http://localhost:8080/v1/users

pick publicUserId and publicAddressId then add it to bellow

GET: http://localhost:8080/v1/users/UAQAp4oxwUfW9K3GrgsYIcSwCL3Pvv/addresses/RdImtQIi98IJvJGB9BZkCQjS9ovgsE

-----------------------

CORS = Cross Origin Resource Sharing

-----------------------

preflight = A preflight request is a small request that is sent by the browser before the actual request.
            It contains information like which HTTP method is used,
            as well as if any custom HTTP headers are present. 
            The preflight gives the server a chance to examine what the actual request will look like before it's made.

-----------------------
install tomcat:

d:\apache-tomcat-9.0.30\webapps\manager\WEB-INF\web.xml
    <multipart-config>
      <!-- 100MB max -->
      <max-file-size>104857600</max-file-size>
      <max-request-size>104857600</max-request-size>
      <file-size-threshold>0</file-size-threshold>
    </multipart-config>
  </servlet>
  
  d:\apache-tomcat-9.0.30\conf\tomcat-users.xml
  <role rolename="tomcat"/>
  <role rolename="manager-gui"/>
  <role rolename="admin-gui"/>
  <role rolename="admin-script"/>
  <role rolename="manager-jmx"/>
  <role rolename="manager-status"/>
  <role rolename="manager-script"/>
  <user username="tomcat" password="tomcat" 
        roles="tomcat,manager-gui,admin-gui,admin-script
                ,manager-jmx,manager-status,manager-script"/>
  
  -----------------------
  
  -after change packaging to 'WAR':
  
  WARNING: Illegal reflective access by org.springframework.cglib.core.ReflectUtils
            method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
  
-In JDK 9+, add the following option to the JVM to disable the warning from Spring's use of CGLIB:
   -Add the --add-opens mentioned by Jan to your run/debug configuration. 
    Just edit the configuration and look under Environment / VM Options. 
    This takes care of silencing some of the "illegal access messages".
  
  -for intellij add ->
    --add-opens java.base/java.lang=ALL-UNNAMED
  
  -in command line add ->
    java --add-opens java.base/java.lang=ALL-UNNAMED -jar target/*.jar
    
    
  -----------------------