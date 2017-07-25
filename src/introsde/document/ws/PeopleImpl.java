package introsde.document.ws;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;

import introsde.document.model.Measure;

import introsde.document.model.Person;

//Service Implementation

@WebService(endpointInterface = "introsde.document.ws.People", serviceName = "PeopleService")
public class PeopleImpl implements People {

	// Method #1
	@Override
	public List<Person> getPeople() {
		return Person.getAll();
	}

	// Method #2
	@Override
	public Person readPerson(Long id) {
		System.out.println("---> Reading Person by id = " + id);
		Person p = Person.getPersonById(id);

		if (p == null) {
			System.out.println("---> Didn't find any Person with  id = " + id);
		} else {
			System.out.println("---> Found Person by id = " + id + " => " + p.getName());
		}
		return p;
	}

	// Method #3
	@Override
	public String updatePerson(Person p) {
		Person exist = Person.getPersonById(p.getIdPerson());

		if (exist == null) {
			return "Didn't found person"+p.getIdPerson();
		} else {
			Person.updatePerson(p);
			return "Person update";
		}
	}

	// Method #4
	@Override
	public Long addPerson(Person person, List<Measure> m) {
		if (m == null) {
			Person p = Person.savePerson(person);
			return p.getIdPerson();
		} else {
			Person p = Person.savePerson(person);
			ArrayList<String> control = new ArrayList<>();
			for (int i = 0; i < m.size(); i++) {
				if (!control.contains(m.get(i).getMeasureType())) {
					control.add(m.get(i).getMeasureType());
					m.get(i).setPerson(p);
					Measure.saveMeasure(m.get(i));
				}
			}
			Person.getPersonById(p.getIdPerson());
			return p.getIdPerson();
		}
	}

	// Method #5
	@Override
	public String deletePerson(Long id) {
		Person p = Person.getPersonById(id);
		if (p != null) {
			Person.removePerson(p);
			return "delete person";
		} else {
			return "couldn't find person "+id;
		}
	}

	// Method #6
	@Override
	public List<Measure> readPersonHistory(Long id, String measureType) {
		System.out.println("---> Reading Person by id = " + id);
		Person p = Person.getPersonById(id);
		List<Measure> history = null;
		if (p == null) {
			System.out.println("---> Didn't find any Person with  id = " + id);
		} else {
			System.out.println("---> Found Person by id = " + id + " => " + p.getName());
			history = Person.getHistory(p, measureType);
		}
		return history;
	}

	// Method #7
	@Override
	public List<String> readMeasureTypes() {
		return Measure.getMeasureTypes();
	}

	// Method #8
	@Override
	public Measure readPersonMeasure(Long id, String measureType, int mid) {
		return Measure.getMeasureById(mid);
	}

	// Method #9
	@Override
	public int savePersonMeasure(Long id, Measure m) {
		Person p = Person.getPersonById(id);
		if (p == null) {
			return -1;
		} else {
			m.setPerson(p);
			m = Measure.saveMeasure(m);
			return m.getIdMeasureHistory();
			
		}
	}

	// Method #10
	@Override
	public Measure updatePersonMeasure(Long id, Measure m) {
		Measure h = Measure.getMeasureById(m.getIdMeasureHistory());
		if (h.getPerson().getIdPerson() != id) {
			return null;
		} else {
			m.setPerson(h.getPerson());
			return Measure.updateMeasure(m);
			
		}
	}
}