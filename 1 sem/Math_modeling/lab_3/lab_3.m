clear
clc

yStart = dlmread('y3.txt',' ');
[M,N] = size(yStart);

c(1) = 0.1; % estimate
c(2) = 0.3;
c(3) = 0.1; % estimate
c(4) = 0.12;

m(1) = 9; % estimate
m(2) = 28;
m(3) = 18;

h = 0.2;
epsilon = 1e-6;
I = inf;
numberOfIterations = 0;

while (I > epsilon)
    numberOfIterations = numberOfIterations+1;
    y = yStart(:,1);
    dy = zeros(M,1);
    U = zeros(6,3);
    firstPart = zeros(3,3);
    secondPart = zeros(3,1);
    I = 0.0;
    i = 2;
    
    while(i <= N)
        A = getA(m,c);
        Unew = U_RungeKutt(A, U, h, y, m, c);
        yNew = Y_RungeKutt(A, y, h);
        dyNew = yStart(:,i) - yNew;
        
        firstPart = firstPart + h*(U'*U + Unew'*Unew) / 2.0;
        secondPart = secondPart + h*(U'*dy + Unew'*dyNew) / 2.0;
        
        I = I + h*(dy'*dy + dyNew'*dyNew) / 2.0;
        
        U = Unew;
        y = yNew;
        dy = dyNew;
        i = i + 1;
    end
    deltaBeta = inv(firstPart)*secondPart;
    c(1) = c(1) + deltaBeta(1);
    c(3) = c(3) + deltaBeta(2);
    m(1) = m(1) + deltaBeta(3);
    disp(I);
end

disp('c1: ');
disp(c(1));
disp('c3: ');
disp(c(3));
disp('m1: ');
disp(m(1));
disp('Number of iterations: ');
disp(numberOfIterations);

function result = getA(m, c)
    A = zeros(6,6);
    A(1, 2) = 1;
    A(2, 1) = -(c(1) + c(2)) / m(1);
    A(2, 3) = c(2) / m(1);
    A(3, 4) = 1;
    A(4, 1) = c(2) / m(2);
    A(4, 3) = -(c(2) + c(3)) / m(2);
    A(4, 5) = c(3) / m(2);
    A(5, 6) = 1;
    A(6, 3) = c(3) / m(3);
    A(6, 5) = -(c(4) + c(3)) / m(3);
    result = A;
end

function result = fU(A, U, y, m, c)
    dAy = zeros(6,3);
    dAy(2, 1) = -(y(1) / m(1));
    dAy(2, 3) = ((c(1)+c(2))*y(1) - c(2)*y(3))/ (m(1) * m(1));
    dAy(4, 2) = (-y(3)+y(5)) / m(2);
    dAy(6, 2) = (y(3)-y(5))/ m(3);
    result = A * U + dAy;
end

function result = U_RungeKutt(A, U, h, y, m, c)
    k1 = h * fU(A, U, y, m, c);
    k2 = h * fU(A, U + k1 / 2.0, y, m, c);
    k3 = h * fU(A, U + k2 / 2.0, y, m, c);
    k4 = h * fU(A, U + k3, y, m, c);
    result = U + (k1 + 2 * k2 + 2 * k3 + k4) / 6.0;
end

function result = Y_RungeKutt(A, y, h)
    k1 = h * fy(A, y);
    k2 = h * fy(A, y + k1 / 2.0);
    k3 = h * fy(A, y + k2 / 2.0);
    k4 = h * fy(A, y + k3);
    result = y + (k1 + 2 * k2 + 2 * k3 + k4) / 6.0;
end

function result = fy(A, y)
    result = A*y;
end