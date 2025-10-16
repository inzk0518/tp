package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertyBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Uuid;
import seedu.address.model.property.Property;
import seedu.address.testutil.PersonBuilderUtil;
import seedu.address.testutil.PropertyBuilderUtil;
import static seedu.address.model.uuid.Uuid.StoredItem.PERSON;

import seedu.address.model.uuid.Uuid;

public class ShowPropertiesCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalPropertyBook(), new UserPrefs());

    @Test
    public void execute_clientIsOwner_success() {
        // Create a person
        Person person = new PersonBuilderUtil().withUuid(100).withName("Tan Wei Ming").build();
        model.addPerson(person);

        // Create a property owned by this person
        Property property = new PropertyBuilderUtil()
                .withId("prop100")
                .withPropertyAddress("123 Orchard Road")
                .withOwner("100")
                .withPrice("1500000")
                .build();
        model.addProperty(property);

        ShowPropertiesCommand command = new ShowPropertiesCommand(new Uuid(100));

        String expectedMessage = "Showing properties for Client UUID 100 (Tan Wei Ming):\n\n"
                + "As Owner:\n"
                + "  - Property prop100: 123 Orchard Road (landed, $1,500,000, sold)\n\n"
                + "Total: 1 property";

        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_clientIsBuyer_success() {
        // Create a person with buying property IDs
        Person buyer = new PersonBuilderUtil()
                .withUuid(200)
                .withName("Lim Hui Ying")
                .withBuyingPropertyIds("prop200")
                .build();
        model.addPerson(buyer);

        // Create a property that the person wants to buy
        Property property = new PropertyBuilderUtil()
                .withId("prop200")
                .withPropertyAddress("456 Toa Payoh Lorong 6")
                .withOwner("999")
                .withPrice("650000")
                .build();
        model.addProperty(property);

        ShowPropertiesCommand command = new ShowPropertiesCommand(new Uuid(200));

        String expectedMessage = "Showing properties for Client UUID 200 (Lim Hui Ying):\n\n"
                + "As Buyer:\n"
                + "  - Property prop200: 456 Toa Payoh Lorong 6 (landed, $650,000, sold)\n\n"
                + "Total: 1 property";

        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_clientIsSeller_success() {
        // Create a person with selling property IDs
        Person seller = new PersonBuilderUtil()
                .withUuid(300)
                .withName("Kumar Rajan")
                .withSellingPropertyIds("prop300")
                .build();
        model.addPerson(seller);

        // Create a property that the person wants to sell
        Property property = new PropertyBuilderUtil()
                .withId("prop300")
                .withPropertyAddress("789 Bukit Timah Road")
                .withOwner("888")
                .withPrice("2800000")
                .build();
        model.addProperty(property);

        ShowPropertiesCommand command = new ShowPropertiesCommand(new Uuid(300));

        String expectedMessage = "Showing properties for Client UUID 300 (Kumar Rajan):\n\n"
                + "As Seller:\n"
                + "  - Property prop300: 789 Bukit Timah Road (landed, $2,800,000, sold)\n\n"
                + "Total: 1 property";

        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_clientHasMultipleRoles_success() {
        // Create a person with multiple property relationships
        Person person = new PersonBuilderUtil()
                .withUuid(400)
                .withName("Chen Xiao Li")
                .withBuyingPropertyIds("prop401")
                .withSellingPropertyIds("prop402")
                .build();
        model.addPerson(person);

        // Property owned by the person
        Property ownedProperty = new PropertyBuilderUtil()
                .withId("prop400")
                .withPropertyAddress("100 Marine Parade Central")
                .withOwner("400")
                .withPrice("1200000")
                .build();
        model.addProperty(ownedProperty);

        // Property the person wants to buy
        Property buyingProperty = new PropertyBuilderUtil()
                .withId("prop401")
                .withPropertyAddress("200 Clementi Avenue 2")
                .withOwner("999")
                .withPrice("850000")
                .build();
        model.addProperty(buyingProperty);

        // Property the person wants to sell
        Property sellingProperty = new PropertyBuilderUtil()
                .withId("prop402")
                .withPropertyAddress("300 Tampines Street 21")
                .withOwner("888")
                .withPrice("700000")
                .build();
        model.addProperty(sellingProperty);

        ShowPropertiesCommand command = new ShowPropertiesCommand(new Uuid(400));

        String expectedMessage = "Showing properties for Client UUID 400 (Chen Xiao Li):\n\n"
                + "As Owner:\n"
                + "  - Property prop400: 100 Marine Parade Central (landed, $1,200,000, sold)\n\n"
                + "As Buyer:\n"
                + "  - Property prop401: 200 Clementi Avenue 2 (landed, $850,000, sold)\n\n"
                + "As Seller:\n"
                + "  - Property prop402: 300 Tampines Street 21 (landed, $700,000, sold)\n\n"
                + "Total: 3 properties";

        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_clientHasNoProperties_throwsCommandException() {
        // Create a person with no property associations
        Person person = new PersonBuilderUtil().withUuid(500).withName("David Ng").build();
        model.addPerson(person);

        ShowPropertiesCommand command = new ShowPropertiesCommand(new Uuid(500));

        assertCommandFailure(command, model, String.format(
                ShowPropertiesCommand.MESSAGE_NO_PROPERTIES, "500"));
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        ShowPropertiesCommand command = new ShowPropertiesCommand(new Uuid(999));

        assertCommandFailure(command, model, String.format(
                ShowPropertiesCommand.MESSAGE_PERSON_NOT_FOUND, "999"));
    }

    @Test
    public void equals() {
        Uuid firstUuid = new Uuid(123, PERSON);
        Uuid secondUuid = new Uuid(456, PERSON);

        ShowPropertiesCommand showFirstCommand = new ShowPropertiesCommand(firstUuid);
        ShowPropertiesCommand showSecondCommand = new ShowPropertiesCommand(secondUuid);

        // same object returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values return true
        ShowPropertiesCommand showFirstCommandCopy = new ShowPropertiesCommand(firstUuid);
        assertTrue(showFirstCommand.equals(showFirstCommandCopy));

        // different types return false
        assertFalse(showFirstCommand.equals(1));

        // null value returns false
        assertFalse(showFirstCommand.equals(null));

        // different uuid returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Uuid uuid = new Uuid(123, PERSON);
        ShowPropertiesCommand command = new ShowPropertiesCommand(uuid);
        String expected = ShowPropertiesCommand.class.getCanonicalName()
                + "{clientUuid=" + uuid + "}";
        assertEquals(expected, command.toString());
    }
}
