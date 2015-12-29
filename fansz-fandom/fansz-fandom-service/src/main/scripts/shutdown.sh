#!/bin/sh
#current dir  
 cd `dirname $0`/..  
 BASE_DIR="`pwd`"

 FANDOM_PID="$BASE_DIR/logs/fandom.pid"
 
 echo "star to kill process:$FANDOM_PID"
    if [ -f "$FANDOM_PID" ]; then
      if [ -s "$FANDOM_PID" ]; then
        kill -15 `cat "$FANDOM_PID"` >/dev/null 2>&1
        rm -f "$FANDOM_PID"
        if [ $? -gt 0 ]; then
          echo "PID file found but no matching process was found. Stop aborted."
          exit 1
        fi
      else
        echo "PID file is empty and has been ignored."
      fi
    else
      echo "\$FANDOM_PID was set but the specified file does not exist. Is $FANDOM_PID running? Stop aborted."
      exit 1
    fi
