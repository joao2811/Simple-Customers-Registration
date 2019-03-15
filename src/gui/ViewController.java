package gui;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tools.Clients;
import tools.ToolsBank;

public class ViewController implements Initializable{

	//================== Declarations ======================//
	private List<Clients> clients;
	
	@FXML
	private TextField tbx1, tbx2, tbx3, tbx4, tbx5, tbx6;
	
	@FXML
	private DatePicker tbx_birt1, tbx_birt2, tbx_birt3;
	
	@FXML
	private Button bt1, bt2, bt3, bt4, bt5;
	
	@FXML
	private TableView<Clients> table1, table2, table3;
	
	@FXML
	private ImageView img1, img2, img3, img4;
	
	@FXML
	private TableColumn<Clients, Integer> c1, c5, c9;
	@FXML
	private TableColumn<Clients, String> c2, c3, c6, c7, c10, c11;
	@FXML
	private TableColumn<Clients, Date> c4, c8, c12;
	
	@FXML
	private Spinner<Integer> spinner1, spinner2;
	//===================================================//
	
	//====================== Buttons =======================//
	
	@FXML
	public void onBt1Action() throws ParseException {
		if(tbx1.getText().equals("") || tbx2.getText().equals("") || tbx_birt1.getValue().toString().equals("")) {
			JOptionPane.showMessageDialog(null, "Please Complete All Fields Correctly!!!","Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int id = 0;
		for(Clients i : clients) {
			if(i.getId_client() == clients.size()) {
				id = clients.size() + 1;
			}
		}
		id = id == 0 ? clients.size() : id;
		clients.add(new Clients(id, tbx1.getText(), tbx2.getText(), Date(tbx_birt1)));
		clients.get(clients.size() - 1).gravar();
		
		LoadTables();
		
		JOptionPane.showMessageDialog(null, "Successful Customer!!!", "Success", JOptionPane.INFORMATION_MESSAGE);
		
		tbx1.setText("");
		tbx2.setText("");
		tbx_birt1.setValue(null);
	}

	@FXML
	public void onBt2Action() throws ParseException {
		boolean res = false;
		if(tbx3.getText().equals("") && tbx4.getText().equals("") && tbx_birt2.getValue() == null) {
			JOptionPane.showMessageDialog(null, "Please Complete All Fields Correctly!!!","Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(tbx3.getText().equals("") == false) {
			res = ToolsBank.Update(spinner2.getValue(), 2, tbx3.getText());
		}
		if(tbx4.getText().equals("") == false) {
			res = ToolsBank.Update(spinner2.getValue(), 3, tbx4.getText());
		}
		if(tbx_birt2.getValue() != null) {			
			res = ToolsBank.Update(spinner2.getValue(), 4, Date(tbx_birt2));
		}
		
		if(res == true) {
			clients = ToolsBank.Loading2();
			LoadTables();
			
			tbx3.setText("");
			tbx4.setText("");
			tbx_birt2.setValue(null);
			JOptionPane.showMessageDialog(null, "Data Update Successfully!!!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@FXML
	public void onBt3Action() {
		if(JOptionPane.showConfirmDialog(null, "Do you really want to delete this client's information?","Delete",JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
			boolean res = ToolsBank.Delete(spinner2.getValue());
		
			if(res == true) {
				JOptionPane.showMessageDialog(null, "Information deleted successfully!!!","Success",JOptionPane.INFORMATION_MESSAGE);
				clients = ToolsBank.Loading2();
				LoadTables();
			}
			else {
				JOptionPane.showMessageDialog(null, "Error deleting client infomation!!!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	@FXML
	public void onBt4Action() throws ParseException {
		
		List<Object> ls = new ArrayList<>();
		
		if(tbx5.getText().equals("") && tbx6.getText().equals("") && tbx_birt3.getValue() == null) {
			JOptionPane.showMessageDialog(null, "Complete all the fields correctly!!!","Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		ls.add(tbx5.getText().equals("") == false ? tbx5.getText() : null);
		ls.add(tbx6.getText().equals("") == false ? tbx6.getText() : null);
		ls.add(tbx_birt3.getValue() != null ? Date(tbx_birt3) : null);
		
		table3.setItems(ToolsBank.Select((tbx5.getText().equals("") == false ? 1 : 0), 
						 				 (tbx6.getText().equals("") == false ? 1 : 0),
						 				 (tbx_birt3.getValue() != null ? 1 : 0), ls));
		
		JOptionPane.showMessageDialog(null, "Search Executed Successfully!!!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	@FXML
	public void onBt5Action() {
		if(JOptionPane.showConfirmDialog(null, "Do you really want to reset?\n"
		+ "Will anu data already added be delected?", "Reset", JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
			clients = ToolsBank.Reset();
			JOptionPane.showMessageDialog(null, "Reset completed successfully!!!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
			LoadTables();
		}
	}
	
	
	//================== Metodos Initialize ======================//
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Standards();
		//===============//		
		ToolsBank.CreateBank();
		clients = ToolsBank.Loading2();
		//===============//
		LoadTables();
		//===============//
		
	}
	
	public Image Img(String caminho) {
		File file = new File(caminho);
		return new Image(file.toURI().toString());
	}

	public void Standards() {
		SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100000, 1);
		SpinnerValueFactory<Integer> value2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100000, 1);
		spinner1.setValueFactory(value);
		spinner2.setValueFactory(value2);
		
		String font = "-fx-font-family: 'News706 BT'";
		
		spinner1.setStyle(font);
		spinner2.setStyle(font);
		
		tbx_birt1.setStyle(font);
		tbx_birt2.setStyle(font);
		tbx_birt3.setStyle(font);

		
		img1.setImage(Img("src/gui/logo.png"));
		img2.setImage(Img("src/gui/ficha.png"));
		img3.setImage(Img("src/gui/loading.png"));
		img4.setImage(Img("src/gui/delet.png"));
		
		tbx1.setText("Cintia Santos");
		tbx2.setText("cintiaasantos@gmail.com");
	}

	public void LoadTables() {
		
		table1.getColumns().forEach(X -> X.setStyle("-fx-font-family: 'News706 BT';"));
		table2.getColumns().forEach(X -> X.setStyle("-fx-font-family: 'News706 BT';"));
		table3.getColumns().forEach(X -> X.setStyle("-fx-font-family: 'News706 BT';"));		
		
		c1.setCellValueFactory(new PropertyValueFactory<>("id_client"));
		c2.setCellValueFactory(new PropertyValueFactory<>("name_client"));
		c3.setCellValueFactory(new PropertyValueFactory<>("email_client"));
		c4.setCellValueFactory(new PropertyValueFactory<>("birthday_client"));
		
		c5.setCellValueFactory(new PropertyValueFactory<>("id_client"));
		c6.setCellValueFactory(new PropertyValueFactory<>("name_client"));
		c7.setCellValueFactory(new PropertyValueFactory<>("email_client"));
		c8.setCellValueFactory(new PropertyValueFactory<>("birthday_client"));

		c9.setCellValueFactory(new PropertyValueFactory<>("id_client"));
		c10.setCellValueFactory(new PropertyValueFactory<>("name_client"));
		c11.setCellValueFactory(new PropertyValueFactory<>("email_client"));
		c12.setCellValueFactory(new PropertyValueFactory<>("birthday_client"));
		
		table1.setItems(ToolsBank.Loading1());
		table2.setItems(ToolsBank.Loading1());
		table3.setItems(ToolsBank.Loading1());
	}

	public java.sql.Date Date(DatePicker pick) throws ParseException {
		int dia = pick.getValue().getDayOfMonth();
		int mes = pick.getValue().getMonthValue();
		int ano = pick.getValue().getYear();
		String dat = (dia < 10 ? "0" + dia : dia) + "/" + (mes < 10 ? "0" + mes : mes) + "/" + ano;
						
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
		Date date = form.parse(dat);
		java.sql.Date date2 = new java.sql.Date(date.getTime());
		
		return date2;
	}
}
