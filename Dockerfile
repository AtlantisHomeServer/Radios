FROM java:8
ADD target/scala-2.11/atlantis_radio_server.jar /root/atlantis_radio_server.jar
ENTRYPOINT java -jar /root/atlantis_radio_server.jar

EXPOSE 8888 9990