#!/bin/sh
#current dir  
 cd `dirname $0`/..  
 BASE_DIR="`pwd`"

 TOKEN_PID="$BASE_DIR/logs/token.pid"
 
 echo "start to kill process:$TOKEN_PID"
    if [ -f "$TOKEN_PID" ]; then
      if [ -s "$TOKEN_PID" ]; then
        kill -15 `cat "$TOKEN_PID"` >/dev/null 2>&1
        rm -f "$TOKEN_PID"
        if [ $? -gt 0 ]; then
          echo "PID file found but no matching process was found. Stop aborted."
          exit 1
        fi
      else
        echo "PID file is empty and has been ignored."
      fi
    else
      echo "\$TOKEN_PID was set but the specified file does not exist. Is $TOKEN_PID running? Stop aborted."
      exit 1
    fi
