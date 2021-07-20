import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Represents a array of songs, using the song class
 * 
 * Only requires a name for the playlist, everything
 * else uses the methods in this class to alter the
 * object.
 *
 * @author Jake Olsen
 */
public class PlayList  implements PlayListInterface{
	private String name;
	private int playCount;
	private int totalPlayTime;
	private ArrayList<Song> songList;

	/**
	 * Initializes a new PlayList with playCount 0.
	 * 
	 * @param name is the name for the object playlist
	 */
	public PlayList(String name) {
		this.name = name;
		this.playCount = 0;
		this.songList = new ArrayList<Song>();
	}
	/**
	 * Creates a play list from the song data in the given file.
	 * @param file The file containing the song data.
	 */
	public PlayList(File file)
	{ 
		try {
			songList = new ArrayList<Song>();
			Scanner scan = new Scanner(file);
			System.out.println("here" + file.getName());
			this.name = scan.nextLine().trim();
			
			while(scan.hasNextLine()) {
				String title = scan.nextLine().trim();
				String artist = scan.nextLine().trim();
				int playtime = Integer.parseInt(scan.nextLine().trim());
				String fileName = scan.nextLine().trim();
				Song song = new Song(title, artist, playtime, fileName);
				this.addSong(song);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.err.println("Failed to load playlist. " + e.getMessage());
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public int getPlayCount() {
		return playCount;
	}
	public void wasPlayed() {
		playCount++;
	}
	public void addSong(Song s)
	{
		songList.add(s);
	}
	public void removeSong(int id)
	{
		if (!((songList.size()-1) < id))
	    songList.remove(id);
	}
	public int getNumSongs()
	{
	    return songList.size();
	}
	public ArrayList<Song> getSongList()
	{
	    return songList;
	}

	/**
	 * Returns a string formated string with all playlist info.
	 * @return blockString
	 */
	public String toString() {
		String blockString = "------------------\n";
		blockString += name;
		blockString += " (" + songList.size() + " songs)";
		blockString += "\n------------------\n";
		int i = 0;
		if (songList.size() > 0)
		{
			System.out.println("");
			for (Song temp: songList)
			{
				blockString += "(" + i + ") " + temp.toString() + "\n";
				i++;
			}
			blockString += "\n------------------\n";
			return blockString;
		}
		else
		{
			blockString += "There are no songs.";
			blockString += "\n------------------\n";
			return blockString;
		}
		
	}
	@Override
	public int getTotalPlayTime() {
		totalPlayTime = 0;
		for (Song temp: songList)
		{
			totalPlayTime += temp.getPlayTime();
		}
		
		return totalPlayTime;
	}
	@Override
	public Song[] getSongArray() {
		Song[] copy = new Song[songList.size()];
		int i = 0;
		for(Song p : songList) {
			copy[i] = p;
			i++;
		}
		return copy;
	}
	@Override
	public int moveUp(int index) {
			songList.add((index-1), songList.get(index));
			songList.remove(index+1);
			return (index-1);
	}
	@Override
	public int moveDown(int index) {
		songList.add((index), songList.get(index+1));
		songList.remove(index+2);
		return (index+1);
	}
	@Override
	public Song[][] getMusicSquare() {
    	final int length = (int)Math.ceil(Math.sqrt(songList.size()));

    	Song[][] grid = new Song[length][length];
    	
    	for(int row = 0; row < grid.length; row++){
    		for(int col = 0;col < grid[row].length; col++){
    			grid[row][col] = songList.get((row*length+col)%songList.size());
    		}
    		//break new row
    	}
		return grid;
	}
}
