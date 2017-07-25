package introsde.document.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import introsde.document.dao.LifeCoachDao;

@Entity  // indicates that this class is an entity to persist in DB
@Table(name="\"Person\"") // to whole table must be persisted
@NamedQueries({
	@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p"),
	@NamedQuery(name="Person.currentHealth", query="SELECT h FROM Measure h "
												+ "WHERE h.person = ?1 "
												+ "GROUP BY h.measureType "
												+ "HAVING h.timestamp = MAX(h.timestamp)"),
	@NamedQuery(name="Person.readHistory", query="SELECT h FROM Measure h "
												+ "WHERE h.person = ?1 AND h.measureType LIKE ?2")
})
@XmlType(propOrder={"idPerson", "name", "lastname" , "birthdate", "currentHealth"})
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_person")
    @TableGenerator(name="sqlite_person", table="sqlite_sequence",
        pkColumnName="name", valueColumnName="seq",
        pkColumnValue="Person")
    
    @Column(name="\"idPerson\"")
    private Long idPerson;
    
    @Column(name="\"lastname\"")
    private String lastname;
    
    @Column(name="\"name\"")
    private String name;
    
    @Column(name="\"username\"")
    private String username;
    
    @Temporal(TemporalType.DATE) // defines the precision of the date attribute
    @Column(name="\"birthdate\"")
    private Date birthdate; 
    
    @Column(name="\"email\"")
    private String email;
    
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<Measure> currentHealth; 
    
    // add below all the getters and setters of all the private attributes
    
    // getters
    public Long getIdPerson(){
        return this.idPerson;
    }

    public String getLastname(){
        return this.lastname;
    }
    @XmlElement(name="firstname")
    public String getName(){
        return this.name;
    }
    @XmlTransient
    public String getUsername(){
        return this.username;
    }
    public String getBirthdate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(this.birthdate);
    }
    @XmlTransient
    public String getEmail(){
        return this.email;
    }
    
    @XmlElementWrapper(name="currentHealth")
    @XmlElement(name="measure")
    public List<Measure> getCurrentHealth() {
    		return this.getQueryCurrentHealth();
	}

    // setters
    public void setIdPerson(Long idPerson){
        this.idPerson = idPerson;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setBirthdate(String birthdate) throws ParseException{
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = format.parse(birthdate);
        this.birthdate = date;
    }
    public void setEmail(String email){
        this.email = email;
    }   
    public void setCurrentHealth(List<Measure> param) {
		this.currentHealth = param;
	}
    
    public void toPrint() {
    	System.out.println("******************");
    	System.out.println("id       : "+ idPerson);
		System.out.println("firstname: "+ name);
		System.out.println("lastname : "+ lastname);
		for(Measure m:  this.getCurrentHealth()){
			System.out.println("	");
			m.toPrint();
		}
	}
    
    public static Person getPersonById(Long id) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        Person p = em.find(Person.class, id);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static List<Person> getAll() {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
            .getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }

    public static Person savePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    } 

    public static Person updatePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static void removePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }
    
    public List<Measure> getQueryCurrentHealth() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<Measure> list = em.createNamedQuery("Person.currentHealth", Measure.class)
	    		.setParameter(1, this)
	    		.getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}

	public static List<Measure> getHistory(Person person, String measureType) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<Measure> list = em.createNamedQuery("Person.readHistory", Measure.class)
	    		.setParameter(1, person)
	    		.setParameter(2, measureType)
	    		.getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
}