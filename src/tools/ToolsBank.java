package tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ToolsBank {
	
	public static void CreateBank() {

		Connection conn = null;
		PreparedStatement comands = null;
		
		try {
			conn = DB.getConnection();
			comands = conn.prepareStatement(""
					+ "create table if not exists Clients("
					+ "ID_Client integer primary key not null,"
					+ "Name_Client varchar(45) not null,"
					+ "Email_Client varchar(75) not null,"
					+ "Date_Birthday date not null,"
					+ "Date_Registration date not null);");
			comands.execute();
			
			comands = conn.prepareStatement(""
					+ "insert into Clients values (1, 'João Cleber', 'joaocleber2811@yahoo.com', '2000-11-28', CURDATE()),"
											   + "(2, 'Carla Alves', 'carlaalvesPed11@gmail.com', '1998-06-12', CURDATE());");
			comands.executeUpdate();
		}
		catch(SQLException ex) {
			
		}
		finally {
			DB.closeConnection();
		}

	}

	public static List<Clients> Reset() {
		Connection conn = null;
		PreparedStatement comands = null;
		
		try {
			conn = DB.getConnection();
			comands = conn.prepareStatement("drop table Clients");	
			comands.execute();
		}
		catch(SQLException ex) {
			
		}
		finally {
			DB.closeConnection();
			CreateBank();
		}
		return Loading2();
	}
	
	public static ObservableList<Clients> Loading1() {
		List<Clients> clients = new ArrayList<>();
		Connection conn = null;
		PreparedStatement comands = null;
		ResultSet table = null;
		
		try {
			conn = DB.getConnection();
			comands = conn.prepareStatement("select * from Clients");
			table = comands.executeQuery();
			
			while(table.next()) {
				clients.add(new Clients(table.getInt(1), table.getString(2), table.getString(3), table.getDate(4)));
			}					
		}
		catch(SQLException ex) {
			System.out.println("ERROR: " + ex);
		}
		finally {
			DB.closeConnection();
		}		
		ObservableList<Clients> list = FXCollections.observableArrayList(clients);
		return list;
	}

	public static List<Clients> Loading2() {
		List<Clients> clients = new ArrayList<>();
		Connection conn = null;
		PreparedStatement comands = null;
		ResultSet table = null;
		
		try {
			conn = DB.getConnection();
			comands = conn.prepareStatement("select * from Clients");
			table = comands.executeQuery();
			
			while(table.next()) {
				clients.add(new Clients(table.getInt(1), table.getString(2), table.getString(3), table.getDate(4)));
			}					
		}
		catch(SQLException ex) {
			
		}
		finally {
			DB.closeConnection();
		}		
		return clients;
	}
	
	public static void Insert(List<Object> parameters) {
		
		Connection conn = null;
		PreparedStatement comands = null;
		
		try {
			conn = DB.getConnection();
			comands = conn.prepareStatement("insert into Clients values (?, ?, ?, ?, CURDATE())");
			
			for(int i = 1; i <= parameters.size(); i++) {
				comands.setObject(i, parameters.get(i - 1));
			}
			
			comands.executeUpdate();
		}
		catch(SQLException ex) {
			
		}
		finally {
			DB.closeConnection();
		}
		
	}

	public static boolean Update(Integer id, Integer collumn, Object obj) {
		boolean res = false;
		Connection conn = null;
		PreparedStatement comands = null;
		
		try {
			conn = DB.getConnection();
			
			if(collumn == 2)
				comands = conn.prepareStatement("update Clients set Name_Client = ? where ID_Client = ?");
			else if(collumn == 3)
				comands = conn.prepareStatement("update Clients set Email_Client = ? where ID_Client = ?");
			else if(collumn == 4)
				comands = conn.prepareStatement("update Clients set Date_Birthday = ? where ID_Client = ?");
			
			comands.setObject(1, obj);
			comands.setObject(2, id);
						
			res = comands.executeUpdate() > 0 ? true : false;
		}
		catch(SQLException ex){
			res = false;
		}
		finally {
			DB.closeConnection();
		}
		
		return res;
	}

	public static boolean Delete(Integer id) {
		boolean rs = false;
		Connection conn = null;
		PreparedStatement comands = null;
		
		try {
			conn = DB.getConnection();
			comands = conn.prepareStatement("delete from Clients where ID_Client = ?");
			comands.setObject(1, id);
			rs = comands.executeUpdate() > 0 ? true : false; 
		}
		catch(SQLException ex) {
			rs = false;
		}
		finally {
			DB.closeConnection();
		}		
		return rs;
	}

	public static ObservableList<Clients> Select(int type1, int type2, int type3, List<Object> query){
		Connection conn = null;
		PreparedStatement comands = null;
		ResultSet table = null;
		
		List<Clients> client = new ArrayList<>();
		
		try {
			conn = DB.getConnection();
			
			
			if(type1 != 0 && type2 != 0 && type3 != 0) 
				comands = conn.prepareStatement("select * from Clients where Name_Client = ? and Email_Client = ? and Date_Birthday = ?");
			else if(type1 != 0 && type2 != 0) 
				comands = conn.prepareStatement("select * from Clients where Name_Client = ? and Email_Client = ?");
			else if(type1 != 0 && type3 != 0) 
				comands = conn.prepareStatement("select * from Clients where Name_Client = ? and Date_Birthday = ?");
			else if(type3 != 0 && type2 != 0) 
				comands = conn.prepareStatement("select * from Clients where Email_Client = ? and Date_Birthday = ?");
			else if(type1 != 0) 
				comands = conn.prepareStatement("select * from Clients where Name_Client = ?");
			else if(type2 != 0) 
				comands = conn.prepareStatement("select * from Clients where Email_Client = ?");
			else if(type3 != 0) 
				comands = conn.prepareStatement("select * from Clients where Date_Birthday = ?");
			
			int num = 1;
			for(Object i : query) {
				if(i != null) {
					comands.setObject(num, i);
					num++;
				}
			}
			
			table = comands.executeQuery();
			
			while(table.next()) {
				client.add(new Clients(table.getInt(1), table.getString(2), table.getString(3), table.getDate(4)));
			}
		}
		catch(SQLException ex) {
			
		}
		finally {
			DB.closeConnection();
		}
		
		return FXCollections.observableArrayList(client);
	}
}
