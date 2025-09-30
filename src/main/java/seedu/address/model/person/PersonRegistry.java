package seedu.address.model.person;

import java.util.HashMap;
import java.util.Map;

public class PersonRegistry {
    private final Map<Integer, Person> personMap = new HashMap<>();

    public void addPerson(Person person) {
        personMap.put(person.getUUID(), person);
    }

    public Person getPerson(Integer id) {
        return personMap.get(id);
    }

    public boolean containsId(Integer id) {
        return personMap.containsKey(id);
    }

    public void removePerson(Integer id) {
        personMap.remove(id);
    }
}
