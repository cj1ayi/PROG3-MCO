#!/bin/bash
javac -Xmaxerrs 10000 -Xmaxwarns 500 controller/*.java model/*.java view/*.java *.java && java Main

