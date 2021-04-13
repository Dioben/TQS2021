##### A
Examples of assertj usage:
- assertThat( found ).isEqualTo(alex);
- assertThat(fromDb).isNull();
- assertThat(fromDb).isNotNull();
- assertThat(fromDb.getEmail()).isEqualTo( emp.getEmail());
- assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(...);
##### B
Repository was mocked for purpose B (EmployeeService_UnitTest)
```java
 @Mock( lenient = true)
    private EmployeeRepository employeeRepository;
    
@BeforeEach
    public void setUp() {
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }
```
##### C
We can use the @MockBean to add mock objects to the Spring application context. The mock will replace any existing bean of the same type in the application context.
If no bean of the same type is defined, a new one will be added. This annotation is useful in integration tests where a particular bean – for example, an external service – needs to be mocked
When we use the annotation on a field, as well as being registered in the application context, the mock will also be injected into the field.
[source](https://www.baeldung.com/java-spring-mockito-mock-mockbean)
It registers mocked classes as beans on top of normal mock functionality.
##### D
It is used for to override application properties for Integration Tests
