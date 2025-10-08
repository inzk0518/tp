package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.LINK_DESC_AMY_BUYER_PROPERTY_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.LINK_DESC_BOB_SELLER_PROPERTY_BETA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LinkCommand.LinkDescriptor;
import seedu.address.testutil.LinkDescriptorBuilder;

public class LinkDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        LinkDescriptor descriptorWithSameValues = new LinkDescriptor(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA);
        assertTrue(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA));

        // null -> returns false
        assertFalse(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(null));

        // different types -> returns false
        assertFalse(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(5));

        // different values -> returns false
        assertFalse(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(LINK_DESC_BOB_SELLER_PROPERTY_BETA));

        // different relationship -> returns false
        LinkDescriptor editedDescriptor = new LinkDescriptorBuilder(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA)
                .withRelationship(LINK_DESC_BOB_SELLER_PROPERTY_BETA.getRelationship()).build();
        assertFalse(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(editedDescriptor));

        // different personId -> returns false
        editedDescriptor = new LinkDescriptorBuilder(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA)
                .withPersonIndex(LINK_DESC_BOB_SELLER_PROPERTY_BETA.getPersonId()).build();
        assertFalse(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(editedDescriptor));

        // different propertyId -> returns false
        editedDescriptor = new LinkDescriptorBuilder(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA)
                .withPropertyIndex(LINK_DESC_BOB_SELLER_PROPERTY_BETA.getPropertyId()).build();
        assertFalse(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(editedDescriptor));
    }

    @Test
    public void toStringMethod() {
        LinkDescriptor descriptor = LINK_DESC_AMY_BUYER_PROPERTY_ALPHA;
        String expectedString = LinkDescriptor.class.getCanonicalName()
                + "{personId=" + descriptor.getPersonId()
                + ", relationship=" + descriptor.getRelationship()
                + ", propertyId=" + descriptor.getPropertyId() + "}";
        assertTrue(descriptor.toString().equals(expectedString));
    }
}
