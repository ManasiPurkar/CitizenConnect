
# Stage 1: Build the React application
#FROM node:20 AS build

# Set working directory
#WORKDIR /app

# Copy package.json and package-lock.json
#COPY ../Frontend/package.json ../Frontend/package-lock.json  ../Frontend/babel.config.js ./

#COPY ./Frontend/package.json ./Frontend/package-lock.json  ./Frontend/babel.config.js ./


# Install dependencies
#RUN npm install

# Copy the rest of the application code
#COPY ./Frontend ./

#RUN npm install -g expo-cli

#EXPOSE 19000
#EXPOSE 19001
#EXPOSE 19002

#CMD npm i -f && npm start

# Use the official Node.js image as the base image
FROM  node:latest AS builder

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the entire frontend directory to the working directory
COPY . .

# Expose the port the app runs on
EXPOSE 8081
#EXPOSE 19001
#EXPOSE 19002

# set environment variables
#ARG REACT_APP_SERVER_URL
#ENV REACT_APP_SERVER_URL $REACT_APP_SERVER_URL

# Command to run the app
CMD ["npm", "start"]
