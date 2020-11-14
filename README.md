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
        ControllerLinkBuilder.linkTo -is now > WebMvcLinkBuilde.linkTo
        ControllerLinkBuilder.methodOn -is now > WebMvcLinkBuilde.methodOn
        EntityModel is use when Entity is not extending RepresentationModel
        EntityLinks is another way for adding links in just spring MVC
        RepresentationModelAssemblerSupport is another way for adding links use for all methods in controller
        RepresentationModelProcessor is another way for adding links 
       
    RepresentationModel:
        EntityModel -|> RepresentationModel
        CollectionModel -|> RepresentationModel
        PagedModel -|> CollectionModel    


    notes:
        -header set to application/hal+json
        
        -instead extending Entity from RepresentationModel we can use:
            Item resource representation model:
                Person person = new Person("Dave", "Matthews");
                EntityModel<Person> model = new EntityModel<>(person);
            Collection resource representation model:
                Collection<Person> people = Collections.singleton(new Person("Dave", "Matthews"));
                CollectionModel<Person> model = new CollectionModel<>(people);
        
        -import static org.sfw.hateoas.server.mvc.WebMvcLinkBuilder.*
        
        -response header values:
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(linkTo(PersonController.class).slash(person).toUri());
                return new ResponseEntity<PersonModel>(headers, HttpStatus.CREATED);
        
        -Building links that point to methods:
                Link link = linkTo(methodOn(PersonController.class).show(2L)).withSelfRel();
                assertThat(link.getHref()).endsWith("/people/2");
                better is:
                public ResponseEntity<?> show(@PathVariable Integer id){
                    Link link = linkTo(methodOn(PersonController.class).show(id)).withSelfRel();
                return ...}
        
        -Affordances:
            -Automatic: Connecting affordances to GET /employees/{id}
                    @GetMapping("/employees/{id}")
                    public EntityModel<Employee> findOne(@PathVariable Integer id) {                   
                      Class<EmployeeController> controllerClass = EmployeeController.class;
                      Link findOneLink = linkTo(methodOn(controllerClass).findOne(id)).withSelfRel(); 
                      return new EntityModel<>(EMPLOYEES.get(id), //
                          findOneLink //
                              .andAffordance(afford(methodOn(controllerClass).updateEmployee(null, id))) 
                              .andAffordance(afford(methodOn(controllerClass).partiallyUpdateEmployee(null, id)))); 
                    }
                    
                    updateEmpoyee method that responds to PUT /employees/{id}:
                            @PutMapping("/employees/{id}")
                            public ResponseEntity<?> updateEmployee( //
                                @RequestBody EntityModel<Employee> employee, @PathVariable Integer id)
                                
                    partiallyUpdateEmployee method that responds to PATCH /employees/{id}:
                            @PatchMapping("/employees/{id}")
                            public ResponseEntity<?> partiallyUpdateEmployee( //
                                @RequestBody EntityModel<Employee> employee, @PathVariable Integer id)
                                
            -Manually: Building affordances manually
                    var methodInvocation = methodOn(EmployeeController.class).all();
                    var link = Affordances.of(linkTo(methodInvocation).withSelfRel())  
                                      
                        .afford(HttpMethod.POST)  //EmployeeController.newEmployee(…)
                        .withInputAndOutput(Employee.class) 
                        .withName("createEmployee")
                                          
                        .andAfford(HttpMethod.GET) //EmployeeController.search(…)
                        .withOutput(Employee.class) 
                        .addParameters(
                            QueryParameter.optional("name"), 
                            QueryParameter.optional("role")) 
                        .withName("search")
                                             
                        .toLink();
                    
        -EntityLinks interface:
             -EntityLinks links = …;
              LinkBuilder builder = links.linkFor(Customer.class);
              Link link = links.linkToItemResource(Customer.class, 1L)
              
             -EntityLinks based on Spring MVC controllers: @ExposesResourceFor(…)
                    -@ExposesResourceFor(…):
                             @Controller
                             @ExposesResourceFor(Order.class) 
                             @RequestMapping("/orders") 
                             class OrderController {
                             
                               @GetMapping 
                               ResponseEntity orders(…) { … }
                             
                               @GetMapping("{id}") 
                               ResponseEntity order(@PathVariable("id") … ) { … }
                             }
                    -EntityLinks @EnableHypermediaSupport in your Spring MVC configuration:
                             @Controller
                             class PaymentController {
                             
                               private final EntityLinks entityLinks;
                             
                               PaymentController(EntityLinks entityLinks) { 
                                 this.entityLinks = entityLinks;
                               }
                             
                               @PutMapping(…)
                               ResponseEntity payment(@PathVariable Long orderId) {
                             
                                 Link link = entityLinks.linkToItemResource(Order.class, orderId); 
                                 …
                               }
                             }
                    -EntityLinks API in detail:
                             entityLinks.linkToItemResource(order, order.getId());
                             
                             Function<Order, Object> idExtractor = Order::getId; 
                             entityLinks.linkToItemResource(order, idExtractor);
                    -TypedEntityLinks:
                             class OrderController {
                             
                               private final TypedEntityLinks<Order> links;
                             
                               OrderController(EntityLinks entityLinks) { 
                                 this.links = entityLinks.forType(Order::getId); 
                               }
                             
                               @GetMapping
                               ResponseEntity<Order> someMethod(…) {
                             
                                 Order order = … // lookup order
                             
                                 Link link = links.linkToItemResource(order); 
                               }
                             }
                    -EntityLinks as SPI:
                            -Declaring a custom EntityLinks implementation:
                            @Configuration
                            class CustomEntityLinksConfiguration {
                            
                              @Bean
                              MyEntityLinks myEntityLinks(…) {
                                return new MyEntityLinks(…);
                              }
                            }
                            
             -Representation model assembler:
                     -https://howtodoinjava.com/spring5/hateoas/spring-hateoas-tutorial/
                     -representation model must be used in multiple places:
                     -RepresentationModelAssemblerSupport:        
                     class PersonModelAssembler extends RepresentationModelAssemblerSupport<Person, PersonModel> {
                     
                       public PersonModelAssembler() {
                         super(PersonController.class, PersonModel.class);
                       }
                     
                       @Override
                       public PersonModel toModel(Person person) {
                     
                         PersonModel resource = createResource(person);
                         // … do further mapping
                         return resource;
                       }
                     }
                      
                      -then use the assembler to either assemble a RepresentationModel or a CollectionModel:
                             
                      Person person = new Person(…);
                      Iterable<Person> people = Collections.singletonList(person);
                      
                      PersonModelAssembler assembler = new PersonModelAssembler();
                      PersonModel model = assembler.toModel(person);
                      CollectionModel<PersonModel> model = assembler.toCollectionModel(people);  
        
        -Representation Model Processors:
                -tweak and adjust hypermedia representations after they have been assembled:
                -You wish to add a link so the client can make payment, 
                    but don’t want to mix details about your PaymentController into the OrderController:
                 {
                   "orderId" : "42",
                   "state" : "AWAITING_PAYMENT",
                   "_links" : {
                     "self" : {
                       "href" : "http://localhost/orders/999"
                     }
                   }
                 }            
             
                 -RepresentationModelProcessor:
                      public class PaymentProcessor implements RepresentationModelProcessor<EntityModel<Order>> { 
                        @Override
                        public EntityModel<Order> process(EntityModel<Order> model) {
                          model.add( 
                              new Link("/payments/{orderId}").withRel(LinkRelation.of("payments")) 
                                  .expand(model.getContent().getOrderId()));
                          return model; 
                        }
                      }     
                 
                 -Register the processor with your application:
                       @Configuration
                       public class PaymentProcessingApp {
                       
                         @Bean
                         PaymentProcessor paymentProcessor() {
                           return new PaymentProcessor();
                         }
                       }
                       
                 -client receives this:
                        {
                          "orderId" : "42",
                          "state" : "AWAITING_PAYMENT",
                          "_links" : {
                            "self" : {
                              "href" : "http://localhost/orders/999"
                            },
                            "payments" : { 
                              "href" : "/payments/42" 
                            }
                          }
                        }  
                        
        -Using the LinkRelationProvider API:
               
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
  
  Many-to-One Associations:
  
  Unidirectional Many-to-One Association:
  
  @Entity
  public class OrderItem {   
      @ManyToOne
      @JoinColumn(name = “fk_order”)
      private Order order;
  }
  
  Order o = em.find(Order.class, 1L);
  OrderItem i = new OrderItem();
  i.setOrder(o);
  em.persist(i);
  
  ---
  
  Unidirectional One-to-Many Association:
  
  @Entity
  public class Order {   
      @OneToMany
      @JoinColumn(name = “fk_order”)
      private List<OrderItem> items = new ArrayList<OrderItem>();
      
      public void addItem(OrderItem item){
          if(null != item){
            if(null == items){
              items = new ArrayList<>();
          }
           item.setOrder(this);
           items.add(item);
         }
       }
     }

  Order o = em.find(Order.class, 1L);
  OrderItem i = new OrderItem();
  o.getItems().add(i);
  em.persist(i);
  
  --- --- ---
  
  Bidirectional Many-to-One Associations:
  
  @Entity
  public class OrderItem {   
      @ManyToOne
      @JoinColumn(name = “fk_order”)
      private Order order;
  }
  
  @Entity
  public class Order {   
      @OneToMany(mappedBy = “order”)
      private List<OrderItem> items = new ArrayList<OrderItem>();
  }
  
  But adding and removing an entity from the relationship requires an additional step. 
  You need to update both sides of the association:
  
  Order o = em.find(Order.class, 1L); 
  OrderItem i = new OrderItem();
  i.setOrder(o);
  o.getItems().add(i);
  em.persist(i);
  
  a lot of developers prefer to implement it in a utility method which updates both entities.
  
  @Entity
  public class Order {
      …           
      public void addItem(OrderItem item) {
          this.items.add(item);
          item.setOrder(this);
      }
      …
  }
  
  -----------------------
  
  Many-to-Many Associations:
  
  Unidirectional Many-to-Many Associations:
    
  @Entity
  public class Store {   
      @ManyToMany
      @JoinTable(name = “store_product”,
             joinColumns = { @JoinColumn(name = “fk_store”) },
             inverseJoinColumns = { @JoinColumn(name = “fk_product”) })
      private Set<Product> products = new HashSet<Product>();   
      …
  }
  
  Store s = em.find(Store.class, 1L);
  Product p = new Product();
  s.getProducts().add(p);
  em.persist(p);
  
  --- --- ---
  
  Bidirectional Many-to-Many Associations:
  
  @Entity
  public class Store {   
      @ManyToMany
      @JoinTable(name = “store_product”,
             joinColumns = { @JoinColumn(name = “fk_store”) },
             inverseJoinColumns = { @JoinColumn(name = “fk_product”) })
      private Set<Product> products = new HashSet<Product>();   
      …
  }
  
  @Entity
  public class Product{   
      @ManyToMany(mappedBy=”products”)
      private Set<Store> stores = new HashSet<Store>();   
      …
  }
  
  You need to update both ends of a bidirectional association when you want to add or remove an entity.
  
  @Entity
  public class Store {   
      public void addProduct(Product p) {
          this.products.add(p);
          p.getStores().add(this);
      }   
      public void removeProduct(Product p) {
          this.products.remove(p);
          p.getStores().remove(this);
      }   
      …
  }
  
  -----------------------
  
  One-to-One Associations:
  
  Unidirectional One-to-One Associations:
  
  @Entity
  public class Customer{   
      @OneToOne
      @JoinColumn(name = “fk_shippingaddress”)
      private ShippingAddress shippingAddress;   
      …
  }
  
  Customer c = em.find(Customer.class, 1L);
  ShippingAddress sa = c.getShippingAddress();
  
  --- --- ---
  
  Bidirectional One-to-One Associations:
  
  @Entity
  public class Customer{
      @OneToOne
      @JoinColumn(name = “fk_shippingaddress”)
      private ShippingAddress shippingAddress;
         …
  }
  
  @Entity
  public class ShippingAddress{
      @OneToOne(mappedBy = “shippingAddress”)
      private Customer customer;
      …
  }
  -----------------------
  
  On Delete Cascade:
    - mean if referenced record (ID or ID in table PERSON) delete then delete child record (PERSON_ID or PERSON_ID in CONTACT)
    - remember, no body care about child record, what is important is parent record, because we are referencing to it
    - ON DELETE of parent CASCADE [by deleting] here. deletes of the parent get cascaded.
    -there are 5 different referential actions:
     1.CASCADE, 2.RESTRICT, 3.NO ACTION, 4.SET NULL, 5.SET DEFAULT
     1.CASCADE:
         ON DELETE CASCADE means that if the parent record is deleted, any child records are also deleted. This is not a good idea in my opinion. You should keep track of all data that's ever been in a database, although this can be done using TRIGGERs. (However, see caveat in comments below).
         ON UPDATE CASCADE means that if the parent primary key is changed, the child value will also change to reflect that. Again in my opinion, not a great idea. If you're changing PRIMARY KEYs with any regularity (or even at all!), there is something wrong with your design. Again, see comments.
         ON UPDATE CASCADE ON DELETE CASCADE means that if you UPDATE OR DELETE the parent, the change is cascaded to the child. This is the equivalent of ANDing the outcomes of first two statements.
     2.RESTRICT:
         RESTRICT means that any attempt to delete and/or update the parent will fail throwing an error. This is the default behaviour in the event that a referential action is not explicitly specified.
         For an ON DELETE or ON UPDATE that is not specified, the default action is always RESTRICT`.
     3.NO ACTION:
        NO ACTION: From the manual. A keyword from standard SQL. In MySQL, equivalent to RESTRICT. The MySQL Server rejects the delete or update operation for the parent table if there is a related foreign key value in the referenced table. Some database systems have deferred checks, and NO ACTION is a deferred check. In MySQL, foreign key constraints are checked immediately, so NO ACTION is the same as RESTRICT.
     4.SET NULL:
        SET NULL - again from the manual. Delete or update the row from the parent table, and set the foreign key column or columns in the child table to NULL. This is not the best of ideas IMHO, primarily because there is no way of "time-travelling" - i.e. looking back into the child tables and associating records with NULLs with the relevant parent record - either CASCADE or use TRIGGERs to populate logging tables to track changes (but, see comments).
     5.SET DEFAULT:
        SET DEFAULT. Yet another (potentially very useful) part of the SQL standard that MySQL hasn't bothered implementing! Allows the developer to specify a value to which to set the foreign key column(s) on an UPDATE or a DELETE. InnoDB and NDB will reject table definitions with a SET DEFAULT clause.
     
  -----------------------
  
  Self Join:
  
  On deleting child, parent is also getting deleted:
  
  @ManyToOne(cascade={CascadeType.ALL})
  @JoinColumn(name="parent_id")
  private Menu parent;
  
  @OneToMany(mappedBy="parent",orphanRemoval=true)
  private List<Menu> children = new ArrayList<Menu>();
  
  Do not use cascade={CascadeType.ALL} on Parent if you do wish to cascade CRUD operations from child to parent:
  
  --- --- --- another example:
  
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "category_id")
   private Integer categoryId;
   
   @NotFound(action = NotFoundAction.IGNORE)
   @ManyToOne
   @JsonIgnore
   @JoinColumn(name = "parent_category_id")
   private FetchSubCategory mainCategory;   
   
   Get your sub categories:
   
   public List<FetchSubCategory> fetchSubCategory() throws SQLException, ClassNotFoundException, IOException {
       List<FetchSubCategory> groupList = null;
       try {
           Session session = sessionFactory.getCurrentSession();
           Query query = session.createQuery("select distinct e FROM FetchSubCategory e INNER JOIN e.subCategory m ORDER BY m.mainCategory");
           groupList = query.list();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return groupList;
   }
   
   
  --- --- --- another example:
  
  where there is only ever only one child per depth:
  
  @Entity
  @Table(name="TREE")
  public class Tree {
      @Id
      @GeneratedValue(strategy= GenerationType.AUTO)
      private Long treeId;
    
      // the column parentTree is going to return the object Tree, instead of the parentTree as a Long
      @OneToOne(fetch=FetchType.EAGER, optional = false)
      @JoinColumn(name="parent_tree", referencedColumnName="treeId", nullable = true)  
      private Tree parentTree;
  
      @Override
      public String toString() {
          return "Tree [treeId=" + treeId + ", parentTree=" + parentTree + "]";
      }
 
  }
  
  -----------------------
  
  ID Generators:
    GenerationType.AUTO
    GenerationType.IDENTITY
    GenerationType.SEQUENCE
    GenerationType.TABLE
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    @GenerationType.IDENTITY
    create table employee(id int PRIMARY KEY AUTO_INCREMENT,name varchare(20));
    
    
  -----------------------
      
      Sorting:
      repository.fondAll(new Sort(new Sort.Order(Direction.DESC,"name"),new Sort.Order("price")));
      
      Paging And Sorting:
      Pageable pageable = new PageRequest(0,2,Direction.DESC,"name");
      repository.findAll(pageable);
  
  -----------------------
  Inheritance Strategies:
        SINGLE_TABLE
        TABLE_PER_CLASS
        JOINED
       
        
  SINGLE_TABLE:
        -here we have one table in database 
        -and field 'pmode' fill when each extended table have value
        @Inheritance(strategy=InheritanceType.SINGLE_TABLE)
        @DiscriminatorColumn(name="pmode",discriminatorType=DiscriminatorType.STRING)
        public abstract class Payment(id,amount)
        
        @DiscriminatorValue("cc")
        public class CreditCard extends Payment(cardnumber)
        
        @DiscriminatorValue("ch")
        public class Check extends Payment(checknumber)
  
  
  TABLE_PER_CLASS:
        -here we have three table in database 
        -and each extended table have field 'id' and 'amount' as their parent table
        @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
        public abstract class Payment(id,amount)
        
        public class CreditCard extends Payment(cardnumber)
        
        public class Check extends Payment(checknumber)
        
  
  JOINED:
        -here we have three table
        -and field 'id' in child tables are foreignKey of field 'id' in parent tab;e
        -we specify join when creating child tables
        @Inheritance(strategy=InheritanceType.JOINED)
        public abstract class Payment(id,amount)
        
        @PrimaryKeyJoinColumn(name="id")
        public class CreditCard extends Payment(cardnumber)
        
        @PrimaryKeyJoinColumn(name="id")
        public class Check extends Payment(checknumber)
  
  
  
  
  -----------------------
  Component Mapping:
    
        create table employee(
        id int,
        name varchar(20),
        streetaddress varchar(30),
        city varchar(20),
        state varchar(20)
        )
    
        
        public class Employee
            id;
            name;
            @Embeded
            Address address;
            
        @Embeddable
        Address
            streetaddress;
            city;
            state;
  
  
  
  -----------------------
      
  Hibernate Caching Mechanism:
        Level one
        level two
        
        
  Level One:
        Session session = entityManager.unwrap(Session.class);
        Product product = repository.findOne(1);
        //free the cache
        session.evict(product);
        
  Level Two:
        1.add maven dependency:
            <dependency>
                <groupId>org.hibernate</groupId>
                <artifactId>hibernate-ehcache</artifactId>
            </dependency>
        2.enable cache for the application:
            spring.jpa.properties.hibernate.cache.use_second_level_cache=true
            spring.jpa.properties.hibernate.cache.use_query_cache=true
            spring.jpa.properties.hibernate.cache.region.factory_class=org.hibernate.cache.ehcache.EhCacheRegionFactory
            spring.jpa.properties.javax.persistence.sharedCache.mode=ALL
            spring.cache.ehcache.config=classpath:ehcache.xml
        3.create ehcache.xml:
            <?xml version="1.0" encoding="UTF-8"?>
            <ehcache>
              <diskStore path="java.io.tmpdir"/>
              <defaultCache maxElementsInMemory="1000" eternal="false" tomeToIdleSeconds="5" timeToLiveSeconds="10" overflowToDisk="true"/>
            </ehcache>
        4.make entities cacheables:
                @Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
                public class Product implements Serializable
            
            
  Cache Concurrency Strategies:
        -READ_ONLY
        -NONSTRICT_READ_WRITE
        -READ_WRITE
        -TRANSACTIONAL
        
  -----------------------
  
-Isolation level:
	-Durability: from underlying database
	-Read_Uncommitted: read uncommitted changes
	-Read_Committed: read only committed changes
	-Repeatable_Read: read identical values multiple times
	-Serializable: read identical rows multiple times

  -----------------------
  
 -@Transactional(propagation=
 	-Propagation.Required: code always run in a transaction. (default)
 	-Propagation.REQUIRES_NEW: code will always run in a new transaction
 	-Propagation.Never: a method should not run in transaction
 	
 -@Transactional(isolation=
 	-how data is available too other users or systems
 	-Isolation.READ_UNCOMMITTED: always dirty read
 	-Isolation.READ_COMMITTED: does not allow dirty read
 	-Isolation.REPEATABLE_READ: result always the same if row read twice
 	-Isolation.SERIALIZABLE: perform all transactions in a sequence
 	
 @Transactional(Timeout=5
 	-time out for the operation wrapped by the transaction
 	
 @transactional(ReadOnly=true)
 	-transaction don't write back to database (default is false)
 	
 @Transactional(rollbackFor=....,noRollbackFor=...,
 	-default is for unchecked exception
 	-example: Exception.class	
 
 @Transactional(rollbackForClassName={"Exception"})
 	-define specific class name that should trigger rollback
 	
 @Transactional(noRollbackFor=
 	-NoSuchElementException.class
 	-specify class name where rollbacks should not occure 
  
   -----------------------  
    
  Predicate<PeriodEntity> periodPredicate = 
                        newPeriod->newPeriod.getId().equals(newPeriod);
                        
  periodEntities.stream()
                .map(PeriodEntity::getPeriodWebService)
                .filter(periodWebServiceEntities.stream().collect(Collectors.toSet())::contains)
                .map(newPeriodEntities::add);
                
     -----------------------  
  
  
-----------------------

SonarQube:

--- D:\SonarQube\sonarqube-8.1.0.31237\conf\sonar.properties file:
    --create user 'sonarqube' and schema 'sonarcube' in postgreSQL
sonar.jdbc.username=sonarqube
sonar.jdbc.password=123
sonar.jdbc.url=jdbc:postgresql://localhost:5432/sonarqube?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true&useConfigs=maxPerformance&useSSL=false
sonar.web.javaAdditionalOpts=-server
sonar.web.host=127.0.0.1

--- D:\SonarQube\sonarqube-8.1.0.31237\conf\wrapper.conf file:
wrapper.java.command=C:\Program Files\Java\jdk-11.0.2\bin\java


(optional)--- D:\SonarQube\sonar-scanner-4.2.0.1873-windows\conf\sonar-scanner.properties:
sonar.host.url=http://localhost:9000
sonar.sourceEncoding=UTF-8
sonar.projectVersion=1.0
sonar.sources=src/main/java
sonar.sourceEncoding=UTF-8
sonar.language=java
sonar.java.binaries=target/classes


---(for remote server) /.m2/settings.xml:
    --get 'sonar.login' code from sonarqube web by generating token.
    --'sonar.jdbc.url' and 'sonar.jdbc.username' and 'sonar.jdbc.password' is not essential.

<profiles>
    <profile>
        <id>sonar</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>  
            <sonar.jdbc.url>jdbc:postgresql://172.17.70.55/sonar</sonar.jdbc.url>                 
            <sonar.jdbc.username>sonar</sonar.jdbc.username>
            <sonar.jdbc.password>sonar</sonar.jdbc.password>   
            <sonar.host.url>http://172.17.70.55:9000/</sonar.host.url>       
            <sonar.login>54f3fda8b8eccc470b81c0015243e0115f9ac1bf</sonar.login>      
        </properties>
    </profile> 
</profiles>


-----------------------


useful codes:

-- controller:
    1)
    List<Long> periodWebServiceIds = StreamSupport
                     .stream(periodWebServiceFastDtoPages.spliterator(),false)
                     .map(PeriodWebServiceFastDto::getId).collect(Collectors.toList());
    
    List<PeriodWebServiceDto> periodWebServiceDtos = periodWebServiceService
                     .findAllPeriodWebServiceDtoById(periodWebServiceIds);
    
    Page<PeriodResponseOld> periodResponsesPages = PageableExecutionUtils
            .getPage(periodWebServiceDtoPeriodResponseOldMapper
                    .PeriodWebServiceDtosToPeriodResponses(periodWebServiceDtos,
                            new CycleAvoidingMappingContext()),
                    paperiodPageable,
                    periodWebServiceFastDtoPages::getTotalElements);
    
    2)
                  
-- services:
    1)
    List<PeriodEntity> periodEntities = StreamSupport
                    .stream(periodRepository.findAllById(periodIds).spliterator(),false)
                    .collect(Collectors.toList());
    
    2)
    public List<PeriodEntity> findNewPeriodNotInPeriodWebService(List<PeriodWebServiceEntity> periodWebServiceEntities) {
            List<PeriodEntity> newPeriods = new ArrayList<>();
            List<Long> oldPeriodIds = periodWebServiceEntities
                    .stream()
                    .map(PeriodWebServiceEntity::getPeriodId)
                    .collect(Collectors.toList());  
            List<PeriodOnly> allPeriodOnlyList = periodRepository.findBy();
            // set Id Entity as Key in Map<Long,List<Entity>> from List<Entity>
            Map<Long, List<PeriodOnly>> allPeriodOnlyMap = allPeriodOnlyList
                    .stream()
                    .collect(Collectors.groupingBy(PeriodOnly::getId));
            // compare List<Entity> with Map<Long,List<Entity>> and remove same Entityes
            // also can use as compare two List<Entity>
            Map<Long, List<PeriodOnly>> newPeriodOnlyMap = allPeriodOnlyMap
                    .entrySet()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(e -> !oldPeriodIds.stream().collect(Collectors.toSet()).contains(e.getKey()))
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
            // get List<Entity> from map<Long,List<Entity>>
            List<PeriodOnly> newPeriodOnlyList = newPeriodOnlyMap
                    .values()
                    .stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            newPeriods = periodOnlyMapperOld
                    .PeriodOnliesToPeriodEntities(newPeriodOnlyList, new CycleAvoidingMappingContext())
            return newPeriods;
        }
        
    3)        
     oldPeriods = periodRepository.findByIdIn(periodIds);
     oldPeriods = periodRepository.findPeriodEntitiesByIdIn(periodIds);
     oldPeriods = periodRepository.findByCreatorNotIn(personEntities);
     // Ids that are not in PeriodWebService but search among data that selected query get from database
     newPeriods = periodRepository.findAllByPeriodWebService_IdNotIn(periodWebServiceIds);
    
    4)
    // Page<Entity> to Page<EntityDto>
    Page<PeriodEntity> periodPageable = periodRepository.findAll(pageable);
    Page<PeriodDto> periodDtoPageable = periodPageable
                    .map(period -> periodMapper.PeriodEntityToPeriodDto(period, new CycleAvoidingMappingContext()));

    5)
    // Iterable<Entity> to List<Entity>
    List<PeriodEntity> periodEntities = StreamSupport
                        .stream(periodRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
                        
    6)
    // compare two List<Entity> and remove Entitys that have property which is equal in both
    periodEntities
                .stream()
                .map(PeriodEntity::getPeriodWebService)
                .collect(Collectors.toList())
                .removeAll(periodWebServiceEntities);
    
    list2.stream().map(list2->list2.property)
                        .filter(list1.stream().map(list1->list1.propery)
                        .collect(Collectors.toSet())::contains)
    
    7)
    // get 20 recored of list
    List<PeriodWebServiceEntity> temp20 = newPeriodWebServiceEntities
                    .stream().limit(20).collect(Collectors.toList());
    
    8)
    // sort list by id
    lis1.sort(Comparator.comparing(list1::getId));
    lis1.sort(Comparator.comparing(list1::getId)).reversed;
    -other example:
        Collections.sort(testList);
        Collections.reverse(testList);
    -other example:
        Collections.sort(testList, Collections.reverseOrder());
     -other example:
        lis1.stream().distinct().sorted(Comparator.comparing(PersonEntity::getId,
                            Comparator.nullsFirst(Comparator.naturalOrder())));
    
    9)Iterate over Pageable:
    PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize());
    Page<Entity> entityPages = entiryService.findAll(pageable);
    while (!entityPages.isEmpty()){
       entityPages = entityService.findAll(pageRequest);
       ...do something
       pageRequest = (PageRequest) pageRequest.next();
     }
     
    10)Map vs flatMap:
     flatMap: input of Streams to a Stream
     example:
        List<List<String>> to List<String>
        listA              to listB 
            listA.stream().flatMap(Collection::stream).collect(Collectors.toList());
            -instead we should use forEach: listA.stream().forEach(listB::addAll)
            
    11)
    Negate method Reference:
    Predicate.not( … )
    example: s.filter(Predicate.not(String::isEmpty))
             s.filter(Predicate.not(listX::contains))
             
     .map(Stream.of(PersonEntity::getPersonWebServiceEntity).setPersonPublicId(this.generatePublicId()))
    
    
    
    List<PersonWebServiceEntity> newPersonWebServices = 
                    newPersons
                            .stream()
                            .filter(Objects::nonNull)
                            .map(PersonEntity::getPersonWebServiceEntity)
                            .filter(Objects::nonNull)
                            .peek(p->p.setPersonPublicId(this.generatePublicId()))
                    .collect(Collectors.toList());
    
    12)
    Stream<List<Entity>> to List<Entity>
            listA         to    listB
    example: listB = listA.flatMap(List::stream).collect(Collectors.toList());
    
    13)
    Distinct by property in List<Entity>:
    example:
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
    persons.stream().filter(distinctByKey(Person::getName));
    -
    other example:
    persons.collect(Collectors.toMap(Person::getName, p -> p, (p, q) -> p)).values();
    -
    other Example:
    return new ArrayList(new HashSet(recipients));
    
    14)
    Stream<Object> add to ArrayList:
    -ArrayList<T> arrayList = stream.collect(Collectors .toCollection(ArrayList::new));
    -
    other example:
    listA.stream().forEachOrdered(listB::add);
    -
    other example:
    listA.stream().map(listB::add);
    
    15)
    List<Entiry> to List<EntityDto>
    ListA        to     ListADto
    -:_
        List<A> a = aDto.findAll();
        List<ADto> aDto = a.stream().map(u->new ADto(a.getId(),a.getName())).collect(Collectors.toList());
    -_:
        
        
-- repository:
    1)
    @EntityGraph(value = "PeriodEntity.periodWebServiceEntity", type = EntityGraph.EntityGraphType.FETCH)
    List<PeriodEntity> findAllByPeriodWebService_IdIn(List<Long> periodWebServiceId);
    
    @EntityGraph(value = "PeriodEntity.periodWebServiceEntity", type = EntityGraph.EntityGraphType.FETCH)
    List<PeriodEntity> findAllByPeriodWebService_IdNotIn(List<Long> periodWebServiceId);
    
    2)
    @EntityGraph(value = "PeriodWebServiceEntity.periodWebServicePeriodFastGraph",type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "select pw from PeriodWebServiceEntity pw left join pw.period p ",
            countQuery = "select count(pw) from PeriodWebServiceEntity pw left join pw.period p")
    Page<PeriodWebServiceEntity> findBy(Pageable pageable);
    
    3)
    Iterable<Entity> findAllByDeleteStatusIsNotNull;
    
    4)Select All with pageable:
    Pageable wholePage = Pageable.unpaged();
    entityRepository.findAll(wholePage);
    
    5)
    //null check before getting a stream 
    //(method that return a List that might be null)
    <T> Stream<T> getStream(List<T> list) {
        return Optional.ofNullable(list).map(List::stream).orElseGet(Stream::empty);
    }
    //If the argument is a collection, we can then flatMap to turn it into a stream:
    <T> Stream<T> fromNullableCollection(Collection<? extends T> collection) {
        return Stream.ofNullable(collection).flatMap(Collection::stream);
    }
    //Example:
    Optional.ofNullable(userList)
                    .orElseGet(Collections::emptyList)
                    .stream()
                    .map(user -> user.getName())
                    .collect(toList());
                    
    6)
    


-----------------------

Lombok:
    -@RequiredArgsConstructor:
        when use it, one argument (parameter) is required for each filed. 
        if fields are initialized with 'final' or '@NonNull' they are created with no-args (parameters).
    -IMPORTANT:  hibernate and Service Provider Interface require the no-args constructor.
        so, always in serviceImpl or Hibernate use 'private final' for fields or we get null point exception in persist.
        hibernate need no-arg constructor because in find or cast syntax do not know which one we want for creating.
        

-----------------------

@RequestParam:
    -makes Spring to map request parameters from the GET/POST request to your method argument.
    -example:
        http://testwebaddress.com/getInformation.do?city=Sydney&country=Australia
        public String getCountryFactors(@RequestParam(value = "city") String city, 
                            @RequestParam(value = "country") String country){ }
                            
     
@RequestBody:
    -POST/PUT Request
    -makes Spring to map entire request to a model class and from there you can retrieve or set values from its getter and setter methods.
    -example:
        http://testwebaddress.com/getInformation.do
        JSON data:
            {
               "city": "Sydney",
               "country": "Australia"
            }
            
        public String getCountryFactors(@RequestBody Country countryFacts)
            {
                countryFacts.getCity();
                countryFacts.getCountry();
            }
            
@PathVariable:
    -obtain some placeholder from the URI (Spring call it an URI Template) 
    -example:
        http://localhost:8080/MyApp/user/1234/invoices?date=12-05-2013
        @RequestMapping(value="/user/{userId}/invoices", method = RequestMethod.GET)
        public List<Invoice> listUsersInvoices(
            @PathVariable("userId") int user,
            @RequestParam(value = "date", required = false) Date dateOrNull) {
              ...
            }
    
            
-----------------------

    -important security classes:
    
    -org.springframework.security.authentication.AbstractAuthenticationToken :
        for AbstractAuthenticationToken::authorities.
    
    -org.springframework.security.access.intercept.AbstractSecurityInterceptor :
        for InterceptorStatusToken::object
   
    -org.springframework.security.access.vote.AffirmativeBased :
        for decide::voter

    -org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider :
        for Authentication
    
    
-----------------------

    Exception:
    -UnsupportedOperationException when trying to remove/add an element from/to a List
    -Arrays.asList returning a fixed-size list, So you can't add/remove to/from it. and You can't structurally modify the List.
    -use : new ArrayList<>(Arrays.asList(new User(...), new User(...)))
    
-----------------------

MVC: Response Redirect:
    -public ResponseEntity<?> afterPaymentResponse(HttpServletRequest request)
    -:_
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "IMI eyJhb");
        String baseUrl = ServletUriComponentsBuilder
                            .fromRequestUri(request)
                                .replacePath(null)
                                    .build().toUriString();
                                    
            request.getLocalName();
            // or this
            request.getLocalAddr();
            URI _redirectUri = null;
            _redirectUri = new URI("http://foo.example.com/webpageC");
            httpHeaders.setLocation(_redirectUri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    -_:
    -
    -:_
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .header(HttpHeaders.LOCATION,
                    "http://ashouri-pc:8080/edu-imi-ws/...?operationStatus=success")
            .build();
            
        -in the above way we could only get parameter in destination from @RequestParam like bellow:
            @RequestParam(value = "operationStatus", required = false) String operationStatus,
    -_:
    -
    -:_
        -in the following way we get value from flash attributes(addFlashAttribute() or addAttribute()):
        -Send Method:
            make return String as bellow: 
                return "redirect:/...";
            -or
            for RedirectAttributes: the method returns a redirect view name or a RedirectView to implement
                we should use string as return "redirect:/foo/bar" or RedirectView like bellow:
            @RequestMapping(value = "/bar", method = RequestMethod.POST)
            public RedirectView handlePost(RedirectAttributes redirectAttrs) {
              redirectAttrs.addFlashAttributes("some", "thing");
              return new RedirectView("/foo/bar", true);
            }
        -    
        -Receive Method:
            @RequestMapping(value = "/bar", method = RequestMethod.GET)
            public ModelAndView handleGet(Model model) {
              String some = (String) model.asMap().get("some");
              // do the job
            }
            -or
            @RequestMapping(value = "/bar", method = RequestMethod.GET)
            public ModelAndView handleGet(HttpServletRequest request) {
              Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
              if (inputFlashMap != null) {
                String some = (String) inputFlashMap.get("some");
                // do the job
              }
            }
            -or
            for RedirectAttributes:
                @RequestMapping(value = "/bar")
                public void handleBar(@ModelAttribute("some") String some)
                {
                    System.out.println("some=" + some);
                }
            
    -_:
    -
    -:_
        return ResponseEntity.ok()
                        .header("Authorization","IMI eyJhb")
                        .body(MYBODYCLASS);
    -_:
    -
    -:_
        @RequestMapping(value = "/rm1", method = RequestMethod.POST)
        public String rm1(Model model,RedirectAttributes rm) {
            System.out.println("Entered rm1 method ");
            rm.addFlashAttribute("modelkey", "modelvalue");
            rm.addAttribute("nonflash", "nonflashvalue");
            model.addAttribute("modelkey", "modelvalue");
            return "redirect:/rm2.htm";
        }
        
        @RequestMapping(value = "/rm2", method = RequestMethod.GET)
        public String rm2(Model model,HttpServletRequest request) {
            Map md = model.asMap().get(modelKey);
            java.util.Enumeration<String> reqEnum = request.getParameterNames();
            return "controller2output"
        }
    -_:
    -
    -public String afterPaymentResponse()
    :_
        String returnValue = "redirect:/api/v1/contacts/nationalCode/0000000000";
        return returnValue;
    _:
    -
    -:_
    1.Redirect With the RedirectView:
        @Controller
        @RequestMapping("/")
        public class RedirectController {
             
            @GetMapping("/redirectWithRedirectView")
            public RedirectView redirectWithUsingRedirectView(
              RedirectAttributes attributes) {
                attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
                attributes.addAttribute("attribute", "redirectWithRedirectView");
                return new RedirectView("redirectedUrl");
            }
        }
    
    2.Redirect With the Prefix redirect:
        @Controller
        @RequestMapping("/")
        public class RedirectController {
             
            @GetMapping("/redirectWithRedirectPrefix")
            public ModelAndView redirectWithUsingRedirectPrefix(ModelMap model) {
                model.addAttribute("attribute", "redirectWithRedirectPrefix");
                return new ModelAndView("redirect:/redirectedUrl", model);
            }
        }
        
    3.Forward With the Prefix forward:
        @Controller
        @RequestMapping("/")
        public class RedirectController {
             
            @GetMapping("/forwardWithForwardPrefix")
            public ModelAndView redirectWithUsingForwardPrefix(ModelMap model) {
                model.addAttribute("attribute", "forwardWithForwardPrefix");
                return new ModelAndView("forward:/redirectedUrl", model);
            }
        }
        
    4.Attributes With the RedirectAttributes:
        @GetMapping("/redirectWithRedirectAttributes")
        public RedirectView redirectWithRedirectAttributes(RedirectAttributes attributes) {
          
            attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectAttributes");
            attributes.addAttribute("attribute", "redirectWithRedirectAttributes");
            return new RedirectView("redirectedUrl");
        }
        
        @GetMapping("/redirectedUrl")
        public ModelAndView redirection(
          ModelMap model, 
          @ModelAttribute("flashAttribute") Object flashAttribute) {
              
             model.addAttribute("redirectionAttribute", flashAttribute);
             return new ModelAndView("redirection", model);
         }
    
    5.Redirecting an HTTP POST Request:
        @PostMapping("/redirectPostToPost")
        public ModelAndView redirectPostToPost(HttpServletRequest request) {
            request.setAttribute(
              View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
            return new ModelAndView("redirect:/redirectedPostToPost");
        }
        
        @PostMapping("/redirectedPostToPost")
        public ModelAndView redirectedPostToPost() {
            return new ModelAndView("redirection");
        }
       
    -_:
    -
    -Attention:
        -status codes 301 (Moved Permanently) and 302 (Found):
            allow the request method to be changed from POST to GET. 
        
        -status codes 307 (Temporary Redirect) and 308 (Permanent Redirect):
            status codes that don't allow the request method to be changed from POST to GET.
        
-----------------------

@ModelAttribute vs  @RequestParam:
    -if you have any binding activities from form to a model class
        like Person->name then use @ModelAttribute("person") Person person
        and if there is no biding @RequestParam("name")
    -example:
    -:_
        <form:form method="POST" action="/spring-mvc-basics/addEmployee" 
          modelAttribute="employee">
            <form:label path="name">Name</form:label>
            <form:input path="name" />
            <form:label path="id">Id</form:label>
            <form:input path="id" />
            <input type="submit" value="Submit" />
        </form:form>
        @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
        public String submit(@ModelAttribute("employee") Employee employee) {
            model.addAttribute("name", employee.getName());
            model.addAttribute("id", employee.getId());
            return "employeeView";
        }
        
-----------------------

Mapstruct:
    
    -NullValuePropertyMappingStrategy:
        -This strategy can be set on @Mapping, @BeanMapping, @Mapper or @MapperConfig in precedence order.
        -SET_TO_NULL: If the source property is null or not present the target property is set to null
        -SET_TO_DEFAULT: If the source property is null or not present the target property is set to default 
        -IGNORE: If the source property is null or not present the target property is not set at all
        -example:
            @Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    
    -ignoreStrategy:(DEPRACATED)
        -@Mapping(ignoreStrategy=...)
        -ALWAYS (as true)
        -NEVER (as false)
        -NULL_SOURCE (ignore if source value is null)
            -:_
                if (source.getValue() != null)
                        target.setValue (source.getValue());
            -_:
        -NULL_TARGET (ignore if target value is null)
            -:_
                if (target.getValue() != null)
                        target.setValue(source.getValue());
            -_:
            
    -Java expressions:
        -@Mapping( target="discount", expression="java( source.getRetailPrice() - source.getSalePrice())" )
    
    -@IterableMapping(qualifiedBy) or @IterableMapping(qualifiedByName) :
        -You should use org.mapstruct.Named and not javax.inject.Named for this to work. 
         You can also define your own annotation by using org.mapstruct.Qualifier
        -:_
         AssigmentFileDTO assigmentFileToAssigmentFileDTO(AssigmentFile assigmentFile);
        
         @IterableMapping(qualifiedByName="mapWithoutData")
         List<AssigmentFileDTO> assigmentFilesToAssigmentFileDTOs(List<AssigmentFile> assigmentFiles);
        
         @Named("mapWithoutData")
         @Mapping(target = "data", ignore = true)
         AssignmentFileDto mapWithouData(AssignmentFile source)
        -_:
        
    -The generated code for create method is fine, but in case of update, 
     I want to set the properties in the target, 
     only if they are not null in the source.
        -:_
            @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
                        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
            void update(AmcPackageRequest amcPackageRequest, @MappingTarget AmcPackage amcPackage);
        -_:
        
    -java Expression updateDate:
         @Mapping(target = "updatedTime", expression = "java( java.time.LocalDateTime.now() )")})
         
    -Java Expression (Inject Service)
        @Mapper(componentModel = "spring")
        public abstract class ContactResponseContactDtoMapper{
            
                AccountService accountService;
                @Autowired
                public void setAccountService(AccountService accountService) {
                    this.accountService = accountService;
                }
                
                @Mappings({
                    @Mapping(target = "accountDto", source = "accountPublicId",
                                        defaultExpression = "java(accountService
                                            .findAccountDtoByAccountPublicId(contactResponse.getAccountPublicId()))")
                    })
                abstract ContactDto toContactDto(ContactResponse contactResponse, @Context CycleAvoidingMappingContext context);
                
                AccountDto map(String accountPublicId){
                        AccountDto accountDto = new AccountDto();
                        accountDto = accountService.findAccountDtoByAccountPublicId(accountPublicId);
                        return accountDto;
                    }
        }
        
    -@AfterMapping: Use Injected Service
        -@Mapper(componentModel = "spring")
            public abstract class ContactResponseContactDtoMapper{
            
                    AccountService accountService;
                    @Autowired
                    public void setAccountService(AccountService accountService) {
                        this.accountService = accountService;
                    }
                    
            @AfterMapping
                public void handleDtoEntity_Ids(ContactResponse contactResponse,
                                                        @MappingTarget ContactDto contactDto
                ) {
                    if(contactResponse.getAccountPublicId()!=null){
                        AccountEntity account= accountService
                                .findAccountByAccountPublicId(contactResponse.getContactPublicId());
                    }
                }       
            
            }
        -:we could not have Dto to entity and get error incompatible types
            
    -Inject Service with @Context:
        -not working in mapstruct version 1.4.1
        -search use of @ObjectFactory in mapstruct
        -@Mapper(componentModel = "spring")
         public interface ContactResponseContactDtoMapper{
            
            @AfterMapping
            default void handleDtoEntity_Ids(ContactResponse contactResponse,
                                                    @MappingTarget ContactDto contactDto,
                                                    @Context AccountService accountService) {
                
                if(contactResponse.getAccountPublicId()!=null){
                    AccountEntity account= accountService
                            .findAccountByAccountPublicId(contactResponse.getContactPublicId());
                }
            }
         }
            
    -Mapper inside Other Mapper:
        -@Mapper(componentModel="spring", uses={AccountDtoMapper.class})
        
    -Inject Service with @DecoratedWith:
        -@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
          componentModel = "spring")
         @DecoratedWith(FooMapperDecorator.class)
         public interface FooMapper {
             FooDTO map(Foo foo);
         }
        
        -public abstract class FooMapperDecorator implements FooMapper{
             @Autowired
             @Qualifier("delegate")
             private FooMapper delegate;
         
             @Autowired
             private MyBean myBean;
         
             @Override
             public FooDTO map(Foo foo) {
                 FooDTO fooDTO = delegate.map(foo);
                 fooDTO.setBar(myBean.getBar(foo.getBarId());
                 return fooDTO;
             }
         }
         
        -Mapstruct will generate 2 classes and mark the FooMapper that extends FooMapperDecorator as the @Primary bean.

-----------------------




        
