#!/bin/sh
#current dir  
 cd `dirname $0`/..  
 BASE_DIR="`pwd`"

 STORAGE_PID="$BASE_DIR/logs/storage.pid"
 
 echo "star to kill process:$STORAGE_PID"
    if [ -f "$STORAGE_PID" ]; then
      if [ -s "$STORAGE_PID" ]; then
        kill -15 `cat "$STORAGE_PID"` >/dev/null 2>&1
        rm -f "$STORAGE_PID"
        if [ $? -gt 0 ]; then
          echo "PID file found but no matching process was found. Stop aborted."
          exit 1
        fi
      else
        echo "PID file is empty and has been ignored."
      fi
    else
      echo "\$STORAGE_PID was set but the specified file does not exist. Is $STORAGE_PID running? Stop aborted."
      exit 1
    fi
