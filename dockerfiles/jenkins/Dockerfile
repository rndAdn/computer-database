FROM jenkins:2.32.3
MAINTAINER renaud

USER root
RUN mkdir /var/log/jenkins
RUN chown -R  jenkins:jenkins /var/log/jenkins
RUN mkdir /cdb
RUN chown -R  jenkins:jenkins /cdb
# RUN mkdir /cdb_war
# RUN chown -R  jenkins:jenkins /cdb_war
RUN apt-get update \
      && apt-get install -y sudo \
      && rm -rf /var/lib/apt/lists/*
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
RUN groupadd -g 999 docker && usermod -a -G docker jenkins
## CHANGER LE GROUP ID '994' avec le groupe ID de docker `getent group`

USER jenkins

# docker build -t renaud/jenkins:1.0 .
# docker run -d --name test_jenkins -v CDB_Volume:/cdb -v WAR_CDB:/cdb_war -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -p 8085<:8080 renaud/jenkins:1.0
# docker exec 3209bbcbdeae cat /var/jenkins_home/secrets/initialAdminPassword

# cp -a ./* /cdb ; docker start test_maven
# cp -a /cdb/target/computerdb.war /cdb_war
