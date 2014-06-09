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

public class musicPlayer
{
	private String songPath;
	boolean playing = true;
	AudioInputStream audio;
	Clip clip;
	
	public void playSound(String songPath)
	{
		try
		{
			audio = AudioSystem.getAudioInputStream(new File(songPath));
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
	public void stop()
	{
		clip.stop();
	}
	public void playMusic(String songPath)
	{
		try
		{
			audio = AudioSystem.getAudioInputStream(new File(songPath));
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