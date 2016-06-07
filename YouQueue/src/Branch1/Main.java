package Branch1;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
		if(DownloadDriver.driver != null){
			DownloadDriver.driver.quit();
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		File mainGUI = new File("./YQ_GUI.fxml");
    	Pane page = (Pane) FXMLLoader.load(mainGUI.toURI().toURL());
    	Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.setTitle("YouQueue");
        stage.setResizable(false);
        stage.show();
        System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	}

}
