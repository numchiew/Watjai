 

unsigned long time;
extern volatile unsigned long timer0_overflow_count;
float fanalog0;
int analog0;
byte serialByte;
String temp = "";
int j = 0;
  #include <eHealth.h>
  void setup()
    {
      Serial.begin(115200);
      
    }
  void loop()
  {
    
     while (Serial.available()>0){  
    serialByte=Serial.read();
    if (serialByte=='C'){        
      while(1){
        fanalog0=eHealth.getECG();  
        // Use the timer0 => 1 tick every 4 us
        time=(timer0_overflow_count << 8) + TCNT0;        
        // Microseconds conversion.
        time=(time*4);   
        //Print in a file for simulation
       // Serial.print("cc");
        //Serial.print(";");
        //Serial.println(fanalog0);
        temp =  temp + fanalog0 + ";";
        j++;
        if(j==10){
          Serial.print(temp);
          j = 0;
        }
        
        
        if (Serial.available()>0){
          serialByte=Serial.read();
          if (serialByte=='F')  break;
        }
        delay(7);
      }
    }
  }
 }
  

