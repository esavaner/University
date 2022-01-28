package variables is
    mode: Boolean := False;
    start: Boolean := False;
   
    magazineSize: constant Integer := 4;
    taskListSize: constant Integer := 6;
   
    employeesNumber: constant Integer := 4;

    menagerDelay: constant Duration := Duration(1);
    workerDelay: constant Duration := Duration(15);
   clientDelay: constant Duration := Duration(30);
   
   addingMachineNumber: constant Integer := 4;
   multMachineNumber: constant Integer := 4;
   
   addingMachineDelay: constant Duration := Duration(5);
   multMachineDelay: constant Duration := Duration(5);
   
   impatientDelay: constant Duration := Duration(3);
end variables;
