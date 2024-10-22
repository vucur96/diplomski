FROM node:18-alpine


# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install dependencies
RUN npm install 

# Copy rest of application code
COPY . .

# Expose the port the app runs on in this case we use vite which typically runs on port 5173 for dev
EXPOSE 5173

# Command to run the app
CMD ["npm","run","dev"]