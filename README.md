# Jippi

Jippi is a Java port of the [SLP Parser](https://github.com/project-slippi/slp-parser-js) library writter for [Project Slippi](https://github.com/project-slippi/project-slippi).

Jippi is aimed at providing a better typed environment for Slippi Replay data analyzing applications. Down the line Jippi's goal is to surpass the original parser in speed.

### Running Jippi

Jippi doesn't have any built releases yet, but executing Jippi can be done by running the `jar` file manually with

```
java -jar target/jippi-0.1.0-jar-with-dependencies.jar --file=slp/test.slp --output=analysis.json
```

#### Parameters

_file_

Determines the replay file path.

Usage example:

```
java -jar target/jippi-0.1.0-jar-with-dependencies.jar --file=slp/test.slp
```

_output_

Determines the data JSON output file

Usage example:

```
java -jar target/jippi-0.1.0-jar-with-dependencies.jar --file=slp/test.slp --output=analysis.json
```

### TODO:

-   Finish tests
-   Create a executable
-   Document API
-   Package for use in other Java Projects
-   Finish Realtime analysis functionality

### Future

This project will be used as a tool for a upcoming Smash Melee analysis tool similiar to [SC2ReplayStats](http://sc2replaystats.com/)
