package seedu.address.model.person;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests whether a {@code Person} matches the filtering criteria given.
 * <p>
 * Each field (name, phone, email, etc.) is optional. If a field is present, at least one
 * of its keywords must match the corresponding {@code Person} field (case-insensitive, substring match).
 * Budget range (min/max) is inclusive.
 */
public class FilterContactPredicate implements Predicate<Person> {
    private final Optional<List<String>> names;
    private final Optional<List<String>> phones;
    private final Optional<List<String>> emails;
    private final Optional<List<String>> addresses;
    private final Optional<List<String>> tags;
    private final Optional<Integer> budgetMin;
    private final Optional<Integer> budgetMax;
    private final Optional<List<String>> notes;
    private final Optional<List<String>> status;

    /**
     * Creates a {@code FilterContactPredicate} with optional filtering fields.
     * Any empty field means "no restriction" for that field.
     */
    public FilterContactPredicate(Optional<List<String>> names,
                                  Optional<List<String>> phones,
                                  Optional<List<String>> emails,
                                  Optional<List<String>> addresses,
                                  Optional<List<String>> tags,
                                  Optional<Integer> budgetMin,
                                  Optional<Integer> budgetMax,
                                  Optional<List<String>> notes,
                                  Optional<List<String>> status) {
        this.names = names;
        this.phones = phones;
        this.emails = emails;
        this.addresses = addresses;
        this.tags = tags;
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.notes = notes;
        this.status = status;
    }

    @Override
    public boolean test(Person person) {
        return names.map(list ->
                        list.stream().anyMatch(k ->
                                StringUtil.containsSubstringIgnoreCase(person.getName().fullName, k)))
                .orElse(true)

                && phones.map(list ->
                        list.stream().anyMatch(k ->
                                StringUtil.containsSubstringIgnoreCase(person.getPhone().value, k)))
                .orElse(true)

                && emails.map(list ->
                        list.stream().anyMatch(k ->
                                StringUtil.containsSubstringIgnoreCase(person.getEmail().value, k)))
                .orElse(true)

                && addresses.map(list ->
                        list.stream().anyMatch(k ->
                                StringUtil.containsSubstringIgnoreCase(person.getAddress().value, k)))
                .orElse(true)

                && tags.map(list ->
                        person.getTags().stream().anyMatch(tag ->
                                list.stream().anyMatch(k ->
                                        StringUtil.containsSubstringIgnoreCase(tag.tagName, k))))
                .orElse(true)

                && budgetMin.map(min -> Integer.parseInt(person.getBudgetMin().value) <= min).orElse(true)

                && budgetMax.map(max -> Integer.parseInt(person.getBudgetMax().value) >= max).orElse(true)

                && notes.map(list ->
                        list.stream().anyMatch(k ->
                                StringUtil.containsSubstringIgnoreCase(person.getNotes().value, k)))
                .orElse(true)

                && status.map(list ->
                        list.stream().anyMatch(k ->
                                StringUtil.containsSubstringIgnoreCase(person.getStatus().value, k)))
                .orElse(true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FilterContactPredicate
                && names.equals(((FilterContactPredicate) other).names)
                && phones.equals(((FilterContactPredicate) other).phones)
                && emails.equals(((FilterContactPredicate) other).emails)
                && addresses.equals(((FilterContactPredicate) other).addresses)
                && tags.equals(((FilterContactPredicate) other).tags)
                && budgetMin.equals(((FilterContactPredicate) other).budgetMin)
                && budgetMax.equals(((FilterContactPredicate) other).budgetMax)
                && notes.equals(((FilterContactPredicate) other).notes)
                && status.equals(((FilterContactPredicate) other).status));
    }
}
