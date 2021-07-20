import java.util.ArrayList;
/**
 * Represents a array of songs, using the song class
 * 
 * Only requires a name for the playlist, everything
 * else uses the methods in this class to alter the
 * object.
 *
 * @author Jake Olsen
 */
public class PlayList {
	private String name;
	private int playCount;
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
	public String getName() {
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public int getPlayCount() {
		return playCount;
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
	 * Plays all songs in the playlist.
	 */
	public void playAll()
	{
		for (Song play: songList)
		{
		    TimedAudioClip clip = new TimedAudioClip(play.getTitle(), play.getArtist(), play.getPlayTime());
		    clip.playAndWait();
		}
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
}
