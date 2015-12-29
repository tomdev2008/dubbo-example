#!/bin/sh
#current dir  
 cd `dirname $0`/..  
 BASE_DIR="`pwd`"

 RELATION_PID="$BASE_DIR/logs/relation.pid"
 
 echo "star to kill process:$RELATION_PID"
    if [ -f "$RELATION_PID" ]; then
      if [ -s "$RELATION_PID" ]; then
        kill -15 `cat "$RELATION_PID"` >/dev/null 2>&1
        rm -f "$RELATION_PID"
        if [ $? -gt 0 ]; then
          echo "PID file found but no matching process was found. Stop aborted."
          exit 1
        fi
      else
        echo "PID file is empty and has been ignored."
      fi
    else
      echo "\$RELATION_PID was set but the specified file does not exist. Is $MEMBERS_PID running? Stop aborted."
      exit 1
    fi
