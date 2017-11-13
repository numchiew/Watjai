 

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
        temp =  temp + fanalog0 + ";";
        j++;
        if(j==10){
          Serial.print(temp);
          j = 0;
          temp = "";
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
  

