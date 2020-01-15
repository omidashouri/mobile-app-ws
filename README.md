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
   
   @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)//Avoiding empty json arrays.objects
   @OneToMany(mappedBy = "mainCategory", fetch = FetchType.EAGER)
   private List<FetchSubCategory> subCategory;
   
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
  