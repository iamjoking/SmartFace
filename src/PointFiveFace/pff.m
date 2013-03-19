%获得光照条件比较好的半边脸
%image代表输入的黑白图像矩阵
%需要准备好向量Optimal Curve
function Face = pff(gray_img,optimal_curve)
	normalImg = GetCenterSquareRegion(gray_img,120);%规范化为120*120的图像
%获得两个半边脸
	reverseimg = normalImg(:,size(normalImg,2):-1:1);
	I1 = reverseimg(:,round(0.5*size(normalImg,2) + 1):size(normalImg,2));
	I2 = normalImg(:,round(0.5*size(normalImg,2)+1):size(normalImg,2));
	[X1,X2,d] = CalcMICCurve(normalImg);

	dis1 = norm(optimal_curve - X1);
	dis2 = norm(optimal_curve - X2);
	if dis1 <= dis2
		Face = I1;
		else
			Face = I2;
	end

