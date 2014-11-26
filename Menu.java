import java.awt.image.*;

public class Menu
{
	//PLAY BUTTON
	static Button newGameButton;
	static Button loadGameButton;
	static Button leftPlayArrow;
	static Button rightPlayArrow;
	//SETTINGS BUTTON
	static Button leftSettingsArrow;
	static Button rightSettingsArrow;
	static Button settingsButton;
	//CREDITS BUTTON
	static Button creditsButton;
	//BACKGROUND
	static BufferedImage background;
	static BufferedImage message1;
	public Menu()
	{
		int xAlign = Window.windowX/2 - 400;
		int yAlign = 100;
		newGameButton = new Button("/images/NewGameOrig.png", "/images/NewGameMoused.png", "/images/NewGameClicked.png", xAlign, yAlign, 400, 100);
		yAlign+=120;
		loadGameButton = new Button("/images/LoadGameOrig.png", "/images/LoadGameMoused.png", "/images/LoadGameClicked.png",xAlign, yAlign, 400, 100);
		yAlign+=120;
		//leftPlayArrow = new Button("LeftArrowOrig.png", "LeftArrowMoused.png", "LeftArrowClicked.png", yAlign, yAlign, 50, 50);
		//rightPlayArrow = new Button("RightArrowOrig.png","RightArrowMoused.png","RightArrowClicked.png", yAlign, yAlign, 50, 50);
		//leftSettingsArrow = new Button("LeftArrowOrig.png", "LeftArrowMoused.png", "LeftArrowClicked.png", yAlign, yAlign, 50, 50);
		//rightSettingsArrow = new Button("RightArrowOrig.png","RightArrowMoused.png","RightArrowClicked.png", yAlign, yAlign, 50, 50);
		settingsButton = new Button("/images/SettingsOrig.png","/images/SettingsMoused.png","/images/SettingsClicked.png",xAlign, yAlign,400, 100);
		yAlign+=120;
		creditsButton = new Button("/images/CreditsOrig.png", "/images/CreditsMoused.png", "/images/CreditsClicked.png", xAlign, yAlign, 400, 100);
		background = ImageLoader.loadImage("/images/menu.png");
		message1 = ImageLoader.loadImage("/images/message1.png");
	}
}