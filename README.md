GamePatcher
===========

A game updater library in Java.  It can be used for any file type, whether that be .exe, .jar, .txt, or more.

This library figures out if an update is necessary by checking the time of the most recent update and updating if the time of the most recent update is later than the time when last updated (the time on the file at the same directory as the game).  When using the update() method, the updater will automatically timestamp the update with the current system time at the time of the update.

How to use:

This library requires Java 7 because it uses some really helpful file methods in that Java version.  Therefore, you must use Java 7 in order to use this library.

First, add the library to your build path.  Unlike the C# version, this library doesn't need any other libraries in order to work.   You can now create a new Downloader in your Main method. You will need to pass four arguments into making a new Downloader: the file name, the date file name, the file internet address, and the date file internet address. The file names are not an absolute path. The program will automatically add the file name to the local directory, so if you put in "text.txt" and your program is located in C:\\Users\\Sam\\Downloads\\ the program will end up with the path C:\\Users\\Sam\\Downloads\\text.txt . The internet address is better to be copied down than written yourself. An example of an internet address is http://sammidysam.github.com/text.txt . From there you can call the CheckForUpdate() method in Downloader to download an update if one is available.

To update, you must make a new Updater. There are only three arguments in making a new Updater: the file name, the date file name, and the path of where to update the files to. The path is an absolute path. It must be absolute or else an error will arise. The reason why it is absolute and not relative is because this will not be called in games that are played by non-developers. You will call this method if you are a developer and make sure it is not called when you release your game. If it were non-absolute, then it would be much harder to update the files to the right path. Files cannot be uploaded directly to the website. Instead, you will update it to your website location on your computer and from there update the website. After creating a new Updater, you will call the Update() method in Updater and it will update the file and date file at the absolute location specified.

This program will detect if the computer in use has internet. If it does, it will attempt to download the files necessary. If it doesn't it will not. This allows you, the developer, to only need to call the CheckForUpdate() method. The program detects if internet is available by pinging github.com .  Google.com was going to be pinged initially, but Google seems to have some firewall on Java's method of pinging and it was denied.

If you are using this library, please make your launcher a different JAR than your actual game.  This will allow the library to update it, as it can't update itself!  Here is some example code that will show you what I did to download and update files:

<pre>
  	Downloader downloader = new Downloader("text.txt", "date.txt", "http://sammidysam.github.com/text.txt", "http://sammidysam.github.com/date.txt");
		downloader.checkForUpdate();
		Updater updater = new Updater("text.txt", "date.txt", "C:\\Users\\Sam\\Documents\\Website\\Sammidysam.github.com\\");
		updater.update();
</pre>
