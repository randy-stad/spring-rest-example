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

import us.stad.api.model.Employee;
import us.stad.api.repository.EmployeeRepository;

@RestController
class EmployeeController {

  private final EmployeeRepository repository;

  EmployeeController(EmployeeRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/employees")
  List<Employee> getAll() {
    return repository.findAll().stream().filter(e -> "ACTIVE".equals(e.getStatus())).collect(Collectors.toList());
  }

  @PostMapping("/employees")
  Employee create(@RequestBody Employee newEmployee) {
    return repository.save(newEmployee);
  }

  @GetMapping("/employees/{id}")
  Employee get(@PathVariable Long id) {
    final Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    if ("ACTIVE".equals(employee.getStatus())) {
      return employee;
    }
    throw new EmployeeNotFoundException(id);
  }

  @PutMapping("/employees/{id}")
  Employee update(@RequestBody Employee newEmployee, @PathVariable Long id) {
    return repository.findById(id).map(employee -> {
      employee.update(newEmployee);
      return repository.save(employee);
    }).orElseThrow(() -> new EmployeeNotFoundException(id));
  }

  @DeleteMapping("/employees/{id}")
  void delete(@PathVariable Long id) {
    repository.findById(id).map(employee -> {
      employee.setStatus("INACTIVE");
      return repository.save(employee);
    }).orElseThrow(() -> new EmployeeNotFoundException(id));
  }
}