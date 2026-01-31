# Movie Reservation System

## Running with Docker

### Build the application

**Windows:**
```bash
mvnw.cmd clean package
```

**Linux/Mac:**
```bash
./mvnw clean package
```

### Build Docker image
```bash
docker build -t movie-reservation .
```

### Run the container
```bash
docker run -p 8080:8080 movie-reservation
```

### Access the application
```
http://localhost:8080/welcome
```

### Stop the container
```bash
docker ps
docker stop <container_id>
```
