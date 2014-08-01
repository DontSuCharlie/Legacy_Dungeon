LEGACY DUNGEON
================================
A top-down 2-D dungeon crawler with randomly generated maps. The game is currently in pre-alpha stage.

[0] {Inspiration Stage - Basic concepts and ideas for game are created. Programming commences!}
[1] {Pre-Alpha Stage - Basic structures (window, image/sound loading, map generation, map management, world map, menu screen, GUI implementors, the blueprints for characters, monsters, NPC, items, tiles, and skills) are finished.}
[2] {Alpha Stage - Begin fleshing out blueprints (characters, monsters, NPC, items, tiles, and skills). New idea round}
[3] {Pre-Beta Stage -Game is completable (for player). Work is reviewed by peers/mentors/people with lots of experience and knowledge, then updated}
[4] {Beta Stage - Game is tested by close people/classmates who liek playing games. Lots of re-iteration. Start looking for musicians, artists, and writers}
[5] {Pre-Gamma Stage - Game implements new art, music, and writing. Game might become more ambitious at this stage.}
[6] {Gamma Stage - Testing once more. Final feedback.}
[7] {Pre-Release - Begin getting information out there.}
[8] {Release!}

Inspiration Stage - This is the stage where we came up with the concepts and ideas for the game. Commence programming!
Pre-Alpha Stage - This is the stage where we have already laid out the most basic structures of our game.
Alpha Stage - This is the stage where we begin adding unique objects to the game along
Pre-Beta Stage - This is the 
Progress:

Inspiration -> Pre-Alpha Stage ->


Copyright 2014 Charlie Su, Anish Kannan

==============
Authors: Charlie Su and Anish Kannan
Mentors: David Ma
Musicians: Alex Wu

Table of Contents
=================
I. Game Content
II. To-Do List
III. Completed List
IV. Activity Log

I. GAME CONTENT
================
a. What is this game you speak of?
	In a dark, dark world, you are assigned the duty as guardian of the Great Spiral Tower - the main source of light to your world. A force is amassing armies to try to take over the Great Spiral Tower, so it is up to you to defend the tower, strengthen yourself, and make sure the enemy is eliminated so you can be at peace.

b. Story Background
	In a completely different world, the source of light and energy come from shiny orbs called crystals. They are also capable of granting super powers. In the past, the crystals were distributed all over the world to ensure light and energy are spread out evenly throughout the world. However, a greedy creature has decided to take these crystals to take over the world. It is up to you to (temporarily?) amass crystals so you can combat this force and restore the crystals back to the world.
	
	At the middle of the world is the Great Spiral Tower, which is responsible for making these crystals. The Great Spiral Tower has not made a new crystal in decades; however, the greedy monster still wants full control of the tower to see if he could reproduce crystals and dominate the world.
	
	You yourself are not sure what's so special about the Great Spiral Tower; but, nevertheless you vow to protect it because you are a heroic, RPG-generic protagonist.

c. WORLD MAP PERSPECTIVE
WORLD MAP
We need to find a pretty picture that will fit our needs. I guess we can have multiple maps/hearts so people can adjust it to their hearts content? IDK. Let's start with at least 1.

CHARACTER MOVEMENT
You are able to move to the closest nodes. To move, you click on the node you want to move to (with yo mouse). Once your character piece moves to the node, you will be asked to confirm your movement. If you realize you had made a fatal mistake in terms of making that one, single move, you can return to your original position without any penalties. You can move as much as you want on the world map; however, each move costs 1 turn.

ENEMY MOVEMENT
Every turn, your enemy also moves. In the beginning, you are given X amount of free turns, allowing you to build up some sort of barrier OR strengthen yourself before the first wave of enemies shows up.

There are 7 enemy "armies", which means there are 7 different bosses. They will all move at the same time and will be trying to conquer as many nodes as possible. You can eliminate armies by defeating them in a Battlefield node; however, this is not recommended. The amount of energy needed to beat all 7 bosses staggeringly outweighs the benefits.

After Y amount of turns, the final boss will appear on the scene. All nodes cleared by the final boss are completely inaccessible. Beating all 7 armies will also automatically summon the final boss (making it so that you can't have a free-for-all reign if you do beat all 7 armies unscathed, ha!)

STAGES
The first stage happens during the X amount of free turns. The map is entirely viewable, everything's peaceful and sunshiney.

The second stage begins when the first armies start moving in. They discolor the map, making it a dark grey-purple hue. The BGM also changes. The BGM also changes when the armies are relatively close to the heart.

The final stage begins when the final boss's icon appears. The final boss shrouds the path it conquers with complete darkness. The BGM also changes. The BGM also changes when either the (still alive) armies or the final boss are relatively close to the heart.

THE HEART
The heart is the target of all enemy forces. Your goal is to keep the heart alive. To permanently keep it alive, you'll have to eliminate all enemy forces, which you can do by defeating the final boss.

The HEART's HP (or amount of turns it takes to conquer) is dependent on the amount of defensive nodes you have captured before. That means, if you had 9 unique defensive nodes throughout the whole game, but only 2 left when the Heart is under attack, the Heart will have an HP of 9. The Heart cannot regenerate HP UNLESS you have the same amount of ACTIVE defensive nodes left. Using the same situation described above, if your Heart's HP drops to 1, then it will be capable of regenerating itself to 2. If you find create another active defensive node, it'll be capable of regenerating itself to 3, and so on.

DUNGEONS
The dungeon is in essence an unexplored location. At the end of every dungeon, there is a crystal. Dungeons increase in difficulty as the game progresses. Every dungeon is randomly generated and will have various, different properties.

CLEARED DUNGEONS
After clearing a dungeon, you are given a choice of taking the crystal or imbuing the crystal with defensive powers. When you choose to take the crystal, it means you have cleared the dungeon. Taking the crystal grants you a new power up.

The following properties are in cleared dungeons:
*Enemies require 0 turns to cross cleared dungeons
*However, the final boss will not be able to obtain the skill originally in the dungeon

DEFENSIVE NODES
After clearing a dungeon, you are given a choice of taking the crystal or imbuing the crystal with defensive powers. A defensive node is formed when you choose to imbue the crystal with defensive capabilities. The purpose of a defensive node is that it can slow down enemy's movement, which buys you more time to strengthen yourself before the final boss. The more defensive nodes you have, the more it impedes enemy movement. Remember that every 5 minutes you are in a dungeon, the enemy is granted 1 turn on the World Map; however, with each defensive node you have, it takes an additional 20 seconds for enemies to move. Another property of the defensive node is that enemies cannot freely move through it. It takes an enemy 2 turns to defeat the node and move to a new location. However, defensive nodes can be strengthened. Every 5 defensive nodes you have adds another turn it takes for enemies to move.

The following properties are in defensive nodes:
*Defensive nodes impede movement. Enemies require 2+(X/5) turns to clear the dungeon. X = amount of defensive nodes.
*After clearing your defensive node, the final boss will have a 50% probability of obtaining the skill unique to the dungeon.
*You can opt to defend your node. When a node is under attack, it becomes a BATTLEFIELD.
*Defending your node successfully will move enemy forces back by 1-2 markers.

BATTLEFIELD
A battlefield is a node getting invaded by enemy forces. This can either be an uncleared dungeon or a defensive node. In an uncleared dungeon, instead of a regular dungeon, you'll have to compete with the enemy force to clear the dungeon. If the enemy forces clears the dungeon ahead of you, you lose. If you clear the dungeon before the enemy, you'll be able to take the crystal. If you clear the dungeon at the same approximate time as the enemy, you have to fight a mini-boss.
In a defensive node, there's a different mode of play that I'm trying to think of right now.

The following are properties of battlefields:
*Battlefields have a ticker floating above it. x/X, where x is the # of turns left before the node is taken, and X is the # of turns it takes to complete the dungeon for the enemy. If x!=X, then the enemy gets a head start (ie: being a few floors ahead of you).
*After a battlefield is won, the enemy forces are repelled back 1 marker. If the battlefield was a defensive node, then it turns back to a recovering defensive node. If the battlefield was an uncleared dungeon, then it becomes a cleared dungeon.

ENEMY NODES
They are dead. You cannot touch these nodes. QQ for you.
//Anish suggests making it recapturable, but extremely, unnecessarily difficult that it is deterring; however, if you have a key point you want to capture, then this would be useful.

d. DUNGEON PERSPECTIVE

CRYSTALS
Crystals are the very objects that give you super powers. They can be found at the end of every dungeon. Both you and the enemy forces are capable of gaining super powers from these crystals. What does that mean? It means every dungeon you lose to the enemy not only prevents YOU from becoming stronger, it makes the enemy stronger.

There are 7 "armies". The bosses themselves will be capable of using crystals they obtained from conquering their dungeons. When the final boss appears, the bosses will give all of their skills to the final boss. IF you kill a boss that had a skill, because it's dead, it cannot give the earned skill to the final boss. (which means you can track a specific icon down if it had taken a very valuable skill).

Crystals are close to the essence of the game. You can use skills, each with its own unique properties. These skills can be used in combinations with other skills to create devastating combinations.

Every time you opt to take a crystal, you will be able to choose the key you want to assign it to on your keyboard. If the crystal has a cool-down, then an icon will appear every time its cool down is active.

TILES
There are different types of tiles. The normal tile is just a normal tile; however, each environment has different tiles with different properties.

	ICE: Slide in the direction you were moving
	HOLE: Makes you fall down to the floor below you. Depending on the direction you're heading this might be good or bad.
	???: Help, I need mo4r ideas
	
MONSTERS
There will be monsters. All monsters should have unique properties - as in different skills, strategies, weaknesses, and strengths. We can't make all enemies too easy, otherwise it'll turn to a boring grindfest. We can't make it TOO hard, because then the game would just be pure stress.
Note: If we finish making the AI for monsters, why not make AI for allies?!!?!?!?!?

ITEMS/INVENTORY
Items can be found in the dungeon OR dropped by monsters upon their death.

MARKETS
"You're surprised that a merchant has gotten his hands onto a crystal. Looking at his goods, you decide not to question him."

e. MENU
The game opens up with the MENU SCREEN. The following will be available in the menu screen:

Game Mode: <Story, Regular>
Play>>New Game/Load Save File
Settings>>Graphics/Sound/Other Options
Credits
Exit

II. To-Do List
===============
a. World Map Category
	1) Fix bug with infinite loop when generating nodes.
	2) Finish making maxAreaPolygon for the defensive area.
	3) Create the art/animation for the nodes
	4) Create the art/animation for the world map
	5) Create the art/animation of the player's icon
	6) Create the art/animation of the army's icon
	7) Create the art/animation of the final boss's icon
	8) Create the art/animation for the Great Spiral Tower
	9) Create mouseListener, and update nodeWorld so that hovering over a node will cause animations, clicking it will move the character to the specific location
	10) Create a pop-up message that verifies whether or not you wanted to move to the specific node.
	11) Create GUI that displays the nth turn the game is currently on.
	12) Create a method that selects the available nodes you are able to move to.
	13) If necessary, we might have to adjust the nodes' positions so that it's more distributed in a fair, even way.
	14) Create a method that loads sounds and music
	15) CAMERA?! HALP!?
	16) Create a filter that can change the color of the World map based on the current stage the game is in
	17) Enemy AI movement might be helpful (if we're going to implement AI, we might as well have different strategies - ie: ganging up on 1 node, attacking on all fronts, focusing on 1 front, etc., depending on difficulty.) (if we make it easy, then armies won't ALL appear at the same time. Maybe different strategies for different levels of difficulty? IDK I NEED TO FIND A WAY TO INCREASE STRATEGY IN THIS GAME!)
	
	
b. Dungeon Category
	1) Dungeon generation + saving + efficient loading
	2) Unique tiles
	3) Spritesheets
	4) Timer + Current Turn GUI
	5) Race Against Enemy GUI (for Battlefield Nodes)
	6) Monsters + Monster AI + Hitboxes
	7) Skills - balance, animation, ideas, everything!
	8) Inventory/Items
	9) Music/Sound
	11) Character + Key Control + Character HP/MP/Cooldowns/Hitboxes
	12) Environmental qualities (ie: fog, water, etc.)
	13) 
	
c. Menu Category
	1) Load menu screen
	2) Create buttons for the menu screen
	3) Add menu screen to main method
	4) Key and Mouse Listeners are both important
III. Completed List
=====================
//RANDOM NOTES
/*///////////////////////////////////ORIGINAL NOTES//////////////////////////////////
   //If player enters a dungeon, load dungeon
      //Use node information to generate dungeon floor
      //Takes input.
      //Displays UI that reflects HP, Skill EXP (skill EXP is dependent on use), Cooldown, etc.
      //Displays Timer
      //Every 5 minutes, the enemy forces will have moved by 1 turn (will notify player)
      //Check to see if the player has reached another floor
         //If the player is about to approach the final floor, a new type of floor will be generated - the final floor
      //Generates new map
      //repeat until player reaches final floor
      //If player reaches end of dungeon, dies, or uses Escape Crystal, return to World Map
      //Update World Map with movement of Enemy Forces
   //Checks to see if Final Boss is at the Heart. If so, starts counting down on turns left before game ends
   //If Final Boss succeeds, game ends
   //If player defeats Final Boss, the player wins!
   //Creates a drawing based from a picture file
   */

IV. Activity Log
====================
4/12: Charlie created the basic frame. The game officially exists.
4/12: Charlie decided to get distracted and instead of actually making the game work, decided to make it so that the window now has its own custom icon.
4/12: (technically 4/13 now because past midnight) Charlie continues to delve on useless functions - like making the window pop up in the MIDDLE of the user's screen. ANY user's screen.
4/19: Anish is writing in third person.
4/22: .assignNodePos() works! Updates needed left: make sure the positions do not go off screen. Allow for full screen option, but do not allow resizable. Make the windowed mode dependent on user screen (not only appearing in the middle, but also its size). Make sprites dependent on user screen as well. Fix assignNodePos bc the reference point of an image is NOT in the center, but rather on its topleft corner.
4/23: Anish finished the tile generator after much struggles.
4/23: Charlie copy and pasted David's code to create an ImageLoader() class! Much skill! Much wow! NodeWorld is cleaned.
5/1: Anish finished implementing KeyListener for the Dungeon aspect of the game
5/6: Charlie is still struggling with maxAreaPolygon.
5/7: Charlie came up with a pseudo-story for the game.
5/8: Charlie updated README.md
5/8: Anish is increasing the efficiency of tile generator by removing the x/y components and just creating a 2D array of objects.