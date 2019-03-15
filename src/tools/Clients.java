package tools;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Clients {

	private SimpleIntegerProperty id_client;
	private SimpleStringProperty name_client;
	private SimpleStringProperty email_client;
	private Date birthday_client;
	
 	public Clients(Integer id_client, String name_client, String email_client, Date birthday_client) {
		this.id_client = new SimpleIntegerProperty(id_client);
		this.name_client = new SimpleStringProperty(name_client);
		this.email_client = new SimpleStringProperty(email_client);
		this.birthday_client = birthday_client;
	}

	public String getName_client() {
		return name_client.get();
	}
	public void setName_client(SimpleStringProperty name_client) {
		this.name_client = name_client;
	}
	public String getEmail_client() {
		return email_client.get();
	}
	public void setEmail_client(SimpleStringProperty email_client) {
		this.email_client = email_client;
	}
	public Date getBirthday_client() {
		return birthday_client;
	}
	public void setBirthday_client(Date birthday_client) {
		this.birthday_client = birthday_client;
	}
	public Integer getId_client() {
		return id_client.get();
	}
	
	public void gravar(){
		List<Object> list = new ArrayList<Object>();
		
		list.add(id_client.get());
		list.add(name_client.get());
		list.add(email_client.get());
		list.add(birthday_client);
		
		ToolsBank.Insert(list);
	}
}
