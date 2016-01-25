#!/bin/sh
#current dir  
 cd `dirname $0`/..  
 BASE_DIR="`pwd`"

 EVENT_PID="$BASE_DIR/logs/event-server.pid"
 
 echo "start to kill process:$EVENT_PID"
    if [ -f "$EVENT_PID" ]; then
      if [ -s "$EVENT_PID" ]; then
        kill -15 `cat "$EVENT_PID"` >/dev/null 2>&1
        rm -f "$EVENT_PID"
        if [ $? -gt 0 ]; then
          echo "PID file found but no matching process was found. Stop aborted."
          exit 1
        fi
      else
        echo "PID file is empty and has been ignored."
      fi
    else
      echo "\$EVENT_PID was set but the specified file does not exist. Is $EVENT_PID running? Stop aborted."
      exit 1
    fi
