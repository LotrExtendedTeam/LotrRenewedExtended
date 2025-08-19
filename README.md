# Lotr Renewed Extended
This is the home of the Lotr (Renewed) Extended mod for Minecraft 1.16.5

## Developing Extended
1. Download this repository (LotrRenewedExtended) with git (or via the GitHub Desktop software)
2. Run ForgeGradle setup
    * Eclipse: run `./gradlew eclipse genEclipseRuns`
    * IntelliJ: configure the project using the Gradle side menu in the IDE
       * Or alternately run `./gradlew genintellijruns` then use the "Reload all gradle projects" button in the IDE

If having problems with Eclipse, use the following commands:
1. First try cleaning the Eclipse configuration completely. 
    * "./gradlew cleanEclipse"
2. If that doesn't work you may try more specific tasks: 
    * "./gradlew cleanEclipseProject"       to remove the .project file
    * "./gradlew cleanEclipseClasspath"     to empty the project's class-path
3. Run to rebuild the Eclipse configuration and run files
    * "./gradlew eclipse genEclipseRuns"
4. If non of the above work, delete the local environment and re-download the remote origin. 

## Credits
* Firstly, credit goes to everyone who contributed to the mod directly. 
* Secondly, credit needs to go to Mevans for his ideas in LOTR: Legacy and for developing LOTR: Renewed, of which we have build our mod off of.
* Additionally, Credits also go to Gruk on the inspirations for our textures.
* Credit also needs to be given to all that have contributed to the localization of the mod via our localization repository.
* Credit should also be given to all those that have contributed to out wiki.
* Finally credit needs to be given to J.R.R.Tolkien for the inspiration this mod has taken from his amazing works.