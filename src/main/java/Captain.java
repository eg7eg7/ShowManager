import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage; 

public class Captain extends Application {
	
	
	public static final String PROJECT_NAME = "Super Bakara";
	final static String dir = System.getProperty("user.dir");
	private static int CHOSEN_SHEET = 8;
	private static int KSO_MAX_HALL = 18;
	private static final boolean showLog = false;
	private static final boolean printLog = true;
	public final static String globeFile = "Autoglobe.dat";
	public static final String MOVIE = "Movie";
	public static final String EVENT = "Event";
	public static final String NO_BREAK = "No intermission";
	public static String log_name = "AutoGlobe.log";
	public static File f = new File(log_name);
	public static BufferedWriter bw = null;
	public static FileWriter fw = null;
	public static Database db;
	
	
	public static void main(String[] args) {
		db = new Database();
		setLog();
		System.out.println(db.getProgrammingList().size());
		//DummyAdd();
		launch(args);
		closeLog();
	}

	private static void openGui(Stage stage) {

        BorderPane borderPane = new BorderPane();
        StackPane actions = new StackPane();
        Scene scene = new Scene(borderPane, 500, 500);
        FileChooser chooser = new FileChooser();
        chooser.setTitle(PROJECT_NAME);
        File defaultDirectory = new File(dir);
        chooser.setInitialDirectory(defaultDirectory);
        chooser.getExtensionFilters().add(new ExtensionFilter("Globus Format Excel File", "*.xls"));
        
        Button browseBtn = new Button("Browse...");
        browseBtn.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent event) {
				File selected = chooser.showOpenDialog(stage);
				if(selected != null)
					db.addProgramming(selected);

				
			}
        });
        actions.getChildren().add(browseBtn);
        
        borderPane.setBottom(actions);
        stage.setTitle(PROJECT_NAME);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler() {

			@Override
			public void handle(Event event) {
				//TODO save data
				try {
					db.saveData();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
        	
        });
        stage.show();
	}

	private static String DummyAdd() {


		try {
			db.addProgramming(new Programming("01.xls"));
			db.addProgramming(new Programming("02.xls"));
			db.addProgramming(new Programming("03.xls"));
			db.addProgramming(new Programming("04.xls"));
			db.addProgramming(new Programming("05.xls"));
			db.addProgramming(new Programming("06.xls"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.showExpiredMovies(1);
		db.showExpiredMovies(2);
		db.showExpiredMovies(3);
		db.showExpiredMovies(4);
		db.showExpiredMovies(5);
		String s = db.showExpiredMovies(6);
		return s;
	}

	private static void closeLog() {
		try {
			if (bw != null)
				bw.close();
			if (fw != null)
				fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void setLog() {
		try {
			if (f.exists())
				f.delete();
			f.createNewFile();
			fw = new FileWriter(log_name, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);
	}

	public static void log(String string) {

		if (showLog)
			System.out.println(string);
		if (printLog) {
			try {
				writeLog(string);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void writeLog(String info) throws IOException {
		if (!f.exists())
			f.createNewFile();

		try {
			bw.write(info);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static int getCHOSEN_SHEET() {
		return CHOSEN_SHEET;
	}

	public static void setCHOSEN_SHEET(int cHOSEN_SHEET) {
		CHOSEN_SHEET = cHOSEN_SHEET;
	}

	public static int getMAX_HALL() {
		return KSO_MAX_HALL;
	}

	public static void setMAX_HALL(int mAX_HALL) {
		KSO_MAX_HALL = mAX_HALL;
	}

	@Override
	public void start(Stage a) throws Exception {
		
		openGui(a);
	}

}
