with Text_IO;
use Text_IO;
with Ada.Numerics.Discrete_Random;


with variables;

procedure Main is
   type op is ('+', '-', '*');
   type taskT is
      record
         number: Integer;
         x: Integer;
         y: Integer;
         operation: op;
         result: Integer;
      end record;

   task type menager;

   task type worker is
      entry id (workerIdent : in Integer);
      entry Print;
   end worker;

   task type client is
      entry id (clientId : Integer);
   end client;

   task taskServer is
      entry newTask (taskNew: in taskT);
      entry getTask (taskNew: out taskT);
      entry getState (state: in Boolean);
   end taskServer;

   task magazineServer is
      entry newItem (item: in Integer);
      entry buyItem (item: out Integer);
      entry getState (state: in Boolean);
   end magazineServer;

   task type addingMachine is
      entry id (id : in Integer);
      entry newTasks (newTask: in taskT; result: out Integer);
      entry backdoor;
   end addingMachine;

   task type multMachine is
      entry id (id : in Integer);
      entry newTasks (newTask: in taskT; result: out Integer);
      entry backdoor;
   end multMachine;

   task machineService is
      entry newService(machineId: in Integer; machineType: in String);
      entry getService(machineId: out Integer; machineType: out String);
      entry servicesDone(machineId: in Integer; machineType: in String);
   end machineService;

   task type serviceWorker;


   package RandGen is
      function get_random(n: in Positive) return Integer;
   end RandGen;
   package body RandGen is
      subtype  Rand_Range is Positive;
      package Rand_Int is new  Ada.Numerics.Discrete_Random(Rand_Range);

      gen: Rand_Int.generator;
      function get_random(n: in Positive) return Integer is
      begin
         return Rand_Int.Random(gen) mod n;
      end get_random;
   begin
      Rand_Int.Reset(gen);
   end RandGen;

   addingMachines : array(0..variables.addingMachineNumber) of addingMachine;
   multMachines : array(0..variables.multMachineNumber) of multMachine;
   workers : array(0..variables.employeesNumber) of worker;
   servW: array (0..variables.serviceWorkerNumber) of serviceWorker;

   p : menager;
   c : client;
   UserInput: String(1..80);
   Last: Natural;
   UserInput1: String(1..80);
   Last1: Natural;

   task body menager is
      operations: array (0..2) of Op := ('+', '-', '*');
      taskNew: taskT;
      taskNumber: Integer := 1;

      subtype RangeNumbers is Integer range 0..100;
      subtype RangeOperations is Integer range 0..2;

      package RNum is new Ada.Numerics.Discrete_Random(RangeNumbers);
      package ROp is new Ada.Numerics.Discrete_Random(RangeOperations);
      GNum: RNum.Generator;
      GOp: ROp.Generator;
   begin
      menagerStart:
      loop
         if variables.start then
            taskNew := (taskNumber, RNum.Random(GNum), RNum.Random(GNum), operations(ROp.Random(GOp)), -1);
            taskNumber := taskNumber + 1;
            taskServer.newTask(taskNew);



            delay variables.menagerDelay;
         end if;
      end loop menagerStart;
   end menager;

   task body worker is
      workerId : Integer;
      currentTask: taskT;
      result: Integer;
      workerType : String(1..12);
      workerTypeA : array (0..1) of String(1..12) := (" cierpliwy  ", "niecierpliwy");
      currentM : Integer;
      done : Integer := 0;
   begin
      accept id (workerIdent: in Integer) do
         workerId := workerIdent;
      end Id;

      workerType := workerTypeA(RandGen.get_random(2));
      workerStart:
      loop
         if variables.start then
            select
               accept Print do
                  Put_Line("Pracownik " & workerType & " nr " & Integer'Image(workerId) & " zrobil: " & Integer'Image(done));
               end Print;
            else
                null;
            end select;
            taskServer.getTask(currentTask);


            case currentTask.operation is
            when '-' =>

               if workerType = "niecerpliwy" then
                  result := -2;
                  while result = -2 loop
                     currentM := RandGen.get_random(variables.addingMachineNumber);
                     select
                        addingMachines(currentM).newTasks(currentTask, result);
                     or
                        delay variables.impatientDelay;
                     end select;
                  end loop;
               else
                  currentM := RandGen.get_random(variables.addingMachineNumber);
                  addingMachines(currentM).newTasks(currentTask, result);
               end if;

               if result = -1 then
                  machineService.newService(currentM, "+");
               end if;

            when '+' =>

               if workerType = "niecerpliwy" then
                  result := -2;
                  while result = -2 loop
                     currentM := RandGen.get_random(variables.addingMachineNumber);
                     select
                        addingMachines(currentM).newTasks(currentTask, result);
                     or
                        delay variables.impatientDelay;
                     end select;
                  end loop;
               else
                  currentM := RandGen.get_random(variables.addingMachineNumber);
                  addingMachines(currentM).newTasks(currentTask, result);
               end if;

               if result = -1 then
                  machineService.newService(currentM, "+");
               end if;

            when '*' =>

               if workerType = "niecerpliwy" then
                  result := -2;
                  while result = -2 loop
                     currentM := RandGen.get_random(variables.multMachineNumber);
                     select
                        multMachines(currentM).newTasks(currentTask, result);
                     or
                        delay variables.impatientDelay;
                     end select;
                  end loop;
               else
                  currentM := RandGen.get_random(variables.multMachineNumber);
                  multMachines(currentM).newTasks(currentTask, result);
               end if;

               if result = -1 then
                  machineService.newService(currentM, "*");
               end if;

            end case;
            if variables.mode then
               Put_Line("Pracownik nr (" & Integer'Image(workerId) & ") wykonal zadanie " & Integer'Image(currentTask.number) & " z wynikiem " & Integer'Image(result));
            end if;
            done := done + 1;
            magazineServer.newItem(result);
            delay variables.workerDelay;
         end if;
      end loop workerStart;
   end worker;

   task body client is
      ident: Integer;
      item: Integer;
   begin
      accept id (clientId: in Integer) do
         ident := clientId;
      end id;
      clientStart:
      loop
         if variables.start then
            magazineServer.buyItem(item);
            if variables.mode then
               Put_Line("Klient kupil produkt o wartosci: " & Integer'Image(item));
            end if;
            delay variables.clientDelay;
         end if;
      end loop clientStart;
   end client;

   task body taskServer is
      tasks : array(0..variables.taskListSize) of taskT;
      tasksCount: Integer := 0;

      Head: Integer := 0;
      Tail: Integer := 0;
   begin
      taskStart:
      loop
         if variables.start then
            select
               when tasksCount < variables.taskListSize =>
                  accept newTask(taskNew: in taskT) do
                     tasks(Tail) := taskNew;
                  end newTask;
                  Tail := (Tail+1) mod variables.magazineSize;
                  tasksCount := tasksCount + 1;
            or
               when tasksCount > 0 =>
                  accept getTask(taskNew: out taskT) do
                     taskNew := tasks(Head);
                  end getTask;
                  Head := (Head + 1) mod variables.magazineSize;
                  tasksCount := tasksCount - 1;
            or
               accept getState(state: in Boolean) do
                  for I in tasks'Range loop
                     Put("[");
                     Put(Integer'Image(tasks(I).x));
                     Put(Op'Image(tasks(I).operation));
                     Put(Integer'Image(tasks(I).y));
                     Put("]");
                  end loop;
                  New_Line;
               end getState;
            end select;
         end if;
      end loop taskStart;
   end taskServer;

   task body magazineServer is
      Items: array (0..variables.magazineSize) of Integer;
      ItemsCount: Integer := 0;

      Head: Integer := 0;
      Tail: Integer := 0;
   begin
      magazineStart:
      loop
         if variables.start then
            select
               when ItemsCount < variables.magazineSize =>
                  accept newItem(item: in Integer) do
                     Items(Tail) := item;
                  end newItem;
                  Tail := (Tail+1) mod variables.magazineSize;
                  ItemsCount := ItemsCount+1;
            or
               when ItemsCount > 0 =>
                  accept buyItem (item: out Integer) do
                     item := Items(Head);
                  end buyItem;

                  Head := (Head + 1) mod variables.magazineSize;
                  ItemsCount := ItemsCount-1;
            or
               accept getState(state: in Boolean) do
                  Put("[");
                  for I in Items'Range loop
                     Put(Integer'Image(Items(I)));
                  end loop;
                  Put(" ]");
                  New_Line;
               end getState;
            end select;
         end if;
      end loop magazineStart;
   end magazineServer;

   task body addingMachine is
      ident: Integer;
      taskM: taskT;
      working: Boolean := True;
   begin
      accept id (id: in Integer) do
         ident := id;
      end id;
      loop
         select
            accept backdoor do
               working := true;
            end backdoor;
         else
            null;
         end select;
         if working and RandGen.get_random(100) > variables.machineProbability then
            working := false;
            Put_Line("Z: Zepsula sie maszyna dodajaca nr " & Integer'Image(ident));
         end if;
         accept newTasks(newTask: in taskT; result: out Integer) do
            taskM := newTask;
            if variables.mode then
                Put_Line("Maszyna" & Integer'Image(ident) & " doadaje " & Integer'Image(taskM.x) & " i " & Integer'Image(taskM.y));
            end if;
            delay variables.addingMachineDelay;

            if working then
               result := taskM.x + taskM.y;
            else
               result := -1;
            end if;
         end newTasks;
      end loop;
   end addingMachine;

   task body multMachine is
      ident: Integer;
      taskM: taskT;
      working: Boolean := True;
    begin
        accept id (id: in Integer) do
            ident := id;
        end id;
      loop
         select
            accept backdoor do
               working := true;
            end backdoor;
         else
            null;
         end select;

         if working = True and RandGen.get_random(100) > variables.machineProbability then
            working := false;
            Put_Line("Z: Zepsula sie maszyna mnozaca nr " & Integer'Image(ident));
         end if;
         accept newTasks(newTask: in taskT; result: out Integer) do
            taskM := newTask;
            if variables.mode then
                Put_Line("Maszyna" & Integer'Image(ident) & " mnozy " & Integer'Image(taskM.x) & " i " & Integer'Image(taskM.y));
            end if;
            delay variables.addingMachineDelay;

            if working then
               result := taskM.x * taskM.y;
            else
               result := -1;
            end if;
         end newTasks;
      end loop;
   end multMachine;

   type service is
      record
         machineId: Integer;
         machineType: String (1..1);
      end record;

   type serviceArr is array (1..variables.serviceSize) of service;

   function findService(arr: in serviceArr; searched: service) return Integer is
   begin
      for i in arr'Range loop
         if arr(i).machineType = searched.machineType and arr(i).machineId = searched.machineId then
            return i;
         end if;
      end loop;
      return -1;
   end findService;

   task body machineService is
      items: array (0..variables.serviceSize) of service;
      itemCount: Integer := 0;

      head: Integer := 0;
      tail: Integer := 0;

      serv : service;

      currentServ: serviceArr;
      index: Integer;
   begin
      for i in currentServ'Range loop
         currentServ(i) := (-1, "+");
      end loop;
      loop
         select
            when itemCount < variables.serviceSize =>
               accept newService (machineId : in Integer; machineType : in String) do
                  index := findService(currentServ, (machineId, machineType));
                  if index = -1 then
                     items(tail) := (machineId, machineType);
                     beginService:
                     for i in currentServ'Range loop
                        if currentServ(i) = (-1, "+") then
                           currentServ(i) := items(tail);
                           exit beginService;
                        end if;
                     end loop beginService;
                  end if;
               end newService;
               tail := (tail+1) mod variables.magazineSize;
               itemCount := itemCount + 1;
         or
            when itemCount > 0 =>
               accept getService (machineId : out Integer; machineType : out String) do
                  serv := items(head);
                  machineId := serv.machineId;
                  machineType := serv.machineType;
               end getService;

               head := (head + 1) mod variables.magazineSize;
               itemCount := itemCount -1;
         or
            accept servicesDone (machineId : in Integer; machineType : in String) do
               index := findService(currentServ, (machineId, machineType));
               if index /= -1 then
                  currentServ(index) := (-1, "+");
               end if;
            end servicesDone;
         end select;
      end loop;
   end machineService;

   task body serviceWorker is
      id: Integer;
      currType: String(1..1);
   begin
      loop
         machineService.getService(id, currType);
         delay variables.serviceWorkerDelay;
         if currType = "+" then
            addingMachines(id).backdoor;
         else
            multMachines(id).backdoor;
         end if;

         machineService.servicesDone(id, currType);
         if variables.mode then
            Put_Line("S: Pracownik serwsiu naprawil maszyne " & Integer'Image(id));
         end if;
      end loop;
   end serviceWorker;


begin
   Put_Line("Wybierz tryb pracy: spokojny (s) lub gadatliwy (g)");
   Get_Line(UserInput, Last);
   case UserInput(1) is
   when 's' =>
      variables.mode := False;
   when 'g' =>
      variables.mode := True;
   when others =>
      Put_Line("Wybierz tryb pracy: spokojny (s) lub gadatliwy (g)");
   end case;

   variables.start := True;

   for I in workers'Range loop
      workers(I).Id(I);
   end loop;

   c.id(1);

   for I in addingMachines'Range loop
      addingMachines(I).Id(I);
   end loop;

   for I in multMachines'Range loop
      multMachines(I).Id(I);
   end loop;

   if not variables.mode then
      loop
         Put_Line("Wyswietl stan magazynu (m) stan listy zadan (z)");
         Get_Line(UserInput1, Last1);
         case UserInput1(1) is
         when 'm' =>
            magazineServer.getState(True);
         when 'z' =>
            taskServer.getState(True);
         when others =>
            Put_Line("Wyswietl stan magazynu (m) stan listy zadan (z)");
         end case;
      end loop;
   end if;
end Main;
