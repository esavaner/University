package variables is
   mode: Boolean := False;
   start: Boolean := False;
   
   magazineSize: constant Integer := 10;
   taskListSize: constant Integer := 10;
   serviceSize: constant Integer := 20;
   
   employeesNumber: constant Integer := 4;
   serviceWorkerNumber: constant Integer := 2;

   menagerDelay: constant Duration := Duration(1);
   workerDelay: constant Duration := Duration(2);
   clientDelay: constant Duration := Duration(3);
   serviceWorkerDelay: constant Duration := Duration(2);
   
   addingMachineNumber: constant Integer := 2;
   multMachineNumber: constant Integer := 2;
   machineProbability: constant Integer := 50;
   
   addingMachineDelay: constant Duration := Duration(2);
   multMachineDelay: constant Duration := Duration(2);
   
   impatientDelay: constant Duration := Duration(3);
end variables;
