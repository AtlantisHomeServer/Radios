##Atlantis Radio Server

Radio server is api server to control signaling server.

Current signaling server is ejabberd. we can extend later.

### Run server in local instance

just type

```bash
$ bin/activator run
```

### Build dockerfile

**atlantis-ejabberd-docker should be running**

just type

```bash
# example - sh ./build_docker.sh 0.0.1
$ sh ./build_docker.sh $VERSION
```

Now docker images will generate

### Build and push dockerfile

```bash
# example - sh ./build_docker.sh 0.0.1 publish
$ sh ./build_docker.sh $VERSION publish
```

Now docker image will generate and push to docker repo

###Swagger Support
Radio Server Support Swagger ui. 

You can see in [http://localhost:8888/api-docs/ui](http://localhost:8888/api-docs/ui)