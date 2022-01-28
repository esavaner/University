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

   type taskAccess is access taskT;

   type machineTask is
   record
         taskAccess1: taskAccess;
         workerId: Integer;
   end record;

    task type menager;

    task type worker is
      entry id (workerIdent : in Integer);
      entry taskFinished(b : in Boolean);
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
      entry newTasks (newTask: in machineTask);
   end addingMachine;

   task type multMachine is
      entry id (id : in Integer);
      entry newTasks (newTask: in machineTask);
   end multMachine;


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
            taskNew := (taskNumber, RNum.Random(GNum), RNum.Random(GNum), operations(ROp.Random(GOp)), 0);
            taskNumber := taskNumber + 1;
            taskServer.newTask(taskNew);
            if variables.mode then
                Put_Line("Prezes stworzyl nowe zadanie o numerze:" & Integer'Image(taskNew.number) & " z wartosciami: " & Integer'Image(taskNew.x) & " i " & Integer'Image(taskNew.y) & " dla operacji: " & Op'Image(taskNew.operation));
            end if;
            delay variables.menagerDelay;
        end if;
        end loop menagerStart;
    end menager;

    task body worker is
      workerId : Integer;
      currentTask: taskT;
      currentTaskA: taskAccess;
      result: Integer;
      workerType : String(1..12);
      workerTypeA : array (0..1) of String(1..12) := (" cierpliwy  ", "niecierpliwy");
      currentM : Integer;
      resultFound: Boolean := false;
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
                    resultFound := false;
                    while not resultFound loop
                        currentM := RandGen.get_random(variables.addingMachineNumber);
                        currentTaskA := new taskT'(currentTask.number, currentTask.x, currentTask.y, currentTask.operation, currentTask.result);
                        addingMachines(currentM).newTasks((currentTaskA, workerId));
                        select
                            accept taskFinished(b: in Boolean) do
                                result := currentTaskA.result;
                                resultFound := b;
                            end taskFinished;
                        or
                            delay variables.impatientDelay;
                        end select;
                    end loop;
                else
                    currentM := RandGen.get_random(variables.addingMachineNumber);
                    currentTaskA := new taskT'(currentTask.number, currentTask.x, currentTask.y, currentTask.operation, currentTask.result);
                    addingMachines(currentM).newTasks((currentTaskA, workerId));
                    resultFound := false;
                    while not resultFound loop
                        select
                            accept taskFinished(b: in Boolean) do
                                result := currentTaskA.result;
                                resultFound := b;
                            end taskFinished;
                        end select;
                    end loop;
                end if;
            when '+' =>
                if workerType = "niecerpliwy" then
                    resultFound := false;
                    while not resultFound loop
                        currentM := RandGen.get_random(variables.addingMachineNumber);
                        currentTaskA := new taskT'(currentTask.number, currentTask.x, currentTask.y, currentTask.operation, currentTask.result);
                        addingMachines(currentM).newTasks((currentTaskA, workerId));
                        select
                            accept taskFinished(b: in Boolean) do
                                result := currentTaskA.result;
                                resultFound := b;
                            end taskFinished;
                        or
                            delay variables.impatientDelay;
                        end select;
                    end loop;
                else
                    currentM := RandGen.get_random(variables.addingMachineNumber);
                    currentTaskA := new taskT'(currentTask.number, currentTask.x, currentTask.y, currentTask.operation, currentTask.result);
                    addingMachines(currentM).newTasks((currentTaskA, workerId));
                    resultFound := false;
                    while not resultFound loop
                        select
                            accept taskFinished(b: in Boolean) do
                                result := currentTaskA.result;
                                resultFound := b;
                            end taskFinished;
                        end select;
                    end loop;
                end if;
            when '*' =>
                if workerType = "niecerpliwy" then
                    resultFound := false;
                    while not resultFound loop
                        currentM := RandGen.get_random(variables.multMachineNumber);
                        currentTaskA := new taskT'(currentTask.number, currentTask.x, currentTask.y, currentTask.operation, currentTask.result);
                        multMachines(currentM).newTasks((currentTaskA, workerId));
                        select
                            accept taskFinished(b: in Boolean) do
                                result := currentTaskA.result;
                                resultFound := b;
                            end taskFinished;
                        or
                            delay variables.impatientDelay;
                        end select;
                    end loop;
                else
                    currentM := RandGen.get_random(variables.multMachineNumber);
                    currentTaskA := new taskT'(currentTask.number, currentTask.x, currentTask.y, currentTask.operation, currentTask.result);
                    multMachines(currentM).newTasks((currentTaskA, workerId));
                    resultFound := false;
                    while not resultFound loop
                        select
                            accept taskFinished(b: in Boolean) do
                                result := currentTaskA.result;
                                resultFound := b;
                            end taskFinished;
                        end select;
                    end loop;
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
        taskM: machineTask;
    begin
        accept id (id: in Integer) do
            ident := id;
        end id;
        loop
            accept newTasks(newTask: in machineTask) do
                taskM := newTask;
            end newTasks;
            if variables.mode then
                Put_Line("Maszyna" & Integer'Image(ident) & " doadaje " & Integer'Image(taskM.taskAccess1.x) & " i " & Integer'Image(taskM.taskAccess1.y));
            end if;
            delay variables.addingMachineDelay;
            taskM.taskAccess1.result := taskM.taskAccess1.x + taskM.taskAccess1.y;
            select
                workers(taskM.workerId).taskFinished(true);
            or
                delay 0.1;
            end select;
        end loop;
   end addingMachine;

   task body multMachine is
        ident: Integer;
        taskM: machineTask;
    begin
        accept id (id: in Integer) do
            ident := id;
        end id;
        loop
            accept newTasks(newTask: in machineTask) do
                taskM := newTask;
            end newTasks;
            if variables.mode then
                Put_Line("Maszyna" & Integer'Image(ident) & " mnozy " & Integer'Image(taskM.taskAccess1.x) & " i " & Integer'Image(taskM.taskAccess1.y));
            end if;
            delay variables.multMachineDelay;
            taskM.taskAccess1.result := taskM.taskAccess1.x * taskM.taskAccess1.y;
            select
                workers(taskM.workerId).taskFinished(true);
            or
                delay 0.1;
            end select;
        end loop;
   end multMachine;

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
