package us.stad.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
public class Employee {

  @ApiModelProperty(notes = "The database generated employee ID")
  private @Id @GeneratedValue Long id;

  @ApiModelProperty(notes = "Employee first name")
  private String firstName;
  @ApiModelProperty(notes = "Employee middle initial")
  private String middleInitial;
  @ApiModelProperty(notes = "Employee last name")
  private String lastName;
  @ApiModelProperty(notes = "Employee date of birth")
  private String dateOfBirth;
  @ApiModelProperty(notes = "Employee date of employment")
  private String dateOfEmployment;
  @ApiModelProperty(notes = "Employee status")
  private String status = "ACTIVE";

  public Employee() {
    super();
  }

  public Employee(String firstName, String middleInitial, String lastName, String dateOfBirth, String dateOfEmployment,
      String status) {
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
