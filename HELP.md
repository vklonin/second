
### step by step run process

```
git clone https://github.com/vklonin/second
cd second
mvn clean package -DskipTest    
docker run -d -p 27017:27017 -v mongodb_data:/data/db --name my-mongo mongo:latest     
mvn spring-boot:run
```
make sure that my-mongo name is avalible for a docker container name - or rename it

### check application
http://localhost:8080/api/base

http://localhost:8080/api/fenbase

http://localhost:8080/api/fendata?fen=rnbqkb1r/pppppppp/8/3n4/2PP4/8/PP2PPPP/RNBQKBNR%20w%20KQkq%20-%201%203
