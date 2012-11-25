function adaboost_model = Adaboost_train_wrap(flag1,string1,flag2,string2,flag3,string3,flag4,string4)
inputflag = {flag1,flag2,flag3,flag4};
inputstring = {string1,string2,string3,string4}; %need more codes here to verify the inputstring
for i = 1:4
    switch inputflag{i}
        case '-o'
            output = inputstring{i};
        case '-p'
            posfile = inputstring{i};
        case '-n'
            negfile = inputstring{i};
        case '-h'
            no_of_hypothesis = inputstring{i};
            no_of_hypothesis = str2num(no_of_hypothesis);
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
size(train_set);
size(labels);
adaboost_model = ADABOOST_tr(train_set,labels,no_of_hypothesis);

output_para = strcat(output,'_para');
for i = 1:no_of_hypothesis
    if i == 1
        dlmwrite(output_para,adaboost_model.parameters{i}.min_error);
        dlmwrite(output_para,adaboost_model.parameters{i}.min_error_thr,'-append');
        dlmwrite(output_para,adaboost_model.parameters{i}.pos_neg,'-append');
        dlmwrite(output_para,adaboost_model.parameters{i}.dim,'-append');
    else
        dlmwrite(output_para,adaboost_model.parameters{i}.min_error,'-append');
        dlmwrite(output_para,adaboost_model.parameters{i}.min_error_thr,'-append');
        dlmwrite(output_para,adaboost_model.parameters{i}.pos_neg,'-append');
        dlmwrite(output_para,adaboost_model.parameters{i}.dim,'-append');
    end
end
dlmwrite(output,adaboost_model.weights);