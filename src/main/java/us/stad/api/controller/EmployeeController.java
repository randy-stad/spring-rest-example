package us.stad.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import us.stad.api.model.Employee;
import us.stad.api.repository.EmployeeRepository;

@RestController
@Api(value="employees", description="Operations pertaining to employees")
class EmployeeController {

  private final EmployeeRepository repository;

  EmployeeController(EmployeeRepository repository) {
    this.repository = repository;
  }

  @ApiOperation(value = "View a list of employees", response = Iterable.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully retrieved list"),
  })
  @GetMapping("/employees")
  List<Employee> getAll() {
    return repository.findAll().stream().filter(e -> "ACTIVE".equals(e.getStatus())).collect(Collectors.toList());
  }

  @ApiOperation(value = "Create a new employee", response = Employee.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully created resource"),
  })
  @PostMapping("/employees")
  Employee create(@RequestBody Employee newEmployee) {
    Employee employee = new Employee();
    employee.update(newEmployee);
    return repository.save(employee);
  }

  @ApiOperation(value = "Get an employee by id", response = Employee.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully retrieved resource"),
    @ApiResponse(code = 404, message = "Resource not found")
  })
  @GetMapping("/employees/{id}")
  Employee get(@PathVariable Long id) {
    final Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    if ("ACTIVE".equals(employee.getStatus())) {
      return employee;
    }
    throw new EmployeeNotFoundException(id);
  }

  @ApiOperation(value = "Update an employee by id", response = Employee.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully updated resource"),
    @ApiResponse(code = 404, message = "Resource not found")
  })
  @PutMapping("/employees/{id}")
  Employee update(@RequestBody Employee newEmployee, @PathVariable Long id) {
    return repository.findById(id).map(employee -> {
      employee.update(newEmployee);
      return repository.save(employee);
    }).orElseThrow(() -> new EmployeeNotFoundException(id));
  }

  @ApiOperation(value = "Delete an employee by id", response = Employee.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully deleted resource"),
    @ApiResponse(code = 401, message = "You are not authorized to delete the resource"),
    @ApiResponse(code = 404, message = "Resource not found")
  })
  @DeleteMapping("/employees/{id}")
  void delete(@PathVariable Long id) {
    repository.findById(id).map(employee -> {
      employee.setStatus("INACTIVE");
      return repository.save(employee);
    }).orElseThrow(() -> new EmployeeNotFoundException(id));
  }

}