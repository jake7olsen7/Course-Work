# Project 3: Jukebox

* Author: Jake Olsen
* Class: CS121 Section 1
* Semester: Spring 2016

## Overview

This java application takes 2 classes, Song, and PlayList, to make a
song application where you can add, remove and play songs from a list.

## Compiling and Using

To compile, execute the following command in the main project directory:
```
$ javac Jukebox.java
```

Run the compiled class with the command:
```
$ java Jukebox
```

You will be prompted with a main menu greeting that will give you two options
(f) load playlist from file
(n) create new playlist

Either option will make a playlist with the following options.
(p) play all
(l) list songs
(a) add song
(d) delete song
(q) quit


## Discussion

Writing this program was challenging, writing classes to ensure the
test programs worked seemed slightly backwards to me at first.  Having
a program to check your progress almost one method at a time was easy.
At some points it did seem very repetitive making the same getter and
setter methods for all of the song variables.

Seeing everything work out in the test program is very satisfying.
Knowing that you can make a single instance of a very important
variable, or object and reuse it makes this type of programing click.
The most challenging part of this program was working with the
code that was already provided, and understanding how to make it
work with the classes.

Also building a completed return block for the tostring for the
playlist added an extra challenge i hadn't done in previous labs
or projects. 

## Extra Credit

I did not attempt the extra credit from this assignment, with all the
Different classes and tests performed on this program it consumed my time.

## Sources used

N/A