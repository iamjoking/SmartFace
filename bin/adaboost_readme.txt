adaboost_train_wrap
Parameters: pos,neg,model,num
-pos:正样本文件
-neg:负样本文件
-model:模型输出文件，输出两个文件，model和model_para，model中为各个分类器的权重，model_para包含四个参数，供以后使用
-num:弱分类器个数

adaboost_test_wrap
Parameters:model,pos,neg,result
-model:模型输入文件，默认需两个文件model和model_para。只需要输入model参数即可。
-pos:正样本文件
-neg:负样本文件
-result:输出命中次数