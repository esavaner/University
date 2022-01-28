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
            taskNew := (taskNumber, RNum.Random(GNum), RNum.Random(GNum), operations(ROp.Random(GOp)));
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
        currentTask: TaskT;
        result: Integer;
    begin
        accept id (workerIdent: in Integer) do
            workerId := workerIdent;
        end Id;
        workerStart:
        loop
        if variables.start then
            taskServer.getTask(currentTask);
            case currentTask.operation is
            when '+' =>
                result := currentTask.x + currentTask.y;
            when '-' =>
                result := currentTask.x - currentTask.y;
            when '*' =>
                result := currentTask.x * currentTask.y;
            end case;
            if variables.mode then
                Put_Line("Pracownik nr (" & Integer'Image(workerId) & ") wykonal zadanie " & Integer'Image(currentTask.number) & " z wynikiem " & Integer'Image(result));
            end if;

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
                    Put_Line("Tasks");
                    for I in tasks'Range loop
                        if tasks(I).x = 0 then
                            Put("[]");
                        else
                            Put("[");
                            Put(Integer'Image(tasks(I).x));
                            Put(Op'Image(tasks(I).operation));
                            Put(Integer'Image(tasks(I).y));
                            Put("]");
                        end if;
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
                    Put_Line("Magazine");
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
    p : menager;
    w : array (1 .. variables.employeesNumber) of worker;
    c : client;
    UserInput: String(1..80);
    Last: Natural;
    UserInput1: String(1..80);
    Last1: Natural;
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
    for I in w'Range loop
        w(I).Id(I);
    end loop;
    c.id(1);
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
