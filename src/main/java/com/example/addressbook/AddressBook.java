package com.example.addressbook;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class AddressBook {
 private final List<Contact> contacts = new ArrayList<>();
 public void add(Contact contact) {
 if (contact == null) {
 throw new IllegalArgumentException("contact must not be null");
 }
 contacts.add(contact);
 }
 public int size() {
 return contacts.size();
 }
 public List<Contact> getAll() {
 return Collections.unmodifiableList(contacts);
 }
 public Contact findByName(String name) {
 for (Contact c : contacts) {
 if (c.getName().equalsIgnoreCase(name)) {
 return c;
 }
 }
 return null;
 }
}
