const int pwm = 3 ;	//initializing pin 2 as pwm
const int in_1 = 8 ;
const int in_2 = 7 ;

const int pwm2 = 5 ;	//initializing pin 2 as pwm
const int in_3 = 12 ;
const int in_4 = 13 ;
char mes = 't';
//For providing logic to L298 IC to choose the direction of the DC motor 

void setup()
{
  Serial.begin(9600);
  pinMode(pwm,OUTPUT) ;  	//we have to set PWM pin as output
  pinMode(in_1,OUTPUT) ; 	//Logic pins are also set as output
  pinMode(in_2,OUTPUT) ;
  pinMode(pwm2,OUTPUT) ;  	//we have to set PWM pin as output
  pinMode(in_3,OUTPUT) ; 	//Logic pins are also set as output
  pinMode(in_4,OUTPUT) ;
  delay(5);
  analogWrite(pwm,255) ;
  analogWrite(pwm2,255) ;
}

void loop() {
  if(Serial.available()>0){
    while(Serial.available()>0){
      mes=Serial.read();
    }
    switch (mes) {
    case 'f':
       forward();
       break;
    case 'b':
       backward();  
       break;
    case 'r':
       right();
       break;
     case 'l':
       left();
       break;
     case 's':
       brake();  
       break;
  }   
} 

 

 }
 void forward(){
   digitalWrite(in_1,HIGH) ;
   digitalWrite(in_2,LOW) ;
   digitalWrite(in_3,HIGH) ;
   digitalWrite(in_4,LOW) ;
 }
 
 void backward(){
   digitalWrite(in_1,LOW) ;
  digitalWrite(in_2,HIGH) ;
  digitalWrite(in_3,LOW) ;
  digitalWrite(in_4,HIGH) ;
 }
 
 void right(){
   digitalWrite(in_1,LOW) ;
   digitalWrite(in_2,HIGH) ;
   digitalWrite(in_3,HIGH) ;
   digitalWrite(in_4,LOW) ;
 }
 
 void left(){
   digitalWrite(in_1,HIGH) ;
   digitalWrite(in_2,LOW) ;
   digitalWrite(in_3,LOW) ;
   digitalWrite(in_4,HIGH) ;
 }
 
 void brake(){
   digitalWrite(in_1,LOW) ;
   digitalWrite(in_2,LOW) ;
   digitalWrite(in_3,LOW) ;
   digitalWrite(in_4,LOW) ;
 }
 
