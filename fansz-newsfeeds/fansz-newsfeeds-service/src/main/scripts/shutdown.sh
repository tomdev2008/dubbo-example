#!/bin/sh
#current dir  
 cd `dirname $0`/..  
 BASE_DIR="`pwd`"

 FEEDS_PID="$BASE_DIR/logs/feeds.pid"
 
 echo "start to kill process:$FEEDS_PID"
    if [ -f "$FEEDS_PID" ]; then
      if [ -s "$FEEDS_PID" ]; then
        kill -15 `cat "$FEEDS_PID"` >/dev/null 2>&1
        rm -f "$FEEDS_PID"
        if [ $? -gt 0 ]; then
          echo "PID file found but no matching process was found. Stop aborted."
          exit 1
        fi
      else
        echo "PID file is empty and has been ignored."
      fi
    else
      echo "\$FEEDS_PID was set but the specified file does not exist. Is $FEEDS_PID running? Stop aborted."
      exit 1
    fi
