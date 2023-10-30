Certainly, here's an improved and more concise README for your Trollo project, including installation instructions and commands:

# Trollo - A Trello Clone

Trollo is a Trello clone with a server written in Java using Spring Boot and Hibernate with H2 for both testing and running, and a client running React with TypeScript through Vite, using Redux for state management.

## Installation

Follow these steps to get Trollo up and running:

### Server

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/yourusername/trollo.git
   cd trollo
   ```

2. **Build the Java Backend:**

   To build the Java backend, you'll need the Java Development Kit (JDK) installed on your system. Run the following command from the project root directory:

   ```bash
   cd ./server
   ./gradlew clean install
   ```

   This command will compile the server code and create the necessary JAR file.

3. **Start the Server:**

   After building the backend, you can start the server using the following command:

   ```bash
   ./gradlew spring-boot:run
   ```

### Client

1. **Navigate to the Client Directory from the trollo folder:**

   ```bash
   cd client
   ```

2. **Install Node Packages:**

   To install the required Node.js packages for the client, run:

   ```bash
   npm install
   ```

3. **Start the Client:**

   Run the client application using Vite's development server:

   ```bash
   npm run dev
   ```

## Swagger Documentation

After starting the server, you can access the Swagger documentation at:

[http://localhost:3000/swagger-ui.html](http://localhost:3000/swagger-ui.html)
