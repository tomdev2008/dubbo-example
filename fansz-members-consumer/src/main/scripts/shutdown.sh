#!/bin/sh
#current dir  
 cd `dirname $0`/..  
 BASE_DIR="`pwd`"

 CONSUMER_PID="$BASE_DIR/logs/consumer.pid"
 
 echo "star to kill process:$MEMBERS_PID"
    if [ -f "$CONSUMER_PID" ]; then
      if [ -s "$CONSUMER_PID" ]; then
        kill -15 `cat "$CONSUMER_PID"` >/dev/null 2>&1
        rm -f "$CONSUMER_PID"
        if [ $? -gt 0 ]; then
          echo "PID file found but no matching process was found. Stop aborted."
          exit 1
        fi
      else
        echo "PID file is empty and has been ignored."
      fi
    else
      echo "\$CONSUMER_PID was set but the specified file does not exist. Is $CONSUMER_PID running? Stop aborted."
      exit 1
    fi
