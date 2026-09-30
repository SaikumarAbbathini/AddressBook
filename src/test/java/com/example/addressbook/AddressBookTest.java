package com.example.addressbook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
class AddressBookTest {
 @Test
 void newAddressBookIsEmpty() {
 assertEquals(0, new AddressBook().size());
 }
 @Test
 void addingContactIncreasesSize() {
 AddressBook book = new AddressBook();
 book.add(new Contact("Asha Rao", "+91-90000-00001",
"asha@example.com"));
 assertEquals(1, book.size());
 }
 @Test
 void findByNameIgnoresCase() {
 AddressBook book = new AddressBook();
 book.add(new Contact("Asha Rao", "+91-90000-00001",
"asha@example.com"));
 assertNotNull(book.findByName("asha rao"));
 }
 @Test
 void findUnknownNameReturnsNull() {
 assertNull(new AddressBook().findByName("Nobody"));
 }
 @Test
 void nullContactIsRejected() {
 assertThrows(IllegalArgumentException.class, () -> new
AddressBook().add(null));
 }
}
