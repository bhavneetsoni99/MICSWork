function [ sr,r ] = MNXsonitp( datafile )
% sonitp calculates the best fit curve for the input data
% the program is designed for the shear rate and viscocity
% relations with respect of changing concenterations of Xanthan


% datafile is the file containing the data to be analysed
% datafile should be entered with single quotes 'datafile'
%

XMconc = (xlsread (datafile,'b2:zz3'))'

shrate = xlsread (datafile, 'A:A') 
viscocity = xlsread(datafile, 'b3:zz9')

ic = length (Xconc)
ish = length (shrate)

y = log10 (viscocity)%y is the log of viscocity
x1 = log10 (shrate)%x1 is the log of shear rate applied

for i = 1:ish
plot(y(1,1:ish), x1,'o-')
end

end

