#!/bin/sh
LOCAL_PATH=/Users/allan/Works/backend/fansz-backend
REMOTE_PATH=/usr/local/applications

pssh -P -h hosts.txt $REMOTE_PATH/access-layer-2.0.0-SNAPSHOT/bin/shutdown.sh 
pssh -P -h hosts.txt $REMOTE_PATH/fansz-auth-oauth-2.0.0-SNAPSHOT/bin/shutdown.sh  
pssh -P -h hosts.txt $REMOTE_PATH/event-server-2.0.0-SNAPSHOT/bin/shutdown.sh  
pssh -P -h hosts.txt $REMOTE_PATH/fansz-fandom-service-2.0.0-SNAPSHOT/bin/shutdown.sh  
pssh -P -h hosts.txt $REMOTE_PATH/fansz-newsfeeds-service-2.0.0-SNAPSHOT/bin/shutdown.sh  
pssh -P -h hosts.txt $REMOTE_PATH/fansz-relations-service-2.0.0-SNAPSHOT/bin/shutdown.sh  
