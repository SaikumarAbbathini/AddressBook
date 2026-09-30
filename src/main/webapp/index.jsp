<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.addressbook.*" %>
<%
 AddressBook book = new AddressBook();
 book.add(new Contact("Asha Rao", "+91-90000-00001", "asha@example.com"));
 book.add(new Contact("Vikram Shah", "+91-90000-00002",
"vikram@example.com"));
 book.add(new Contact("Meera Iyer", "+91-90000-00003", "meera@example.com"));
 String host = "unknown";
 try {
 host = java.net.InetAddress.getLocalHost().getHostName();
 } catch (Exception e) {
 host = "unknown";
 }
%>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
 <title>AddressBook</title>
 <style>
 body { font-family: Arial, sans-serif; margin: 40px; }
 table { border-collapse: collapse; }
 th, td { border: 1px solid #999; padding: 8px 14px; text-align: left; }
 th { background: #eee; }
 .meta { color: #555; margin-top: 20px; }
 </style>
</head>
<body>
 <h1>AddressBook - Version 1</h1>
 <p>Contacts stored: <%= book.size() %></p>
 <table>
 <tr><th>Name</th><th>Phone</th><th>Email</th></tr>
 <% for (Contact c : book.getAll()) { %>
 <tr>
 <td><%= c.getName() %></td>
 <td><%= c.getPhone() %></td>
 <td><%= c.getEmail() %></td>
 </tr>
 <% } %>
 </table>
 <p class="meta">Served by host: <%= host %></p>
</body>
</html>
