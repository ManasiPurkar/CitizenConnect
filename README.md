# CitizenConnect
This project aims to develop Citizen Connect, a comprehensive application designed to streamline the registration and management of government complaints submitted by citizens. The current process for reporting government-related issues often suffers from inefficiency and lack of transparency, leading to delays in complaint resolution and citizen dissatisfaction.

Functionalities -
  1. Registration of all citizens.
  2. Admin will register a nagarsevak for a specific area.
  3. Citizens can register complaints.
  4. Citizens can view complaints registered by them and in their area separately.
  5. Citizens can comment on any complaint.
  6. A nagarsevak can view complaints registered in his/her area.
  7. A nagarsevak can comment on a complaint to provide a status update of the
  work.
  8. Nagarsevak can change the status of the complaint. There are three status
  possibilities namely pending, ongoing and resolved.

Technical Stack:
  ● Frontend: React Native
  ● Backend: Java Spring Boot
  ● Database: MySQL
  ● Version Control System (VCS): Git
  ● Containerization: Docker
  ● Container Orchestration : Docker compose
  ● CI/CD Tool: Jenkins
  ● Logging and Monitoring: ELK Stack

STEPS TO BUILD:

Step 1: Create a Frontend application in React Native and a Backend application in SpringBoot. After creation of these applications separately, integrate them both. Check if the application works fine after integration. Push all the changes into the Github
repository whenever any new changes are made.

Step 2: Create the following files - Jenkinsfile, Ansible Inventory file, Ansible Playbook, DockerFiles for Frontend application, User service, Complaint service, Service registry and API Gateway. Create Docker Compose file for following services - serviceregistry, apigateway, userservice, complaintservice, userservice_db, complaintservice_db, citizenconnectfrontend, elasticsearch, kibana and logstash.

Step 3: Install Jenkins and Install following plugins : Docker, Ansible, Git and Pipeline.

Step 4: Setting Up Jenkins Pipeline Job
  1. Create a New Pipeline Job: Go to Jenkins, create a new item, and select
  "Pipeline".
  2. Configure Pipeline:
    ● Under the "Pipeline" section, set "Definition" to "Pipeline script from
    SCM".
    ● Select "Git" and provide the repository URL where your Jenkinsfile is
    stored.
    ● Specify the script path if it's not in the root of the repository.
  3. Add Credentials:
    ● Add Docker Hub credentials in Jenkins under "Credentials" with the
    ID docker-hub-credentials-id.
    ● Change credentials in inventory file according to you credentials.
  4. Save and Build: Save the configuration and trigger a build. Jenkins will now
  pull the code, build Docker images, push them to Docker Hub, and deploy
  the services using Ansible.
Step 5: We can check on our localhost if all containers are running using command “docker ps -a”. Now check all logs on elastic search. Navigate to “localhost:5601” and verify logs as needed.

Step 6: Admin user needs to be added manually. Use following API: "http://localhost:9093/v1/auth/register/Admin" (Instead of localhost, add ngrok's public IP). Provide following body: email, password and role.

Step 7: Insert area queries into "userservice_db" container and department queries in "complaintservice_db". Queries are present in UserService folder named as "area insert queries" file.
