function sr = sr(a,x0,y0)
yp=a(1)*x0.*(exp(a(2)*x0));
sr=sum(((y0-yp)).^2); 
end