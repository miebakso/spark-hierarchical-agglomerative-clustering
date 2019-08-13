
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "/home/miebakso/Desktop/INPUT/INPUT/input.txt";
        AttributesRandomGenerator ARG = new AttributesRandomGenerator(filePath);
        int option = 1;
        int n2 = 23000;
        int numberOfAttr = 2;
        int maxNearObj = 30;
        int maxDist = 40;
        if(option==1){
            ARG.generateData(numberOfAttr,n2);
        } else {
            ARG.generateDataPatern(numberOfAttr,n2,maxNearObj, maxDist);
        }
    }
}
