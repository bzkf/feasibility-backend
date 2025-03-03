FROM eclipse-temurin:17-jre@sha256:b9f5c0acd03eb8275690fe310506f62743a5aaf90e37d165bc0484037a5a9376

RUN apt update -yqq && apt upgrade -yqq && \
    apt-get autoremove -y && apt-get clean && rm -rf /var/lib/apt/lists/

WORKDIR /opt/codex-feasibility-backend
COPY ./target/*.jar ./feasibility-gui-backend.jar
COPY ontology ontology

RUN addgroup --system feasibility && adduser --system feasibility --ingroup feasibility
RUN mkdir logging
RUN chown -R feasibility:feasibility /opt/codex-feasibility-backend

USER feasibility:feasibility

ARG VERSION=2.1.0
ENV APP_VERSION=${VERSION}
ENV FEASIBILITY_DATABASE_HOST="feasibility-network"
ENV FEASIBILITY_DATABASE_PORT=5432
ENV FEASIBILITY_DATABASE_USER=postgres
ENV FEASIBILITY_DATABASE_PASSWORD=password
ENV CERTIFICATE_PATH=/opt/codex-feasibility-backend/certs
ENV TRUSTSTORE_PATH=/opt/codex-feasibility-backend/truststore
ENV TRUSTSTORE_FILE=self-signed-truststore.jks

RUN mkdir -p $CERTIFICATE_PATH $TRUSTSTORE_PATH
RUN chown feasibility:feasibility $CERTIFICATE_PATH $TRUSTSTORE_PATH

HEALTHCHECK --interval=5s --start-period=10s CMD curl -s -f http://localhost:8090/actuator/health || exit 1

COPY ./docker-entrypoint.sh /
ENTRYPOINT ["/bin/bash", "/docker-entrypoint.sh"]

ARG GIT_REF=""
ARG BUILD_TIME=""
LABEL maintainer="medizininformatik-initiative" \
    org.opencontainers.image.created=${BUILD_TIME} \
    org.opencontainers.image.authors="medizininformatik-initiative" \
    org.opencontainers.image.source="https://github.com/medizininformatik-initiative/feasibility-backend" \
    org.opencontainers.image.version=${VERSION} \
    org.opencontainers.image.revision=${GIT_REF} \
    org.opencontainers.image.vendor="medizininformatik-initiative" \
    org.opencontainers.image.title="feasibility backend" \
    org.opencontainers.image.description="Provides backend functions for feasibility UI including query execution"
