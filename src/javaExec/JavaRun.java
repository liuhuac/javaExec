package javaExec;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class JavaRun {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if(!(3==args.length||4==args.length)) {
			printUsage();
			return;
		}
		
		String map = args[0];
		String node = args[1];
		String appPath = args[2];
		String outPath;
		if(4==args.length){
			outPath = args[3];
		} else {
			outPath = System.getProperty("user.dir") + "/";
		}
		
		if(!appPath.endsWith("/")){
			appPath = appPath + "/";
		}
		
		if(!outPath.endsWith("/")){
			outPath = outPath + "/";
		}
		
		List<String> nodeNames = new ArrayList<String>();
		
		BufferedReader brMap = new BufferedReader(new FileReader(map));
		BufferedReader brNode = new BufferedReader(new FileReader(node));
		
		String line = null;
		while ((line = brNode.readLine()) != null) {
            nodeNames.add(line);
        }
		brNode.close();
		
		String cmd = null;
		while ((line = brMap.readLine()) != null) {
            String[] parts = line.split("\\s+");
            int n = Integer.valueOf(parts[1]);
            String host = nodeNames.get(n);
            String app = appPath + parts[0];
            Random rd = new Random();
            int numbers = 100000 + (int)(rd.nextFloat() * 899900);
            String ofName = host.substring(0, host.indexOf('.')) + "." + parts[0] + "." + String.format("%5d", numbers);
            cmd = "ssh " + host + " " + app + " > " + outPath + ofName;
            System.out.println(cmd);
            //Runtime.getRuntime().exec(cmd);
        }
		brMap.close();

	}
	
	public static void printUsage(){
		System.out.println("JavaExec 1.0, CopyRight: OSK");
		System.out.println("java -jar javaExec [Vm2PmMapping file] [$PBS_NODEFILE] [app path] [out put path]");
	}

}
