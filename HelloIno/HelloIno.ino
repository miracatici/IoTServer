const int pwm = 3 ;	//initializing pin 2 as pwm
const int in_1 = 8 ;
const int in_2 = 7 ;

const int pwm2 = 10     ;	//initializing pin 2 as pwm
const int in_3 = 11 ;
const int in_4 = 12 ;
int value = 0;
char mes = 't';
//For providing logic to L298 IC to choose the direction of the DC motor 

void setup()
{
  pinMode(pwm,OUTPUT) ;  	//we have to set PWM pin as output
  pinMode(in_1,OUTPUT) ; 	//Logic pins are also set as output
  pinMode(in_2,OUTPUT) ;
  pinMode(pwm2,OUTPUT) ;  	//we have to set PWM pin as output
  pinMode(in_3,OUTPUT) ; 	//Logic pins are also set as output
  pinMode(in_4,OUTPUT) ;
  Serial.begin(9600);
  Serial.setTimeout(10);
  delay(5);
}

void loop() {
  if(Serial.available()>0){
    mes=Serial.read();
    value = Serial.parseInt();
    Serial.print(mes);
    Serial.println(value);

    switch (mes) {
    case 'f':
       forward(value);
       break;
    case 'b':
       backward(value);  
       break;
    case 'r':
       right(value);
       break;
     case 'l':
       left(value);
       break;
     case 's':
       brake();  
       break;
  }   
} 

 

 }
 void forward(int hiz){
   analogWrite(pwm,hiz) ;
   analogWrite(pwm2,hiz) ;
   digitalWrite(in_1,HIGH) ;
   digitalWrite(in_2,LOW) ;
   digitalWrite(in_3,HIGH) ;
   digitalWrite(in_4,LOW) ;
 }
 
 void backward(int hiz){
   analogWrite(pwm,hiz) ;
   analogWrite(pwm2,hiz) ;
   digitalWrite(in_1,LOW) ;
  digitalWrite(in_2,HIGH) ;
  digitalWrite(in_3,LOW) ;
  digitalWrite(in_4,HIGH) ;
 }
 
 void right(int hiz){
   analogWrite(pwm,hiz) ;
   analogWrite(pwm2,hiz) ;
   digitalWrite(in_1,HIGH) ;
   digitalWrite(in_2,LOW) ;
   digitalWrite(in_3,LOW) ;
   digitalWrite(in_4,HIGH) ;
   
  
 }
 
 void left(int hiz){
  analogWrite(pwm,hiz) ;
   analogWrite(pwm2,hiz) ;
   digitalWrite(in_1,LOW) ;
   digitalWrite(in_2,HIGH) ;
   digitalWrite(in_3,HIGH) ;
   digitalWrite(in_4,LOW) ;
 }
 
 void brake(){
   analogWrite(pwm,0) ;
   analogWrite(pwm2,0) ;
   digitalWrite(in_1,LOW) ;
   digitalWrite(in_2,LOW) ;
   digitalWrite(in_3,LOW) ;
   digitalWrite(in_4,LOW) ;
 }
 
