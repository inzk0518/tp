package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.uuid.Uuid;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Uuid uuid;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final PersonAddress address;
    private final Set<Tag> tags = new HashSet<>();
    private final BudgetMin budgetMin;
    private final BudgetMax budgetMax;
    private final Notes notes;
    private final PersonStatus status;
    private final Set<Uuid> buyingPropertyIds = new HashSet<>();
    private final Set<Uuid> sellingPropertyIds = new HashSet<>();

    /**
     * At least name and phone must not be null.
     */
    public Person(Uuid uuid, Name name, Phone phone, Email email,
                  PersonAddress address, Set<Tag> tags, BudgetMin budgetMin,
                  BudgetMax budgetMax, Notes notes, PersonStatus status,
                  Set<Uuid> buyingPropertyIds, Set<Uuid> sellingPropertyIds) {
        requireAllNonNull(name, phone);
        this.uuid = uuid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.notes = notes;
        this.status = status;
        this.buyingPropertyIds.addAll(buyingPropertyIds);
        this.sellingPropertyIds.addAll(sellingPropertyIds);
    }

    /**
     * Overloaded constructor for missing UUID as UUID will be made in AddCommand
     */
    public Person(Name name, Phone phone, Email email,
                  PersonAddress address, Set<Tag> tags, BudgetMin budgetMin,
                  BudgetMax budgetMax, Notes notes, PersonStatus status,
                  Set<Uuid> buyingPropertyIds, Set<Uuid> sellingPropertyIds) {
        requireAllNonNull(name, phone);
        this.uuid = null;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.notes = notes;
        this.status = status;
        this.buyingPropertyIds.addAll(buyingPropertyIds);
        this.sellingPropertyIds.addAll(sellingPropertyIds);
    }

    public Uuid getUuid() {
        return uuid;
    }

    /**
     * Duplicates Person with the new Uuid.
     * Used for updating Person when adding to addressbook.
     */
    public Person duplicateWithNewUuid(Uuid uuid) {
        return new Person(uuid, name, phone, email, address, tags, budgetMin,
                budgetMax, notes, status, buyingPropertyIds, sellingPropertyIds);
    }

    public BudgetMin getBudgetMin() {
        return budgetMin;
    }

    public BudgetMax getBudgetMax() {
        return budgetMax;
    }

    public Notes getNotes() {
        return notes;
    }

    public PersonStatus getStatus() {
        return status;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public PersonAddress getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable property index set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Uuid> getBuyingPropertyIds() {
        return Collections.unmodifiableSet(buyingPropertyIds);
    }

    /**
     * Returns an immutable property index set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Uuid> getSellingPropertyIds() {
        return Collections.unmodifiableSet(sellingPropertyIds);
    }

    /**
     * Duplicates Person with the new BuyingPropertyIds.
     * Used for updating Person when linking or unlinking.
     */
    public Person duplicateWithNewBuyingPropertyIds(Set<Uuid> buyingPropertyIds) {
        return new Person(uuid, name, phone, email, address, tags, budgetMin,
                budgetMax, notes, status, buyingPropertyIds, sellingPropertyIds);
    }

    /**
     * Duplicates Person with the new SellingPropertyIds.
     * Used for updating Person when linking or unlinking.
     */
    public Person duplicateWithNewSellingPropertyIds(Set<Uuid> sellingPropertyIds) {
        return new Person(uuid, name, phone, email, address, tags, budgetMin,
                budgetMax, notes, status, buyingPropertyIds, sellingPropertyIds);
    }

    /**
     * Returns true if both persons have the same name & same phone number.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return Objects.equals(name.toString().toLowerCase(), otherPerson.name.toString().toLowerCase())
                && Objects.equals(phone, otherPerson.phone);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(uuid, name, phone, email, address, tags,
                budgetMin, budgetMax, notes, status, buyingPropertyIds, sellingPropertyIds);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("uuid", uuid)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("budgetMin", budgetMin)
                .add("budgetMax", budgetMax)
                .add("notes", notes)
                .add("status", status)
                .add("buyingPropertyIds", buyingPropertyIds)
                .add("sellingPropertyIds", sellingPropertyIds)
                .toString();
    }

}
