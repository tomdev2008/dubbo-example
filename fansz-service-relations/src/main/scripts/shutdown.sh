#!/bin/sh
#current dir  
 cd `dirname $0`/..  
 BASE_DIR="`pwd`"

 MEMBERS_PID="$BASE_DIR/logs/members.pid"
 
 echo "star to kill process:$MEMBERS_PID"
    if [ -f "$MEMBERS_PID" ]; then
      if [ -s "$MEMBERS_PID" ]; then
        kill -15 `cat "$MEMBERS_PID"` >/dev/null 2>&1
        rm -f "$MEMBERS_PID"
        if [ $? -gt 0 ]; then
          echo "PID file found but no matching process was found. Stop aborted."
          exit 1
        fi
      else
        echo "PID file is empty and has been ignored."
      fi
    else
      echo "\$MEMBERS_PID was set but the specified file does not exist. Is $MEMBERS_PID running? Stop aborted."
      exit 1
    fi
