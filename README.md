# Tech_Eazy_Task
Running from IDE
If you are using an Integrated Development Environment (IDE) like IntelliJ IDEA, Eclipse, or Spring Tool Suite (STS), you can easily run the application directly from the IDE:
1. Open the project in your IDE.
2. Locate the main application class annotated with @SpringBootApplication. This class typically contains the main method.
3. Right-click on the class and select Run (or use the run button in the IDE).

Accessing the Application
Once the application is running, you can access it via:
Localhost: http://localhost:8080

Make use of Postman to Test API's
Post : "/user"      RequestBody : AppUserRequest
Post : "/auth"      RequestBody : AuthRequest
Post : "/student"   RequestBody : StudentRequest
Post : "/subject"   RequestBody : SubjectRequest
Put  : "/student"   RequestParam : student id, RequestParam : subject id
Get  : "/students"  
Get  : "/subjects"
