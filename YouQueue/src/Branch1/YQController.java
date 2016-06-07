package Branch1;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class YQController implements Initializable{

	@FXML
	Button ButtonDownload;
	@FXML
	Button ButtonClearAll;
	@FXML
	Button ButtonClearSel;
	@FXML
	Button ButtonSetFolder;
	@FXML
	ListView<String> ListURL;
	@FXML
	TextField TextFieldURL;
	@FXML
	ChoiceBox<String> ChoiceBoxBrowser; 
	@FXML
	Label OutputLabel;
	
	public static ObservableList<String> oslURLs;
	public static List<String> URLS;
	public static String dlDir = "Libraries/Music";
	public static boolean setOutput = false;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		URLS = new ArrayList<String>();
		oslURLs = FXCollections.observableList(URLS);
		ListURL.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ListURL.setItems(oslURLs);
		
		ButtonDownload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(setOutput){
            	try {
					DownloadDriver.runDownloadQueue(oslURLs, ChoiceBoxBrowser.getSelectionModel().getSelectedItem());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            	}else{
            		JOptionPane.showMessageDialog(null, "You Need to Set an Output Location before Downloading!", "Can not Download", JOptionPane.INFORMATION_MESSAGE); 
            	}
            }
        });
		
		ButtonClearAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	oslURLs.clear();
            }
        });
		
		ButtonClearSel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	ObservableList<String> selected =  ListURL.getSelectionModel().getSelectedItems();
            	oslURLs.removeAll(selected);
            }
        });
		
		ButtonSetFolder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("choosertitle");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                 
              
                } 
                try {
                	if(chooser.getSelectedFile()!=null){
                		dlDir = chooser.getSelectedFile().getCanonicalPath();
                		OutputLabel.setText("Output Location: "+dlDir);
                		setOutput = true;
                	}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		
		TextFieldURL.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
			        @Override
			        public void handle(KeyEvent ke)
			        {
			            if (ke.getCode().equals(KeyCode.ENTER))
			            {
			               //enter pressed in textfield, add to list
			            	String entry = TextFieldURL.getText();
			            	System.out.println("Adding URL: " + entry);
			            	addNewURL(entry);
			            	TextFieldURL.setText("");
			            	
			            }
			        }
	    });
		
		ObservableList<String> bOptions;
		bOptions=FXCollections.observableList(new ArrayList<String>());
		bOptions.add("Chrome");
		bOptions.add("Firefox");
		ChoiceBoxBrowser.setItems(bOptions);
		ChoiceBoxBrowser.getSelectionModel().select(0);
		OutputLabel.setText("Output Location: NOT SET YET!");
	}
	
	public static void addNewURL(String URL){
		oslURLs.add(URL);
	}
	
}
