#!/bin/sh

#kill server
SERVER_PID=`ps auxf | grep "scallop.center.Server" | grep -v "grep"| awk '{print $2}'`
echo " scallop server pid is ${SERVER_PID}"
if [ -n $SERVER_PID ] 
then
  kill $SERVER_PID
  echo "$SERVER_PID is killed!"
fi
