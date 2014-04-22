Legacy-Dungeon Game Plan
===============

Table of Contents
I. Game Content: List of game mechanics we want.
II. To-Do List: List of stuff need to do.
III. Completed List: List of goals completed.
IV. Activity Log: Contributors, please update this log with your date and description of what significant achievement you accomplished.


I. Game Content
----------------
There will be two main parts to the game - the World Map and the actual Dungeons.


2] There will be a World Map.
3] The game

II. To-Do List
---------------
1] Create a Window for the game to run
2] Upload images
3] Play sound/music
4] 

III. Completed List
---------------------

IV. Daily Log
-------------------
4/12: Charlie created the basic frame. The game officially exists.
4/12: Charlie decided to get distracted and instead of actually making the game work, decided to make it so that the window now has its own custom icon.
4/12: (technically 4/13 now because past midnight) Charlie continues to delve on useless functions - like making the window pop up in the MIDDLE of the user's screen. ANY user's screen.
4/19: Anish is writing in third person.
4/22: .assignNodePos() works! Updates needed left: make sure the positions do not go off screen. Allow for full screen option, but do not allow resizable. Make the windowed mode dependent on user screen (not only appearing in the middle, but also its size). Make sprites dependent on user screen as well.
ALSO - Make an ImageLoader() class. This will make the whole code much cleaner. (as per David's suggestion)
ALSO - I read something about making sure that the JFrame function runs in a specific thread? I'll need to read and research about it to make sure that things work.
ALSO - Play around with the overloaded version of repaint(). (the one that takes in a rectangle area too). It might make it less CPU consuming.
NEXT - Load character sprite on World Map. Distribute nodes betterly (inner, mid, outer). Draw polygon. Add maximum area polygon method. Character movement. Keyboard Listener. Mouse Listener. (AI should be later...)
DUNGEON - finish dungeon generator. Maybe tweak it a bit to favor cooler looking dungeons. Add edge detector (for adding walls). Add Keyboard Listener. Add Hitbox (for walls/monsters/character). (EVERYTHING ELSE - HP, SKILLS, MONSTERS, AI, BOSSES, BOSS HP, GUI (IE: FLOOR, TEXT, NPC), LOOT, INVENTORY, ANIMATION, SOUNDS, BGM (OH MAKE SURE BGM HAS MULTIPLER LAYERS OF MUSIC - IF EPIC ADD EPIC PART), UGH IDK WHAT ELSE HALP)



/*
//Needs to construct Window with JFrame
//Needs to paint the Window constantly
//Needs to take keyboard/mouse input and do something with it
//Collision check should be in a separate file
//music/Sound should be in a different file
//AI should be in a a different file
//Random map generation should be in a different file
//Character stats/skills should also be in a different file
//While in the dungeon, the World Map can be updated (use a thread!!! huuehuuehuue) 
//What the hell are threads??????? - uses the Runnable Interface
//Threads allow for parallel processing - in which multiple methods run at the same time
//We need something that allows the program to shutdown safely
//How do you make it multi-platform? What safety things (prevent crashing, allows for different OS's, etc.) are behind a video game?
//How do games install? wtf??!?!?!?!?!?!?!!?
//We also need to include Java DL files for people without JAVA to guarantee that the game will run
//Also needs safe file (save will be in a different file)
//package LegacyDungeon;//a package ensures that upon mixing it with other people's codes, names won't confuse the compiler
*/

//Why do we need to import java.awt.event when java.awt.* already imports everything after that????
//I think it's because .* does not include other files (it only includes stuff directly in the file, but not subfolders)
