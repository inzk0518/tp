package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PropertyBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.property.Property;

/**
 * An Immutable PropertyBook that is serializable to JSON format.
 */
@JsonRootName(value = "propertybook")
class JsonSerializablePropertyBook {

    public static final String MESSAGE_DUPLICATE_PROPERTY = "Properties list contains duplicate property(s).";

    private final List<JsonAdaptedProperty> properties = new ArrayList<>();
    @JsonProperty("nextUuid")
    private final int nextUuid; // stored UUID counter

    /**
     * Constructs a {@code JsonSerializablePropertyBook} with the given properties.
     */
    @JsonCreator
    public JsonSerializablePropertyBook(
            @JsonProperty("properties") List<JsonAdaptedProperty> properties,
            @JsonProperty("nextUuid") int nextUuid) {
        this.properties.addAll(properties);
        this.nextUuid = nextUuid;
    }

    /**
     * Converts a given {@code ReadOnlyPropertyBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePropertyBook}.
     */
    public JsonSerializablePropertyBook(ReadOnlyPropertyBook source) {
        properties.addAll(source.getPropertyList().stream().map(JsonAdaptedProperty::new).collect(Collectors.toList()));
        nextUuid = source.getNextUuid();
    }

    /**
     * Returns the stored UUID counter.
     */
    public int getNextUuid() {
        return nextUuid;
    }

    /**
     * Converts this property book into the model's {@code PropertyBook} object.
     * @return PropertyBook model with properties and nextUuid restored.
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PropertyBook toModelType() throws IllegalValueException {
        PropertyBook propertyBook = new PropertyBook();
        for (JsonAdaptedProperty jsonAdaptedProperty : properties) {
            Property property = jsonAdaptedProperty.toModelType();
            if (propertyBook.hasProperty(property)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROPERTY);
            }
            propertyBook.addProperty(property);
        }
        propertyBook.setNextUuid(this.nextUuid);
        return propertyBook;
    }

}
