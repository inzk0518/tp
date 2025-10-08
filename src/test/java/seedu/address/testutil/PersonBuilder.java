package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.BudgetMax;
import seedu.address.model.person.BudgetMin;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonAddress;
import seedu.address.model.person.PersonStatus;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Uuid;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final Integer DEFAULT_UUID = 1;
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BUDGET_MIN = "0";
    public static final String DEFAULT_BUDGET_MAX = "1000";
    public static final String DEFAULT_NOTES = "No notes";
    public static final String DEFAULT_STATUS = "Active";

    private Name name;
    private Phone phone;
    private Email email;
    private PersonAddress address;
    private Set<Tag> tags;
    private BudgetMin budgetMin;
    private BudgetMax budgetMax;
    private Notes notes;
    private PersonStatus status;
    private Uuid uuid;
    private Set<String> buyingPropertyIds;
    private Set<String> sellingPropertyIds;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        uuid = new Uuid(DEFAULT_UUID);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new PersonAddress(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        budgetMin = new BudgetMin(DEFAULT_BUDGET_MIN);
        budgetMax = new BudgetMax(DEFAULT_BUDGET_MAX);
        notes = new Notes(DEFAULT_NOTES);
        status = new PersonStatus(DEFAULT_STATUS);
        buyingPropertyIds = new HashSet<>();
        sellingPropertyIds = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        uuid = personToCopy.getUuid();
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        budgetMin = personToCopy.getBudgetMin();
        budgetMax = personToCopy.getBudgetMax();
        notes = personToCopy.getNotes();
        status = personToCopy.getStatus();
        buyingPropertyIds = new HashSet<>(personToCopy.getBuyingPropertyIds());
        sellingPropertyIds = new HashSet<>(personToCopy.getSellingPropertyIds());
    }

    /**
     * Sets the {@code uuid} of the {@code Person} that we are building.
     */
    public PersonBuilder withUuid(int uuid) {
        this.uuid = new Uuid(uuid);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new PersonAddress(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code BudgetMin} of the {@code Person}.
     */
    public PersonBuilder withBudgetMin(String min) {
        this.budgetMin = new BudgetMin(min);
        return this;
    }

    /**
     * Sets the {@code BudgetMax} of the {@code Person}.
     */
    public PersonBuilder withBudgetMax(String max) {
        this.budgetMax = new BudgetMax(max);
        return this;
    }

    /**
     * Sets the {@code Notes} of the {@code Person}.
     */
    public PersonBuilder withNotes(String notes) {
        this.notes = new Notes(notes);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Person}.
     */
    public PersonBuilder withStatus(String status) {
        this.status = new PersonStatus(status);
        return this;
    }

    /**
     * Parses the {@code ids} into a {@code Set<Index>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withBuyingPropertyIds(String ... ids) {
        this.buyingPropertyIds = Set.of(ids);
        return this;
    }

    /**
     * Parses the {@code ids} into a {@code Set<Index>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withSellingPropertyIds(String ... ids) {
        this.sellingPropertyIds = Set.of(ids);
        return this;
    }


    /**
     * Builds and returns the {@code Person} with the specified fields.
     * A new {@code Uuid} will be generated automatically.
     */
    public Person build() {
        return new Person(
                new Uuid(1), // auto-generate UUID
                name,
                phone,
                email,
                address,
                tags,
                budgetMin,
                budgetMax,
                notes,
                status,
                buyingPropertyIds,
                sellingPropertyIds
        );
    }

}
