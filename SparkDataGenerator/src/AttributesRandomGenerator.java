import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class AttributesRandomGenerator {
    private String filePath;
    private FileWriter fw;
    private BufferedWriter bw;
    private Random rand = new Random();

    public AttributesRandomGenerator(String path) throws IOException {
        this.filePath = path;
        fw = new FileWriter(filePath);
        bw = new BufferedWriter(fw);
    }

    public void generateDataPatern(int attributes, int n2, int maxNearObj, int maxDist) throws IOException{
        int i,j,y,x;
        int step1 = n2/4;
        int step2 = n2/2;
        int step3 = n2*3/4;
        int step4 = n2*9/10;
        for(i=0;i<n2;i++){
            if(i==step1){
                System.out.println("25%");
            } else if(i==step2) {
                System.out.println("50%");
            } else if(i==step3) {
                System.out.println("75%");
            } else if(i==step4){
                System.out.println("90%");
            }
            for(j=0;j<n2;j++){
                double[] arr = new double[attributes];
                for(y=0;y<attributes;y++){
                    arr[y] = rand.nextDouble()*450 + 50;
                    if(y==attributes-1) {
                        bw.write("" +arr[y]);
                    } else {
                        bw.write("" +arr[y]+",");
                    }
                }
                bw.write("\n");
                int neighboursNumber = rand.nextInt(maxNearObj);
                for(x=0;x<neighboursNumber;x++){
                    for(y=0;y<attributes;y++){
                        int negatif = rand.nextInt(2);
                        double difference = rand.nextDouble()*maxDist;
                        if(negatif==0){
                            if(y==attributes-1) {
                                bw.write("" +(arr[y]-difference));
                            } else {
                                bw.write("" +(arr[y]-difference)+",");
                            }
                        } else {
                            if(y==attributes-1) {
                                bw.write("" +(arr[y]+difference));
                            } else {
                                bw.write("" +(arr[y]+difference)+",");
                            }
                        }
                    }
                    bw.newLine();
                }
            }
        }
    }

    public void generateData(int attributes, int n2) throws IOException{
        int i,j,y;
        int step1 = n2/4;
        int step2 = n2/2;
        int step3 = n2*3/4;
        int step4 = n2*9/10;
        for(i=0;i<n2;i++){
            if(i==step1){
                System.out.println("25%");
            } else if(i==step2) {
                System.out.println("50%");
            } else if(i==step3) {
                System.out.println("75%");
            } else if(i==step4){
                System.out.println("90%");
            }
            for(j=0;j<n2;j++){
                for(y=0;y<attributes;y++){
                    if(y==attributes-1) {
                        bw.write("" + ((rand.nextDouble() * 100)+10));
                    } else {
                        bw.write("" + ((rand.nextDouble() * 100)+10)+",");
                    }
                }
                bw.newLine();
            }
        }
        System.out.println("100%");
        bw.close();
        fw.close();
    }




}
