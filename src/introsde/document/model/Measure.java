package introsde.document.model;


import introsde.document.dao.LifeCoachDao;
import introsde.document.model.Person;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;



/**
 * The persistent class for the "Measure" database table.
 * 
 */
@Entity
@Table(name="Measure")
@NamedQueries({
	@NamedQuery(name="Measure.findAll", query="SELECT h FROM Measure h"),
	@NamedQuery(name="Measure.readMeasureTypes", query="SELECT DISTINCT h.measureType FROM Measure h")
})
@XmlType(propOrder={"idMeasureHistory", "timestamp", "measureType" , "value" , "measureValueType"})
public class Measure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_mhistory")
	@TableGenerator(name="sqlite_mhistory", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="Measure")
	@Column(name="idMeasureHistory")
	private int idMeasureHistory;

	@Temporal(TemporalType.DATE)
	@Column(name="timestamp")
	private Date timestamp;

	@Column(name="value")
	private String value;
	
	@Column(name="measureType")
	private String measureType;
	
	@Column(name="measureValueType")
	private String measureValueType;
	
	@ManyToOne
	@JoinColumn(name = "idPerson", referencedColumnName = "idPerson")
	private Person person;

	public Measure() {
	}
	
	@XmlElement(name="mid")
	public int getIdMeasureHistory() {
		return this.idMeasureHistory;
	}

	public void setIdMeasureHistory(int idMeasureHistory) {
		this.idMeasureHistory = idMeasureHistory;
	}
	
	@XmlElement(name="dateRegistered")
	public String getTimestamp() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    return df.format(this.timestamp);
	}

	public void setTimestamp(String ts) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = format.parse(ts);
		this.timestamp = date;
	}
	
	@XmlElement(name="measureValue")
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}
	
	public String getMeasureType() {
		return this.measureType;
	}

	public void setMeasureValueType(String measureValueType) {
		this.measureValueType = measureValueType;
	}
	
	public String getMeasureValueType() {
		return this.measureValueType;
	}
	
	@XmlTransient
	public Person getPerson() {
	    return this.person;
	}

	public void setPerson(Person param) {
	    this.person = param;
	}
	
	public void toPrint() {
    	System.out.println("-----------------------");
    	System.out.println("measure id 		 			 : "+ idMeasureHistory);
		System.out.println("measure date of registered	 : "+ getTimestamp());
		System.out.println("measure type 	 			 : "+ measureType);
		System.out.println("value type of measure 		 : "+ measureValueType);
		System.out.println("value of the measure		 : "+ value);
		
	}
	
	// database operations
	public static Measure getMeasureById(int id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Measure p = em.find(Measure.class, id);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<Measure> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<Measure> list = em.createNamedQuery("Measure.findAll", Measure.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static Measure saveMeasure(Measure p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static Measure updateMeasure(Measure p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeMeasure(Measure p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
	
	public static List<String> getMeasureTypes() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<String> list = em.createNamedQuery("Measure.readMeasureTypes", String.class)
	    		.getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
}