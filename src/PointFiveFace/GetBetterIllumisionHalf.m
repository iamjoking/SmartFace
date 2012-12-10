%获得光照条件比较好的那半边脸
function [X1_MIC,X2_MIC,MIC_d] = GetBetterIllumisionHalf(img)
	reverseimg = img(:,size(img,2):-1:1);
	I1 = reverseimg(:,round(0.5*size(img,2) + 1):size(img,2));
	I2 = img(:,round(0.5*size(img,2)+1):size(img,2));
	X1 = mean(I1);
	X2 = mean(I2);
	X1_Bar = mean(X1) .* ones(1,size(X1,2));
	X2_Bar = mean(X2) .* ones(1,size(X2,2));
	X1_MIC = (X1 - X1_Bar) / norm(X1-X1_Bar);
	X2_MIC = (X2 - X2_Bar) / norm(X2-X2_Bar);
	MIC_d = norm(X1_MIC-X2_MIC);

