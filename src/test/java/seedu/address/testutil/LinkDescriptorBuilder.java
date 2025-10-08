package seedu.address.testutil;

import seedu.address.logic.commands.LinkCommand.LinkDescriptor;
import seedu.address.model.person.Uuid;

/**
 * A utility class to help with building LinkDescriptor objects.
 */
public class LinkDescriptorBuilder {

    private LinkDescriptor descriptor;

    public LinkDescriptorBuilder() {
        descriptor = new LinkDescriptor();
    }

    /**
     * Returns an {@code LinkDescriptor} with fields containing {@code descriptor}'s details
     */
    public LinkDescriptorBuilder(LinkDescriptor descriptor) {
        this.descriptor = new LinkDescriptor(descriptor);
    }

    /**
     * Sets the {@code personId} of the {@code LinkDescriptor} that we are building.
     */
    public LinkDescriptorBuilder withPersonIndex(Uuid personId) {
        descriptor.setPersonId(personId);
        return this;
    }

    /**
     * Sets the {@code propertyId} of the {@code LinkDescriptor} that we are building.
     */
    public LinkDescriptorBuilder withPropertyIndex(String propertyId) {
        descriptor.setPropertyId(propertyId);
        return this;
    }

    /**
     * Sets the {@code relationship} of the {@code LinkDescriptor} that we are building.
     */
    public LinkDescriptorBuilder withRelationship(String relationship) {
        descriptor.setRelationship(relationship);
        return this;
    }

    /**
     * Builds the LinkDescriptor object.
     */
    public LinkDescriptor build() {
        return descriptor;
    }
}
