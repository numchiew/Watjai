 
 char command;
  String string;
  boolean ledon = false;
  boolean check = false;
  #define led 5
  #include <eHealth.h>
  void setup()
    {
      Serial.begin(115200);
      pinMode(led, OUTPUT);
    }
  void loop()
  {
    if(Serial.available()){
      string = "";
      check = true;
       float ECG = eHealth.getECG();
    Serial.print(ECG, 2);
   Serial.println(); 
    delay(4);
  }
 }
   
  

