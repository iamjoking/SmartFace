[TrainingSetGeneration] Training Set Generator.
Usage    : TrainingSetGeneration -i inputFeatureFiles -d outputDirectoryPath -o outputFileName
           [-s [positiveSamples]:[negativeSamples]] [-f identifierEndFlag]

Attension: 1.The "inputFeatureFiles" should be separated with ";".
           2.If "positiveSamples" is odmitted or is 0, all of the possible
             combinations will be given. So does "negativeSamples".
           3.The default value of "identifierEndFlag" is "_".

Example  : TrainingSetGeneration -i A_1.fea;A_2.fea;B_1.fea;B_2.fea -d output\ -o samples
           -s 2:2 -f _
		   

[FeatureCombiner] Feature File Combiner.
	This program will combine features from feature file to produce training set files used by trainer.
Usage    : FeatureCombiner -i inputTrainingSetFile -d outputTrainingSetFeatureFileName Directory

Attention: The two "inputFeatureFiles" should be separated with ";".

Example  : FeatureCombiner -i sample.pos.set -o output/
