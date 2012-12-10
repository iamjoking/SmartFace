<?xml version="1.0" encoding="UTF-8"?>
<root>

	<Preprocess>
		<program>
			<name>Haar Cascade Classifier</name>
			<path>D:\Study\code\GitHub\SmartFace\bin\FaceDetection.exe</path>
			<description>Description...This is Haar Cascade Classifier</description>
			<ioOption>
				<name>i</name>
				<description>Input images</description>
			</ioOption>
			<path>D:\Study\code\GitHub\SmartFace\bin\FaceDetection.exe</path>
			<ioOption>
				<name>d</name>
				<description>Output directory</description>
			</ioOption>
			<nonvalueOption>
				<name>g</name>
				<description>Output image is in gray</description>
			</nonvalueOption>
			<nonvalueOption>
				<name>e</name>
				<description>Equalize the intensity histogram before detection</description>
			</nonvalueOption>
			<valueOption>
				<name>x</name>
				<description>Output image file extension name(jpg,png...)</description>
			</valueOption>
			<optionalValueOption>
				<name>o</name>
				<description>outputInfomationFileDirectory</description>
			</optionalValueOption>
		</program>
	</Preprocess>

	<FeatureExtraction>
		<program>
			<name>Local Binary Pattern</name>
			<path>D:\Study\code\GitHub\SmartFace\bin\lbp.exe</path>
			<description>Description...</description>
			<ioOption>
				<name>i</name>
				<description>Input images</description>
			</ioOption>
			<ioOption>
				<name>d</name>
				<description>Output directory</description>
			</ioOption>
			<valueOption>
				<name>r</name>
				<description>Sampling radius</description>
			</valueOption>
			<valueOption>
				<name>n</name>
				<description>Sampling number</description>
			</valueOption>
			<valueOption>
				<name>t</name>
				<description>Mapping type (can be 'ri', 'u2' or 'riu2')</description>
			</valueOption>
			<valueOption>
				<name>m</name>
				<description>Mode (can be 'h', 'hist' or 'nh')</description>
			</valueOption>
		</program>
	</FeatureExtraction>

	<TrainingSetGeneration>
		<program>
			<name>Strategy 1</name>
			<path>D:\Study\code\GitHub\SmartFace\bin\TrainingSetGeneration.exe</path>
			<description>Description...</description>
			<ioOption>
				<name>i</name>
				<description>Input features</description>
			</ioOption>
			<ioOption>
				<name>d</name>
				<description>Output directory</description>
			</ioOption>
			<ioOption>
				<name>o</name>
				<description>Output name</description>
			</ioOption>
			<optionalValueOption>
				<name>s</name>
				<description>The number of positive and negative samples.(like 10:20, 150:200)</description>
			</optionalValueOption>
			<optionalValueOption>
				<name>f</name>
				<description>The end flag of the identifier (dafult as '_')</description>
			</optionalValueOption>
		</program>
	</TrainingSetGeneration>

	<SampleCombination>
		<program>
			<name>Strategy 1</name>
			<path>D:\Study\code\GitHub\SmartFace\bin\FeatureCombiner.exe</path>
			<description>Description...</description>
			<ioOption>
				<name>i</name>
				<description>Input</description>
			</ioOption>
			<ioOption>
				<name>d</name>
				<description>Output directory</description>
			</ioOption>
		</program>
	</SampleCombination>

	<ClassifierTraining>
		<program>
			<name>Adaboost training</name>
			<path>D:\Study\code\GitHub\SmartFace\bin\adaboost_train_wrap.exe</path>
			<description>Description...</description>
			<ioOption>
				<name>p</name>
				<description>Positive samples</description>
			</ioOption>
			<ioOption>
				<name>n</name>
				<description>Negative samples</description>
			</ioOption>
			<ioOption>
				<name>o</name>
				<description>Output</description>
			</ioOption>
			<valueOption>
				<name>n</name>
				<description>The number of weak classifiers.</description>
			</valueOption>
		</program>
	</ClassifierTraining>

</root>