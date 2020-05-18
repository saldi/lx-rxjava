FROM openjdk:14-alpine

RUN echo 'start'
RUN apk add --no-cache curl
RUN apk add --update bash && rm -rf /var/cache/apk/*
RUN apk add --no-cache unzip

RUN mkdir /opt/download -p
RUN mkdir /h2-data -p
RUN curl https://h2database.com/h2-2019-10-14.zip -o /opt/download/h2.zip
RUN unzip /opt/download/h2 -d /opt

ADD init.h2.db.sql /opt/init/

EXPOSE 9092 8080
WORKDIR /opt/h2/bin

RUN java -cp ./h2-1.4.200.jar org.h2.tools.RunScript  -user sa -password sa -script /opt/init/init.h2.db.sql  -url "jdbc:h2:/h2-data/database"
ENTRYPOINT  java -cp ./h2-1.4.200.jar org.h2.tools.Server -webAllowOthers -tcpAllowOthers -tcpPort 9092 -webPort 8082