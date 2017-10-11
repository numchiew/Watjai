/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MIT_QrsDetector_Runner {
    public static void main(String[] args) {
        //Change root_dir to your project path, for example:"/home/joe/workspace/PhysioNet2011_Challenge_Java/";
		String root_dir="C:/Users/USER/Documents/NetBeansProjects/QRS/";
		String data_dir= "C:/Users/USER/Documents/NetBeansProjects/QRS/";
		
		double elapsed_time=0,result;
		
		long start_time=0, end_time=0, trial_time=0;
		short i=0;

		QrsDetector code = new QrsDetector();
		
                try{
                InputStream HiFile = new FileInputStream(data_dir + "ecg.txt");
		InputStreamReader inread= new InputStreamReader(HiFile);
		BufferedReader buf = new BufferedReader(inread);
                String strLine = null;
                BufferedWriter out_log =null;
                
                String file_name="ECG_LOG_" + strLine + ".txt";
		File log_file = new File(root_dir, file_name);
                out_log= new BufferedWriter(new FileWriter(log_file));
                
                strLine = buf.readLine();
                
                if(strLine != null){
                    InputStream FiFile = new FileInputStream(data_dir + "ecg.txt");
                    start_time=System.currentTimeMillis();
                    code.Initialise(strLine);
                    result = code.get_result(FiFile,strLine) ;
                    end_time=System.currentTimeMillis();

                    trial_time=end_time-start_time;
                    elapsed_time +=  ((double) trial_time/1000);
                    String strLog= strLine + " " + result + " beats detected.";
                    out_log.write(strLog);
                    System.out.print(strLog);
                    out_log.flush();
                    i++;
                   // strLine = buf.readLine();
                }
                out_log.close();
                }catch(Exception e){
                    System.out.println(e);
                }
		
    }
    
}
