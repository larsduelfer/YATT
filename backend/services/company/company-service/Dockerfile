FROM gcr.io/distroless/java17-debian12
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} company-service.jar
#User nonroot (ID 65532) is a user created by distroless images
#See https://github.com/GoogleContainerTools/distroless/issues/235
USER 65532
CMD ["/company-service.jar"]