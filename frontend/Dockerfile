# Use an official latest LTS Node.js image
FROM node:20-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package.json ./

# Install dependencies inside the container
RUN npm install

# Copy the rest of the application code
COPY . .

# Expose the port Vite's development server will run on
EXPOSE 5173

# Command to start the Vite development server
CMD ["npm", "run", "dev"]
