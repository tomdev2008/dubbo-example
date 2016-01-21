#!/bin/sh
#current dir  
 cd `dirname $0`/..  
 BASE_DIR="`pwd`"

 AUTH_PID="$BASE_DIR/logs/auth.pid"
 
 echo "star to kill process:$AUTH_PID"
    if [ -f "$AUTH_PID" ]; then
      if [ -s "$AUTH_PID" ]; then
        kill -15 `cat "$AUTH_PID"` >/dev/null 2>&1
        rm -f "$AUTH_PID"
        if [ $? -gt 0 ]; then
          echo "PID file found but no matching process was found. Stop aborted."
          exit 1
        fi
      else
        echo "PID file is empty and has been ignored."
      fi
    else
      echo "\$AUTH_PID was set but the specified file does not exist. Is $AUTH_PID running? Stop aborted."
      exit 1
    fi
