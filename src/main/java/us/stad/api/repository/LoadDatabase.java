package us.stad.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import us.stad.api.model.Employee;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileNotFoundException;

@Component
@Slf4j
class LoadDatabase implements CommandLineRunner {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  public void run(String... args) throws Exception {
    for (String arg : args) {
      try {
        String data = FileUtils.readFileToString(new File(arg));
        log.info("Loading database from file: " + arg);
        ObjectMapper mapper = new ObjectMapper();
        Employee[] employees = mapper.readValue(data, Employee[].class);
        for (Employee employee : employees) {
          employeeRepository.save(employee);
        }
      } catch (FileNotFoundException e) {
        log.info("Error loading database from file: " + arg + " (file not found)");
      }
    }
  }
}