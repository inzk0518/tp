package seedu.address.model.person.predicates;

import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Checks if a person matches all filters (case-insensitive substring matches).
 */
public class PersonMatchesFilterPredicate implements Predicate<Person> {

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String tag;
    private final String status;
    private final String notes;
    private final String budgetMin;
    private final String budgetMax;

    public PersonMatchesFilterPredicate(String name, String phone, String email, String address,
                                        String tag, String status, String notes,
                                        String budgetMin, String budgetMax) {
        this.name = toLower(name);
        this.phone = toLower(phone);
        this.email = toLower(email);
        this.address = toLower(address);
        this.tag = toLower(tag);
        this.status = toLower(status);
        this.notes = toLower(notes);
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
    }

    private String toLower(String s) {
        return s == null ? null : s.trim().toLowerCase();
    }

    @Override
    public boolean test(Person p) {
        // String-based field matching
        if (name != null && !p.getName().toString().toLowerCase().contains(name)) return false;
        if (phone != null && !p.getPhone().toString().toLowerCase().contains(phone)) return false;
        if (email != null && !p.getEmail().toString().toLowerCase().contains(email)) return false;
        if (address != null && !p.getAddress().toString().toLowerCase().contains(address)) return false;
        if (notes != null && !p.getNotes().toString().toLowerCase().contains(notes)) return false;
        if (status != null) {
            String personStatus = p.getStatus().toString().toLowerCase();
            if (!personStatus.equals(status)) return false;
        }
        if (tag != null && !matchesTag(p.getTags(), tag)) return false;

        // Numeric comparison using integer values
        if (budgetMin != null && budgetMax != null) {
            try {
                int personMin = Integer.parseInt(p.getBudgetMin().value);
                int personMax = Integer.parseInt(p.getBudgetMax().value);
                int filterMin = Integer.parseInt(budgetMin);
                int filterMax = Integer.parseInt(budgetMax);

                // Require true overlap (not just touching edges)
                boolean overlaps = personMax > filterMin && personMin < filterMax;
                if (!overlaps) return false;
            } catch (NumberFormatException e) {
                // ignore invalid numbers
            }
        } else if (budgetMin != null) {
            try {
                int personMax = Integer.parseInt(p.getBudgetMax().value);
                int filterMin = Integer.parseInt(budgetMin);
                if (personMax <= filterMin) return false;
            } catch (NumberFormatException e) {
                // ignore invalid numbers
            }
        } else if (budgetMax != null) {
            try {
                int personMin = Integer.parseInt(p.getBudgetMin().value);
                int filterMax = Integer.parseInt(budgetMax);
                if (personMin >= filterMax) return false;
            } catch (NumberFormatException e) {
                // ignore invalid numbers
            }
        }

        return true;
    }

    private boolean matchesTag(Set<Tag> tags, String tag) {
        return tags.stream().anyMatch(t -> t.tagName.toLowerCase().contains(tag));
    }

    /** Simple builder pattern for easy construction */
    public static class Builder {
        private String name, phone, email, address, tag, status, notes;
        private String budgetMin, budgetMax;

        public Builder withName(String s) { this.name = s; return this; }
        public Builder withPhone(String s) { this.phone = s; return this; }
        public Builder withEmail(String s) { this.email = s; return this; }
        public Builder withAddress(String s) { this.address = s; return this; }
        public Builder withTag(String s) { this.tag = s; return this; }
        public Builder withStatus(String s) { this.status = s; return this; }
        public Builder withNotes(String s) { this.notes = s; return this; }
        public Builder withBudgetMin(String s) { this.budgetMin = s; return this; }
        public Builder withBudgetMax(String s) { this.budgetMax = s; return this; }

        public PersonMatchesFilterPredicate build() {
            return new PersonMatchesFilterPredicate(name, phone, email, address, tag, status, notes, budgetMin, budgetMax);
        }
    }
}