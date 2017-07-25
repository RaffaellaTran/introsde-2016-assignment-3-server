package introsde.document.ws;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import introsde.document.model.Measure;
import introsde.document.model.Person;


@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People {
	    
	    //Method #1
	    @WebMethod(operationName="readPersonList")
	    @WebResult(name="person") 
	    public List<Person> getPeople();

	    //Method #2
	    @WebMethod(operationName="readPerson")
	    @WebResult(name="person") 
	    public Person readPerson(@WebParam(name="personId") Long idPerson);

	    //Method #3
	    @WebMethod(operationName="updatePerson")
	    @WebResult(name="message") 
	    public String updatePerson(@WebParam(name="person") Person person);
   
	    //Method #4
	    @WebMethod(operationName="createPerson")
	    @WebResult(name="person") 
	    public Long addPerson(@WebParam(name="person") Person person, @WebParam(name="measure") List<Measure> m);
	 
	    //Method #5
	    @WebMethod(operationName="deletePerson")
	    @WebResult(name="personId") 
	    public String deletePerson(@WebParam(name="personId") Long idPerson);
	    
	    //Method #6
	    @WebMethod(operationName="readPersonHistory")
	    @WebResult(name="measure") 
	    public List<Measure> readPersonHistory(@WebParam(name="personId") Long idPerson, @WebParam(name="measureType") String measureType);
	    
	    //Method #7
	    @WebMethod(operationName="readMeasureTypes")
	    @WebResult(name="measureType") 
	    public List<String> readMeasureTypes();
	    
	    //Method #8
	    @WebMethod(operationName="readPersonMeasure")
	    @WebResult(name="measure")
		public Measure readPersonMeasure(@WebParam(name="personId") Long idPerson, @WebParam(name="measureType") String measureType, @WebParam(name="measureId") int mid);
	    
	    //Method #9
	    @WebMethod(operationName="savePersonMeasure")
	    @WebResult(name="measure")
	    public int savePersonMeasure(@WebParam(name="personId") Long idPerson, @WebParam(name="measure", mode=Mode.IN) Measure m);

	    //Method #10
	    @WebMethod(operationName="updatePersonMeasure")
	    @WebResult(name="measure")
	    public Measure updatePersonMeasure(@WebParam(name="personId") Long idPerson, @WebParam(name="measure", mode=Mode.IN) Measure m);
	    
}