import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Database implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 311944406923611050L;
	private ArrayList<Programming> programmingList = new ArrayList<Programming>();

	public Database() {
		fillDatabaseFromFile();
	}

	private void fillDatabaseFromFile() {
		// TODO clean previous data and read again
		// TODO Get file location here
		File f = new File(Captain.globeFile);
		if(!f.exists())
		{
			return;
		}
		
		
		try(FileInputStream fis = new FileInputStream (f);BufferedInputStream bis = new BufferedInputStream (fis);ObjectInputStream input = new ObjectInputStream(bis)) {
			readProgrammingFromDatabase(input);
			
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find file in " + Captain.globeFile);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not save " + e.toString());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		f.delete();

	}

	private void readProgrammingFromDatabase(ObjectInputStream input) throws IOException, ClassNotFoundException {
		int num = input.readInt();
		for(int i=0;i<num;i++)
		{
			Programming p = (Programming) input.readObject();
			programmingList.add(p);
		}
	}

	public ArrayList<Programming> getProgrammingList() {
		return programmingList;
	}

	public void setProgrammingList(ArrayList<Programming> programmingList) {
		this.programmingList = programmingList;
	}

	public void addProgramming(Programming p) {
		if (!programmingList.contains(p))
			programmingList.add(p);
	}

	@Override
	public String toString() {
		return programmingList.toString();
	}

	public String showExpiredMovies(int i) {
		if(i-2 < 0)
		{

			return "There is no programming to compare with.";
		}

		Set<String> old_movies = programmingList.get(i-2).getMovie_titles();
		Set<String> new_movies = programmingList.get(i-1).getMovie_titles();
		Set<String> removed_movies = new HashSet<String>();
		
		
		String[] old_movies_arr = old_movies.stream().toArray(n -> new String[n]);
		
		
		for(int j=0;j<old_movies_arr.length;j++)
		{
			String movie_name = old_movies_arr[j];
			if(!new_movies.contains(movie_name))
			{
				removed_movies.add(movie_name);
			}
		}
		if(!removed_movies.isEmpty())
			return removed_movies.toString();
		else
			return "No movies to show";
		
	}

	public void saveData() throws IOException {
		File f = new File(Captain.globeFile);

		try(FileOutputStream fis = new FileOutputStream (f);BufferedOutputStream bis = new BufferedOutputStream (fis);ObjectOutputStream output = new ObjectOutputStream(bis);) {
			
			if(f.exists())
			{
				f.delete();
				Captain.log("Deleting old database file");
			}
			f.createNewFile();
			
			writeProgramsToFile(output);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find file in " + Captain.globeFile);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not save " + e.toString());
			e.printStackTrace();
		}

		
	}

	private void writeProgramsToFile(ObjectOutputStream output) throws IOException {
		output.writeInt(programmingList.size());
		for(Programming p: programmingList)
		{
			output.writeObject(p);
		}
		
	}

	public void addProgramming(File selectedDirectory) {
		try {
			Programming p = new Programming(selectedDirectory);
			this.addProgramming(p);
		}
		catch(Exception e)
		{
			Captain.log(e.getMessage());
		}
		
	}

}
