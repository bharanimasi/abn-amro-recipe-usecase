# abn-amro-recipe-usecase
API Service which allows user to manage their favourite recipes . As part of thie microservices below operations can be performed by user
1. Add new recipes
2. Update existing recipe
3. Removing existing recipe
4. Search recipes based on recipe name ,type , serving numbers , ingredients and text search on instruction

**Design Principles Considered:**

As part of implementation below design principles are considered

**1. Single Responsibility Principle :**
      As the name suggests, this principle states that each class should have one responsibility, one single purpose. This means that a class will do only one job, which leads us to conclude it should have only one reason to change . According this class created purse their own responsibilites as controller, service , mappers, converters so on. The classes created for implememtation will adhere to one functionality. Their methods and data will be concerned with one clear purpose. This means high cohesion, as well as robustness, which together reduce errors.
      
**2. Interface Segregation Principle**  
      Precise application design and correct abstraction is the key behind the Interface Segregation Principle . Looked at a simple scenario, where  first deviated from following the interfaces such as repository , Mappers and Service   how to apply the principle correctly in order to achieve clean responsibility implementation.

**3. Dependency Inversion Principle**
            Every dependency in the design should target an interface or an abstract class. No dependency should target a concrete class.Object creation code is centralized in the framework and  code is not messed up with any future implemenetation

Design Pattern Considered:

1. **Database per Service**
      Accessed by the microservice API only.For relational databases, use private-tables-per-service, schema-per-service, or database-server-per-service. Each microservice should have a separate database id so that separate access can be given to put up a barrier and prevent it from using other service tables.
 
2. **Decomposition Patterns**
      One strategy is to decompose by business capability. A business capability is something that a business does in order to generate value. The set of capabilities for a given business.
           


**Steps to builld and run application :**

1. Clone the project from GITHUB : https://github.com/bharanimasi/abn-amro-recipe-usecase
2. mvn clean install (to clean and compile and generate jar)
3. mvn spring-boot:run

Note : For local verification H2 database is configure as part of application . Table will automatically created during startup

Docker based application steps below

1. docker build -f DockerFile -t bharanimasi11/bharanirepo:amnapp . ( specify docker file with path if executed from different location and tag name along with docker repository , added personal repository for sample )
2. docker push bharanimasi11/bharanirepo:amnapp
3. docker run bharanimasi11/bharanirepo:amnapp . (Application will startup in port mentioned in application properties)

Once application up, below url can be used :

1. Health Check : http://localhost:8081/actuator/health
2. Swagger Documentation : http://localhost:8081/swagger-ui/index.html



   




