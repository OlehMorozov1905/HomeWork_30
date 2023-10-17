package dao;

import lesson_29_abstract.Employee;

public interface Company {

   boolean addEmployee(Employee employee);
   Employee removeEmployee(int id);
   Employee findEmployee(int id);
   double totalSalary();
   int quantity();
   double avgSalary();
   double totalSales();
   void printEmployees();


}
