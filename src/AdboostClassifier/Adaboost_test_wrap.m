function [L,hits] = Adaboost_test_wrap(flag1,string1,flag2,string2,flag3,string3,flag4,string4)
inputflag = {flag1,flag2,flag3,flag4};
inputstring = {string1,string2,string3,string4}; %need more codes here to verify the inputstring
for i = 1:4
    switch inputflag{i}
        case '-m'
            modelfile = inputstring{i};
        case '-p'
            posfile = inputstring{i};
        case '-n'
            negfile = inputstring{i};
        case '-o'
            output = inputstring{i};
    end
end
if exist(posfile,'file') == 0
    pos_instance = [];
else
    pos_instance = dlmread(posfile);
end
if exist(negfile,'file') == 0
    neg_instance = [];
else
    neg_instance = dlmread(negfile);
end

pos_cnt = size(pos_instance,1);
neg_cnt = size(neg_instance,1);

train_set = [pos_instance;neg_instance];
pos_labels = zeros(1,pos_cnt);
neg_labels = zeros(1,neg_cnt);
pos_labels = pos_labels + 1;
neg_labels = neg_labels + 2;
labels = zeros(1,pos_cnt+neg_cnt);
labels = [pos_labels neg_labels];
labels = labels';
[model.weights] = dlmread(modelfile);
hypothesis_n = length(model.weights);


output_para = strcat(modelfile,'_para');
fd = fopen(output_para,'r');
if fd == -1
    error('cannot open para file');
end
for i = 1:hypothesis_n
    model.parameters{i}.min_error = str2num(fgetl(fd));
    model.parameters{i}.min_error_thr =  str2num(fgetl(fd));
    model.parameters{i}.pos_neg =  fgetl(fd);
    model.parameters{i}.pos_neg = strrep(model.parameters{i}.pos_neg,',','');
    model.parameters{i}.dim =  str2num(fgetl(fd));
end
[L,hits,ferr,perr] = ADABOOST_te(model,train_set,labels);
error_rate = 1-hits/length(labels);
dlmwrite(output,hits);
dlmwrite(output,error_rate,'-append');
dlmwrite(output,ferr,'-append');
dlmwrite(output,perr,'-append');