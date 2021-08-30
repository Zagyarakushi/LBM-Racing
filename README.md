<h1 align="center">
  <br>
  <a href="https://gitlab.com/zagyarakushi/lbm-racing"><img src="https://gitlab.com/zagyarakushi/lbm-racing/-/raw/main/images/kartGreen/kartGreen3.png" alt="LBM Racing"></a>
</h1>

<h4 align="center">A Low Budget Multiplayer Racing Game written in Java.</h4>
============================

Note: This project was originally pushed to Gitlab and as such, all issues, pull/merge requests and any other disucussion or changes should be made [here](https://gitlab.com/zagyarakushi/lbm-racing). (In case you are wondering, I have this mirrored on Github so people can follow the project even if they prefer Github. Also it acts as a backup.)

[![License](https://img.shields.io/badge/License-MIT-lightgray.svg?style=flat-square)]()


Table of contents
-----------------

* [Introduction](#introduction)
* [Installation](#installation)
* [Usage](#usage)
* [Known issues and limitations](#known-issues-and-limitations)
* [Getting help](#getting-help)
* [Contributing](#contributing)
* [License](#license)


Introduction
------------

This repository consists of several java files, images and audio. Two of the java files are for server and the rest are for client. By downloading this repository and compiling the client and the server program, you will be able to play a racing game!

![screenshot](https://)


Installation
------------

You will need any version of java runtime environment and a java compiler. For example:

Fedora
```bash
dnf install java-latest-openjdk-devel
```

Void Linux
```bash
xbps-install -S openjdk
```

Then clone the repository:
```bash
git clone https://gitlab.com/zagyarakushi/lgm-racing
```


Usage
-----

Now go to the directory of the game and compile the client and server.

Compile the client:
```bash
javac Game.java
```

Compile the server:
```bash
javac Server.java
```

Then run the Server first then the Client. The server must be running when the client tries to connect to the server. Which is when user clicks multiplayer from the menu.

Run the server:
```bash
java Server
```

Run the client:
```bash
java Client
```

Known issues and limitations
----------------------------

This project has many issues such as in game menu not working when pressing the escape key. There are parts of the code which is messy and not documented clearly. Some of the issues are shown in a table below.

| -------------------------- |
| In game menu               |
| Set keys option in settings|
| Missing setting options    |


Getting help
------------

You can create an issue and I will try to help you as much as I can.


Contributing
------------

First read the code of conduct and contributing file. Then you can fork the repository, add your own stuff and create a pull/merge request.


License
-------

MIT
