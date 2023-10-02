docker compose down
# remove previous docker image
docker rmi `docker images | grep particle-server-web | awk '{print $3}'` 
# pull change
git pull
# build and deploy
docker compose up -d
