# docker dependence start up script
# start up mysql
# docker run -d -p 3306:3306 mysql
# start up rabbitmq
docker run -d -p 5672:5672 -p 15672:15672 rabbitmq:management
# start up redis
docker run -d -p 6379:6379 redis
