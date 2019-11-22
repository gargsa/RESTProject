package com.jersey.client;

import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class RestfulJerseyClient {

	private static final String webServiceURI = "http://localhost:8080/UserManagement";

	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		URI serviceURI = UriBuilder.fromUri(webServiceURI).build();
		WebTarget webTarget = client.target(serviceURI);

		// response
		//System.out.println(webTarget.path("rest").path("UserService/users").request()
		//		.accept(MediaType.TEXT_PLAIN).get(Response.class).toString());

		// text
		//System.out.println(webTarget.path("rest").path("UserService/users").request()
		//		.accept(MediaType.TEXT_PLAIN).get(String.class));

		// xml - uncomment the below code to retrieve the response as array list
		//ArrayList<User> userList = webTarget.path("rest").path("UserService/users").request()
			//	.accept(MediaType.APPLICATION_XML).get(new GenericType<ArrayList<User>>() {});
		//json - below code not working. we need to read json response as String.class as further below
		//Caused by: org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException: 
		//MessageBodyReader not found for media type=application/json, type=class java.util.ArrayList, 
		//genericType=java.util.ArrayList<com.garg.sachin.User>.
		//ArrayList<User> userList = webTarget.path("rest").path("UserService/users").request()
				//.accept(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<User>>() {});
		//uncomment the below code also when you uncomment the above xml code
		//ArrayList<User> userList = new ArrayList<User>();		
		//for (User user: userList)
		//{
		//	System.out.println(user.getId() + " " + user.getName() + " " + user.getProfession());
		//}		
		// This works as we are receiving response as json response as string
		String jsonResponse = webTarget.path("rest").path("UserService/users").request()
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		//System.out.println(jsonResponse);
		
		StringReader reader = new StringReader(jsonResponse);
		JsonParser parser = Json.createParser(reader);	
		JsonParser.Event evt = parser.next();
		//System.out.println("first=" + evt);
		while(evt != JsonParser.Event.END_ARRAY)
		{

			evt = parser.next();
			//System.out.println(evt);
			if (evt == JsonParser.Event.KEY_NAME)
			{
				evt = parser.next();
				System.out.print(" " + parser.getString());
			}
			if (evt == JsonParser.Event.START_OBJECT)
				System.out.println("");			
			
		}
		//System.out.println(webTarget.path("rest").path("UserService/users").request()
			//	.accept(MediaType.APPLICATION_XML).get(String.class));

		// html
		//System.out.println(webTarget.path("rest").path("UserService/users").request()
		//		.accept(MediaType.TEXT_HTML).get(String.class));
	}
}
