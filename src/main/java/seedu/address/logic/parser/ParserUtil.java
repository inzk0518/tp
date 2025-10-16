package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.uuid.Uuid.StoredItem.PERSON;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.BudgetMax;
import seedu.address.model.person.BudgetMin;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.PersonAddress;
import seedu.address.model.person.PersonStatus;
import seedu.address.model.person.Phone;
import seedu.address.model.property.Bathroom;
import seedu.address.model.property.Bedroom;
import seedu.address.model.property.FloorArea;
import seedu.address.model.property.Listing;
import seedu.address.model.property.Owner;
import seedu.address.model.property.Postal;
import seedu.address.model.property.Price;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.Status;
import seedu.address.model.property.Type;
import seedu.address.model.tag.Tag;
import seedu.address.model.uuid.Uuid;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_UUID = "UUID is not a valid format.";
    public static final String MESSAGE_INVALID_PROPERTY_ID = "Property ID must be 6 alphanumeric characters.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code personId} into a {@code Uuid} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified personId is invalid (not non-zero unsigned integer).
     */
    public static Uuid parsePersonId(String personId) throws ParseException {
        requireNonNull(personId);
        String trimmedpersonId = personId.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedpersonId)) {
            throw new ParseException(MESSAGE_INVALID_UUID);
        }
        return new Uuid(Integer.parseInt(trimmedpersonId), PERSON);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Uuid>},
     * both representing a collection of person ids, and returns it.
     */
    public static Set<Uuid> parsePersonIds(Collection<String> personIds) throws ParseException {
        requireNonNull(personIds);
        final Set<Uuid> personIdsSet = new HashSet<>();
        for (String personId : personIds) {
            personIdsSet.add(parsePersonId(personId));
        }
        return personIdsSet;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Returns a non-null string value by replacing {@code null} inputs with {@code defaultValue}.
     * <p>
     * This method helps simplify null checks when parsing optional user inputs.
     *
     * @param value the input string, which may be {@code null}
     * @param defaultValue the fallback string to use if {@code value} is {@code null}
     * @return {@code value} if non-null, otherwise {@code defaultValue}
     */
    private static String sanitiseNull(String value, String defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static PersonAddress parseAddress(String address) throws ParseException {
        String trimmedAddress = sanitiseNull(address, "").trim();
        if (!PersonAddress.isValidAddress(trimmedAddress)) {
            throw new ParseException(PersonAddress.MESSAGE_CONSTRAINTS);
        }
        return new PersonAddress(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        String trimmedEmail = sanitiseNull(email, "").trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
    /**
     * Parses a {@code String budgetMin} into a {@code BudgetMin}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code budgetMin} is not a valid integer or violates constraints.
     */
    public static BudgetMin parseBudgetMin(String budgetMin) throws ParseException {
        String trimmedBudgetMin = sanitiseNull(budgetMin, "0").trim();

        if (!BudgetMin.isValidBudgetMin(trimmedBudgetMin)) {
            throw new ParseException(BudgetMin.MESSAGE_CONSTRAINTS);
        }
        return new BudgetMin(trimmedBudgetMin);
    }

    /**
     * Parses a {@code String budgetMax} into a {@code BudgetMax}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code budgetMax} is not a valid integer or violates constraints.
     */
    public static BudgetMax parseBudgetMax(String budgetMax) throws ParseException {
        String trimmedBudgetMax = sanitiseNull(budgetMax, String.valueOf(200_000_000_000L)).trim();

        if (!BudgetMax.isValidBudgetMax(trimmedBudgetMax)) {
            throw new ParseException(BudgetMax.MESSAGE_CONSTRAINTS);
        }
        return new BudgetMax(trimmedBudgetMax);
    }

    /**
     * Parses a {@code String notes} into a {@code Notes}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code notes} is invalid (e.g., null).
     */
    public static Notes parseNotes(String notes) throws ParseException {
        String trimmedNotes = sanitiseNull(notes, "").trim();
        return new Notes(trimmedNotes); //TODO add exception?
    }

    /**
     * Parses a {@code String status} into a {@code PersonStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid (fails {@link PersonStatus#isValidStatus}).
     */
    public static PersonStatus parsePersonStatus(String status) throws ParseException {
        String trimmedStatus = sanitiseNull(status, "").trim();
        if (!PersonStatus.isValidStatus(trimmedStatus)) {
            throw new ParseException(PersonStatus.MESSAGE_CONSTRAINTS);
        }
        return new PersonStatus(trimmedStatus);
    }
    // ================ Property parsing methods ================

    /**
     * Parses a {@code String address} into a property {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static PropertyAddress parsePropertyAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!PropertyAddress.isValidPropertyAddress(trimmedAddress)) {
            throw new ParseException(PropertyAddress.MESSAGE_CONSTRAINTS);
        }
        return new PropertyAddress(trimmedAddress);
    }

    /**
     * Parses {@code propertyId} into a {@code Uuid} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified propertyId is invalid (not non-zero unsigned integer).
     */
    public static Uuid parsePropertyId(String propertyId) throws ParseException {
        requireNonNull(propertyId);
        String trimmedpropertyId = propertyId.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedpropertyId)) {
            throw new ParseException(MESSAGE_INVALID_UUID);
        }
        return new Uuid(Integer.parseInt(trimmedpropertyId), PROPERTY);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Uuid>},
     * both representing a collection of property ids, and returns it.
     */
    public static Set<Uuid> parsePropertyIds(Collection<String> propertyIds) throws ParseException {
        requireNonNull(propertyIds);
        final Set<Uuid> propertyIdsSet = new HashSet<>();
        for (String propertyId : propertyIds) {
            propertyIdsSet.add(parsePropertyId(propertyId));
        }
        return propertyIdsSet;
    }

    /**
     * Parses a {@code String postal} into a {@code Postal}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code postal} is invalid.
     */
    public static Postal parsePostal(String postal) throws ParseException {
        requireNonNull(postal);
        String trimmedPostal = postal.trim();
        if (!Postal.isValidPostal(trimmedPostal)) {
            throw new ParseException(Postal.MESSAGE_CONSTRAINTS);
        }
        return new Postal(trimmedPostal);
    }

    /**
     * Parses a {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
    }

    /**
     * Parses a {@code String type} into a {@code Type}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code type} is invalid.
     */
    public static Type parseType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();
        if (!Type.isValidType(trimmedType)) {
            throw new ParseException(Type.MESSAGE_CONSTRAINTS);
        }
        return new Type(trimmedType);
    }

    /**
     * Parses a {@code String status} into a {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(trimmedStatus);
    }

    /**
     * Parses a {@code String bedroom} into a {@code Bedroom}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code bedroom} is invalid.
     */
    public static Bedroom parseBedroom(String bedroom) throws ParseException {
        requireNonNull(bedroom);
        String trimmedBedroom = bedroom.trim();
        if (!Bedroom.isValidBedroom(trimmedBedroom)) {
            throw new ParseException(Bedroom.MESSAGE_CONSTRAINTS);
        }
        return new Bedroom(trimmedBedroom);
    }

    /**
     * Parses a {@code String bathroom} into a {@code Bathroom}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code bathroom} is invalid.
     */
    public static Bathroom parseBathroom(String bathroom) throws ParseException {
        requireNonNull(bathroom);
        String trimmedBathroom = bathroom.trim();
        if (!Bathroom.isValidBathroom(trimmedBathroom)) {
            throw new ParseException(Bathroom.MESSAGE_CONSTRAINTS);
        }
        return new Bathroom(trimmedBathroom);
    }

    /**
     * Parses a {@code String floorArea} into a {@code FloorArea}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code floorArea} is invalid.
     */
    public static FloorArea parseFloorArea(String floorArea) throws ParseException {
        requireNonNull(floorArea);
        String trimmedFloorArea = floorArea.trim();
        if (!FloorArea.isValidFloorArea(trimmedFloorArea)) {
            throw new ParseException(FloorArea.MESSAGE_CONSTRAINTS);
        }
        return new FloorArea(trimmedFloorArea);
    }

    /**
     * Parses a {@code String listing} into a {@code Listing}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code listing} is invalid.
     */
    public static Listing parseListing(String listing) throws ParseException {
        requireNonNull(listing);
        String trimmedListing = listing.trim();
        if (!Listing.isValidListing(trimmedListing)) {
            throw new ParseException(Listing.MESSAGE_CONSTRAINTS);
        }
        return new Listing(trimmedListing);
    }

    /**
     * Parses a {@code String owner} into a {@code Owner}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code owner} is invalid.
     */
    public static Owner parseOwner(String owner) throws ParseException {
        requireNonNull(owner);
        String trimmedOwner = owner.trim();
        if (!Owner.isValidOwner(trimmedOwner)) {
            throw new ParseException(Owner.MESSAGE_CONSTRAINTS);
        }
        return new Owner(trimmedOwner);
    }
}
