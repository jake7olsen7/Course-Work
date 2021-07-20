# Project 4: Text Statistics

* Author: Jake Olsen
* Class: CS121 Section 1
* Semester: Spring 2016

## Overview

This java application uses a main testing method ProcessText.java, and a
TextStatistics.java class to test a for a valid file, then processes the
file and calculates information about it.

## Compiling and Using

To compile, execute the following command in the main project directory:
```
$ javac ProcessText.java
```

Run the compiled class with the command:
```
$ java ProcessText file1 [file2 ...]
```

## Discussion

I did not run into many issues programming this project, anything that
was new or slightly unfamiliar was well explained in the Project
definition.  There was less code to work with so when i did get runtime
errors i was able to comment out, and find exactly what was going wrong
within a few minutes.
Using a String Builder for the tostring was a definite must, without it
i think this could have taken me along time to format all the data that
needs to be outputted in one return statement.
I had issues with how the project requires the error statement for an
invalid file to include the actual file name, but this was not passed
into the object itself.  I used a workaround within the main method for
the driver class in order to display the 'correct' error message to
match up to test requirements.  Since the project requirements didn't
say that the program had to immediately close i didn't think this was
a workaround that would break the program.

## Extra Credit

I did not attempt the extra credit from this assignment, i was
satisfied by the core program.

## Sources used

N/A