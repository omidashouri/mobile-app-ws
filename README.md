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
        
        
    