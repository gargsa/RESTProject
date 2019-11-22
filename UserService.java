package com.garg.sachin;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;
import javax.ws.rs.GET; 
import javax.ws.rs.Path; 
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.json.*;
@Path("/UserService") 

public class UserService {  
   UserDao userDao = new UserDao();  
   @GET 
   @Path("/users") 
   @Produces(MediaType.APPLICATION_JSON) 
   public Response /*List<User>*/ getUsers(){ 
      //return userDao.getAllUsers(); 
            
      ArrayList<User> userList = (ArrayList<User>)userDao.getAllUsers();
      	JsonArrayBuilder jsonArrayBuilder;
      	JsonObjectBuilder jsonBuilder; //, jsonBuilderW;
          JsonObject object, jsonObject = null;
          JsonArray jsonArray = null;
          Response response = null;
          int i=0;
          try {
        	  jsonArrayBuilder = Json.createArrayBuilder();
        	//jsonBuilderW = Json.createObjectBuilder();  //one time 
            for (User user: userList)
            {
            	i++;
            	jsonBuilder = Json.createObjectBuilder(); //for each User object
            	jsonBuilder
            	.add("Id",user.getId())
            	.add("Name", user.getName())
            	.add("Profession", user.getProfession());
            	jsonObject = jsonBuilder.build(); // build a json object for each user object
            	//jsonBuilderW.add("User"+i, jsonObject);
            	jsonArrayBuilder.add(jsonObject);
            }
            //object = jsonBuilderW.build(); // build final json object
            jsonArray = jsonArrayBuilder.build();
            //response = Response.status(Status.OK).entity(object.toString()).build();
            response = Response.status(Status.OK).entity(jsonArray.toString()).build();
          } catch (Exception e) {
            System.out.println("error=" + e.getMessage());
          }
          return response;
           
   }  
}
