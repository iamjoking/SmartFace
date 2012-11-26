%LBPWRAP is a wrap of lbp method. It Generates a feature file or feature 
%   files of the local binary pattern image or LBP histogram of an image or 
%   images.
% 
%   Arguments :
%
%   IMGPATHS        The input image(s)'s fullpath(s). If more than one image
%                   are included, it should be splited with ";".
%
%   OUTPUTPATH      The name of the output path in which the generated feature 
%                   file(s) will be put.
% 
%   RADIUSSTRING    The LBP codes are computed on a circle of RADIUSSTRING.
% 
%   SAMPLESSTRING   The LBP codes are computed using N sampling points on a
%                   circle.
%
%   MAPPINGTYPESTRNG Possible values for MAPPINGTYPE are
%                   'u2'   for uniform LBP
%                   'ri'   for rotation-invariant LBP
%                   'riu2' for uniform rotation-invariant LBP.
%
%   MODESTRING      Possible values for MODE are
%                   'h' or 'hist'  to get a histogram of LBP codes
%                   'nh'           to get a normalized histogram
%   Example:
%           lbpWrap -i 'input\1.JPG;input\2.JPG' -d 'output\' -r 1 -s 8 -t ri -m
%                   h
%       Now 'output\1.fea' and 'output\2.fea' contain the rotation-invariant
%       uniform LBP histogram for 1.JPG and 2.JPG.
%
%   See also lbp, getmapping.

function lbpWrap(varargin)

if (length(varargin) == 0)
	fprintf('lbp -i InputImages -d OutputDirectory -r SamplingRadius -n SamplingNumbers -t MappingType(ri/u2/riu2) -m Mode(h/hist/nh)\n');
    return ;
end

inputflag = varargin(1:2:11);
inputstring = varargin(2:2:12);
for i = 1:6
    switch inputflag{i}
        case '-i'
            imgPaths = inputstring{i};
        case '-d'
            outputPath = inputstring{i};
        case '-r'
            radiusString = inputstring{i};
        case '-n'
            samplesString = inputstring{i};
		case '-t'
			mappingtypeString = inputstring{i};
		case '-m'
			modeString = inputstring{i};
    end
end

% Some hiden arguments
extension = '.fea';

% Invert input arguments from string to the wanted.
if (imgPaths(length(imgPaths)) == ';')
    imgPaths = imgPaths(1:length(imgPaths) - 1);
end
imgsPath = regexp(imgPaths,';','split');
imgsPath = char(imgsPath);
radius = str2num(radiusString);
samples = str2num(samplesString);
mappingtype = mappingtypeString;
mode = modeString;

% Generate a mapping table.
mapping = getmapping(samples,mappingtype);

% Execute the lbp(local binary pattern) method and genetate outputs.
num = size(imgsPath,1);
for i = 1:num
    imgString = imgsPath(i,:);
    img = imread(imgString);
    H=lbp(img,radius,samples,mapping,mode);
    [pathstr,imgName,ext] = fileparts(imgString);
    output = strcat(outputPath,imgName,extension);
    dlmwrite(output,H,' ');
end

%fprintf('Done\n'); % To offer an output stream.



