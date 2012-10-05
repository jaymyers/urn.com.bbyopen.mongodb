package com.bbyopen.mongodb;

import java.net.UnknownHostException;

import org.json.simple.JSONValue;
import org.netkernel.layer0.nkf.INKFRequestContext;
import org.netkernel.layer0.nkf.INKFResponse;
import org.netkernel.module.standard.endpoint.StandardAccessorImpl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoDB extends StandardAccessorImpl {
	
	public MongoDB(){
		this.declareThreadSafe();
	}
	
	@Override
	public void onSource(INKFRequestContext context) throws Exception{
		String dbserver = context.source("arg:dbserver", String.class);
		String dbname = context.source("arg:dbname", String.class);
		String collection = context.source("arg:collection", String.class);
		String action = context.source("arg:action", String.class);
		String data = "";
		if(context.getThisRequest().argumentExists("data")){
			data = context.source("arg:data", String.class);
		}
		
		insert(dbserver,dbname,collection,data);
		
		INKFResponse response = context.createResponseFrom("hals");
	}
	
	public void insert(String server, String name, String collection, String thedata){
		Object s = JSONValue.parse(thedata);
		
		Mongo m;
		try {
			m = new Mongo(server);
			DB database = m.getDB(name);
			DBCollection coll = database.getCollection(collection);
			
			BasicDBObject doc = new BasicDBObject();
			doc.put("data",s);
			coll.insert(doc);
			
			m.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public void delete(String server, String name, String collection){
		System.out.println("delete!");
	}
	
	
	
}

