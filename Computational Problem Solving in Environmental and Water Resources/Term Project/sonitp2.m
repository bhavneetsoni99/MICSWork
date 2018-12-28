function [  ] = sonitp2( datafile )
% sonitp2 provides the best estiamte of the cuve
% for a power equation that satisfies the given data
% This model is designed to handle upto 3 variables (x1, x2, x3)
% The program is designed for understanding the effect of the 
% concenterations of various solutes and shear rates on viscocity
% datafile is the file containing the data to be analysed
% datafile should be entered with single quotes 'XXXXXXX'
% File should be an excel spread sheet and data should be in the specified
% Order of colums i.e last column should be the function (Visccoty) and 
% the initial colums should be the first variables and the 

clc
dataread = xlsread (datafile);
[m,n] = size (dataread); % gets the number of variables and no of experiments
% Checks the number of Variables and employes the resepective if loop

if n == 2
shrate = xlsread (datafile, 'a:a'); 
viscocity = xlsread(datafile, 'b:b');

y = log10 (viscocity);%y is the log of viscocity
x1 = log10 (shrate); %x1 is the conceteration of xanthan

z = [(ones (a,1)), x1];
end

%%
if n == 3
Xconc = xlsread (datafile,'a:a');
shrate = xlsread (datafile, 'b:b'); 
viscocity = xlsread(datafile, 'c:c');

y = log10 (viscocity);%y is the log of viscocity
x1 = log10 (Xconc); %x1 is the log of conceteration of xanthan
x2 = log10 (shrate);%x2 is the log of shear rate applied
z = [(ones (m,1)), x1, x2];
end

if n == 4
Xconc = xlsread (datafile,'a:a');
Mconc = xlsread (datafile, 'b:b');
shrate = xlsread (datafile, 'c:c'); 
viscocity = xlsread(datafile, 'd:d');

y = log10 (viscocity);%y is the log of viscocity
x1 = log10 (Xconc); %x1 is the conceteration of xanthan
x2 = log10 (Mconc); %x1 is the conceteration of xanthan
x3 = log10 (shrate);%x2 is the log of shear rate applied
z = [(ones(m,1)), x1,x2,x3];
end

ymean = mean (y); % Average of the dependent data set
% Finding the spread of the dataset
for i = 1:m
    diffyymean (i) = y(i) - ymean; 
end

A = [z'*z]^-1*[z]'*y  % calculates the coefficients

% calculate the Values of Viscocity as per estimated model
if n == 2
    
for i = 1:m
Ycalc (i)= [ A(1)+A(2)*x1(i)];
end
disp ('Elements of the power equation a.X1^b.X2^c... are (a,b,c,..)')
Coefficients = [10^A(1), A(2)]
end

% calculate the Values of Viscocity as per estimated model
if n == 3
for i = 1:m
Ycalc (i)= [ A(1)+A(2)*x1(i)+A(3)*x2(i)]; 
end
disp ('Elements of the power equation a.X1^b.X2^c... are (a,b,c,..)')
Coefficients = [10^A(1), A(2), A(3)]
end

% calculate the Values of Viscocity as per estimated model
if n == 4
for i = 1:m
Ycalc (i)= [A(1)+A(2)*x1(i)+A(3)*x2(i)+A(4)*x3(i)];
end
disp ('Elements of the power equation a.X1^b.X2^c... are (a,b,c,..)')
Coefficients = [10^A(1), A(2), A(3), A(4)]
end

%calcualte the difference between the Estimated value and Observerd Value
Yestimated = Ycalc';
for i = 1:m
    estidev(i) = y(i) - Yestimated(i);
end

% Calculate the coefficients of Regression
St = sum (diffyymean.^2)
Sr = sum ((estidev).^2)
R = (((St-Sr)/St))^.5

% Plotting the estimated values against observed data 
if n == 3
xconcenteration = unique (Xconc);
shearrates = unique (x2);
[numbofconc,a] = size (xconcenteration);
[numbofshear,a] = size (shearrates);
for i = 1: numbofconc
for k = 1: numbofshear
Viscocitycalc (i,k)=[A(1)+ A(2)* log10( xconcenteration(i))+ A(3)*shearrates(k)];  
end
hlines = plot (shearrates,Viscocitycalc);
grid on
title('Viscocity as a function of Shear Rate and Xanthan Concenterations');
xlabel('Log-Shear Rate');
ylabel('Log-Viscocity');
set (hlines (i),'Displayname',num2str (xconcenteration (i)));
hold all
end
plot (x2,y,'*')
legend (hlines, num2str (xconcenteration))
hold off
end

if n == 4
xanthanmno4conc = [x1 x2];
shearrates = unique (x3);
xantmno4 = unique (xanthanmno4conc, 'rows');
Union = [Xconc Mconc];
uniquexanmno4 = unique (Union, 'rows'); 
xanthanmno4 = num2str (uniquexanmno4);
[numbofconc,a] = size (xantmno4);
[numbofshear,a] = size (shearrates);
for i = 1: numbofconc
for k = 1: numbofshear
Viscocitycalc (i,k)= [A(1)+ A(2)*xantmno4(i,1)+A(3)*xantmno4(i,2) + A(4)*shearrates(k)];  
end
hlines = plot (shearrates,Viscocitycalc);
grid on
title('Viscocity as a function of Shear Rate and Xanthan-MnO4 Concenterations');
xlabel('Log-Shear Rate');
ylabel('Log-Viscocity');
set (hlines (i),'Displayname',(xanthanmno4 (i)));
hold all
end
plot (x3,y,'*')
legend (hlines, xanthanmno4)
hold off
end

if n > 4
error('Program can handle only 2 and 3 variables');
end
if n < 2
error('Program can handle only 2 and 3 variables');
end

end

