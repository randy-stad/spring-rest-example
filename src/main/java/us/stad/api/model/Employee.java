package us.stad.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Employee {

  private @Id @GeneratedValue Long id;

  private String firstName;
  private String middleInitial;
  private String lastName;
  private String dateOfBirth;
  private String dateOfEmployment;
  private String status = "ACTIVE";

  public Employee(String firstName, String middleInitial, String lastName, String dateOfBirth, String dateOfEmployment, String status) {
    this.firstName = firstName;
    this.middleInitial = middleInitial;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.dateOfEmployment = dateOfEmployment;
    this.status = status;
  }

  /**
   * Update employee information. You cannot update the identifier or the status
   * through this method.
   * 
   * @param employee the new employee information
   */
  public void update(Employee employee) {
    this.firstName = employee.firstName;
    this.middleInitial = employee.middleInitial;
    this.lastName = employee.lastName;
    this.dateOfBirth = employee.dateOfBirth;
    this.dateOfEmployment = employee.dateOfEmployment;
  }

}
