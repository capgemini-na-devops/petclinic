FROM tomcat:8.0.51-jre8-alpine

MAINTAINER sahana

#removing tomcat-users file in the container
RUN rm -rf /usr/local/tomcat/conf/tomcat-users.xml \
&& cd /usr/local/tomcat/conf/ \
&& wget https://kubernetes-repo.s3-us-west-2.amazonaws.com/AWS/Dockerized+Tools/tomcat-users.xml

#copying tomcat-users file from host to container
#COPY tomcat-users.xml /usr/local/tomcat/conf/

#copying war file file from host to container
COPY ./target/petclinic.war /usr/local/tomcat/webapps/

#exposing 8080 port in container
EXPOSE 8080



#starting the tomcat
CMD ["catalina.sh", "run"]
