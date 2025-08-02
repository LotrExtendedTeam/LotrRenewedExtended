# Lotr Renewed Extended
This is the home of the Lotr (Renewed) Extended mod for Minecraft 1.16.5

## Developing Extended
1. Download this repo (LotrRenewedExtended) with git (or via the GitHub Desktop software)
2. Run ForgeGradle setup
    * Eclipse: run `./gradlew eclipse genEclipseRuns`
    * IntelliJ: run `./gradlew genintellijruns` then use the "Reload all gradle projects" button
       * Or alternately for IntelliJ configure the project using the Gradle side menu in the IDE

If having problems with Eclipse, use the following commands:
1. First try cleaning the Eclipse configuration completely. 
    * "./gradlew cleanEclipse"
2. If that doesn't work you may try more specific tasks: 
    * "./gradlew cleanEclipseProject"       to remove the .project file
    * "./gradlew cleanEclipseClasspath"     to empty the project's classpath
3. Run to rebuild the Eclipse configuration and run files
    * "./gradlew eclipse genEclipseRuns"
4. If non of the above work, delete the local environment and re-download the remote origin. 

## Credits
* Firstly, credit goes to everyone who contributed to the mod direclty through the repo, or indirectly via Discord or otherwise. 
* Secondly, credit needs to go to Mevans for his ideas in LOTR: Legacy and for developing LOTR: Renewed, of whigh we have build our mod off of.
* Additionally, Credits also go to Gruk on the inspirations for our textures.
* Credit also needs to be given to all that have contributed to the localization of the mod via our localization repo.
* Credit should also be given to all thoes that have contributed to out wiki.