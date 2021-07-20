import java.text.DecimalFormat;
/**
 * Represents a song.
 * 
 * Two different constructors one taking title and artist.
 * The other takes in playtime and filename.
 * Other than the getters and setters, there is a play function.
 *
 * @author Jake Olsen
 */
public class Song extends PlayableSong{
	private String title,artist,fileName;
	private int playTime;

	public Song(String title,String artist,int playTime,String fileName) {
	    super(fileName);
		this.title = title;
		this.artist = artist;
		this.playTime = playTime;
		this.fileName = fileName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist){
		this.artist = artist;
	}
	public int getPlayTime() {
		return playTime;
	}
	public void setPlayTime(int playTime){
		this.playTime = playTime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	/**
	 * Plays the selected song
	 */
	public String toString(){
		DecimalFormat fmt = new DecimalFormat("00");
		return String.format("%-20s %-25s %-25s %-20s", title, artist,
				fmt.format(playTime/60) + ":" + fmt.format(playTime%60), fileName);
	}
}
