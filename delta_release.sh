#!/bin/sh
LOCAL_PATH=/Users/wukai/projects/fansz/fansz-backend
REMOTE_PATH=/usr/local/applications

APP_VERSION=2.0.0
ACCESS_LAYER_PATH=access-layer-$APP_VERSION-SNAPSHOT
OAUTH_PATH=fansz-auth-oauth-$APP_VERSION-SNAPSHOT
EVENT_PATH=event-server-$APP_VERSION-SNAPSHOT
FANDOM_PATH=fansz-fandom-service-$APP_VERSION-SNAPSHOT
NEWSFEEDS_PATH=fansz-newsfeeds-service-$APP_VERSION-SNAPSHOT
RELATIONS_PATH=fansz-relations-service-$APP_VERSION-SNAPSHOT
TOKEN_PATH=fansz-token-service-$APP_VERSION-SNAPSHOT

pssh -Pi -h hosts.txt $REMOTE_PATH/$ACCESS_LAYER_PATH/bin/shutdown.sh
pssh -Pi -h hosts.txt $REMOTE_PATH/$OAUTH_PATH/bin/shutdown.sh
pssh -Pi -h hosts.txt $REMOTE_PATH/$EVENT_PATH/bin/shutdown.sh
pssh -Pi -h hosts.txt $REMOTE_PATH/$FANDOM_PATH/bin/shutdown.sh
pssh -Pi -h hosts.txt $REMOTE_PATH/$NEWSFEEDS_PATH/bin/shutdown.sh
pssh -Pi -h hosts.txt $REMOTE_PATH/$RELATIONS_PATH/bin/shutdown.sh
pssh -Pi -h hosts.txt $REMOTE_PATH/$TOKEN_PATH/bin/shutdown.sh

pssh -Pi -h hosts.txt rm -rf $REMOTE_PATH/$ACCESS_LAYER_PATH/lib/$ACCESS_LAYER_PATH.jar
pssh -Pi -h hosts.txt rm -rf $REMOTE_PATH/$OAUTH_PATH/lib/$OAUTH_PATH.jar
pssh -Pi -h hosts.txt rm -rf $REMOTE_PATH/$EVENT_PATH/lib/$EVENT_PATH.jar
pssh -Pi -h hosts.txt rm -rf $REMOTE_PATH/$FANDOM_PATH/lib/$FANDOM_PATH.jar
pssh -Pi -h hosts.txt rm -rf $REMOTE_PATH/$NEWSFEEDS_PATH/lib/$NEWSFEEDS_PATH.jar
pssh -Pi -h hosts.txt rm -rf $REMOTE_PATH/$RELATIONS_PATH/lib/$RELATIONS_PATH.jar
pssh -Pi -h hosts.txt rm -rf $REMOTE_PATH/$TOKEN_PATH/lib/$TOKEN_PATH.jar

pscp -h hosts.txt -e error.log $LOCAL_PATH/fansz-access-layer/target/$ACCESS_LAYER_PATH.jar $REMOTE_PATH/$ACCESS_LAYER_PATH/lib/
pscp -h hosts.txt -e error.log $LOCAL_PATH/fansz-auth/fansz-auth-oauth/target/$OAUTH_PATH.jar $REMOTE_PATH/$OAUTH_PATH/lib/
pscp -h hosts.txt -e error.log $LOCAL_PATH/fansz-event-server/target/$EVENT_PATH.jar $REMOTE_PATH/$EVENT_PATH/lib/
pscp -h hosts.txt -e error.log $LOCAL_PATH/fansz-fandom/fansz-fandom-service/target/$FANDOM_PATH.jar $REMOTE_PATH/$FANDOM_PATH/lib/
pscp -h hosts.txt -e error.log $LOCAL_PATH/fansz-newsfeeds/fansz-newsfeeds-service/target/$NEWSFEEDS_PATH.jar $REMOTE_PATH/$NEWSFEEDS_PATH/lib/
pscp -h hosts.txt -e error.log $LOCAL_PATH/fansz-relations/fansz-relations-service/target/$RELATIONS_PATH.jar $REMOTE_PATH/$RELATIONS_PATH/lib/
pscp -h hosts.txt -e error.log $LOCAL_PATH/fansz-token/fansz-token-service/target/$TOKEN_PATH.jar $REMOTE_PATH/$TOKEN_PATH/lib/

pssh -P -h hosts.txt $REMOTE_PATH/$RELATIONS_PATH/bin/startup.sh
pssh -P -h hosts.txt $REMOTE_PATH/$NEWSFEEDS_PATH/bin/startup.sh
pssh -P -h hosts.txt $REMOTE_PATH/$FANDOM_PATH/bin/startup.sh
pssh -P -h hosts.txt $REMOTE_PATH/$EVENT_PATH/bin/startup.sh
pssh -P -h hosts.txt $REMOTE_PATH/$OAUTH_PATH/bin/startup.sh
pssh -P -h hosts.txt $REMOTE_PATH/$TOKEN_PATH/bin/startup.sh
sleep 3s
pssh -P -h hosts.txt $REMOTE_PATH/$ACCESS_LAYER_PATH/bin/startup.sh
