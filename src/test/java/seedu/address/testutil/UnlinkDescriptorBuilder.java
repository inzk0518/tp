package seedu.address.testutil;

import java.util.Set;

import seedu.address.logic.commands.UnlinkCommand.UnlinkDescriptor;
import seedu.address.model.uuid.Uuid;

/**
 * A utility class to help with building UnlinkDescriptor objects.
 */
public class UnlinkDescriptorBuilder {

    private final UnlinkDescriptor descriptor;

    public UnlinkDescriptorBuilder() {
        descriptor = new UnlinkDescriptor();
    }

    /**
     * Returns an {@code UnlinkDescriptor} with fields containing {@code descriptor}'s details
     */
    public UnlinkDescriptorBuilder(UnlinkDescriptor descriptor) {
        this.descriptor = new UnlinkDescriptor(descriptor);
    }

    /**
     * Sets the {@code contactIds} of the {@code UnlinkDescriptor} that we are building.
     */
    public UnlinkDescriptorBuilder withContactIds(Set<Uuid> contactIds) {
        descriptor.setContactIds(contactIds);
        return this;
    }

    /**
     * Sets the {@code propertyId} of the {@code UnlinkDescriptor} that we are building.
     */
    public UnlinkDescriptorBuilder withPropertyIds(Set<Uuid> propertyIds) {
        descriptor.setPropertyIds(propertyIds);
        return this;
    }

    /**
     * Builds the UnlinkDescriptor object.
     */
    public UnlinkDescriptor build() {
        return descriptor;
    }
}
