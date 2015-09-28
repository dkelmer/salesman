#!/bin/bash
#Output from your program should be all cities ordered your path
#Make sure to write your output to $1
#DO NOT ALTER############
rm -f $1
touch $1
########################

DIR_NAME=`dirname $0`
(java -classpath $DIR_NAME Driver $2) >> $1
echo "1"
