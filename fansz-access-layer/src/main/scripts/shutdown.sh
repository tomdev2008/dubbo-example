#!/bin/sh
#current dir  
 cd `dirname $0`/..  
 BASE_DIR="`pwd`"

 ACCESS_PID="$BASE_DIR/logs/access.pid"
 
 echo "start to kill process:$ACCESS_PID"
    if [ -f "$ACCESS_PID" ]; then
      if [ -s "$ACCESS_PID" ]; then
        kill -15 `cat "$ACCESS_PID"` >/dev/null 2>&1
        rm -f "$ACCESS_PID"
        if [ $? -gt 0 ]; then
          echo "PID file found but no matching process was found. Stop aborted."
          exit 1
        fi
      else
        echo "PID file is empty and has been ignored."
      fi
    else
      echo "\$ACCESS_PID was set but the specified file does not exist. Is $ACCESS_PID running? Stop aborted."
      exit 1
    fi
