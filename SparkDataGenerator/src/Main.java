
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "/home/miebakso/Desktop/test.txt";
        AttributesRandomGenerator ARG = new AttributesRandomGenerator(filePath);
        int option = 1;
        int n2 = 9000;
        int numberOfAttr = 2;
        int maxNearObj = 20;
        int maxDist = 10;
        if(option==1){
            ARG.generateData(numberOfAttr,n2);
        } else {
            ARG.generateDataPatern(numberOfAttr,n2,maxNearObj, maxDist);
        }
    }
}
