import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class main {

	private static int CHOSEN_SHEET = 8;
	private static int KSO_MAX_HALL = 18;
	private static final boolean showLog = false;
	private static final boolean printLog = true;
	public static final String MOVIE = "Movie";
	public static final String EVENT = "Event";
	public static final String NO_BREAK = "No intermission";
	public static String log_name = "AutoGlobe.log";
	public static File f = new File(log_name);
	public static BufferedWriter bw = null;
	public static FileWriter fw = null;
	public static Database db = new Database();
	public static void main(String[] args) {
		setLog();
		DummyAdd();
		closeLog();
	}

	private static void DummyAdd() {

		db.addProgramming(new Programming("01.xls"));
		db.addProgramming(new Programming("02.xls"));
		db.addProgramming(new Programming("03.xls"));
		db.addProgramming(new Programming("04.xls"));
		db.addProgramming(new Programming("05.xls"));
		db.addProgramming(new Programming("06.xls"));
		db.showExpiredMovies(1);
		db.showExpiredMovies(2);
		db.showExpiredMovies(3);
		db.showExpiredMovies(4);
		db.showExpiredMovies(5);
		db.showExpiredMovies(6);
		
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

}
