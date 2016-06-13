package server;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Weka {

    public static String MODEL_DIR_PATH = "src/main/java/server/models/";
    public static String HARD = "hard";
    public static String CALM = "calm";

    public static String HARD_MODEL = "hardmodel";
    public static String CALM_MODEL = "calmmodel";

    public static String FILETYPE = ".csv";

    public static String MODEL_FILETYPE = ".model";


    public static String buildModels() {
        try {
            ConverterUtils.DataSource source_hard = new ConverterUtils.DataSource(MODEL_DIR_PATH + HARD + FILETYPE);
            Instances train_hard = source_hard.getDataSet();
            ConverterUtils.DataSource source_calm = new ConverterUtils.DataSource(MODEL_DIR_PATH + CALM + FILETYPE);
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
            ObjectOutputStream oos_hard = new ObjectOutputStream(new FileOutputStream(MODEL_DIR_PATH + HARD_MODEL + MODEL_FILETYPE));//"hard.model");
            oos_hard.writeObject(cModel_hard);
            oos_hard.flush();
            oos_hard.close();
            System.out.println("\nModele hard serialise\n======\n");

    // Serialize calm
            ObjectOutputStream oos_calm = new ObjectOutputStream(new FileOutputStream(MODEL_DIR_PATH + CALM_MODEL + MODEL_FILETYPE));//"calm.model"));
            oos_calm.writeObject(cModel_calm);
            oos_calm.flush();
            oos_calm.close();
            System.out.println("\nModele calm serialise\n======\n");

            return "Serialise!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
