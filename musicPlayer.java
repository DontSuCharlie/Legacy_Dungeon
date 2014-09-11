import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import javax.sound.sampled.*;
import java.io.*;

/*
Using this is easy!
make a new MusicPlayer object
MusicPlayer musicPlayer = new MusicPlayer();

When you want to play a sound that doesn't loop, do musicPlayer.playSound("nameOfSoundfile.wav");
When you want to play music (or a looping sound), do musicPlayer.playMusic("nameOfSoundfile.wav");
When you want to CHANGE the music, simple do

musicPlayer.stop();//stops playing current song
musicPlayer.playMusic("nameofSoundfile2.wav");//starts playing new song

If you want to play multiple songs at once, do above^ w/o .stop().

*/

public class MusicPlayer
{
	private String songPath;
	boolean playing = true;
	AudioInputStream audio;
	Clip clip;
	
	public final void playSound(final String songPath)
	{
		try
		{
		    //For jar and cmd.
		    File file = new File(songPath);    
		    //For eclipse because for some reason it checks one level higher.
		    
		    if (!file.exists())
		    {
		        file = new File("bin/" + songPath);
		    }
		    
			audio = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audio);
			clip.loop(0);
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
	public final void stop()
	{
		clip.stop();
	}
	public final void playMusic(final String songPath)
	{
		try
		{
            //For jar and cmd.
            File file = new File(songPath);    
            //For eclipse because for some reason it checks one level higher.
            
            if (!file.exists())
            {
                System.out.println(songPath + "Does not exist, trying in bin");
                file = new File("bin/" + songPath);
            }
		    
			audio = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audio);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
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
}