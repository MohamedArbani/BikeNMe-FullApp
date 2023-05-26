# Use an official Node.js runtime as a parent image
FROM node:latest
# Set the working directory to /app
WORKDIR /app
# Copy the package.json and package-lock.json to /app
COPY package*.json ./
# Install any needed packages specified in package.json
RUN npm install
# Copy the rest of the application code to /app
COPY . .
# Expose the port that the application will be running on
EXPOSE 4200
# Run the command to start the server
CMD ["npm", "start"]