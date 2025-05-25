#!/usr/bin/env bash
#$DEMO_COMMAND &

./target/demo

export MY_PID=$!
while [ "$(curl -s -o /dev/null -L -w ''%{http_code}'' http://localhost:8080/)" != "200" ];
  do sleep 0.001;
done
hey -n 1000000 http://localhost:8080/cats
ps -p $MY_PID -o rss,cputime
kill -9 $MY_PID
