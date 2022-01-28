clear all;
close all;
clc

S=dlmread('select.txt');
x=S(:,1);
y1=S(:,2);
z1=S(:,3);
xlabel('N');
ylabel('Porownania');

I=dlmread('insert.txt');
y2=I(:,2);
z2=I(:,3);

Q=dlmread('quick.txt');
y3=Q(:,2);
z3=Q(:,3);

H=dlmread('heap.txt');
y4=H(:,2);
z4=H(:,3);

line(x,y1,'Color', 'r','LineWidth',2);
line(x,z1,'Color',[1, 0.2, 0.1],'LineWidth',2);

%line(x,y2,'Color', 'g','LineWidth',2);
%line(x,z2,'Color',[0.4660, 0.6740, 0.1880],'LineWidth',2);

line(x,y3,'Color', 'b','LineWidth',2);
line(x,z3,'Color',[0, 0.447, 0.741],'LineWidth',2);

line(x,y4,'Color', 'y','LineWidth',2);
line(x,z4,'Color',[0.72, 0.75, 0],'LineWidth',2);

legend('Selection porownania', 'Selection zmiany', 'Quick porownania', 'Quick zmianny', 'Heap porownania', 'Heap zmiany');