FROM ibmcom/websphere-traditional:9.0.5.24

USER root

# OpenTelemetry Java Agent
RUN mkdir -p /opt/opentelemetry

COPY Observability/opentelemetry-javaagent-2.31.1.jar \
     /opt/opentelemetry/opentelemetry-javaagent.jar

RUN chmod 644 /opt/opentelemetry/opentelemetry-javaagent.jar

# Configure OpenTelemetry Java Agent in WebSphere server1
RUN sed -i 's|genericJvmArguments="-Xnoloa"|genericJvmArguments="-Xnoloa -javaagent:/opt/opentelemetry/opentelemetry-javaagent.jar"|' \
    /opt/IBM/WebSphere/AppServer/profiles/AppSrv01/config/cells/DefaultCell01/nodes/DefaultNode01/servers/server1/server.xml
