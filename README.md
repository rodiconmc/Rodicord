# Rodicord

Rodicord is a library for manipulating the Discord Api via a distributed system. It was specifically designed for use on
the Rodicon minecraft server, but may have uses elsewhere.

*This project is in it's very early stages and has very limited usability*

Since we maintain this project solely for our use on the Rodicon server, this library does not contain all the endpoints
of the Discord Api. Feel free to contribute other endpoints.

At this time, Rodicord does not connect to the discord websocket. This means that if you want to control a bot, you need
to first authenticate it once with the discord websocket. This only needs to be done once, and can be done manually (as 
opposed to programmatically).

# Gradle/Maven
Check here for the maven/gradle import instructions: https://bintray.com/rodiconmc/RodiconRepo/Rodicord

# Javadocs
https://rodiconmc.github.io/Rodicord/
