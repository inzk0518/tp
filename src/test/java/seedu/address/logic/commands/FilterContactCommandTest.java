package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.*;
import seedu.address.model.person.predicates.PersonMatchesFilterPredicate;
import seedu.address.model.tag.Tag;

/**
 * Tests the FilterContactCommand end-to-end with a real ModelManager.
 */
public class FilterContactCommandTest {

    private Model model;

    private Person alice;
    private Person bob;
    private Person carol;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();

        alice = createPerson("Alice Tan", "91234567", "alice@gmail.com", "123 Orchard Rd",
                500000, 1000000, "Active", Set.of("buyer"));
        bob = createPerson("Bob Lee", "98765432", "bob@yahoo.com", "456 Clementi Ave",
                800000, 1200000, "Inactive", Set.of("seller"));
        carol = createPerson("Carol Koh", "83335555", "carol@home.com", "789 Hougang St",
                400000, 700000, "Active", Set.of("buyer"));

        model.addPerson(alice);
        model.addPerson(bob);
        model.addPerson(carol);
    }

    /**
     * Helper to create a Person using integer-only budgets.
     */
    private Person createPerson(String name, String phone, String email, String address,
                                int min, int max, String status, Set<String> tags) {
        Set<Tag> tagSet = new HashSet<>();
        for (String t : tags) {
            tagSet.add(new Tag(t));
        }

        String minStr = String.valueOf(Math.max(0, min)); // ensure non-negative integer
        String maxStr = String.valueOf(Math.max(0, max));

        return new Person(
                new Uuid(1),                     // dummy UUID wrapper
                new Name(name),
                new Phone(phone),
                new Email(email),
                new PersonAddress(address),
                tagSet,
                new BudgetMin(minStr),
                new BudgetMax(maxStr),
                new Notes("N/A"),
                new PersonStatus(status)
        );
    }

    @Test
    public void execute_filterByName_success() throws CommandException {
        PersonMatchesFilterPredicate predicate =
                new PersonMatchesFilterPredicate.Builder().withName("alice").build();

        FilterContactCommand command = new FilterContactCommand(predicate, 20, 0);
        CommandResult result = command.execute(model);

        assertTrue(model.getFilteredPersonList().contains(alice));
        assertEquals("1 contacts matched (showing 1–1)", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByStatus_success() throws CommandException {
        PersonMatchesFilterPredicate predicate =
                new PersonMatchesFilterPredicate.Builder().withStatus("active").build();

        FilterContactCommand command = new FilterContactCommand(predicate, 20, 0);
        CommandResult result = command.execute(model);

        List<Person> shown = model.getFilteredPersonList();
        assertEquals(2, shown.size());
        assertTrue(shown.contains(alice));
        assertTrue(shown.contains(carol));
        assertEquals("2 contacts matched (showing 1–2)", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByBudgetRange_success() throws CommandException {
        PersonMatchesFilterPredicate predicate =
                new PersonMatchesFilterPredicate.Builder()
                        .withBudgetMin("700000")
                        .withBudgetMax("1500000")
                        .build();

        FilterContactCommand command = new FilterContactCommand(predicate, 20, 0);
        CommandResult result = command.execute(model);

        List<Person> shown = model.getFilteredPersonList();
        assertEquals(2, shown.size());
        assertTrue(shown.contains(alice));
        assertTrue(shown.contains(bob));
        assertEquals("2 contacts matched (showing 1–2)", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterWithLimitAndOffset_success() throws CommandException {
        PersonMatchesFilterPredicate predicate =
                new PersonMatchesFilterPredicate.Builder().withStatus("active").build();

        FilterContactCommand command = new FilterContactCommand(predicate, 1, 1);
        CommandResult result = command.execute(model);

        List<Person> shown = model.getFilteredPersonList();
        assertEquals(1, shown.size());
        assertEquals("2 contacts matched (showing 2–2)", result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByTag_success() throws CommandException {
        PersonMatchesFilterPredicate predicate =
                new PersonMatchesFilterPredicate.Builder().withTag("seller").build();

        FilterContactCommand command = new FilterContactCommand(predicate, 20, 0);
        CommandResult result = command.execute(model);

        List<Person> shown = model.getFilteredPersonList();
        assertEquals(1, shown.size());
        assertTrue(shown.contains(bob));
        assertEquals("1 contacts matched (showing 1–1)", result.getFeedbackToUser());
    }
}
