FROM jenkins:2.32.3
MAINTAINER renaud

USER root
RUN mkdir /var/log/jenkins
RUN chown -R  jenkins:jenkins /var/log/jenkins
RUN apt-get update \
      && apt-get install -y sudo \
      && rm -rf /var/lib/apt/lists/*
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
#TODO the group ID for docker group on my Ubuntu is 125, therefore I can only run docker commands if I have same group id inside.
# Otherwise the socket file is not accessible.
USER jenkins

# docker build -t renaud/jenkins:1.0 .
# docker run -d --name test_jenkins -v CDB_Volume:/cdb -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -p 8080:8080     renaud/jenkins:1.0

# pwd; ls ; ls /cdb ;git clone -b develop --single-branch https://github.com/rndAdn/computer-database.git /cdb/ ; docker run --rm --name test_maven -v CDB_Volume:/usr/src/app renaud/maven:1.0
