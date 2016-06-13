package server;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.ObjectOutputStream;

public class Weka {

    public void buildModels() throws Exception{
        String location = "";
        ConverterUtils.DataSource source_hard = new ConverterUtils.DataSource(location);
        Instances train_hard = source_hard.getDataSet();
        ConverterUtils.DataSource source_calm = new ConverterUtils.DataSource("");
        Instances train_calm = source_calm.getDataSet();

// Set class index
        train_hard.setClassIndex(0);
        train_calm.setClassIndex(0);
// Build Classifier
        Classifier cModel_hard = (Classifier)new J48();
        cModel_hard.buildClassifier(train_hard);
        Classifier cModel_calm = (Classifier)new J48();
        cModel_calm.buildClassifier(train_calm);


// Serialize model hard
        ObjectOutputStream oos_hard = new ObjectOutputStream(null);//"hard.model");
        oos_hard.writeObject(cModel_hard);
        oos_hard.flush();
        oos_hard.close();
        System.out.println("\nModele hard serialise\n======\n");

// Serialize calm
        ObjectOutputStream oos_calm = new ObjectOutputStream(null);//"calm.model"));
        oos_calm.writeObject(cModel_calm);
        oos_calm.flush();
        oos_calm.close();
        System.out.println("\nModele calm serialise\n======\n");
    }
}
