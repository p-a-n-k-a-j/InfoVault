# Use an official Tomcat base image
FROM tomcat:9.0

# Remove default Tomcat web applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your .war file from the build folder into the Tomcat webapps directory
COPY build/EmployeeDataManager.war /usr/local/tomcat/webapps/ROOT.war

# Expose Tomcat port
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]
