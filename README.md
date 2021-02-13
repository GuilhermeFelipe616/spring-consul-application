# spring-consul-application

A simple application showing how get **Spring application properties** from **Consul KV Store** and how to integrate **Consul KV Store** with Git/Gitlab repositories by using **git2consul**.

The properties used in this project are mapped in my [spring-consul-config](https://github.com/GuilhermeFelipe616/spring-consul-config) repository.

##### What is Consul?
> Consul is a service mesh solution providing a full featured control plane with service discovery, configuration, and segmentation functionality. Each of these features can be used individually as needed, or they can be used together to build a full service mesh.

Access [Consul documentation](https://www.consul.io/docs/intro) for mor information.

##### What is git2consul?
> git2consul takes one or many git repositories and mirrors them into Consul KVs. The goal is for organizations of any size to use git as the backing store, audit trail, and access control mechanism for configuration changes and Consul as the delivery mechanism.

Access [git2consul project](https://github.com/breser/git2consul) for mor information.

### Requirements
- Java 11
- Docker and Docker Compose

### Docker

The project uses **docker-compose** to configure and run 2 services: **consul** and **git2consul**.

#### Consul

```yaml
consul:
    image: consul
    container_name: consul
    ports:
      - "8500:8500"
    volumes:
      - ./config:/consul/config
```

Inside the **config** folder there a file named **config.json**, which is used to register our service in Consul service discovery:

```json
{
  "services": [
    {
      "id": "spring-consul-application",
      "name": "spring-consul-application",
      "tags": ["primary"],
      "port": 8080
    }
  ]
}
```

Access [Consul Service Discovery documentation](https://www.consul.io/docs/discovery/services) for more details.   

#### git2consul

```yaml
git2consul:
    image: cimpress/git2consul
    depends_on:
      - consul
    container_name: git2consul
    volumes:
      - .:/config
    command: --endpoint consul --config-file /config/git2consul.json
```

The file **git2consul.json** is used to inform the minimal configuration required by git2consul:

```json
{
  "version": "1.0",
  "repos" : [
    {
      "url" : "https://github.com/GuilhermeFelipe616/spring-consul-config.git",
      "name" : "config",
      "branches" : ["master"],
      "expand_keys": true,
      "include_branch_name" : false,
      "hooks": [{
        "type" : "polling",
        "interval" : "1"
      }]
    }
  ]
}
```

Access [git2consul configuration section](https://github.com/breser/git2consul#configuration) for more details.

### Running the project

#### Setting up the environment
 
1) Clone the project;
2) Open a bash window inside the project root folder;
3) Run ``docker-compose up`` command;
4) Start the spring application;

**Obs**: Remember to start Docker before running

#### Consulting the properties

The properties can be consulted by requesting the REST API like below:

 ```
[GET] http://localhost:8080/spring-consul/name
[GET] http://localhost:8080/spring-consul/nick
[GET] http://localhost:8080/spring-consul/code
 ```
