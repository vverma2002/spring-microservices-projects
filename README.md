# spring-microservices-projects
Spring Microservices Projects


#### Maven Create Project Guide 
###### To Create Archtype Project From Existing Project :
```CMD
mvn archetype:create-from-project
```


###### To create New Archtype Project :
```CMD
mvn archetype:generate -DgroupId=com.vik.archetype -DartifactId=spring-microsevice-archetype  -DarchetypeGroupId=org.apache.maven.archetypes  -DarchetypeArtifactId=maven-archetype-archetype
```
```CMD
mvn archetype:generate -DgroupId=com.vik.archetype -DartifactId=spring-microsevice-project-archetype  -DarchetypeGroupId=org.apache.maven.archetypes  -DarchetypeArtifactId=maven-archetype-archetype
```


###### To create Projects from the Archtype :
```CMD
mvn archetype:generate -DarchetypeGroupId=com.vik.archetype -DarchetypeArtifactId=spring-microsevice-project-archetype -DarchetypeVersion=0.0.1-SNAPSHOT  -DgroupId=com.vik.os  -DartifactId=online-shpping-app
```
