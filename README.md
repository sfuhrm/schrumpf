Schrumpf ![Schrumpf Logo](https://raw.githubusercontent.com/sfuhrm/schrumpf/master/src/main/resources/de/sfuhrm/schrumpf/ui/Logo-Entwurf-80x60.png "Schrumpf Logo")
========

![Schrumpf Screenshot](https://raw.githubusercontent.com/sfuhrm/schrumpf/master/art/Main-EN.png "Schrumpf main window screenshot")

Java UI application for batch image resizing and format changing

### Introduction

Schrumpf is designed to be an easy to use but yet powerful application to do batch processing of images.
The operations supported at the moment are
* Scaling
* Format changing (i.e. PNG -> JPG)
* Target name generation

### Features

The main features are:

* Easy usability (my mother is able to use it and she loves it!)
* Localized with English and German locales
* Good performance using all of the machines available hardware threads

### Runtime Requirements

The requirements are at the moment only an installed [Java Runtime 7+](http://java.com/).

### Downloading

Releases can be found on Github. You can download the current
files here:

https://github.com/sfuhrm/schrumpf/releases/tag/v1.0

### Current status

Schrumpf is working and operating as expected. Work and participation is very anticipated.
There's only two conditions:
* My mother should still be able to use it.
* Keep the code quality high (or even better, improve it).

There is some very basic Debian package generation shell scripting voodoo which creates a working Debian package.

### Building

Schrumpf is best edited using [NetBeans](https://netbeans.org/)
because the UI classes are designed with NetBeans' Matisse UI designed.

You can build Schrumpf from the source code by utilizing the command line

      mvn clean package

After that, in the target directory there'll be a directory containing a working bash shell script that starts Schrumpf:

      ~/dev/git/schrumpf$ ./target/Schrumpf-1.0-SNAPSHOT-dist/Schrumpf-1.0-SNAPSHOT/bin/schrumpf.sh 

### License

Schrumpf is licensed under [GPL 2.0](http://www.gnu.org/licenses/gpl-2.0.html).
