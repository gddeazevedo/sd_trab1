# Trab 1 Sistemas Distribuídos

Sistema distribuído com Load Balancer, Client e Servidores Backend.

## Pré-requisitos

- Java 25
- Maven 3.9+
- Docker e Docker Compose (para execução containerizada)

---

## Execução Local (sem Docker)

### 1. Compilar os projetos

```bash
# Compilar Load Balancer
cd sdloadbalancer
mvn clean package

# Compilar Client
cd ../sdclient
mvn clean package

# Compilar Server
cd ../sdserver
mvn clean package
```

### 2. Executar os serviços

```bash
# Terminal 1 - Load Balancer
cd sdloadbalancer
java -jar target/sdloadbalancer-1.0-SNAPSHOT.jar

# Terminal 2 - Client
cd sdclient
java -jar target/sdclient-1.0-SNAPSHOT.jar
```

### Variáveis de Ambiente (opcional)

**Load Balancer:**
- `PORT` - Porta do load balancer (padrão: 8888)
- `SERVERS` - Lista de servidores no formato `host1:port1,host2:port2`

**Client:**
- `LOADBALANCER_HOST` - Host do load balancer (padrão: localhost)
- `LOADBALANCER_PORT` - Porta do load balancer (padrão: 8888)

---

## Execução com Docker Compose

### Construir e executar todos os serviços

```bash
docker compose up --build
```

### Executar em background

```bash
docker compose up --build -d
```

### Parar os serviços

```bash
docker compose down
```

### Reconstruir um serviço específico

```bash
docker compose build loadbalancer
docker compose build client
```

---

## Acessar Containers Individualmente

### Ver logs de cada serviço

```bash
# Logs do Load Balancer
docker compose logs -f loadbalancer

# Logs do Client
docker compose logs -f client

# Logs de todos os serviços
docker compose logs -f
```

### Executar serviços em terminais separados

```bash
# Terminal 1 - Iniciar apenas o Load Balancer
docker compose up --build loadbalancer

# Terminal 2 - Iniciar apenas o Client
docker compose up --build client
```

### Acessar o shell de um container

```bash
# Entrar no container do Load Balancer
docker compose exec loadbalancer bash

# Entrar no container do Client
docker compose exec client bash
```

### Verificar status dos containers

```bash
docker compose ps
```
