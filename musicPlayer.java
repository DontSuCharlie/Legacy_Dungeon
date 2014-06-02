import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import javax.sound.sampled.*;
import java.io.*;

public class musicPlayer implements Runnable
{
	private String songPath;
	private Thread musicThread;
	public musicPlayer(String song)
	{
		songPath = song;
	}
	public void run()//this is what the thread will do. It's like a second main function
	{
		try
		{
			AudioInputStream menu = AudioSystem.getAudioInputStream(new File(songPath));
			Clip clip = AudioSystem.getClip();
			clip.open(menu);
			clip.loop(10);
			//AudioClip menuTheme = Applet.newAudioClip(new URL("menu.wav"));
			//menuTheme.play();
		}
		catch (UnsupportedAudioFileException e)
		{
			System.out.println(e);
		}
		catch(LineUnavailableException e)
		{
			System.out.println(e);
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	public void start()
	{
		if(musicThread == null)
		{
			musicThread = new Thread (this, "musicThread");
			musicThread.start();
		}
	}
}