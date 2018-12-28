function [ sr,r ] = sonitp( datafile )

% NV is number of variables this program can handle only 2 and 3 variables
% sonitp calculates the best fit curve for the input data
% the program is designed for the shear rate and viscocity
% relations with respect of changing concenterations of Xanthan
% datafile is the file containing the data to be analysed
% datafile should be entered with single quotes 'datafile'

dataread = xlsread (datafile);
[m,n] = size (dataread);

if n == 3
%%
    
Xconc = xlsread (datafile,'a:a');
shrate = xlsread (datafile, 'b:b'); 
viscocity = xlsread(datafile, 'c:c');

y = log10 (viscocity);%y is the log of viscocity
x1 = log10 (Xconc); %x1 is the conceteration of xanthan
x2 = log10 (shrate);%x2 is the log of shear rate applied

sigmax1 = sum (x1);
sigmax2 = sum( x2);
sigmax1sqrd =  sum(x1.^2);
sigmax2sqrd = sum (x2.^2);
sigmay = sum (y);
sigmax1x2 = sum (x1.*x2)+.00001;
sigmax1y = sum (x1.*y);
sigmax2y = sum (x2.*y);
ymean = mean (y)

for i = 1:m
    diffyymean (i) = y(i) - ymean;
end
 
%%

Xx = [m, sigmax1 , sigmax2; sigmax1, sigmax1sqrd, sigmax1x2; sigmax2, sigmax1x2, sigmax2sqrd]
Yy =[sigmay; sigmax1y; sigmax2y]
[M,n] = size (Xx);
nb = n+1;
Pivot = [Xx Yy];
Aug = sortrows (Pivot, abs(-1));
% Sorts the matrix From Highest absolute
% Value to the least for the first column

for k = 1:n-1
    for i = k+1:n   % n-1 iteration
        factor = Aug(i,k)/Aug(k,k);
        Aug(i, k:nb) = Aug(i, k:nb)-factor*Aug(k,k:nb);
        
    end
end

% back substitution
X = zeros(n,1);
X(n)= Aug(n,nb)/Aug(n,n);  % one division

for i = n-1: -1: 1
    X(i) = (Aug(i,nb)-Aug(i,i+1:n)*X(i+1:n))/Aug(i,i);
end
X
%%
 
for i = 1:m
Ycalc (i)= [ X(1)+X(2)*x1(i)+X(3)*x2(i)];
end
Yestimated = Ycalc';
for i = 1:m
    estidev(i) = y(i) - Yestimated(i);
end

St = sum (diffyymean.^2)
Sr = sum ((estidev).^2)
r = ((St-Sr)/St)^.5

plot3 (x1,x2,y,'o')
plot3 (x1,x2,Ycalc, 'r')
grid on
title('Viscocity as a function of shear rate and Xanthan Concenterations');
xlabel('Xanthan Concenteration - log scale');
ylabel('Shear Rate - log scale');
zlabel('Viscocity - log scale')
%%
if n == 4
Xconc = xlsread (datafile,'a:a');
Mconc = xlsread (datafile, 'b:b')
shrate = xlsread (datafile, 'c:c'); 
viscocity = xlsread(datafile, 'd:d');
data = [Xconc , Mconc, shrate, viscocity];

y = log10 (viscocity);%y is the log of viscocity
x1 = log10 (Xconc); %x1 is the conceteration of xanthan
x2 = log10 (Mconc); %x1 is the conceteration of xanthan
x3 = log10 (shrate);%x2 is the log of shear rate applied

m = length (viscocity); % number of experiment combinations
sigmax1 = sum (x1);
sigmax2 = sum( x2);
sigmax3 = sum( x3);
sigmax1sqrd = sum (x1.^2);
sigmax2sqrd = sum (x2.^2);
sigmax2sqrd = sum (x3.^2);
sigmay = sum (y);
sigmax1x2 = sum (x1.*x2);
sigmax1y = sum (x1.*y);
sigmax2y = sum (x2.*y);
ymean = mean (y)

for i = 1:m
    diffyymean (i) = [y(i) - ymean];
end
 

Xx = [m, sigmax1 , sigmax2; sigmax1, sigmax1sqrd, sigmax1x2; sigmax2, sigmax1x2, sigmax2sqrd]
Yy =[sigmay; sigmax1y; sigmax2y]
[M,n] = size (Xx);
nb = n+1;
Pivot = [Xx Yy];
Aug = sortrows (Pivot, abs(-1));
% Sorts the matrix From Highest absolute
% Value to the least for the first column

for k = 1:n-1
    for i = k+1:n   % n-1 iteration
        factor = Aug(i,k)/Aug(k,k);
        Aug(i, k:nb) = Aug(i, k:nb)-factor*Aug(k,k:nb);
        
    end
end

% back substitution
X = zeros(n,1);
X(n)= Aug(n,nb)/Aug(n,n);  % one division
for i = n-1: -1: 1
    X(i) = (Aug(i,nb)-Aug(i,i+1:n)*X(i+1:n))/Aug(i,i);
end
X
 m
for i = 1:m
Ycalc (i)= [ X(1)+X(2)*x1(i)+X(3)*x2(i)];
end
Yestimated = Ycalc';
for i = 1:m
    estidev(i) = y(i) - Yestimated(i);
end

St = sum (diffyymean.^2)
Sr = sum ((estidev).^2)
r = ((St-Sr)/St)^.5

else
    error('Program can handle only 2 and 3 variables');
end
    
end

