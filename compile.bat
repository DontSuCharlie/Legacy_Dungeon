::Put this batch file into the directory above the LegacyDungeon repo to run it
@ECHO off
javac -cp LegacyDungeon/libs/* LegacyDungeon/KeyboardInput.java LegacyDungeon/MouseButton.java LegacyDungeon/MouseLocation.java LegacyDungeon/MouseScroll.java LegacyDungeon/LegacyDungeon.java

IF [%1]==[run] java -cp .;LegacyDungeon/libs/* -Djava.library.path=LegacyDungeon/native/ LegacyDungeon/LegacyDungeon
